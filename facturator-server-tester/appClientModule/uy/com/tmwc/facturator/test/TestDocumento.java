package uy.com.tmwc.facturator.test;

import java.math.BigDecimal;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;

import junit.framework.TestCase;

import org.junit.Test;

import uy.com.tmwc.facturator.rapi.DocumentoService;

public class TestDocumento extends TestCase {

	DocumentoService service;

	public TestDocumento() {
		try {
			Properties prop = new Properties();
			prop.put(Context.INITIAL_CONTEXT_FACTORY, "org.jnp.interfaces.NamingContextFactory");
			prop.put("java.naming.provider.url", "jnp://localhost:1099");
			prop.put("java.naming.factory.url.pkgs", "org.jboss.naming:org.jnp.interfaces");

			Context context = new InitialContext(prop);

			service = (DocumentoService) context.lookup("facturator-server-ear/DocumentoServiceImpl/remote");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void testGetStock() {
		BigDecimal stock = service.getStock("050.I006.ALCAN1", "1");
		assert (stock == new BigDecimal(165));
	}

	@Test
	public void testEdit() {
		//edit linea,
		//add linea, 
		//remove linea
		
//		Documento documento = service.findDocumento("123");
		
//		test edit
//		LineaDocumento linea = documento.getLineas().getLineas().get(0);
//		linea.setCantidad(linea.getCantidad() + 1);
		
//		test add
//		LineaDocumento lineaNueva = new LineaDocumento();
//		Articulo articulo = new Articulo();
//		articulo.setCodigo("016.2.I15.BRGLOSS");
//		lineaNueva.setArticulo(articulo);
//		lineaNueva.setCantidad(2);
//		lineaNueva.setConcepto("concept");
//		lineaNueva.setCosto(BigDecimal.ZERO);
//		lineaNueva.setDescuento(BigDecimal.ZERO);
//		lineaNueva.setDocumento(documento);
//		lineaNueva.setPrecio(new BigDecimal("33"));
//		documento.getLineas().getLineas().add(lineaNueva);
		
//		test remove
//		documento.getLineas().getLineas().remove(1);
		
//		service.modificar(documento);

	}

}
