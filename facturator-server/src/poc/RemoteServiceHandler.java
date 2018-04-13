package poc;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.mail.MessagingException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.tmwc.facturator.deudores.DocumentoDeudor;
import uy.com.tmwc.facturator.dto.AgendaTareaDTO;
import uy.com.tmwc.facturator.dto.AntecedentesArticulo;
import uy.com.tmwc.facturator.dto.ArticuloDTO;
import uy.com.tmwc.facturator.dto.ArticuloQuery;
import uy.com.tmwc.facturator.dto.ClienteDTO;
import uy.com.tmwc.facturator.dto.ClienteQuery;
import uy.com.tmwc.facturator.dto.ContactoDTO;
import uy.com.tmwc.facturator.dto.ContactoQuery;
import uy.com.tmwc.facturator.dto.DocumentoDTO;
import uy.com.tmwc.facturator.dto.DocumentoQuery;
import uy.com.tmwc.facturator.dto.EFacturaResult;
import uy.com.tmwc.facturator.dto.ProveedorDTO;
import uy.com.tmwc.facturator.dto.ProveedoresQuery;
import uy.com.tmwc.facturator.dto.ReportParameters;
import uy.com.tmwc.facturator.dto.TableReportResult;
import uy.com.tmwc.facturator.entity.AgendaTarea;
import uy.com.tmwc.facturator.entity.Articulo;
import uy.com.tmwc.facturator.entity.ArticuloCompraVentaCosto;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.entity.ArticuloPrecioFabricaCosto;
import uy.com.tmwc.facturator.entity.Auditoria;
import uy.com.tmwc.facturator.entity.Cliente;
import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.entity.Contacto;
import uy.com.tmwc.facturator.entity.CotizacionesMonedas;
import uy.com.tmwc.facturator.entity.DescuentoPrometidoComprobante;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.Entrega;
import uy.com.tmwc.facturator.entity.Fanfold;
import uy.com.tmwc.facturator.entity.LineaDocumento;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.ParametrosAdministracion;
import uy.com.tmwc.facturator.entity.Persona;
import uy.com.tmwc.facturator.entity.PreciosVenta;
import uy.com.tmwc.facturator.entity.Proveedor;
import uy.com.tmwc.facturator.entity.SerieNumero;
import uy.com.tmwc.facturator.entity.StockActual;
import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.entity.ValidationException;
import uy.com.tmwc.facturator.entity.VinculoDocumentos;
import uy.com.tmwc.facturator.expediciones.AgendaTareaQuery;
import uy.com.tmwc.facturator.javamail.EmailSenderService;
import uy.com.tmwc.facturator.rapi.AgendaTareaService;
import uy.com.tmwc.facturator.rapi.ArticulosService;
import uy.com.tmwc.facturator.rapi.AuditoriaService;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.rapi.ClientesService;
import uy.com.tmwc.facturator.rapi.Cotizaciones;
import uy.com.tmwc.facturator.rapi.DescuentosPrometidosService;
import uy.com.tmwc.facturator.rapi.DocumentoService;
import uy.com.tmwc.facturator.rapi.EFacturaService;
import uy.com.tmwc.facturator.rapi.EntregaService;
import uy.com.tmwc.facturator.rapi.FanfoldService;
import uy.com.tmwc.facturator.rapi.LiquidacionService;
import uy.com.tmwc.facturator.rapi.MonedasCotizacionesService;
import uy.com.tmwc.facturator.rapi.PermisosException;
import uy.com.tmwc.facturator.rapi.RUTINA_MODIFCOSTO_ENUM;
import uy.com.tmwc.facturator.rapi.ReportesService;
import uy.com.tmwc.facturator.rapi.UserPrincipalLocator;
import uy.com.tmwc.facturator.rapi.UsuariosService;
import flex.messaging.FlexContext;

public class RemoteServiceHandler {

	private static Class<?> reportesServiceProxyClass;
	private static Constructor<?> reportesServiceProxyClassConstructor;
	static {
		reportesServiceProxyClass = Proxy.getProxyClass(ReportesService.class.getClassLoader(), new Class[] { ReportesService.class });
		try {
			reportesServiceProxyClassConstructor = reportesServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Class<?> eFacturaServiceProxyClass;
	private static Constructor<?> eFacturaServiceProxyClassConstructor;
	static {
		eFacturaServiceProxyClass = Proxy.getProxyClass(EFacturaService.class.getClassLoader(), new Class[] { EFacturaService.class });
		try {
			eFacturaServiceProxyClassConstructor = eFacturaServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}


	private static Class<?> catalogServiceProxyClass;
	private static Constructor<?> catalogServiceProxyClassConstructor;
	static {
		catalogServiceProxyClass = Proxy.getProxyClass(CatalogService.class.getClassLoader(), new Class[] { CatalogService.class });
		try {
			catalogServiceProxyClassConstructor = catalogServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> docServiceProxyClass;
	private static Constructor<?> docServiceProxyClassConstructor;
	static {
		docServiceProxyClass = Proxy.getProxyClass(DocumentoService.class.getClassLoader(), new Class[] { DocumentoService.class });
		try {
			docServiceProxyClassConstructor = docServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> agendaTareaServiceProxyClass;
	private static Constructor<?> agendaTareaServiceProxyClassConstructor;
	static {
		agendaTareaServiceProxyClass = Proxy.getProxyClass(AgendaTareaService.class.getClassLoader(), new Class[] { AgendaTareaService.class });
		try {
			agendaTareaServiceProxyClassConstructor = agendaTareaServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> entregaServiceProxyClass;
	private static Constructor<?> entregaServiceProxyClassConstructor;
	static {
		entregaServiceProxyClass = Proxy.getProxyClass(EntregaService.class.getClassLoader(), new Class[] { EntregaService.class });
		try {
			entregaServiceProxyClassConstructor = entregaServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	private static Class<?> fanfoldServiceProxyClass;
	private static Constructor<?> fanfoldServiceProxyClassConstructor;
	static {
		fanfoldServiceProxyClass = Proxy.getProxyClass(EntregaService.class.getClassLoader(), new Class[] { FanfoldService.class });
		try {
			fanfoldServiceProxyClassConstructor = fanfoldServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> auditoriaServiceProxyClass;
	private static Constructor<?> auditoriaServiceProxyClassConstructor;
	static {
		auditoriaServiceProxyClass = Proxy.getProxyClass(AuditoriaService.class.getClassLoader(), new Class[] { AuditoriaService.class });
		try {
			auditoriaServiceProxyClassConstructor = auditoriaServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> monedasCotizacionesServiceProxyClass;
	private static Constructor<?> monedasCotizacionesServiceProxyClassConstructor;
	static {
		monedasCotizacionesServiceProxyClass = Proxy.getProxyClass(MonedasCotizacionesService.class.getClassLoader(), new Class[] { MonedasCotizacionesService.class });
		try {
			monedasCotizacionesServiceProxyClassConstructor = monedasCotizacionesServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> liquidacionServiceProxyClass;
	private static Constructor<?> liquidacionServiceProxyClassConstructor;
	static {
		liquidacionServiceProxyClass = Proxy.getProxyClass(LiquidacionService.class.getClassLoader(), new Class[] { LiquidacionService.class });
		try {
			liquidacionServiceProxyClassConstructor = liquidacionServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> descuentosPrometidosServiceProxyClass;
	private static Constructor<?> descuentosServiceProxyClassConstructor;
	static {
		descuentosPrometidosServiceProxyClass = Proxy.getProxyClass(DescuentosPrometidosService.class.getClassLoader(), new Class[] { DescuentosPrometidosService.class });
		try {
			descuentosServiceProxyClassConstructor = descuentosPrometidosServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> usuariosServiceProxyClass;
	private static Constructor<?> usuariosServiceProxyClassConstructor;
	static {
		usuariosServiceProxyClass = Proxy.getProxyClass(UsuariosService.class.getClassLoader(), new Class[] { UsuariosService.class });
		try {
			usuariosServiceProxyClassConstructor = usuariosServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	private static Class<?> clientesServiceProxyClass;
	private static Constructor<?> clientesServiceProxyClassConstructor;
	static {
		clientesServiceProxyClass = Proxy.getProxyClass(ClientesService.class.getClassLoader(), new Class[] { ClientesService.class });
		try {
			clientesServiceProxyClassConstructor = clientesServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}


	private static Class<?> articulosServiceProxyClass;
	private static Constructor<?> articulosServiceProxyClassConstructor;
	static {
		articulosServiceProxyClass = Proxy.getProxyClass(UsuariosService.class.getClassLoader(), new Class[] { ArticulosService.class });
		try {
			articulosServiceProxyClassConstructor = articulosServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}

	public RemoteServiceHandler() {
		// This is required for the Blaze DS to instantiate the class

	}

	public Documento getDocumento(String id) throws PermisosException {
		DocumentoService service = getService();

		try {
			if (id == null) {
				return null;
			}
			Documento documento = service.findDocumento(id);

			Comprobante comprobante = documento.getComprobante();
			comprobante.setAster(esComprobanteAster(comprobante.getCodigo()));
			documento.setComprobante(comprobante);

			return documento;
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<ArticuloPrecioFabricaCosto> getCostoArticulos(Documento doc) {
		return getService().getCostosArticulos(doc);
	}
	
	public void actualizarCostos(List<ArticuloPrecioFabricaCosto> precios) {
		getService().updateArticulosCostos(precios);
	}
	
	public void actualizarArticulosCompraVenta(List<ArticuloCompraVentaCosto> costos) throws PermisosException {
		getService().updateCostosArticuloDocumentos(costos);
	}
	
	public List<ArticuloPrecioFabricaCosto> getPreciosArticuloDocumento(Documento doc) {
		return getService().getPreciosArticuloDocumento(doc);
	}
	
	public List<ArticuloCompraVentaCosto> getCompraVentaCostos(Documento doc) {
		return getService().getCompraVentaCostos(doc);
	}
	
	public List<ArticuloCompraVentaCosto> getCompraVentaCostos(Date fechaDesde, Date fechaHasta, Boolean mostrarTodas) {
		return getService().getComprasPlazaCostos(fechaDesde, fechaHasta, mostrarTodas);
	}
	
	public void actualizarPrecios(List<ArticuloPrecioFabricaCosto> precios, Boolean updateCosto) {
		getService().updateArticulosPrecios(precios, updateCosto);
	}

	private DocumentoService getService() {
		try {
			DocumentoService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (DocumentoService) ctx.lookup("facturator-server-ear/DocumentoServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (DocumentoService) docServiceProxyClassConstructor.newInstance(handler);

			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	public List<DocumentoDTO> queryDocumentos(DocumentoQuery query) {
		if (query.getTipoComprobante().toString().equals("0")) {
			query.setTipoComprobante(null);
		}
		return getService().queryDocumentos(query);
	}

	public long countDocumentos(DocumentoQuery query) {
		if (query.getTipoComprobante().toString().equals("0")) {
			query.setTipoComprobante(null);
		}
		return getService().countDocumentos(query);
	}
	
	private Documento ajustarDocumento(Documento doc) {
		if (doc.getComprobante().isRecibo()) {
			if (doc.getFacturasVinculadas().size() <= 0) {
				doc.setTotal(doc.getDocRecNeto());
				doc.setMoneda(doc.getDocRecMda());
			}
		}
		return doc;
	}

	public String alta(Documento documento) throws ValidationException, PermisosException {
		return alta(documento, null);
	}

	public String alta(Documento documento, Auditoria auditoria) throws ValidationException, PermisosException {
		documento = ajustarDocumento(documento);
		
		NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("ES"));

		if (auditoria == null) {
			String moneda = documento.getMoneda() != null ? documento.getMoneda().getSimbolo() : "";
			String total = documento.getTotal() != null ? formatter.format(documento.getTotal().doubleValue()) : "0";

			auditoria = new Auditoria();
			auditoria.setAudFechaHora(new Date());
			auditoria.setDocId(documento.getDocId());
			auditoria.setNotas("Documento creado por un total de " + moneda + " " + total);
			auditoria.setProblemas("Ninguno");
		}		

		String tcFiscal = getTipoCambioFiscal(Moneda.CODIGO_MONEDA_DOLAR, documento.getFecha());
		if (tcFiscal.length() > 0) {
			documento.setDocTCF(new BigDecimal(tcFiscal));
		}
		if (documento.getComprobante().isRecibo()) {
			// Realizar cosas para los recibos
			documento.setDocTCF(documento.getDocTCC());
		}
		
		if (documento.getComprobante().isNotaCreditoFinanciera()) {
			documento.setSaldo(documento.getTotal());
		}
		
		String docId = getService().alta(documento, auditoria);
		
		// Agregar vínculos de nota de crédito financiera.
		if (documento.getComprobante().isNotaCreditoFinanciera()) {
			Documento doc = getService().findDocumento(docId);
			
			Set<VinculoDocumentos> vinculos = new HashSet<VinculoDocumentos>();
			
			// Agregar vinculos
			List<LineaDocumento> lineas = doc.getLineas().getLineas();
			for (LineaDocumento lineaDocumento : lineas) {
				String notas = lineaDocumento.getNotas() != null ? lineaDocumento.getNotas() : null;
				if (notas == null || notas.length() == 0) {
					continue;
				}
				String[] rows = notas.split("[\\r\\n]+");
				if (rows.length < 2 || rows[1] == null) {
					continue;
				}
				String datosFactura = rows[1];
				
				String[] tokens = datosFactura.split("\\|");
				int docIdVin1 = new Integer(tokens[0]).intValue();
				int docIdVin2 = new Integer(docId).intValue();
				BigDecimal monto = lineaDocumento.getPrecio().multiply(new BigDecimal(1.22)).setScale(4, RoundingMode.HALF_EVEN);
								
				VinculoDocumentos vinculo = new VinculoDocumentos();
				vinculo.setDocIdVin1(docIdVin1);
				vinculo.setDocIdVin2(docIdVin2);
				vinculo.setMonto(monto);
				vinculo.setDescuentoPorc(BigDecimal.ZERO);
				
				vinculos.add(vinculo);
			}
			
			// Vincular facturas en nota de credito financiera.
			doc.setFacturasVinculadas(vinculos);
			
			HashMap<String, Documento> documentos = new HashMap<String, Documento>();
			HashMap<String, Auditoria> audits = new HashMap<String, Auditoria>();
			
			BigDecimal montoTotal = BigDecimal.ZERO;
			
			// Ajustar saldos una vez guardado los vínculos
			for (LineaDocumento lineaDocumento : lineas) {
				String notas = lineaDocumento.getNotas() != null ? lineaDocumento.getNotas() : null;
				if (notas == null || notas.length() == 0) {
					continue;
				} 				
				String[] rows = notas.split("[\\r\\n]+");
				if (rows.length < 2 || rows[1] == null) {
					continue;
				}
				BigDecimal monto = lineaDocumento.getPrecio().multiply(new BigDecimal(1.22));
				montoTotal = montoTotal.add(monto);
				
				String datosFactura = rows[1];
				
				String[] tokens = datosFactura.split("\\|");
				int docIdVin1 = new Integer(tokens[0]).intValue();
				String serie = tokens[1];
				String numero = tokens[2];
				
				Documento docVin = getService().findDocumento(String.valueOf(docIdVin1));
				String moneda = docVin.getMoneda().getCodigo();
				BigDecimal viejoSaldo = docVin.getSaldo();
				BigDecimal nuevoSaldo = viejoSaldo.subtract(monto).setScale(2, RoundingMode.HALF_EVEN);
				nuevoSaldo = ajustarSaldo(moneda, nuevoSaldo);
				
				if (nuevoSaldo.doubleValue() < 0 ) {
					throw new RuntimeException("Error: " + docVin.getSerie() + docVin.getNumero() + " saldo inválido (" + docVin.getSaldo().setScale(2, RoundingMode.HALF_EVEN) + "<" + monto.setScale(2, RoundingMode.HALF_EVEN)+ ").");
				}

				Auditoria audit = new Auditoria();
				audit.setAudFechaHora(new Date());
				audit.setDocId(docVin.getDocId());
				audit.setNotas("Vinculado por N/C36 PC X DTOS FINANCIEROS [" + serie + numero + "] por un monto de " + docVin.getMoneda().getSimbolo() + formatter.format(monto.setScale(4, RoundingMode.HALF_EVEN).doubleValue()));
				audit.setProblemas("Monto cancelado:\t" + docVin.getMoneda().getSimbolo() + formatter.format(monto.setScale(4, RoundingMode.HALF_EVEN).doubleValue()) + "\n" +
						"Saldo anterior:\t" + docVin.getMoneda().getSimbolo()  + formatter.format(viejoSaldo.doubleValue()) + "\n" +
						"Saldo actual:\t" + docVin.getMoneda().getSimbolo()  + formatter.format(nuevoSaldo.doubleValue()) + "\n");
				
				audits.put(docVin.getDocId(), audit);
				docVin.setSaldo(nuevoSaldo);

				documentos.put(docVin.getDocId(), docVin);
			}

			// Ajustar saldos una vez guardado los vinculos
			for (String docVId : documentos.keySet()) {
				getService().guardar(documentos.get(docVId), audits.get(docVId));
			}
			
			// Ajustar el saldo
			doc.setSaldo(doc.getSaldo().subtract(montoTotal));
			
			// Guardar el documento con las facturas vinculadas.
			getService().guardar(doc);
		}
		
		return docId;
	}
	
	private BigDecimal ajustarSaldo(String moneda, BigDecimal saldo) {
		double ss = Math.abs(saldo.doubleValue());
		if (moneda.equals(Moneda.CODIGO_MONEDA_DOLAR) || moneda.equals(Moneda.CODIGO_MONEDA_EUROS)  ||
				moneda.equals(Moneda.CODIGO_MONEDA_DOLAR_ASTER) || moneda.equals(Moneda.CODIGO_MONEDA_EUROS_ASTER)) {
			if (ss < 0.1) {
				saldo = BigDecimal.ZERO;
			} 
		} else if (moneda.equals(Moneda.CODIGO_MONEDA_PESOS) || moneda.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
			if (ss < 1) {
				saldo = BigDecimal.ZERO;
			} 
		}
		return saldo;

	}

	public List<Auditoria> getLineasAuditoria(String docId) {
		return getAuditoriaService().getAuditoria(docId);
	}
	
	public List<Auditoria> getDepositos(String docId) {
		return getAuditoriaService().getAuditoria(docId);
	}

	
	public void modificar(Documento documento) throws ValidationException, PermisosException {
		modificar(documento, null);
	}

	public void modificar(Documento documento, Auditoria auditoria) throws ValidationException, PermisosException {
		documento = ajustarDocumento(documento);
		
		if (auditoria == null) {
			NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("ES"));

			String moneda = documento.getMoneda() != null ? documento.getMoneda().getSimbolo() : "";
			String total = documento.getTotal() != null ? formatter.format(documento.getTotal().doubleValue()) : "0";
			
			auditoria = new Auditoria();
			auditoria.setAudFechaHora(new Date());
			auditoria.setDocId(documento.getDocId());
			auditoria.setNotas("Documento guardado con modificaciones, por un total de "  + moneda + " " + total);
			auditoria.setProblemas("Ninguno");
		}
		
		if (!documento.isPendiente()) {
			BigDecimal docTCF = getService().getTipoCambioFiscal(Moneda.CODIGO_MONEDA_DOLAR, new Date());
			if (docTCF != null) {
				documento.setDocTCF(docTCF);
			}
		}
		if (documento.getComprobante().isRecibo()) {
			// Realizar cosas para los recibos
			documento.setDocTCF(documento.getDocTCC());
		}
		
		getService().modificar(documento, auditoria);
	}
	

	public int modificarCostos(String codart, Date dateDesde, Date dateHasta, RUTINA_MODIFCOSTO_ENUM costoAnterior, BigDecimal valorCostoAnterior, BigDecimal costoNuevo, String monedaNuevoCosto,
			BigDecimal tcd) {
		try {
			return getService().modificarCostos(codart, dateDesde, dateHasta, costoAnterior, valorCostoAnterior, costoNuevo, monedaNuevoCosto, tcd);
		} catch (RuntimeException e) {
			e.printStackTrace();
			throw e;
		}
	}
	

	public SerieNumero generateSerieNumero(Comprobante comprobante) {
		DocumentoService service = getService();

		return service.generarSerieNumero(comprobante.getCodigo());
	}

	public Boolean baja(Documento documento) throws ValidationException, PermisosException {
		return getService().borrar(documento);
	}
	
	public Boolean borrarCotizaciones(String cotizaciones) throws ValidationException, PermisosException {
		return getService().borrarCotizaciones(cotizaciones);
	}
	
	public String obtenerClaveSupervisora() {
		return "";
	}

	public void guardarClaveSupervisora(String clave) {
	}

	//////////////////
	// COTIZACIONES //
	//////////////////

	public CotizacionesMonedas getCotizacionHoy() {
		CotizacionesMonedas cotizacion =  getMonedasCotizacionesService().getCotizacion(new Date());
		if (cotizacion != null) {
			return cotizacion;
		} else {
			List<CotizacionesMonedas> cotizaciones = getCotizaciones(null);
			if (cotizaciones != null && cotizaciones.size() > 0) {
				return cotizaciones.get(0);
			} else {
				throw new RuntimeException("No hay cotizaciónes definidas.");
			}			
		}
	}
	
	public CotizacionesMonedas getUltimaCotizacion(Date fechaHasta) {
		CotizacionesMonedas cotizacion = getMonedasCotizacionesService().getUltimaCotizacion(fechaHasta);
		if (cotizacion != null) {
			return cotizacion;
		} else {
			throw new RuntimeException("No hay cotización definida.");
		}		
	}

	public CotizacionesMonedas getCotizacion(Date fecha) {
		CotizacionesMonedas cotizacion =  getMonedasCotizacionesService().getCotizacion(fecha);
		if (cotizacion != null) {
			return cotizacion;
		} else {
			throw new RuntimeException("No hay cotización definida.");
		}		
	}

	public List<LineaDocumento> getLineasCotizadas(String cliId, Date fromDate, Date toDate) {
		return getService().getLineasCotizadas(cliId, null, Comprobante.COTIZACION, fromDate, toDate, 1000);
	}

	public List<LineaDocumento> getLineasCotizadas(String cliId, String artId, Date fromDate, Date toDate) {
		return getService().getLineasCotizadas(cliId, artId, Comprobante.COTIZACION, fromDate, toDate, 1000);
	}
	
	public List<CotizacionesMonedas> getCotizaciones(Date fromDate) {
		List<CotizacionesMonedas> list =  getMonedasCotizacionesService().getCotizacionesMonedas(fromDate);
		
		return list;
	}
	
	public Boolean altaCotizacion(CotizacionesMonedas cotizacion) {
		CotizacionesMonedas c =  getMonedasCotizacionesService().getCotizacion(cotizacion.getDia());
		if (c != null) {
			getMonedasCotizacionesService().merge(cotizacion);
		} else {
			getMonedasCotizacionesService().persist(cotizacion);
		}
		
		return true;		
	}

	public void actualizarCotizacion(CotizacionesMonedas cotizacion) {
		getMonedasCotizacionesService().merge(cotizacion);
	}
	
	// ///////////
	// ARTÍCULO //
	// ///////////
	public String altaArticulo(Articulo articulo) {
		return getArticulosService().persist(articulo);
	}

	public void modificarArticulo(Articulo articulo) {
		getArticulosService().merge(articulo);
	}
	
	public void activarArticulos(List<Articulo> articulos) {
		for (Articulo articulo : articulos) {
			Articulo a = findCatalogEntity("Articulo", articulo.getCodigo());
			a.setActivo(true);
			getArticulosService().merge(a);
		}
	}

	public void borrarArticulo(String codigo) {
		Articulo articulo = findCatalogEntity("Articulo", codigo);
		getArticulosService().delete(articulo);
	}
	
	public void updatePrecios(List<ArticuloPrecio> precios) {
		for (ArticuloPrecio articuloPrecio : precios) {
			getArticulosService().updateArticuloPrecio(articuloPrecio);	
		}	
		
	}
	
	/////////////
	// CLIENTE //
	/////////////

	public String altaCliente(Cliente cliente) {
		if (cliente != null && cliente.getContacto() != null && cliente.getContacto().getUsuIdCto() == 0) {
			cliente.getContacto().setUsuIdCto(null);
		}
		return getClientesService().persist(cliente, false);
	}
	
	public String verifyAltaCliente(Cliente cliente) {
		Contacto contacto = cliente.getContacto();
		contacto.setCodigo(cliente.getCodigo());
		contacto.setNombre(cliente.getNombre());
		
		return getClientesService().verifyContacto(contacto);
	}

	public String verifyAltaProveedor(Proveedor proveedor) {
		Contacto contacto = proveedor.getContacto();
		contacto.setCodigo(proveedor.getCodigo());
		contacto.setNombre(proveedor.getNombre());

		return getClientesService().verifyContacto(contacto);
	}

	public String altaCliente(Cliente cliente, Boolean force) {
		if (cliente != null && cliente.getContacto() != null && cliente.getContacto().getUsuIdCto() == 0) {
			cliente.getContacto().setUsuIdCto(null);
		}
		return getClientesService().persist(cliente, force);
	}

	public void modificarCliente(Cliente cliente) {
		if (cliente != null && cliente.getContacto() != null && cliente.getContacto().getUsuIdCto() == 0) {
			cliente.getContacto().setUsuIdCto(null);
		}
		getClientesService().merge(cliente);
	}

	public String getLastId() {
		return getClientesService().getLastId();
	}

	public Boolean borrarCliente(String codigo) {
		Cliente cliente = findCatalogEntity("Cliente", codigo);
		cliente.getContacto().setCtoActivo("N");
		getClientesService().merge(cliente);
		return true;
	}

	public Boolean updateClienteState(String codigo, Boolean state) {
		Cliente cliente = findCatalogEntity("Cliente", codigo);
		cliente.getContacto().setCtoActivo(state ? "S" : "N");
		getClientesService().merge(cliente);
		return true;
	}

	public List<ClienteDTO> queryClientes(ClienteQuery paramClienteQuery) throws ValidationException {
		return getClientesService().queryClientes(paramClienteQuery);
	}
	
	public List<ContactoDTO> queryContactos(ContactoQuery paramContactoQuery) {
		return getClientesService().queryContactos(paramContactoQuery);
	}

	
	/////////////////
	// PROVEEDORES //
	/////////////////

	public String altaProveedor(Proveedor proveedor) {
		if (proveedor != null && proveedor.getContacto() != null && proveedor.getContacto().getUsuIdCto() == 0) {
			proveedor.getContacto().setUsuIdCto(null);
		}
		return getClientesService().persist(proveedor);
	}

	public void modificarProveedor(Proveedor proveedor) {
		if (proveedor != null && proveedor.getContacto() != null && proveedor.getContacto().getUsuIdCto() == 0) {
			proveedor.getContacto().setUsuIdCto(null);
		}
		getClientesService().merge(proveedor);
	}
	
	public Boolean borrarProveedor(String codigo) {
		Proveedor proveedor = findCatalogEntity("Proveedor", codigo);
		proveedor.getContacto().setCtoActivo("N");
		getClientesService().merge(proveedor);
		
		return true;
	}

	public Boolean updateProveedorState(String codigo, Boolean stateActivo) {
		Proveedor proveedor = findCatalogEntity("Proveedor", codigo);
		proveedor.getContacto().setCtoActivo(stateActivo ? "S" : "N");
		getClientesService().merge(proveedor);
		
		return true;
	}
	
	public List<ProveedorDTO> queryProveedores(ProveedoresQuery paramProvQuery) {
		return getClientesService().queryProveedores(paramProvQuery);
	}

	//////////////
	// PERSONAS //
	//////////////

	public List<Persona> getPersonas(String codigo) {
		return getClientesService().queryPersonas(codigo);
	}

	public String altaPersona(Persona persona) {
		return getClientesService().persist(persona);
	}
	
	public void editarPersona(Persona persona) {
		getClientesService().merge(persona);
	}
	
	public Boolean borrarPersona(Persona persona) {
		return getClientesService().delete(persona);
	}


	///////////////
	// ARTICULOS //
	///////////////
	
	public List<ArticuloDTO> queryArticulos(ArticuloQuery paramArticuloQuery) {
		return getArticulosService().queryArticulos(paramArticuloQuery);
	}
	
	public Boolean finalizarMovimientoStock(Documento documento) throws PermisosException {
		return getService().finalizarMovimientoStock(documento);
	}
	
	public Boolean finalizarCompra(Documento documento) throws PermisosException {
		return getService().finalizarCompra(documento);
	}

	public Boolean finalizarRecibo(Documento documento) throws PermisosException {
		return getService().finalizarRecibo(documento);
	}

	public String altaEntrega(Entrega entrega) {
		return getEntregaService().persist(entrega);
	}

	public void modificarEntrega(Entrega entrega) {
		getEntregaService().merge(entrega);
	}

	public Boolean borrarEntrega(Entrega entrega) {
		return getEntregaService().remove(entrega);
	}

	public String altaFanfold(Fanfold fanfold) {
		return getFanfoldService().persist(fanfold);
	}

	public void modificarFanfold(Fanfold fanfold) {
		getFanfoldService().merge(fanfold);
	}

	public Boolean borrarFanfold(Fanfold fanfold) {
		return getFanfoldService().remove(fanfold);
	}

	public SerieNumero emitir(String docId, String fanfoldId) throws ValidationException, PermisosException {
		Documento documento = getDocumento(docId);
		return emitir(documento, fanfoldId);
	}

	public SerieNumero emitir(Documento documento, String fanfoldId) throws ValidationException, PermisosException {
		String monedaId = documento.getMoneda().getCodigo();	
		if (monedaId.equals(Moneda.CODIGO_MONEDA_DOLAR_ASTER)) {
			monedaId = Moneda.CODIGO_MONEDA_DOLAR;
		}
		if (monedaId.equals(Moneda.CODIGO_MONEDA_EUROS_ASTER)) {
			monedaId = Moneda.CODIGO_MONEDA_EUROS;
		}
		
		if (!documento.getComprobante().isRecibo()) {
			if (!monedaId.equals(Moneda.CODIGO_MONEDA_PESOS) && !monedaId.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
				BigDecimal tcFiscal = getService().getTipoCambioFiscal(monedaId, new Date());
				if (tcFiscal == null) {
					throw new RuntimeException("No hay tipo de cambio fiscal definido para el día de hoy.\nDefina el tipo de cambio fiscal para poder emitir.");
				}
				documento.setDocTCF(tcFiscal);
			}			
		}
				
		SerieNumero serieNro = getService().emitir(documento, fanfoldId);
		return serieNro;
	}
	
	public Boolean existeTCFiscal() {
		String monedaId = Moneda.CODIGO_MONEDA_DOLAR;		
		return existeTCFiscal(monedaId);
		
	}
	
	public Boolean existeTCFiscal(String monedaId) {
		if (monedaId.equals(Moneda.CODIGO_MONEDA_PESOS) || monedaId.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
			return true;
		}
		if (monedaId.equals(Moneda.CODIGO_MONEDA_DOLAR_ASTER)) {
			monedaId = Moneda.CODIGO_MONEDA_DOLAR;
		}
		if (monedaId.equals(Moneda.CODIGO_MONEDA_EUROS_ASTER)) {
			monedaId = Moneda.CODIGO_MONEDA_EUROS;
		}
		BigDecimal tcFiscal = getService().getTipoCambioFiscal(monedaId, new Date());
		if (tcFiscal == null) {
			return false;
		}
		return true;
	}
	
	public EFacturaResult generateCFE(Documento documento) throws PermisosException {
		String monedaId = documento.getMoneda().getCodigo();
		
		if (!monedaId.equals(Moneda.CODIGO_MONEDA_PESOS) && !monedaId.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
			BigDecimal tcFiscal = getService().getTipoCambioFiscal(monedaId, new Date());
			if (tcFiscal == null) {
				throw new RuntimeException("No hay tipo de cambio fiscal definido para el día de hoy.\nDefina el tipo de cambio fiscal para poder emitir.");
			}
		}
		if (esContingencia(documento.getComprobante().getCodigo())) {
			if (documento.getSerie() == null || documento.getSerie().trim().equals("")) {
				throw new RuntimeException("La serie en contingencias es requerido");
			}
			if (documento.getNumero() == null) {
				throw new RuntimeException("El número en contingencias es requerido");
			}
		} else {
			documento.setSerie(null);
			documento.setNumero(null);
		}

		try {
			if (!documento.getComprobante().isAster()) {
				return getEFacturaService().generarEfactura(documento);
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	private Boolean esContingencia(String codigo) {
		if (codigo.equals("300") || codigo.equals("301") || codigo.equals("302") || codigo.equals("303") || codigo.equals("304") || codigo.equals("305") || codigo.equals("306") || codigo.equals("307")) {
			return true;
		}
		return false;
	}


	public Boolean guardar(Documento documento) throws ValidationException, PermisosException {
		if (documento.getDocId() != null) {
			getService().guardar(documento);
			return true;
		}
		return false;
	}

	////////////////////////////////////////
	// DESCUENTOS PROMETIDOS COMPROBANTES //
	////////////////////////////////////////
	
	public List<DescuentoPrometidoComprobante> getDescuentosPrometidos(String cmpid, String categoriaCliente) {
		return getDescuentosPrometidosService().getDescuentosPrometidos(cmpid, categoriaCliente);
	}

	public void saveDescuentosPrometidos(List<DescuentoPrometidoComprobante> oldValues, List<DescuentoPrometidoComprobante> newValues) {
		DescuentosPrometidosService service = getDescuentosPrometidosService();
		for (DescuentoPrometidoComprobante descuento : oldValues) {
			service.remove(descuento);
		}
		for (DescuentoPrometidoComprobante descuento : newValues) {
			Integer value = new Integer(descuento.getComprobante().getCodigo());
			descuento.setCmpid(value.intValue());
			descuento.setCategoriaCliente(descuento.getCategCliente().getCodigo());

			service.persist(descuento);
		}
	}

	//////////////////
	// EXPEDICIONES //
	//////////////////
	
	public List<AgendaTareaDTO> queryTareas(AgendaTareaQuery query) {
		return getAgendaTareaService().queryAgendaTareas(query);
	}

	public List<AgendaTareaDTO> queryTareasSupervisadas(AgendaTareaQuery query) {
		List<AgendaTareaDTO> tareas = getAgendaTareaService().queryAgendaTareasSupervisadas(query);
		return tareas;
	}

	// TODO seguridad: un usuario solo puede hacer tareas donde el es el
	// asignador.
	public void alta(AgendaTarea tarea, Boolean matutino) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tarea.getFechaHora());
		if (matutino) {
			calendar.set(Calendar.HOUR_OF_DAY, 8);
		} else {
			calendar.set(Calendar.HOUR_OF_DAY, 16);
		}
		tarea.setFechaHora(calendar.getTime());

		/*
		 * String clientName = session.getUserPrincipal().getName();
		 * System.out.println("client name :: " + clientName);
		 */

		getAgendaTareaService().persist(tarea);
	}

	public void alta(AgendaTarea e) {
		getAgendaTareaService().persist(e);
	}

	public void altaTareas(List<AgendaTarea> tareas, Boolean matutino) {
		AgendaTareaService service = getAgendaTareaService();
		for (AgendaTarea tarea : tareas) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(tarea.getFechaHora());
			if (matutino) {
				calendar.set(Calendar.HOUR_OF_DAY, 8);
			} else {
				calendar.set(Calendar.HOUR_OF_DAY, 16);
			}
			tarea.setFechaHora(calendar.getTime());

			service.persist(tarea);
		}
	}

	// TODO seguridad: un usuario solo puede hacer tareas donde el es el
	// asignador.
	public void modificar(AgendaTarea tarea, Boolean matutino) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(tarea.getFechaHora());
		if (matutino) {
			calendar.set(Calendar.HOUR_OF_DAY, 8);
		} else {
			calendar.set(Calendar.HOUR_OF_DAY, 16);
		}
		tarea.setFechaHora(calendar.getTime());

		getAgendaTareaService().merge(tarea);
	}

	public void modificar(AgendaTarea e) {
		getAgendaTareaService().merge(e);
	}
	
	public void modificarTareaDTO(AgendaTareaDTO e) {
		AgendaTarea tarea =  getAgendaTarea(String.valueOf(e.getAgeId()));
		
		if (tarea != null) {
			tarea.setFechaHora(e.getFechaHora());
			tarea.setDescripcion(e.getDescripcion());
			tarea.setOrden(e.getOrden() != null ? new Long(e.getOrden().toString()) : null);
			tarea.setNroOrden(e.getNroOrden());
			tarea.setEstado(e.getEstado());
			
			if (e.getIdUsuAsignado() > 0) {
				Usuario usuario = findCatalogEntity("Usuario", String.valueOf(e.getIdUsuAsignado()));
				tarea.setUsuarioAsignado(usuario);
			}
			getAgendaTareaService().merge(tarea);
		}		
	}

		
	// TODO seguridad: un usuario solo puede hacer tareas donde el es el asignador.
	public void modificarTareas(List<AgendaTareaDTO> values, Boolean matutino) {
		for (AgendaTareaDTO t : values) {
			AgendaTarea tarea =  getAgendaTarea(String.valueOf(t.getAgeId()));
			
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(tarea.getFechaHora());
			calendar.set(Calendar.HOUR_OF_DAY, matutino ? 8 : 16);
			
			tarea.setFechaHora(calendar.getTime());

			getAgendaTareaService().merge(tarea);
		}
	}

	/**
	 * Copiar las tareas que no se realizarón a la fecha de hoy y quedaron
	 * pendientes de ser realizadas. Además a dicha tarea se le debe de poner
	 * una marca para saber que la misma fue reasignada.
	 * 
	 */
	public void moverTareasPendientes() {
	}

	/**
	 * Reagendar la tarea dada. Esta se hace de forma manual y no de forma
	 * automática.
	 * 
	 * @param agendaTarea
	 *            Tarea a ser re-asignada
	 * @param fechaHasta
	 *            Fecha a la que se agenda la tarea
	 * @return true si la tarea se ha reasignado correctamente.
	 */
	public Boolean reagendarTarea(AgendaTarea agendaTarea, Date fechaHasta, Usuario asignado, Boolean matutino) {
		agendaTarea.setEstado("C");
		agendaTarea.setFechaHoraFin(null); // Poner la fecha del servidor
		getAgendaTareaService().merge(agendaTarea);

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fechaHasta);
		if (matutino) {
			calendar.set(Calendar.HOUR_OF_DAY, 8);
		} else {
			calendar.set(Calendar.HOUR_OF_DAY, 16);
		}

		AgendaTarea tarea = (AgendaTarea) agendaTarea.clone();
		//tarea.setAgeId(null);
		tarea.setEstado("P");
		tarea.setFechaHoraFin(null);
		tarea.setFechaHora(calendar.getTime());
		tarea.setUsuarioAsignado(asignado);

		getAgendaTareaService().persist(tarea);

		return true;
	}

	// ////////////////////////////////////////
	// / FIN EXPEDICIONES
	// ////////////////////////////////////////

	public BigDecimal getTipoCambio(String p1, String p2, Date date) {
		return getService().getTipoCambio(p1, p2, date);
	}
	
	public String getTipoCambioFiscal(String monedaId, Date date) {
		BigDecimal tipoCambio = getService().getTipoCambioFiscal(monedaId, date);
		
		if (tipoCambio != null) {
			return tipoCambio.toString();
		}
		return "";
		
	}


	public BigDecimal getMontoMayorCotizacion(String articulo, Date fechaPrecio, String monedaPrecio, BigDecimal precio, String monedaFacturacion, Cotizaciones paramCotizaciones) {
		return getService().getMontoMayorCotizacion(articulo, fechaPrecio, monedaPrecio, precio, monedaFacturacion, false, paramCotizaciones);
	}

	public BigDecimal getMontoMayorCotizacion(String articulo, Date fechaPrecio, String monedaPrecio, BigDecimal precio, String monedaFacturacion, Boolean esRemito, Cotizaciones paramCotizaciones) {
		return getService().getMontoMayorCotizacion(articulo, fechaPrecio, monedaPrecio, precio, monedaFacturacion, esRemito, paramCotizaciones);
	}

	public ArticuloPrecio getArticuloPrecio(String articulo, String preciosVenta) throws PermisosException {
		return getArticuloPrecio(articulo, preciosVenta, null);
	}
	
	public ArticuloPrecio getArticuloPrecio(String articulo, String preciosVenta, String monedaId) throws PermisosException {
		ArticuloPrecio artPrecio = getService().getArticuloPrecio(articulo, preciosVenta);
		if (artPrecio == null) {
			return null;
		}
		if (monedaId == null || monedaId.equals(artPrecio.getMndIdPrecio())) {
			return artPrecio;
		} else {
			BigDecimal nuevoPrecio = convertPrecio(artPrecio.getPrecio(), artPrecio.getMndIdPrecio(), monedaId);
			Moneda nuevaMoneda = null;
			List<Moneda> monedas = getCatalogoByName("Moneda");
			for (Iterator<Moneda> iterator = monedas.iterator(); iterator.hasNext();) {
				Moneda moneda = (Moneda) iterator.next();
				if (moneda.getCodigo().equals(moneda.getCodigo())) {
					nuevaMoneda = moneda;
					break;
				}
			}
			artPrecio.setPrecio(nuevoPrecio);
			artPrecio.setMoneda(nuevaMoneda);
			artPrecio.setMndIdPrecio(nuevaMoneda.getCodigo());
			
			return artPrecio;
		}
		
	}
	
	/**
	 * Para una precio de 100 pesos
	 *	100 / 32 = 3.125 dolares
	 *	100 / 35 = 2.857 euros
	 *
	 * Para una precio de 100 pesos
	 *	100 * 33 = 3300 pesos
	 *  100 * (32 / 35) = 91.428 dólares
	 *  
	 * Para un precio de 100 euros
	 *	100 * 39 = 3900 pesos
	 *	100 * (39 / 33) = 118.1818 dólares
	 */
	public BigDecimal convertPrecio(BigDecimal precio, String monedaOrigen, String monedaDestino) {
		Calendar calendar = Calendar.getInstance(); 
		//calendar.add(Calendar.DAY_OF_MONTH, -15);
		List<CotizacionesMonedas> tiposCambio =  getMonedasCotizacionesService().getCotizacionesMonedas(calendar.getTime());
		if (tiposCambio.size() < 1) {
			return precio;
		}
		CotizacionesMonedas tipoCambio = tiposCambio.get(0);
		
		BigDecimal dolarCompra = tipoCambio.getDolarCompra().setScale(4, RoundingMode.HALF_UP);
		BigDecimal dolarVenta = tipoCambio.getDolarVenta().setScale(4, RoundingMode.HALF_UP);
		BigDecimal euroCompra = tipoCambio.getEuroCompra().setScale(4, RoundingMode.HALF_UP);
		BigDecimal euroVenta = tipoCambio.getEuroVenta().setScale(4, RoundingMode.HALF_UP);
		
		if (monedaOrigen.equals(Moneda.CODIGO_MONEDA_PESOS)) {
			if (monedaDestino.equals(Moneda.CODIGO_MONEDA_DOLAR)) {
				return precio.setScale(4, RoundingMode.HALF_UP).divide(dolarCompra, 4, RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP); 
			} else if (monedaDestino.equals(Moneda.CODIGO_MONEDA_EUROS)) { 
				return precio.setScale(4, RoundingMode.HALF_UP).divide(euroCompra, 4, RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP);
			}			
		} else if (monedaOrigen.equals(Moneda.CODIGO_MONEDA_DOLAR)) { 
			if (monedaDestino.equals(Moneda.CODIGO_MONEDA_PESOS)) {
				return precio.setScale(4, RoundingMode.HALF_UP).multiply(dolarVenta).setScale(4, RoundingMode.HALF_UP);
			} else if (monedaDestino.equals(Moneda.CODIGO_MONEDA_EUROS)) { 
				return precio.setScale(4, RoundingMode.HALF_UP).multiply(dolarCompra.divide(euroCompra, 4, RoundingMode.HALF_UP)).setScale(4, RoundingMode.HALF_UP);
			}			
		} else if (monedaOrigen.equals(Moneda.CODIGO_MONEDA_EUROS)) {
			if (monedaDestino.equals(Moneda.CODIGO_MONEDA_PESOS)) {
				return precio.setScale(4, RoundingMode.HALF_UP).multiply(euroVenta).setScale(4, RoundingMode.HALF_UP);
			} else if (monedaDestino.equals(Moneda.CODIGO_MONEDA_DOLAR)) { 
				return precio.setScale(4, RoundingMode.HALF_UP).multiply(euroVenta).divide(dolarVenta, 4, RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP);
			}
			
		}
		
		return BigDecimal.ZERO;
	}
	
	public int obtenerUltimoDiaMes (int anio, int mes) {
		Calendar cal = Calendar.getInstance();
		cal.set(anio, mes, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public BigDecimal getArticuloCosto(String articulo) throws PermisosException {
		Articulo a = getCatalogService().findCatalogEntity("Articulo", articulo);
		String familiaId = a.getFamiliaId();
		BigDecimal costo = a.getCosto();
		
		Usuario usuarioLogin = getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		Boolean hasPerm = Usuario.USUARIO_SUPERVISOR.equals(permisoId) || Usuario.USUARIO_ADMINISTRADOR.equals(permisoId) || Usuario.USUARIO_FACTURACION.equals(permisoId) || Usuario.USUARIO_VENDEDOR_SENIOR.equals(permisoId);
		
		if (!hasPerm && permisoId.equals(Usuario.USUARIO_ALIADOS_COMERCIALES)) {
			String regex = System.getProperty("facturator.aliadosComerciales.familias");
			if (regex != null) {
				String[] values = regex.split(",");
				for (int i = 0; i < values.length; i++) {
					if (familiaId.startsWith(values[i].trim())) {
						hasPerm = true;
						break;
					}
				}
			}
		}
		if (hasPerm) {
			return costo;
		} else {
			throw new PermisosException("'" + usuarioLogin.getNombre() + "' no tiene permisos para ver costo.");

		}
	}

	
	public BigDecimal getPrecioSugerido(String articulo, String preciosVenta, String monedaFacturacion) throws PermisosException  {
		Usuario usuarioLogin = getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		Boolean hasPerm = false;
	
		if (preciosVenta.equals("1")) {
			hasPerm = permisoId.equals(Usuario.USUARIO_SUPERVISOR) || permisoId.equals(Usuario.USUARIO_ADMINISTRADOR) || permisoId.equals(Usuario.USUARIO_FACTURACION) || permisoId.equals(Usuario.USUARIO_VENDEDOR_SENIOR) 
						|| permisoId.equals(Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR); 
		} else if (preciosVenta.equals("2") || preciosVenta.equals("3")) {
			hasPerm = true;
		} else if (preciosVenta.equals("4")) {
			hasPerm = permisoId.equals(Usuario.USUARIO_SUPERVISOR) || permisoId.equals(Usuario.USUARIO_ADMINISTRADOR) || permisoId.equals(Usuario.USUARIO_FACTURACION) || permisoId.equals(Usuario.USUARIO_VENDEDOR_SENIOR);
		}
		
		Articulo a = findCatalogEntity("Articulo", articulo);
		String familiaId = a.getFamiliaId();
		
		if (!hasPerm && permisoId.equals(Usuario.USUARIO_ALIADOS_COMERCIALES)) {
			String regex = System.getProperty("facturator.aliadosComerciales.familias");
			if (regex != null) {
				String[] values = regex.split(",");
				for (int i = 0; i < values.length; i++) {
					if (familiaId.startsWith(values[i].trim())) {
						hasPerm = true;
						break;
					}
				}
			}
		}
		
		if (!hasPerm) {
			throw new PermisosException("'" + usuarioLogin.getNombre() + "' no tiene permisos.");
		}
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MONTH, -1);
		
		int mes = cal.get(Calendar.MONTH);
		int anio = cal.get(Calendar.YEAR);

		int ultimoDiaMes = obtenerUltimoDiaMes(anio, mes);

		Calendar fechaCotizacion = Calendar.getInstance();
		fechaCotizacion.set(Calendar.YEAR, anio);
		fechaCotizacion.set(Calendar.MONTH, mes);
		fechaCotizacion.set(Calendar.DAY_OF_MONTH, ultimoDiaMes);

		CotizacionesMonedas oCotizaciones = getUltimaCotizacion(fechaCotizacion.getTime());
		
		Cotizaciones cotizaciones = new Cotizaciones();
		cotizaciones.setFecha(oCotizaciones.getDia());
		cotizaciones.agregarCotizacion(Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_DOLAR, true, oCotizaciones.getDolarCompra());
		cotizaciones.agregarCotizacion(Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_DOLAR, false, oCotizaciones.getDolarVenta());
		cotizaciones.agregarCotizacion(Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_EUROS, true, oCotizaciones.getEuroCompra());
		cotizaciones.agregarCotizacion(Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_EUROS, false, oCotizaciones.getEuroVenta());
				
		return getService().getPrecioSugerido(articulo, preciosVenta, monedaFacturacion, cotizaciones);
	}
	
	public BigDecimal getPrecioSugerido(String articulo, String preciosVenta, String monedaFacturacion, Cotizaciones oCotizaciones) {
		return getService().getPrecioSugerido(articulo, preciosVenta, monedaFacturacion, oCotizaciones);
	}
	
	public List<ArticuloPrecio> getArticuloPrecio(String articulo) {
		return getArticulosService().getArticuloPrecio(articulo);
	}


	public List<AntecedentesArticulo> getAntecedentes(String articulo, String cliente, int max, boolean venta) throws PermisosException {
		return getService().getAntecedentes(articulo, cliente, max, venta);
	}

	public List<AntecedentesArticulo> getLineasCompraCliente(String cliId, int limit) {
		return getService().getLineasCompraCliente(cliId, null, null, limit);
	}

	public List<AntecedentesArticulo> getLineasCompraCliente(String cliId, Date fromDate, Date toDate, int limit) {
		return getService().getLineasCompraCliente(cliId, fromDate, toDate, limit);
	}

	public String getResults(String name) {
		String result = null;
		result = "Hi " + name + ", this is a service and the time now is : " + new Date();

		return result;
	}

	public TableReportResult getReporteStockPrecios(final ReportParameters parameters) {
		long start = new Date().getTime();
		try {
			return getReportesService().getReporteStockPrecios(parameters);
		} finally {
			System.out.println("Tiempo total reporte stock/precio: " + (new Date().getTime() - start));
		}
	}

	public List<Moneda> getMonedasReporteStockPrecios() {
		return getReportesService().getMonedasReporte();
	}

	public byte[] getReporteControlPlus(Date fechaDesde, Date fechaHasta, String rMinima, String rMaxima) {
		long start = new Date().getTime();
		
		BigDecimal rentaMinima = null;
		BigDecimal rentaMaxima = null;
		
		if (rMinima != null && rMinima.length() > 0) {
			rentaMinima = new BigDecimal(rMinima);
		}
		if (rMaxima != null && rMaxima.length() > 0) {
			rentaMaxima = new BigDecimal(rMaxima);
		}
		try {
			byte[] bytes = getReportesService().generarListadoControlLineasVenta(fechaDesde, fechaHasta, rentaMinima, rentaMaxima);
			return bytes;
		} catch (Exception exc) {
			return null;
		} finally {
			System.out.println("Tiempo total reporte Control Plus: " + (new Date().getTime() - start));
		}

	}

	public byte[] getReporteLiquidacion(Date fechaDesde, Date fechaHasta, BigDecimal gastosPeriodo) {
		long start = new Date().getTime();
		try {
			return getLiquidacionService().generarLiquidacion(fechaDesde, fechaHasta, gastosPeriodo);
		} catch (Exception exc) {
			return null;
		} finally {
			System.out.println("Tiempo total reporte Control Plus: " + (new Date().getTime() - start));
		}

	}
	
	public byte[] getLiquidacionVendedores(Date fechaDesde, Date fechaHasta, String compsIncluidos, String compsExcluidos) {
		long start = new Date().getTime();
		
		String[] compsInc = null;
		if (compsIncluidos != null) {
			compsInc = compsIncluidos.split(",");
		}
		String[] compsExc = null;
		if (compsExcluidos != null) {
			compsExc = compsExcluidos.split(",");
		}

		try {
			return getReportesService().generarLiquidacionVendedores(fechaDesde, fechaHasta, compsInc, compsExc);
		} catch (Exception exc) {
			return null;
		} finally {
			System.out.println("Tiempo total reporte Control Plus: " + (new Date().getTime() - start));
		}

	}


	public Boolean esComprobanteAster(String codigo) {
		String regex = System.getProperty("facturator.comprobantes.aster");
		if (regex != null) {
			String[] values = regex.split(",");
			for (int i = 0; i < values.length; i++) {
				if (values[i].trim().equals(codigo.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	public List<String> getCodigosArticulosServicio() {
		ArrayList<String> result = new ArrayList<String>();

		String regex = System.getProperty("facturator.articulos.servicio");
		if (regex != null) {
			String[] values = regex.split(",");
			for (int i = 0; i < values.length; i++) {
				result.add(values[i]);
			}
		}

		return result;
	}

	public Boolean esCotizacionDeVenta(String codigoComprobante) {
		String regex = System.getProperty("facturator.comprobantes.cotizacionVenta");
		if (regex != null) {
			String[] values = regex.split(",");
			for (int i = 0; i < values.length; i++) {
				if (values[i].toString().equals(codigoComprobante)) {
					return true;
				}
			}
		}
		return false;

	}

	public Boolean esOrdenDeVenta(String codigoComprobante) {
		String regex = System.getProperty("facturator.comprobantes.ordenVenta");
		if (regex != null) {
			String[] values = regex.split(",");
			for (int i = 0; i < values.length; i++) {
				if (values[i].toString().equals(codigoComprobante)) {
					return true;
				}
			}
		}
		return false;
	}

	public Boolean esSolicitudCompra(String codigoComprobante) {
		String regex = System.getProperty("facturator.comprobantes.solicitudCompra");
		if (regex != null) {
			String[] values = regex.split(",");
			for (int i = 0; i < values.length; i++) {
				if (values[i].toString().equals(codigoComprobante)) {
					return true;
				}
			}
		}
		return false;

	}

	// public boolean validarUsuario(String userName, String password) {
	// WebClient webClient = new WebClient();
	// HtmlPage page =
	// webClient.getPage("http://server2:8080/libra/servlet/hlibra");
	// System.out.println("follows page:\n" + page.asXml());
	// List<HtmlForm> forms = page.getForms();
	// System.out.println("forms found: " );
	// for (HtmlForm htmlForm : forms) {
	// System.out.println("form: " + htmlForm);
	// }
	// HtmlInput emp = page.getForms().get(0).getInputByName("_EMPID");
	// emp.setValueAttribute("FULLTIME");
	//
	// HtmlInput pass = page.getForms().get(0).getInputByName("_EMPPASSWORD");
	// pass.setValueAttribute("FULLTIME");
	//
	// HtmlForm form = page.getForms().get(0);
	// HtmlInput ok = form.getInputByName("CONFIRMAR");
	// ok.click();
	//
	// return "user".equals(userName) && password.equals("us3r");
	// }
	//

	public <T extends CodigoNombreEntity> List<T> getCatalogoByName(String catalog) {
		return getCatalogService().getCatalog(catalog);
	}

	public <D extends CodigoNombreEntity> D findCatalogEntity(String catalog, String codigo) {
		return getCatalogService().findCatalogEntity(catalog, codigo);
	}

	public  String  sendEmail(String[] addresses, String subject, String body, byte[] attachmentData) {
		EmailSenderService eMailSender = new EmailSenderService();
		eMailSender.setSubjectText(subject);
		eMailSender.setBodyText(body);
		eMailSender.setAddresses(addresses);
		eMailSender.setAttachmentData(attachmentData);
		
		StringBuffer result = new StringBuffer("<result>");
		try {
			eMailSender.sendEmail();
			result.append("<state>true</state>");
			result.append("</result>");
		} catch (MessagingException me) {
			result.append("<state>false</state>");
			
			String details = me.toString().replace("<", "[").replace(">","]");
			
			result.append("<message>").append(details).append("</message>");
			result.append("</result>");
			
			me.printStackTrace();
		} 
		return result.toString();

	}

	public ParametrosAdministracion getParametrosAdministracion() {
		return getCatalogService().getParametrosAdministracion();
	}

	public List<DocumentoDTO> getDocumentosPendientes(String cliente) {
		return getService().pendientesCliente(cliente);
	}

	public List<DocumentoDTO> getTrazabilidad(String docId) {
		return getService().getTrazabilidad(docId);
	}

	public BigDecimal getStock(String articulo, String deposito) {
		return getService().getStock(articulo, deposito);
	}
	
	public List<StockActual> getStockActual(String articuloId) {
		return getService().getStockActual(articuloId);
	}

	public List<AgendaTareaDTO> getAgendaTareas(String usuario) {
		return getAgendaTareaService().getAgendaTareas(usuario);
	}
	
	public AgendaTarea getAgendaTarea(String tareaId) {
		return getAgendaTareaService().getAgendaTarea(tareaId);
	}

	public List<DocumentoDeudor> getDocumentosDeudores() {
		List<DocumentoDeudor> deudores = getLiquidacionService().getDocumentosDeudores(new Date());
		return deudores;
	}

	private LiquidacionService getLiquidacionService() {
		try {
			LiquidacionService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (LiquidacionService) ctx.lookup("facturator-server-ear/LiquidacionServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (LiquidacionService) liquidacionServiceProxyClassConstructor.newInstance(handler);

			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private DescuentosPrometidosService getDescuentosPrometidosService() {
		try {
			DescuentosPrometidosService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (DescuentosPrometidosService) ctx.lookup("facturator-server-ear/DescuentosPrometidosServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (DescuentosPrometidosService) descuentosServiceProxyClassConstructor.newInstance(handler);

			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private ClientesService getClientesService() {
		try {
			ClientesService service;
			InitialContext ctx = new InitialContext();
			service = (ClientesService) ctx.lookup("facturator-server-ear/ClientesServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (ClientesService) clientesServiceProxyClassConstructor.newInstance(handler);
			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private ArticulosService getArticulosService() {
		try {
			ArticulosService service;
			InitialContext ctx = new InitialContext();
			service = (ArticulosService) ctx.lookup("facturator-server-ear/ArticulosServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (ArticulosService) articulosServiceProxyClassConstructor.newInstance(handler);
			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	
	
	private UsuariosService getUsuariosService() {
		try {
			UsuariosService service;
			InitialContext ctx = new InitialContext();
			service = (UsuariosService) ctx.lookup("facturator-server-ear/UsuariosServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (UsuariosService) usuariosServiceProxyClassConstructor.newInstance(handler);
			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private ReportesService getReportesService() {
		try {
			ReportesService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (ReportesService) ctx.lookup("facturator-server-ear/ReportesServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (ReportesService) reportesServiceProxyClassConstructor.newInstance(handler);

			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private EFacturaService getEFacturaService() {
		try {
			EFacturaService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (EFacturaService) ctx.lookup("facturator-server-ear/EFacturaServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (EFacturaService) eFacturaServiceProxyClassConstructor.newInstance(handler);

			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private AgendaTareaService getAgendaTareaService() {
		try {
			AgendaTareaService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (AgendaTareaService) ctx.lookup("facturator-server-ear/AgendaTareaServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (AgendaTareaService) agendaTareaServiceProxyClassConstructor.newInstance(handler);

			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private EntregaService getEntregaService() {
		try {
			EntregaService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (EntregaService) ctx.lookup("facturator-server-ear/EntregaServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (EntregaService) entregaServiceProxyClassConstructor.newInstance(handler);

			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	private FanfoldService getFanfoldService() {
		try {
			InitialContext ctx = new InitialContext();
			FanfoldService service = (FanfoldService) ctx.lookup("facturator-server-ear/FanfoldServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (FanfoldService) fanfoldServiceProxyClassConstructor.newInstance(handler);

			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}


	private MonedasCotizacionesService getMonedasCotizacionesService() {
		try {
			MonedasCotizacionesService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (MonedasCotizacionesService) ctx.lookup("facturator-server-ear/MonedasCotizacionesServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (MonedasCotizacionesService) monedasCotizacionesServiceProxyClassConstructor.newInstance(handler);

			return service;

		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private AuditoriaService getAuditoriaService() {
		try {
			AuditoriaService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (AuditoriaService) ctx.lookup("facturator-server-ear/AuditoriaServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (AuditoriaService) auditoriaServiceProxyClassConstructor.newInstance(handler);

			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private CatalogService getCatalogService() {
		try {
			CatalogService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (CatalogService) ctx.lookup("facturator-server-ear/CatalogServiceImpl/local");

			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (CatalogService) catalogServiceProxyClassConstructor.newInstance(handler);

			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private static class MyInvocationHandler implements InvocationHandler {

		private Object proxiedService;

		public MyInvocationHandler(Object proxiedService) {
			this.proxiedService = proxiedService;
		}

		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			String name = method.getName();
			if (name.equals("hashCode")) {
				return proxiedService.hashCode();
			} else if (name.equals("equals")) {
				return proxiedService.equals(args[0]);
			} else if (name.equals("toString")) {
				return proxiedService.toString();
			} else {
				UserPrincipalLocator.userPrincipalTL.set(FlexContext.getUserPrincipal());
				try {
					return method.invoke(proxiedService, args);
				} catch (InvocationTargetException e) {
					Throwable cause = e.getCause();
					logThrowable(cause);
					throw cause;
				} catch (Throwable e) {
					logThrowable(e);
					throw e;
				} finally {
					UserPrincipalLocator.userPrincipalTL.set(null);
				}
			}
		}

		private void logThrowable(Throwable throwable) {
			System.out.println(throwable);
			throwable.printStackTrace();
		}
	}

	public void updateClaveSup(String userId, String claveSup) throws PermisosException{
		getUsuariosService().updateClaveSup(userId, claveSup);
	}
	
	public void updateEmail(String userId, String email) throws PermisosException{
		getUsuariosService().updateEmail(userId, email);
	}
	
	public void updateUsuario(Usuario user) throws PermisosException{
		getUsuariosService().update(user);
	}

	public Usuario getUsuarioLogin() {
		return getUsuariosService().getUsuarioLogin();
	}

	public List<PreciosVenta> getPreciosVentaUsuario() {
		return getUsuariosService().getPreciosVentaUsuario();
	}

	public Collection<Comprobante> getComprobantesPermitidosUsuario() {
		return getUsuariosService().getComprobantesPermitidosUsuario();
	}

}