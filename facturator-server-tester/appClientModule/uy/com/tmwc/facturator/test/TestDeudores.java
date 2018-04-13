package uy.com.tmwc.facturator.test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.junit.Test;

import uy.com.tmwc.facturator.deudores.DocumentoDeudor;
import uy.com.tmwc.facturator.rapi.LiquidacionService;
import uy.com.tmwc.facturator.utils.Dates;

public class TestDeudores extends TestCase {

	LiquidacionService liquidacionService;

	public TestDeudores() {
		try {
			Properties prop = new Properties();
			prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			prop.put("java.naming.provider.url", "jnp://localhost:1099");
			prop.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");

			Context context = new InitialContext(prop);

			liquidacionService = (LiquidacionService) context.lookup("facturator-server-ear/LiquidacionServiceImpl/remote");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testDeudores() {

		// Vienen ordenados por fecha, luego por codigo de cliente, luego por
		// tipo comprobante, y finalmente por serie y numero.
		List<DocumentoDeudor> list = liquidacionService.getDocumentosDeudores(Dates.createDate(2012, 5, 27));

		list = filtrarCliente(list, "2708");

		// El primer documento es la venta credito
		DocumentoDeudor d = list.get(0);

		assertTrue (d.getDeudor().getCodigo().equals("2708"));
		assertTrue ("1".equals(d.getDeudor().getCategCliId()));
		assertTrue (d.getDeudor().getGrupo() != null && d.getDeudor().getGrupo().getCodigo().equals("3"));
		assertTrue (d.getFacturado().compareTo(new BigDecimal("1208")) == 0);
		assertTrue (d.getAdeudado().compareTo(new BigDecimal("608")) == 0);

		// El adeudadoNeto es diferente a 608 porque hay descuento esperado. Pero tampoco es el 64% de 608, porque dos cuotas se pagan con
		// retraso (y por lo tanto se penaliza con menor descuento)
		// La segunda cuota tiene saldo 205, y tiene 45 dias de retraso
		// (porcentaje: 15)
		// La tercer cuota tiene saldo 403, tiene 15 dias de retraso
		// (porcentaje: 28)
		// Los porcentajes corresponden a la categoria a la que pertenece el
		// cliente (buen pagador)

		// 174.25 + 290.16
		assertTrue("was " + d.getAdeudadoNeto(), d.getAdeudadoNeto().compareTo(new BigDecimal("464.41")) == 0);
		assertTrue (d.isTieneCuotaVencida());
		assertTrue ("was " + d.getDescuento(), d.getDescuento().compareTo(new BigDecimal("23.62")) == 0);
		
		d = list.get(1);
		// Es una nota de credito, asi que el facturado es negativo
		assertTrue (d.getDeudor().getCodigo().equals("2708"));
		assertTrue (d.getFacturado().compareTo(new BigDecimal("3660")) == 0); //al facturado no le aplicamos signo
		assertTrue (d.getAdeudado().compareTo(new BigDecimal("-3294")) == 0); //esto es deuda, asi que va con signo

		// Con el adeudado neto no hay historia.
		assertTrue (d.getAdeudadoNeto().compareTo(new BigDecimal("-3294")) == 0);
		
		assertTrue (!d.isTieneCuotaVencida());
		assertTrue (d.getDescuento().compareTo(BigDecimal.ZERO) == 0);

		// No hay mas pendientes para este vendedor:
		assertTrue (list.size() == 2);

		//TODO: casos borde, cuando es null la categoria del cliente, o cuando no se encuentran descuentos para la categoria.
		//  (si es null, se buscan descuentos que no tengan especificada la categoria. Cuando no se encuentra la categoria, no se esperan descuentos).
	}

	private List<DocumentoDeudor> filtrarCliente(List<DocumentoDeudor> list, String deudor) {
		ArrayList<DocumentoDeudor> filtered = new ArrayList<DocumentoDeudor>();
		for (DocumentoDeudor documentoDeudor : list) {
			if (documentoDeudor.getDeudor().getCodigo().equals(deudor))
				filtered.add(documentoDeudor);
		}
		return filtered;
	}
}
