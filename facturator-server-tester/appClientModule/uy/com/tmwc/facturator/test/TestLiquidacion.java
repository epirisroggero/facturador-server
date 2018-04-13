package uy.com.tmwc.facturator.test;

import java.math.BigDecimal;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;
import uy.com.tmwc.facturator.rapi.LiquidacionService;
import uy.com.tmwc.facturator.utils.Dates;

public class TestLiquidacion extends TestCase {

	LiquidacionService service;

	public TestLiquidacion() {
		try {
			Properties prop = new Properties();
			prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			prop.put("java.naming.provider.url", "jnp://localhost:1099");
			prop.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");

			Context context = new InitialContext(prop);

			service = (LiquidacionService) context.lookup("facturator-server-ear/LiquidacionServiceImpl/remote");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testLiquidacion() {
		service.generarLiquidacion(Dates.createDate(2010, 1, 1), Dates.createDate(2012, 12, 31), new BigDecimal("1000"));

//		CONTADO: (TODO hacer las cuentas, no se si esta bien)
//		CODIGO VENDEDOR,SERIE NUMERO FACTURA,MONEDA,CUOTAPARTE RENTA COMERCIAL,CUOTAPARTE COSTOS OPERATIVOS
//		1,B 231060,1,487.500000000,
//		1,c 3042,3,1.200000,500.000000
//		6,B 231060,1,162.500000000,

//		COBRANZAS
//		CODIGO VENDEDOR | RECIBO Y FACTURA REFERENCIADA | MONEDA | CUOTAPARTE RENTA COMERCIAL | CUOTAPARTE OPERATIVOS*

		//XXX: solo recibos emitidos!!! (verificar en libra que se emiten)
		//XXX: cuotaparte operativos (cargar entregas)
		//XXX: nota para los articulos de servicios: igualan costo a renta generada, por lo cual no intervienen en las cuentas.
		
		//Creditos:
		// id=123 "<no serie> 3" - tot:1208, subtot:990, costo=214.5, renta=990*0.64-214.5=419.1. Vendedores 2(80%), 6(20%)
		//     id=125 "<no serie> 8"  x $600, porcentaje cancelado:600/1208*100=49.67 (descuento en el recibo: 36%-no se usa)
		// 2, "<no serie> 8", "<no serie> 3", 1, 419.1*0.4967*0.8 = 166,533576
		// 6, "<no serie> 8", "<no serie> 3", 1, 419.1*0.4967*0.2 =  41,633394
		
		// id=127 "<no serie> 4" - tot:366, subtot:300, costo=150, renta=300-150=150. Vendedores: 2(50%), 8(50%)
		//		id=126 "<no serie> 5" x 366, 100% cancelado
		//2, "<no serie> 5", "<no serie>" 4, 1, 150*0.5=75
		//8, "<no serie> 5", "<no serie>" 4, 1, 150*0.5=75
		
		//Redist jefaturas:
		//
		// Jefaturas:
		// A 043.01.362.18, 75% a vendedor 8
		// A 050.I006.ALCAN1, 10% a vendedor 9
		//
		// articulos ventas contado:
		//   doc_id=116, c 3042, Vendedor 1(100%)
		//   043.01.362.18, renta del documento prorrateado por la linea=1.2
		//
		//	 doc_id=119, B 231060, Vendedor 1(75%), 6(25%)
		//   050.I006.ALCAN1, 650_iva, costo: 0, moneda 1
		// 
		// articulos cobranzas de ventas credito:
		//   123:
		//			050.I006.ALCAN1
		//			Vend2
		//			Vend6
		//
		//   127:
		//			050.I006.ALCAN2 NO HAY JEFATURA
		//			Vend2
		//			Vend8
		// XXX mas de una linea para probar el prorrateo
		//
	}	
}
