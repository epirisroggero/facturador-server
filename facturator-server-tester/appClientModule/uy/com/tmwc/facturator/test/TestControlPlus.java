package uy.com.tmwc.facturator.test;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;
import uy.com.tmwc.facturator.rapi.ReportesService;
import uy.com.tmwc.facturator.utils.Dates;

public class TestControlPlus extends TestCase {

	ReportesService service;

	public TestControlPlus() {
		try {
			Properties prop = new Properties();
			prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			prop.put("java.naming.provider.url", "jnp://localhost:1099");
			prop.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");

			Context context = new InitialContext(prop);

			service = (ReportesService) context.lookup("facturator-server-ear/ReportesServiceImpl/remote");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testLiquidacion() {
		service.generarListadoControlLineasVenta(Dates.createDate(2010, 1, 1), Dates.createDate(2013, 12, 31), null, null);
	}	
}
