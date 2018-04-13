package uy.com.tmwc.facturator.server.session;

import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.Identity;

@Name("authenticator")
public class Authenticator
{

  @Logger
  private Log log;

  @In
  Identity identity;

  @In
  Credentials credentials;

  public boolean authenticate()
  {
    this.log.info("authenticating {0}", new Object[] { this.credentials.getUsername() });

    if ("admin".equals(this.credentials.getUsername()))
    {
      this.identity.addRole("admin");
      return true;
    }
    return false;
  }
}