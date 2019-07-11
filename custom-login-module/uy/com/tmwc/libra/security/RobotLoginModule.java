//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package uy.com.tmwc.libra.security;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import java.io.IOException;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;
import org.jboss.security.auth.spi.AbstractServerLoginModule;

public class RobotLoginModule extends AbstractServerLoginModule {
    private Principal identity;
    private char[] credential;
    private String libraServer;
    private String empresaId;
    private String empresaPwd;
    private Throwable validateError;

    static {
        System.out.println("carga de Robot");
    }

    public RobotLoginModule() {
    }

    public void initialize(Subject subject, CallbackHandler callbackHandler, Map sharedState, Map options) {
        super.initialize(subject, callbackHandler, sharedState, options);
        System.out.println("en inicializacion");
        this.libraServer = (String)options.get("libraServer");
        this.empresaId = (String)options.get("empresaId");
        this.empresaPwd = (String)options.get("empresaPwd");
        System.out.println("Libra server: " + this.libraServer);
    }

    public boolean login() throws LoginException {
        String username;
        String password;
        if (super.login()) {
            Object username = this.sharedState.get("javax.security.auth.login.name");
            if (username instanceof Principal) {
                this.identity = (Principal)username;
            } else {
                username = username.toString();

                try {
                    this.identity = this.createIdentity(username);
                } catch (Exception var6) {
                    this.log.debug("Failed to create principal", var6);
                    throw new LoginException("Failed to create principal: " + var6.getMessage());
                }
            }

            Object password = this.sharedState.get("javax.security.auth.login.password");
            if (password instanceof char[]) {
                this.credential = (char[])password;
            } else if (password != null) {
                password = password.toString();
                this.credential = password.toCharArray();
            }

            return true;
        } else {
            this.loginOk = false;
            String[] info = this.getUsernameAndPassword();
            username = info[0];
            password = info[1];
            if (username == null && password == null) {
                this.identity = this.unauthenticatedIdentity;
                this.log.trace("Authenticating as unauthenticatedIdentity=" + this.identity);
            }

            if (this.identity == null) {
                try {
                    this.identity = this.createIdentity(username);
                } catch (Exception var7) {
                    this.log.debug("Failed to create principal", var7);
                    throw new LoginException("Failed to create principal: " + var7.getMessage());
                }

                if (!this.libraLogin(username, password)) {
                    Throwable ex = this.getValidateError();
                    FailedLoginException fle = new FailedLoginException("Password Incorrect/Password Required");
                    if (ex != null) {
                        this.log.debug("Bad password for username=" + username, ex);
                        fle.initCause(ex);
                    } else {
                        this.log.debug("Bad password for username=" + username);
                    }

                    throw fle;
                }
            }

            if (this.getUseFirstPass()) {
                this.sharedState.put("javax.security.auth.login.name", username);
                this.sharedState.put("javax.security.auth.login.password", this.credential);
            }

            this.loginOk = true;
            this.log.trace("User '" + this.identity + "' authenticated, loginOk=" + this.loginOk);
            return true;
        }
    }

    private boolean libraLogin(String username, String password) {
        this.log.debug("empieza login en libra");

        try {
            WebClient webClient = new WebClient();
            HtmlPage pageEmpresa = (HtmlPage)webClient.getPage(String.valueOf(this.libraServer) + "/servlet/honline");
            HtmlForm formEmpresa = (HtmlForm)pageEmpresa.getForms().get(0);
            HtmlInput empInput = formEmpresa.getInputByName("_EMPRESA");
            empInput.setValueAttribute(this.empresaId);
            HtmlInput passInput = formEmpresa.getInputByName("_EMPPASSWORD");
            passInput.setValueAttribute(this.empresaPwd);
            HtmlInput ok = formEmpresa.getInputByName("IMAGE16");
            HtmlPage pageUsuario = (HtmlPage)ok.click();
            this.log.debug("login en empresa redirije a: " + pageUsuario.getUrl());
            HtmlForm formUsuario = (HtmlForm)pageUsuario.getForms().get(0);
            HtmlSelect userSelect = formUsuario.getSelectByName("_USUID");
            HtmlOption userOption = userSelect.getOptionByValue(username);
            HtmlPage afterSelectPage = (HtmlPage)userSelect.setSelectedAttribute(userOption, true);
            formUsuario = (HtmlForm)afterSelectPage.getForms().get(0);
            System.out.println("compara afterSelectPage " + (afterSelectPage == pageUsuario));
            HtmlInput usupass = formUsuario.getInputByName("_USUPASSWORD");
            HtmlPage afterPasswordPage = (HtmlPage)usupass.setValueAttribute(password);
            formUsuario = (HtmlForm)afterPasswordPage.getForms().get(0);
            HtmlInput ok2 = formUsuario.getInputByName("IMAGE16");
            HtmlPage pageFinal = (HtmlPage)ok2.click();
            this.log.debug("login de usuario redirije a: " + pageFinal.getUrl());
            System.out.println("login de usuario redirije a: " + pageFinal.getUrl());
            return pageFinal.getUrl().toString().equals(String.valueOf(this.libraServer) + "/servlet/hhomegestion");
        } catch (Throwable var18) {
            this.log.error("Error inesperado ejecutando login en libra", var18);
            throw new RuntimeException(var18);
        }
    }

    protected Principal getIdentity() {
        return this.identity;
    }

    protected String[] getUsernameAndPassword() throws LoginException {
        String[] info = new String[2];
        if (this.callbackHandler == null) {
            throw new LoginException("Error: no CallbackHandler available to collect authentication information");
        } else {
            NameCallback nc = new NameCallback("User name: ", "guest");
            PasswordCallback pc = new PasswordCallback("Password: ", false);
            Callback[] callbacks = new Callback[]{nc, pc};
            String username = null;
            String password = null;

            LoginException le;
            try {
                this.callbackHandler.handle(callbacks);
                username = nc.getName();
                char[] tmpPassword = pc.getPassword();
                if (tmpPassword != null) {
                    this.credential = new char[tmpPassword.length];
                    System.arraycopy(tmpPassword, 0, this.credential, 0, tmpPassword.length);
                    pc.clearPassword();
                    password = new String(this.credential);
                }
            } catch (IOException var9) {
                le = new LoginException("Failed to get username/password");
                le.initCause(var9);
                throw le;
            } catch (UnsupportedCallbackException var10) {
                le = new LoginException("CallbackHandler does not support: " + var10.getCallback());
                le.initCause(var10);
                throw le;
            }

            info[0] = username;
            info[1] = password;
            return info;
        }
    }

    protected Group[] getRoleSets() throws LoginException {
        String[] roles = new String[]{"superuser"};
        Group[] groups = new Group[]{new SimpleGroup("Roles")};
        this.log.info("Getting roles for user=" + this.getUsername());

        for(int r = 0; r < roles.length; ++r) {
            SimplePrincipal role = new SimplePrincipal(roles[r]);
            this.log.info("Found role=" + roles[r]);
            groups[0].addMember(role);
        }

        return groups;
    }

    protected String getUsername() {
        String username = null;
        if (this.getIdentity() != null) {
            username = this.getIdentity().getName();
        }

        return username;
    }

    protected Throwable getValidateError() {
        return this.validateError;
    }
}
