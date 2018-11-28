package uy.com.tmwc.facturator.service;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.io.FileUtils;

import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.dto.ReportParameters;
import uy.com.tmwc.facturator.dto.TableReportResult;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.Entrega;
import uy.com.tmwc.facturator.entity.LineaDocumento;
import uy.com.tmwc.facturator.entity.ListaPrecios;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.entity.TipoCambio;
import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.rapi.Cotizaciones;
import uy.com.tmwc.facturator.rapi.ReportesService;
import uy.com.tmwc.facturator.rapi.UsuariosService;
import uy.com.tmwc.facturator.spi.CatalogDAOService;
import uy.com.tmwc.facturator.spi.DocumentoDAOService;
import uy.com.tmwc.facturator.spi.ReportesDAO;
import uy.com.tmwc.facturator.utils.DateUtils;
import uy.com.tmwc.facturator.utils.LogicaCotizacion;
import uy.com.tmwc.facturator.utils.LogicaCotizacion.GetTipoCambio;
import uy.com.tmwc.facturator.utils.Maths;

import com.csvreader.CsvWriter;

@Stateless
@Local({ ReportesService.class })
@Remote({ ReportesService.class }) //for testing
public class ReportesServiceImpl implements ReportesService {
	
	@EJB DocumentoDAOService documentoDAOService;
	
	@EJB ReportesDAO reportesLibra;
	
	@EJB CatalogDAOService catalogDAOService;
	
	@EJB UsuariosService usuariosService;
	
	
	public TableReportResult getReporteStockPrecios(ReportParameters parameters) {
		
		Locale.setDefault(new Locale("ES"));
		
		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		boolean esSupervisor = usuarioLogin.isSupervisor();

		HashMap<String, Object> table = parameters.getParameters();
		
		Object cotizaciones = table.get("cotizaciones"); 
		if (!(cotizaciones instanceof Cotizaciones)) {
			throw new RuntimeException("El parametro 'cotizaciones' (" + cotizaciones + ") no es valido");
		}
		Object moneda = table.get("moneda"); 
		if (!(moneda instanceof String)) {
			throw new RuntimeException("El parametro 'moneda' (" + moneda + ") no es valido");
		}
		Object filtro = table.get("filtro"); 
		if (filtro != null) {
			if (!(filtro instanceof String)) {
				throw new RuntimeException("El parametro 'filtro' (" + filtro + ") no es valido");
			}
		}
		Object desde = table.get("desde"); 
		if (desde != null) {
			if (!(desde instanceof String)) {
				throw new RuntimeException("El parametro 'desde' (" + desde + ") no es valido");
			}
		}
		Object hasta = table.get("hasta");
		if (hasta != null) {
			if (!(hasta instanceof String)) {
				throw new RuntimeException("El parametro 'hasta' (" + hasta + ") no es valido");
			}
		}
		
		Object proveedores = table.get("proveedores");
		if (proveedores != null) {
			if (!(proveedores instanceof String)) {
				throw new RuntimeException("El parametro 'proveedores' (" + proveedores + ") no es valido");
			} 			
		}

		Object familias = table.get("familias");
		if (familias != null) {
			if (!(familias instanceof String)) {
				throw new RuntimeException("El parametro 'familias' (" + familias + ") no es valido");
			}			
		} 
		
		Object filtrarCeros = table.get("filtrarCeros");
		if (filtrarCeros != null) {
			if (!(filtrarCeros instanceof Boolean)) {
				throw new RuntimeException("El parametro 'filtrarCeros' (" + filtrarCeros + ") no es valido");
			}
		}
		
		Object filtrarNegativos = table.get("filtrarNegativos");
		if (!(filtrarNegativos instanceof Boolean)) {
			throw new RuntimeException("El parametro 'filtrarNegativos' (" + filtrarNegativos + ") no es valido");
		}
		
		Object mostrarCosto = table.get("mostrarCosto");
		if (mostrarCosto != null) {
			if (!(mostrarCosto instanceof Boolean)) {
				throw new RuntimeException("El parametro 'mostrarCostos' (" + mostrarCosto + ") no es valido");
			}
		}

		Object mostrarPrecioFab = table.get("mostrarPrecioFabrica");
		if (mostrarPrecioFab != null) {
			if (!(mostrarPrecioFab instanceof Boolean)) {
				throw new RuntimeException("El parametro 'mostrarPrecioFabrica' (" + mostrarPrecioFab + ") no es valido");
			}
		}

		List<ListaPrecios> listasPrecios = reportesLibra.getListasPrecios();
		String[] listas; 
		
		Object precios = table.get("listasPrecio");
		int i;
		if (precios != null) {
			if (!(precios instanceof String)) {
				throw new RuntimeException("El parametro 'precios' (" + precios + ") no es valido");
			} else {
				listas = String.valueOf(precios).split(",");
			}
		} else {
			listas = new String[listasPrecios.size()];
			i = 0;
			for (ListaPrecios lp : listasPrecios) {
				listas[i] = lp.getCodigo();
				i++;
			}
		}
		
		List<Object[]> dbData = reportesLibra.stockPrecio(listas, (String)filtro, (String)proveedores, (String)familias, (String)desde, (String)hasta, Boolean.TRUE.equals(filtrarCeros), Boolean.TRUE.equals(filtrarNegativos));

		List<TipoCambio> tipoCambios = reportesLibra.getTipoCambios();
		GetTipoCambio getTipoCambioFunction = new LogicaCotizacion.InMemoryGetTipoCambio(tipoCambios);
		
		Boolean mostrarPrecios = esSupervisor || Usuario.USUARIO_SUPERVISOR.equals(permisoId) || Usuario.USUARIO_ADMINISTRADOR.equals(permisoId) || Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId);
		
		if (Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId)) {
			mostrarCosto = false;
			mostrarPrecioFab = false;
		}			
		
		Object[][] rowsWithData = new Object[dbData.size()][2 + listas.length + 2];
		i = 0;
		for (Object[] dbDatum : dbData) {
			Object[] processed = new Object[3 + listas.length + (mostrarPrecios ? ((Boolean)mostrarCosto ? 1 : 0) + ((Boolean)mostrarPrecioFab ? 1 : 0) : 0)];
			int col = 0;
			processed[col++] = dbDatum[4];
			processed[col++] = new CodigoNombre((String)dbDatum[0], (String)dbDatum[1]);
			
			int attrIndex = 5;
			if (mostrarPrecios && Boolean.TRUE.equals(mostrarCosto)) {
				BigDecimal precio = (BigDecimal) dbDatum[attrIndex++];
				String mprecio = (String)dbDatum[attrIndex++];
				Date fprecio = (Date)dbDatum[attrIndex++];
				processed[col++] = new LogicaCotizacion(Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_DOLAR).getMontoMayorCotizacion(fprecio, mprecio, precio, (String)moneda, (Cotizaciones)cotizaciones, Boolean.FALSE, getTipoCambioFunction);
			}	
			
			attrIndex = 8;
			for (int j = 0; j < listas.length; j++) {
				BigDecimal precio = (BigDecimal) dbDatum[attrIndex++];
				String mprecio = (String)dbDatum[attrIndex++];
				Date fprecio = (Date)dbDatum[attrIndex++];
				processed[col++] = new LogicaCotizacion(Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_DOLAR).getMontoMayorCotizacion(fprecio, mprecio, precio, (String)moneda, (Cotizaciones)cotizaciones, Boolean.FALSE, getTipoCambioFunction);
			}
			
			if (mostrarPrecios && Boolean.TRUE.equals(mostrarPrecioFab)) {
				BigDecimal precio = (BigDecimal) dbDatum[attrIndex++];
				String mprecio = (String)dbDatum[attrIndex++];
				Date fprecio = (Date)dbDatum[attrIndex++];
				processed[col++] = new LogicaCotizacion(Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_DOLAR).getMontoMayorCotizacion(fprecio, mprecio, precio, (String)moneda, (Cotizaciones)cotizaciones, Boolean.FALSE, getTipoCambioFunction);
			}
			
			rowsWithData[i] = processed;
			i++;
		}
		
		TableReportResult result = new TableReportResult();
		
		int count = listas.length + 2;
		if (mostrarPrecios) {
			if (Boolean.TRUE.equals(mostrarCosto)) {
				count++;
			}
			if (Boolean.TRUE.equals(mostrarPrecioFab)) {
				count++;
			}
		}

		String[] columns = new String[count];
		i=0;
		columns[i++] = "STOCK";
		columns[i++] = "DSCRIPCION";

		if (mostrarPrecios && Boolean.TRUE.equals(mostrarCosto) ) {
			columns[i++] = "COSTO";
		}		
		for (int j = 0; j < listas.length; j++) {
			ListaPrecios lista = getListaPrecios(listasPrecios, listas[j]);
			columns[i++] = lista != null ? lista.getNombre() : listas[j];
		}		
		if (mostrarPrecios && Boolean.TRUE.equals(mostrarPrecioFab)) {
			columns[i++] = "PRECIO FABRICA";
		}
		
		result.setColumns(columns);
		result.setRowsWithData(rowsWithData);

		return result; 
	}
	
	public List<Moneda> getMonedasReporte() {
		List<Moneda> monedas = catalogDAOService.getCatalog(Moneda.class);
		ArrayList<Moneda> monedasReporte = new ArrayList<Moneda>();
		for (Moneda moneda : monedas) {
			if (!moneda.isAster()) {
				monedasReporte.add(moneda);
			}
		}
		return monedasReporte;
	}

	private ListaPrecios getListaPrecios(List<ListaPrecios> listasPrecios, String codigo) {
		for (ListaPrecios listaPrecios : listasPrecios) {
			if (listaPrecios.getCodigo().equals(codigo)) {
				return listaPrecios;
			}
		}
		return null;
	}

	public byte[] generarListadoControlLineasVenta(Date fechaDesde, Date fechaHasta, BigDecimal rentaMinima, BigDecimal rentaMaxima) {
		List<Documento> docs1 = documentoDAOService.getDocumentos(fechaDesde, fechaHasta, Moneda.CODIGO_MONEDA_PESOS);
		List<Documento> docs2 = documentoDAOService.getDocumentos(fechaDesde, fechaHasta, Moneda.CODIGO_MONEDA_PESOS_ASTER);
		List<Documento> docs3 = documentoDAOService.getDocumentos(fechaDesde, fechaHasta, Moneda.CODIGO_MONEDA_DOLAR);
		List<Documento> docs4 = documentoDAOService.getDocumentos(fechaDesde, fechaHasta, Moneda.CODIGO_MONEDA_DOLAR_ASTER);
		
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		File tmpFilePath = new File(baseDir, "listado-control-plus.csv");
		CsvWriter w = new CsvWriter(tmpFilePath.getPath());
		try {
			w.write("TIPO COMPROBANTE");
			w.write("SERIE NUMERO");
			w.write("FECHA");
			w.write("CLIENTE");
			w.write("MONEDA");
			w.write("IMPORTE");
			w.write("ARTICULO");
			w.write("CANTIDAD");
			w.write("DESCRIPCION");
			w.write("PRECIO");
			w.write("NETO");
			w.write("COSTO");
			w.write("UTILIDAD(%)");
			w.endRecord();
			
			w = generarListadoControlLineasVentaW(docs1, w, rentaMinima, rentaMaxima);
			w = generarListadoControlLineasVentaW(docs2, w, rentaMinima, rentaMaxima);
			w = generarListadoControlLineasVentaW(docs3, w, rentaMinima, rentaMaxima);
			w = generarListadoControlLineasVentaW(docs4, w, rentaMinima, rentaMaxima);
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			w.close();
		}
		
		return getFileByteArray(tmpFilePath);
	}
	
	private CsvWriter generarListadoControlLineasVentaW(List<Documento> docs, CsvWriter w, BigDecimal rentaMinima, BigDecimal rentaMaxima) throws IOException {
		NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("ES"));
		NumberFormat formatterPercent = NumberFormat.getNumberInstance(new Locale("ES"));
		formatterPercent.setMinimumFractionDigits(1);
		formatterPercent.setMaximumFractionDigits(1);

		for (Documento doc: docs) {
			for (LineaDocumento linea : doc.getLineas().getLineas()) {
				BigDecimal renta = linea.getPorcentajeUtilidad(); 
				if (renta == null) {
					renta = BigDecimal.ZERO;
				}	
				if (rentaMinima == null) {
					rentaMinima = new BigDecimal(Integer.MAX_VALUE);
				}
				if (rentaMaxima == null) {
					rentaMaxima = new BigDecimal(Integer.MIN_VALUE);
				}
				if (renta.compareTo(rentaMinima) < 0 || renta.compareTo(rentaMaxima) > 0) {
					w.write(doc.getComprobante().getNombre());
					w.write(doc.getSerieNumero().toString());
					w.write(doc.getFecha());
					w.write(doc.getCliente() != null ? doc.getCliente().getNombre() : "");
					w.write(doc.getMoneda().getNombre().toUpperCase());
					w.write(formatter.format(doc.getTotal() != null ? doc.getTotal() : 0));
					w.write(linea.getArticulo() != null ? linea.getArticulo().getCodigo() + " - " + linea.getArticulo().getNombre() : "");
					w.write(formatter.format(linea.getCantidad() != null ? linea.getCantidad() : 0));
					w.write(linea.getConcepto());
					
					if ((linea.getArticulo() == null) && !("".equals(linea.getConceptoIdLin()))) { // No deberían haber documentos de gasto en los comprobantes de venta
						w.write(formatter.format(0));
						w.write(formatter.format(0));
						w.write(formatter.format(0));
						w.write(formatterPercent.format(0) + "%");
					} else {
						w.write(formatter.format(linea.getSubTotal() != null ? linea.getSubTotal() : 0));
						w.write(formatter.format(linea.getNeto() != null ? linea.getNeto() : 0));
						w.write(formatter.format(linea.getCosto() != null ? linea.getCosto() : 0));
						
						BigDecimal utilidad = linea.getPorcentajeUtilidad() != null ? linea.getPorcentajeUtilidad().setScale(1, RoundingMode.HALF_EVEN) : BigDecimal.ZERO;
						w.write(formatterPercent.format(utilidad) + "%");
					}
					
					w.endRecord();
				}
					
			}
		}
		
		for (int k = 0; k < 2; k++) {
			for (int i = 0; i < 13; i++) {
				w.write("");
			}
			w.endRecord();
		}

		return w;
		
	}
	
	
	private byte[] getFileByteArray(File file) {
		try {
		    FileInputStream fis = new FileInputStream(file);
	        ByteArrayOutputStream bos = new ByteArrayOutputStream();
	        byte[] buf = new byte[1024];
	        try {
	            for (int readNum; (readNum = fis.read(buf)) != -1;) {
	                bos.write(buf, 0, readNum); //no doubt here is 0
	                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
	                System.out.println("read " + readNum + " bytes,");
	            }
	        } catch (IOException ex) {
	        }	    
	        return  bos.toByteArray();
			
		} catch (Exception e){
			throw new RuntimeException(e);
		}

	}
	
	public byte[] generarLiquidacionVendedores(Date desde, Date hasta, String[] compsIncluidos, 
			String[] compsExcluidos) throws IOException {
		
		File file = new File(System.getProperty("jboss.home.url").substring(6)
				+ "/server/default/deploy/facturator-server-ear.ear/facturator-server.war/liquidacion/output");
		FileUtils.deleteDirectory(file);
		file.mkdir();
		
		List<ParticipacionVendedor> participaciones = documentoDAOService.getParticipaciones(desde, hasta, compsIncluidos, compsExcluidos);
		
		List<TipoCambio> tipoCambios = reportesLibra.getUltimosTipoCambios();
		GetTipoCambio getTipoCambioFunction = new LogicaCotizacion.InMemoryGetTipoCambio(tipoCambios);
		LogicaCotizacion lc = new LogicaCotizacion(Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_DOLAR, getTipoCambioFunction);
		
		HashMap<Integer, BigDecimal> importeNetoVendido = new HashMap<Integer, BigDecimal>();
		HashMap<Integer, BigDecimal> rentaBruta = new HashMap<Integer, BigDecimal>();
		HashMap<Integer, BigDecimal> creditoAlVendedor = new HashMap<Integer, BigDecimal>();
		HashMap<Integer, BigDecimal> participacion = new HashMap<Integer, BigDecimal>();
		
		List<TipoCambio> tipoCambios2 = reportesLibra.getTipoCambios();
		GetTipoCambio getTipoCambioFunction2 = new LogicaCotizacion.InMemoryGetTipoCambio(tipoCambios2);

		List<Usuario> usuarios = catalogDAOService.getCatalog(Usuario.class);
		ArrayList<String> vendedoresDist = new ArrayList<String>();
		ArrayList<String> vendedoresJunior = new ArrayList<String>();
		ArrayList<String> vendedoresSenior = new ArrayList<String>();
		for (Usuario user : usuarios) {
			//System.out.println("Usuario :: " + user.getCodigo() + " >> Vend: " + user.getVenId());
			if (user.getVenId() != null && user.getVenId().trim().length() > 0) {
				if (user.getUsuarioModoDistribuidor()) {
					vendedoresDist.add(user.getVenId());
					//System.out.println("Usuario :: " + user.getCodigo() +  " > Vendedor distribuidor :: " + user.getVenId());
				} else if (user.getUsuarioModoMostrador()) {
					vendedoresJunior.add(user.getVenId());
					//System.out.println("Usuario :: " + user.getCodigo() +  " > Vendedor junior :: " + user.getVenId());
				} else {
					vendedoresSenior.add(user.getVenId());
					//System.out.println("Usuario :: " + user.getCodigo() +  " > Vendedor senior :: " + user.getVenId());
				}
			}
		}
		
		for (ParticipacionVendedor participacionVendedor : participaciones) {
			int codigoVen;
			try {
				codigoVen = Integer.parseInt(participacionVendedor.getVendedor().getCodigo());
			} catch (NumberFormatException e) {
				System.err.println("Vendedor tiene un codigo no numerico: " + participacionVendedor.getVendedor().getCodigo());
				e.printStackTrace();
				continue;
			}
			
			Documento doc = participacionVendedor.getDocumento();

			String monedaDoc = Moneda.getCodigoMonedaNoAster(participacionVendedor.getDocumento().getMoneda().getCodigo());
			BigDecimal coti = getTipoCambioFunction2.getTipoCambio(Moneda.CODIGO_MONEDA_DOLAR, doc.getFecha());
			doc.setDocTCF(coti);
			doc.setDocTCC(coti);
			
			if (monedaDoc.equals(Moneda.CODIGO_MONEDA_DOLAR) || monedaDoc.equals(Moneda.CODIGO_MONEDA_DOLAR_ASTER)) {
				coti = BigDecimal.ONE;
			}
			if (coti == null || coti.compareTo(BigDecimal.ZERO) == 0) {
				coti = BigDecimal.ONE;
			}

			BigDecimal ventaNeta = doc.getVentaNeta() != null ? doc.getVentaNeta() : BigDecimal.ZERO;			
			if (importeNetoVendido.containsKey(codigoVen)) {
				ventaNeta = importeNetoVendido.get(codigoVen).add(ventaNeta.divide(coti, 2, RoundingMode.HALF_UP));
			} else {
				ventaNeta = ventaNeta.divide(coti, 2, RoundingMode.HALF_UP);
			}
			importeNetoVendido.put(codigoVen, ventaNeta);
			
			BigDecimal renta = getRenta(doc, lc);
			if (vendedoresSenior.contains(codigoVen)) {
				BigDecimal cuotaparte = participacionVendedor.getCuotaparte(renta) != null ? participacionVendedor.getCuotaparte(renta) : BigDecimal.ZERO;
				if (creditoAlVendedor.containsKey(codigoVen)) {
					cuotaparte = creditoAlVendedor.get(codigoVen).add(cuotaparte.divide(coti, 2, RoundingMode.HALF_UP));
				} else {
					cuotaparte = cuotaparte.divide(coti, 2, RoundingMode.HALF_UP);
				}
				creditoAlVendedor.put(codigoVen, cuotaparte);
			
				BigDecimal aux = renta;
				if (rentaBruta.containsKey(codigoVen)) {
					aux = rentaBruta.get(codigoVen).add(aux.divide(coti, 2, RoundingMode.HALF_UP));
				} else {
					aux = aux.divide(coti, 2, RoundingMode.HALF_UP);
				}
				rentaBruta.put(codigoVen, aux);
				
			} else if (vendedoresDist.contains(codigoVen)) {
				BigDecimal porcentajeComision = participacionVendedor.getVendedor().getPorcentajeComision(doc.getPreciosVenta().getCodigo());
				if (porcentajeComision == null) {
					porcentajeComision = BigDecimal.ZERO;
				}				
				BigDecimal part = porcentajeComision.multiply(participacionVendedor.getCuotaparte(doc.getVentaNeta() == null ? BigDecimal.ZERO : doc.getVentaNeta())).divide(Maths.ONE_HUNDRED);
				if (part == null) {
					part = BigDecimal.ZERO;
				}
				if (participacion.containsKey(codigoVen)) {
					part = participacion.get(codigoVen).add(part.divide(coti, 2, RoundingMode.HALF_UP));
				} else {
					part = part.divide(coti, 2, RoundingMode.HALF_UP);
				}
				participacion.put(codigoVen, part);
			} 
			
		}
	
	
		HashMap<Integer, LiquidacionVendedor> liqByCodigo = new HashMap<Integer, LiquidacionVendedor>();
		
		File zipFile = null;
		try {
			for (ParticipacionVendedor participacionVendedor : participaciones) {
				String codigoVendedorStr = participacionVendedor.getVendedor().getCodigo();
				int codigoVendedor;
				try {
					codigoVendedor = Integer.parseInt(codigoVendedorStr);
				} catch (NumberFormatException e) {
					System.err.println("Vendedor tiene un codigo no numerico: " + codigoVendedorStr);
					e.printStackTrace();
					continue;
				}
				LiquidacionVendedor lv = ensureLiquidacion(codigoVendedorStr, liqByCodigo, lc, vendedoresDist, vendedoresJunior, vendedoresSenior);
				lv.addParticipacion(participacionVendedor);
			}

			
			Collection<LiquidacionVendedor> liqs = liqByCodigo.values();
			for (LiquidacionVendedor liquidacionVendedor : liqs) {
				int codigo = liquidacionVendedor.getCodigoVendedor();
				liquidacionVendedor.totalizar(importeNetoVendido.get(codigo), rentaBruta.get(codigo), creditoAlVendedor.get(codigo), participacion.get(codigo));
			}
			
		} finally {
			Collection<LiquidacionVendedor> liqs = liqByCodigo.values();
			for (LiquidacionVendedor liquidacionVendedor : liqs) {
				liquidacionVendedor.finalizar();
			}
			
			zipFile = File.createTempFile(file.getName(), ".zip");
			zipDirectory(file, zipFile);

		
		}
		return getFileByteArray(zipFile);
	}
	
	public static final void zipDirectory(File directory, File zip) throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
		try {
			zip(directory, directory, zos);
		} finally {
			finallyClose(zos);
		}
	}
	
	private static final void zip(File directory, File base, ZipOutputStream zos) throws IOException {
		File[] files = directory.listFiles();
		byte[] buffer = new byte[8192];
		int read = 0;
		for (int i = 0, n = files.length; i < n; i++) {
			if (files[i].isDirectory()) {
				zip(files[i], base, zos);
			} else {
				FileInputStream in = new FileInputStream(files[i]);
				try {
					ZipEntry entry = new ZipEntry(files[i].getPath().substring(base.getPath().length() + 1));
					zos.putNextEntry(entry);
					while (-1 != (read = in.read(buffer))) {
						zos.write(buffer, 0, read);
					}
				} finally {
					finallyClose(in);
				}
			}
		}
	}
	
	private static void finallyClose(Closeable zos) {
		try {
			zos.close();
		} catch (Throwable t) {
			System.out.println("catched finally exception: " + t);
			t.printStackTrace();
		}
	}
	
	private LiquidacionVendedor ensureLiquidacion(String codigoVendedorStr, HashMap<Integer, LiquidacionVendedor> liqByCodigo, LogicaCotizacion cotizacion,
			ArrayList<String> dist, ArrayList<String> junior, ArrayList<String> senior) throws IOException {
		
		int codigoVendedor = 0;
		try {
			codigoVendedor = Integer.parseInt(codigoVendedorStr);
		} catch (NumberFormatException e) {
			System.err.println("Vendedor tiene un codigo no numerico: " + codigoVendedorStr);
			e.printStackTrace();
		}
		
		LiquidacionVendedor lv = liqByCodigo.get(codigoVendedor);
		if (lv != null) {
			return lv;
		}
		
		File file = new File(System.getProperty("jboss.home.url").substring(6)
				+ "/server/default/deploy/facturator-server-ear.ear/facturator-server.war/liquidacion/output/liquidacion-" + codigoVendedor
				+ ".csv"); //txt para que abra el asistente de importacion de excel
		
		CsvWriter writer = new CsvWriter(file.getAbsolutePath());
		
		if (dist.contains(codigoVendedorStr)) {
			lv = new LiquidacionDistribuidor(codigoVendedor, writer);
		} else if (junior.contains(codigoVendedorStr)) {
			lv = new LiquidacionJunior(codigoVendedor, writer);
		} else {
			lv = new LiquidacionSenior(codigoVendedor, writer, cotizacion);
		} /*else {
			System.err.println("Codigo de vendedor no puede asignarse a un tipo: " + codigoVendedor);
			return null;
		}*/
		lv.inicializar();
		liqByCodigo.put(codigoVendedor, lv);
		return lv;
	}

	private static abstract class LiquidacionVendedor {
		private int codigoVendedor;
		protected CsvWriter writer;
		
		private NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("ES"));

		protected LiquidacionVendedor(int codigoVendedor, CsvWriter writer) {
			super();
			this.codigoVendedor = codigoVendedor;
			this.writer = writer;
			
			formatter.setMinimumFractionDigits(2);
			formatter.setMaximumFractionDigits(2);
			
		}
		
		public int getCodigoVendedor() {
			return codigoVendedor;
		}

		public void inicializar() throws IOException {
			inicializarComun();
			subInicializar();
			writer.endRecord();
		}
		
		public void finalizar() {
			writer.close();
		}
		
		protected void totalizar(BigDecimal param1, BigDecimal param2, BigDecimal param3, BigDecimal param4) throws IOException {
			writer.write(""); writer.write("");
			writer.write(""); writer.write("");
			writer.write(""); writer.write("");
			writer.write("Total en Dolares");
			writer.write(formatter.format(param1 != null ? param1 : BigDecimal.ZERO));

			totalizando(param2, param3, param4);
			writer.endRecord();
		}
				
		protected abstract void totalizando(BigDecimal param1, BigDecimal param2, BigDecimal param3) throws IOException;

		
		protected abstract void subInicializar() throws IOException;

		public void addParticipacion(ParticipacionVendedor pv) throws IOException {
			Documento documento = pv.getDocumento();
			writer.write(documento.getFecha());
			writer.write(documento.getComprobante().getNombre());
			writer.write((documento.getSerie() != null ? documento.getSerie() : "") + documento.getNumero());
			writer.write(documento.getCliente().getCodigo());
			writer.write(documento.getCliente().getNombre());
			writer.write(documento.getMoneda().getSimbolo());
			writer.write(formatter.format(documento.getDocTCF() != null ? documento.getDocTCF() : BigDecimal.ZERO));
			if (!documento.getComprobante().getCodigo().equals("98")) {
				writer.write(formatter.format(documento.getVentaNeta() != null ? documento.getVentaNeta() : BigDecimal.ZERO));
			} else {
				writer.write(formatter.format(documento.getTotal() != null ? documento.getTotal().negate() : BigDecimal.ZERO));
			}
			
			subAddParticipacion(pv);
			writer.endRecord();
		}
		
		
		protected abstract void subAddParticipacion(ParticipacionVendedor pv) throws IOException;

		private void inicializarComun() throws IOException {
			writer.write("Fecha");
			writer.write("Tipo comprobante");
			writer.write("Nro. comprobante");
			writer.write("Cliente nro.");
			writer.write("Cliente nom.");
			writer.write("Moneda");
			writer.write("Cotización");
			writer.write("Importe neto vendido");
		}

	}
	
	public static BigDecimal getRenta(Documento documento, LogicaCotizacion cotizacion) {
		if (!documento.getComprobante().getCodigo().equals("98")) {
			BigDecimal rentaComercial = documento.getRentaNetaComercial();
			Entrega entrega = documento.getEntrega();
			if (entrega != null) {
				String monedaNormalizadaDoc = Moneda.getCodigoMonedaNoAster(documento.getMoneda().getCodigo());
				BigDecimal costoMonedaDoc = cotizacion.cambiar(entrega.getCosto(), entrega.getMonedaCosto(),
						monedaNormalizadaDoc, DateUtils.newDate(2099, 1, 1));
				if (costoMonedaDoc == null) {
					System.err.println("No se pudo realizar cambio: " + entrega.getMonedaCosto() + "->" + monedaNormalizadaDoc + ", monto: " + entrega.getCosto());
					return null;
				}
				rentaComercial.subtract(costoMonedaDoc);
			}
			return rentaComercial;
		} else {
			return documento.getTotal().negate();
		}
		
	}

	
	private static class LiquidacionJunior extends LiquidacionVendedor {

		private NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("ES"));

		protected LiquidacionJunior(int codigoVendedor, CsvWriter writer) {
			super(codigoVendedor, writer);
			
			formatter.setMinimumFractionDigits(2);
			formatter.setMaximumFractionDigits(2);
		}

		@Override
		protected void subInicializar() throws IOException {
			writer.write("Vendedor");
			writer.write("Participación");
			writer.write("Precios venta");
			writer.write("% Comisión");
			writer.write("Comisión a pagar al vendedor");
		}
		
		@Override
		protected void subAddParticipacion(ParticipacionVendedor pv) throws IOException {
			writer.write(pv.getVendedor().getNombre());
			writer.write(formatter.format(pv.getPorcentaje() != null ?  pv.getPorcentaje().intValue() : 0));
			writer.write(pv.getDocumento().getPreciosVenta().getCodigo());
			BigDecimal porcentajeComision = pv.getVendedor().getPorcentajeComision(pv.getDocumento().getPreciosVenta().getCodigo());
			writer.write(formatter.format(porcentajeComision != null ? porcentajeComision : BigDecimal.ZERO));
			writer.write(porcentajeComision == null ? null : formatter.format(porcentajeComision.multiply(pv.getCuotaparte(pv.getDocumento().getVentaNeta())).divide(Maths.ONE_HUNDRED)));
		}
		
		@Override
		protected void totalizando(BigDecimal param1, BigDecimal param2, BigDecimal param3) throws IOException {
			writer.write(""); writer.write("");
			writer.write(""); writer.write("");
			writer.write(formatter.format(param3 != null ? param3 : BigDecimal.ZERO));
		}

	}
	
	private static class LiquidacionDistribuidor extends LiquidacionVendedor {

		private NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("ES"));

		protected LiquidacionDistribuidor(int codigoVendedor, CsvWriter writer) {
			super(codigoVendedor, writer);

			formatter.setMinimumFractionDigits(2);
			formatter.setMaximumFractionDigits(2);
		}

		@Override
		protected void subInicializar() throws IOException {
			writer.write("Diferencia");
			writer.write("Vendedor");
			writer.write("Participación");
			writer.write("Crédito al distribuidor");
		}
		
		@Override
		protected void totalizando(BigDecimal param1, BigDecimal param2, BigDecimal param3) throws IOException {
			writer.write(formatter.format(param1 != null ? param1 : BigDecimal.ZERO));
			writer.write(""); 
			writer.write("");
			writer.write(formatter.format(param2 != null ? param2 : BigDecimal.ZERO));
		}

     
		@Override
		protected void subAddParticipacion(ParticipacionVendedor pv) throws IOException {
			Documento documento = pv.getDocumento();
			BigDecimal rentaDistribuidor = documento.getRentaDistribuidor();
			writer.write(formatter.format(rentaDistribuidor != null ? rentaDistribuidor : BigDecimal.ZERO));
			writer.write(pv.getVendedor().getNombre());
			writer.write(formatter.format(pv.getPorcentaje() != null ? pv.getPorcentaje().doubleValue() : 0));
			writer.write(formatter.format(rentaDistribuidor != null && pv.getCuotaparte(rentaDistribuidor) != null ? pv.getCuotaparte(rentaDistribuidor) : BigDecimal.ZERO));
		}
		
	}
	
	private static class LiquidacionSenior extends LiquidacionVendedor {

		private LogicaCotizacion cotizacion;
		
		private NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("ES"));
		

		protected LiquidacionSenior(int codigoVendedor, CsvWriter writer, LogicaCotizacion cotizacion) {
			super(codigoVendedor, writer);
			this.cotizacion = cotizacion;
			formatter.setMinimumFractionDigits(2);
			formatter.setMaximumFractionDigits(2);
		}

		@Override
		protected void subInicializar() throws IOException {
			writer.write("Renta bruta");
			writer.write("Vendedor");
			writer.write("Participación");
			writer.write("Crédito al vendedor");
		}

		@Override
		protected void totalizando(BigDecimal param1, BigDecimal param2, BigDecimal param3) throws IOException {
			writer.write(formatter.format(param1 != null ? param1 : BigDecimal.ZERO));
			writer.write(""); writer.write("");
			writer.write(formatter.format(param2 != null ? param2 : BigDecimal.ZERO));

		}
		
		@Override
		protected void subAddParticipacion(ParticipacionVendedor pv) throws IOException {
			Documento documento = pv.getDocumento();
			BigDecimal renta = getRenta(documento, cotizacion);
			
			writer.write(formatter.format(renta != null ? renta : BigDecimal.ZERO));
			writer.write(pv.getVendedor().getNombre());
			writer.write(formatter.format(pv.getPorcentaje() != null ? pv.getPorcentaje() : BigDecimal.ZERO));
			writer.write(formatter.format(renta != null && pv.getCuotaparte(renta) != null ? pv.getCuotaparte(renta) : BigDecimal.ZERO));
		}
		
	}


}
