package uy.com.tmwc.facturator.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.dto.AntecedentesArticulo;
import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.dto.DocumentoDTO;
import uy.com.tmwc.facturator.dto.DocumentoQuery;
import uy.com.tmwc.facturator.entity.Articulo;
import uy.com.tmwc.facturator.entity.ArticuloCompraVentaCosto;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.entity.ArticuloPrecioFabricaCosto;
import uy.com.tmwc.facturator.entity.Auditoria;
import uy.com.tmwc.facturator.entity.Cliente;
import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.LineaDocumento;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.SerieNumero;
import uy.com.tmwc.facturator.entity.StockActual;
import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.entity.ValidationException;
import uy.com.tmwc.facturator.rapi.AuditoriaService;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.rapi.Cotizaciones;
import uy.com.tmwc.facturator.rapi.DocumentoService;
import uy.com.tmwc.facturator.rapi.EFacturaService;
import uy.com.tmwc.facturator.rapi.PermisosException;
import uy.com.tmwc.facturator.rapi.RUTINA_MODIFCOSTO_ENUM;
import uy.com.tmwc.facturator.rapi.UserPrincipalLocator;
import uy.com.tmwc.facturator.rapi.UsuariosService;
import uy.com.tmwc.facturator.server.util.DTO2ModelMappingService;
import uy.com.tmwc.facturator.spi.DocumentoDAOService;
import uy.com.tmwc.facturator.utils.LogicaCotizacion;

@Name("documentoService")
@Stateless
@Local(DocumentoService.class)
@Remote(DocumentoService.class)
public class DocumentoServiceImpl implements DocumentoService {

	@EJB
	DocumentoDAOService documentoDAOService;

	@EJB
	CatalogService catalogService;
	
	@EJB
	AuditoriaService auditoriaService;

	@EJB
	DTO2ModelMappingService dto2ModelMappingService;
	
	@EJB
	UsuariosService usuariosService;

	@EJB
	EFacturaService efacturaService;
	
	public String alta(Documento documento, Auditoria auditoria) throws ValidationException, PermisosException {
		documento = replaceReadonlyEntities(documento);
		if (documento.getDocId() != null) {
			throw new IllegalStateException("Usar el metodo modificar para documentos ya existentes");
		}
		preSaveDocumento(documento);

		String docId = this.documentoDAOService.persist(documento);

		if (auditoria != null && docId != null) {
			auditoria.setDocId(docId);
			auditoria.setAudFechaHora(new Date());
			auditoriaService.alta(auditoria);
		}

		return docId;
	}

	public Boolean borrar(Documento documento) throws ValidationException, PermisosException {
		Documento current = this.documentoDAOService.findDocumento(documento.getDocId());
		verificarFecha(current, documento);

		if (current.isEmitido()) {
			throw new ValidationException("Este documento ya ha sido emitido por lo que no es posible borrarlo");
		}
		return this.documentoDAOService.remove(current);
	}

	public Boolean borrarCotizaciones(String cotizaciones) throws ValidationException, PermisosException {
		String[] docId = null;
		if (cotizaciones != null) {
			docId = cotizaciones.split(",");
		}

		for (int i = 0; i < docId.length; i++) {
			Documento current = this.documentoDAOService.findDocumento(docId[i]);
			this.documentoDAOService.remove(current);
		}

		return true;
	}
	
	public Documento guardar(Documento documento) throws ValidationException, PermisosException {
		return guardar(documento, null);
	}
	
	public Documento guardar(Documento documento, Auditoria auditoria) throws ValidationException, PermisosException {
		String docId = documento.getDocId();
		Documento current = this.documentoDAOService.findDocumento(docId);
		verificarFecha(current, documento);

		if (current.isEmitido()) { // Revisar si se cambiaron los importes de iva, total, sub-total post emisión.
			if (!documento.calcularIva().setScale(2, RoundingMode.HALF_EVEN).equals(current.calcularIva().setScale(2, RoundingMode.HALF_EVEN))) {
				throw new ValidationException("Iva no válido");
			}
			if (!documento.getTotal().setScale(2, RoundingMode.HALF_EVEN).equals(current.getTotal().setScale(2, RoundingMode.HALF_EVEN))) {
				throw new ValidationException("Total no válido");
			}
			if (!documento.getSubTotal().setScale(2, RoundingMode.HALF_EVEN).equals(current.getSubTotal().setScale(2, RoundingMode.HALF_EVEN))) {
				throw new ValidationException("Sub-Total no válido");
			}
			
			if (current.getComprobante().getCodigo().equals("122")) { // Es pro-forma de importación y ya esta emitida actualizo nacionalización.
				BigDecimal coeficienteImp = documento.getCoeficienteImp();
				
				List<DocumentoDTO> documentsImp = null;
				if (documento.getPrevDocId() != null) {
					documentsImp = this.documentoDAOService.getSolicitudImportacion(documento.getPrevDocId());
				} else {
					documentsImp = this.documentoDAOService.getSolicitudImportacion(documento.getDocId());
				}
				
				if (documentsImp != null && documentsImp.size() > 0) {
					DocumentoDTO docImp = documentsImp.get(0);
					Documento document = this.documentoDAOService.findDocumento(docImp.getDocId());
					
					current.getLineas().actualizarPrecioLineas(coeficienteImp);
					current.setTotal(null);
					document.setLineas(current.getLineas());					
					document.setTotal(current.getTotal());
					
					this.documentoDAOService.merge(document);
				}				
			}
		
		}

		documento = replaceReadonlyEntities(documento);

		if (!documento.getComprobante().isMueveCaja()) {
			documento.setCajaId(null);
		}

		Date now = new Date();
		documento.setRegistroFecha(now);
		documento.setRegistroHora(now);
		Principal ppal = UserPrincipalLocator.userPrincipalTL.get();

		short usuarioId;
		if (ppal != null && ppal.getName() != null) {
			try {
				usuarioId = Short.parseShort(ppal.getName());
			} catch (NumberFormatException nfe) {
				throw new ValidationException("Código de usuario inesperado: " + ppal.getName());
			}
		} else {
			throw new ValidationException("No se pudo determinar el usuario");
		}
		documento.setUsuarioId(usuarioId);

		documento.getLineas().fixNumerosLineas();
		
		if (auditoria != null) {
			auditoria.setAudFechaHora(new Date());
			auditoriaService.alta(auditoria);
		}

		this.documentoDAOService.merge(documento);
	
		Documento docGuardado = findDocumento(docId);
		return docGuardado;
	}

	public void modificar(Documento documento, Auditoria auditoria) throws ValidationException, PermisosException {
		Documento current = this.documentoDAOService.findDocumento(documento.getDocId());
		verificarFecha(current, documento);

		documento = replaceReadonlyEntities(documento);

		if (documento.isEmitible() && documento.isEmitido()) {
			throw new RuntimeException("El documento ya fué emitido y no se puede modificar.");
		}
		preSaveDocumento(documento);

		this.documentoDAOService.merge(documento);
//
//		if (auditoria != null) {
//			auditoria.setAudFechaHora(new Date());
//			auditoriaService.alta(auditoria);
//		}

	}
	
	public SerieNumero emitir(Documento documento, String fanfoldId) throws ValidationException, PermisosException {
		Documento current = this.documentoDAOService.findDocumento(documento.getDocId());
		verificarFecha(current, documento);

		SerieNumero serieNumero = null;
		if (current.isTieneSerieNumero()) {
			serieNumero = current.getSerieNumero();
			current.toEmitido();
		} else {
			serieNumero = this.documentoDAOService.generarSerieNumero(current.getComprobante().getCodigo());
			current.toEmitido(serieNumero);
		}
		

		if (!documento.getComprobante().isMueveCaja()) {
			current.setCajaId(null);
		}
		current.setEmitidoPor(documento.getEmitidoPor());

		Short cfeStatus = documento.getDocCFEstatus();
		if (cfeStatus.equals(new Short("1"))) {
			this.documentoDAOService.merge(current, Boolean.FALSE);
		} else {
			this.documentoDAOService.merge(current);
		}

		return current.getSerieNumero();
	}
	


	public Boolean finalizarMovimientoStock(Documento documento) throws PermisosException {
		if (!documento.isPendiente()) {
			throw new IllegalStateException("Este Movimiento de Stock ya ha sido finalizado");
		}
		if (documento.getComprobante().getTipo() != Comprobante.MOVIMIENTO_DE_STOCK_DE_CLIENTE && documento.getComprobante().getTipo() != Comprobante.MOVIMIENTO_DE_STOCK_DE_PROVEEDORES) {
			throw new IllegalStateException("Este Documento no es un Movimiento de Stock");
		}

		if (!documento.getComprobante().isMueveCaja()) {
			documento.setCajaId(null);
		}
		return this.documentoDAOService.finalizarMovimientoStock(documento);
	}
	
	public Boolean finalizarCompra(Documento documento) throws PermisosException {
		if (!documento.isPendiente()) {
			throw new IllegalStateException("Esta compra ya ha sido finalizado");
		}
		if (!documento.getComprobante().isMueveCaja()) {
			documento.setCajaId(null);
		}
		return this.documentoDAOService.finalizarCompra(documento);
	}

	public Boolean finalizarRecibo(Documento documento) throws PermisosException {
		if (!documento.isPendiente()) {
			throw new IllegalStateException("Este recibo ya ha sido finalizado");
		}
		if (!documento.getComprobante().isMueveCaja()) {
			documento.setCajaId(null);
		}
		return this.documentoDAOService.finalizarCompra(documento);
	}


	private Boolean verificarFecha(Documento current, Documento modificado) throws ValidationException {
		Date currentDate = current.getRegistroFecha();
		Date currentTime = current.getRegistroHora();

		Date newDate = modificado.getRegistroFecha();
		Date newTime = modificado.getRegistroHora();

		if (modificado.getComprobante().getCodigo().equals("122") || modificado.getComprobante().getCodigo().equals("124")) {
			return Boolean.TRUE;
		}
		
		if (!currentDate.equals(newDate) || !currentTime.equals(newTime)) {
			String msg;
			if (current.getSerie() != null || current.getNumero() != null) {
				msg = "El Documento " + (current.getSerie() != null ? current.getSerie() : "") + (current.getNumero() != null ? current.getNumero() : "") + " ha sido modificado por otro usuario.";
			} else {
				msg = "El Documento ha sido modificado por otro usuario.";
			}
			throw new RuntimeException(msg);
		}

		return Boolean.TRUE;

	}

	private void preSaveDocumento(Documento documento) throws ValidationException {
		Date now = new Date();
		documento.setRegistroFecha(now);
		documento.setRegistroHora(now);
		Principal ppal = UserPrincipalLocator.userPrincipalTL.get();
		short usuarioId;
		if (ppal != null && ppal.getName() != null) {
			try {
				usuarioId = Short.parseShort(ppal.getName());
			} catch (NumberFormatException nfe) {
				throw new ValidationException("Código de usuario inesperado: " + ppal.getName());
			}
		} else {
			throw new ValidationException("No se pudo determinar el usuario");
		}

		if (!documento.getComprobante().isMueveCaja()) {
			documento.setCajaId(null);
		}
		if (documento.getComprobante().isRecibo()) {
			documento.setCajaId(new Short("1"));
		}		
		if (documento.getEstado() == null) {
			documento.setEstado("");
		}
		
		documento.setUsuarioId(usuarioId);

		documento.establecerFormaPago();

		documento.getLineas().fixNumerosLineas();

		documento.sanityCheck();

		documento.validate();
	}
	

	public BigDecimal getTipoCambio(String monedaOrigen, String monedaDestino, Date fecha) {
		return new LogicaCotizacion(Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_DOLAR).getTipoCambio(monedaOrigen, monedaDestino, fecha, new LogicaCotizacion.GetTipoCambio() {
			public BigDecimal getTipoCambio(String monedaOrigen, Date fecha) {
				return documentoDAOService.getTipoCambio(monedaOrigen, fecha);
			}
		});
	}

	public BigDecimal getTipoCambioFiscal(String monedaId, Date fecha) {
		return documentoDAOService.getTipoCambioFiscal(monedaId, fecha);
	}

	public int obtenerUltimoDiaMes (int anio, int mes) {
		Calendar cal = Calendar.getInstance();
		cal.set(anio, mes-1, 1);
	
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public BigDecimal getMontoMayorCotizacion(String articulo, Date fechaPrecio, String monedaPrecio, BigDecimal precio, String monedaFacturacion, Boolean esRemito, Cotizaciones oCotizaciones) {
		monedaFacturacion = Moneda.getCodigoMonedaNoAster(monedaFacturacion);
		return new LogicaCotizacion(Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_DOLAR).getMontoMayorCotizacion(fechaPrecio, monedaPrecio, precio, monedaFacturacion, oCotizaciones, esRemito,
				new LogicaCotizacion.GetTipoCambio() {
					public BigDecimal getTipoCambio(String monedaOrigen, Date fecha) {
						BigDecimal tipoCambio = documentoDAOService.getTipoCambio(monedaOrigen, fecha);
						return tipoCambio;
					}
				});
	}
	

	public ArticuloPrecio getArticuloPrecio(String articulo, String preciosVenta) throws PermisosException {
		Articulo a = catalogService.findCatalogEntity("Articulo", articulo);
		String familiaId = a.getFamiliaId();
		
		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		boolean esSupervisor = usuarioLogin.isSupervisor();
		
		Boolean hasPerm = esSupervisor || Usuario.USUARIO_SUPERVISOR.equals(permisoId) || Usuario.USUARIO_ADMINISTRADOR.equals(permisoId) 
			|| Usuario.USUARIO_FACTURACION.equals(permisoId) || Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId);
		
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
			return this.catalogService.getPrecioArticulo(preciosVenta, articulo);
		} else {
			throw new PermisosException("'" + usuarioLogin.getNombre() + "' no tiene permisos.");

		}

	}

	public BigDecimal getPrecioSugerido(String articulo, String preciosVenta, String monedaFacturacion, Cotizaciones oCotizaciones) {
		ArticuloPrecio precio = this.catalogService.getPrecioArticulo(preciosVenta, articulo);
		if (precio == null) {
			return BigDecimal.ZERO;
		}

		Articulo art = precio.getArticulo();

		BigDecimal precioSinIva = precio.getImporteArticuloSinIVA();
		if (precioSinIva == null) {
			return BigDecimal.ZERO;
		}
		if (precio.getMoneda() == null) {
			return BigDecimal.ZERO;
		}
		
		Date fechaCosto = art.getFechaCosto();
		if (fechaCosto == null) {
			fechaCosto = new Date();
		}

		return getMontoMayorCotizacion(articulo, fechaCosto, precio.getMoneda().getCodigo(), precioSinIva, monedaFacturacion, Boolean.FALSE, oCotizaciones);
	}

	public List<AntecedentesArticulo> getAntecedentes(String articulo, String cliente, int limit, boolean venta) throws PermisosException {
		List<LineaDocumento> lineas = this.documentoDAOService.getAntecedentes(articulo, cliente, limit, venta);

		ArrayList<AntecedentesArticulo> antecedentes = new ArrayList<AntecedentesArticulo>(lineas.size());

		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		String usuarioVend = usuarioLogin.getVenId();
		
		for (LineaDocumento linea : lineas) {
			Documento documento = linea.getDocumento();			
			
			AntecedentesArticulo antecedente = new AntecedentesArticulo();
			antecedente.setCantidad(linea.getCantidad());
			antecedente.setFecha(documento.getFecha());
			antecedente.setPrecioUnitario(linea.getPrecioUnitario());
			antecedente.setCosto(linea.getCosto());
			antecedente.setNeto(linea.getNeto());
			antecedente.setRenta(linea.getPorcentajeUtilidad());
			antecedente.setTipoCambio(documento.getDocTCC());
			antecedente.setDocumentoSerie(documento.getSerie());
			antecedente.setDocumentoNumero(documento.getNumero());
			
			if (documento.getComprobante() != null) {
				antecedente.setComprobante(new CodigoNombre(documento.getComprobante()));
			}
			if (documento.getMoneda() != null) {
				antecedente.setMoneda(new CodigoNombre(documento.getMoneda()));
			}

			if (Usuario.USUARIO_ALIADOS_COMERCIALES.equals(permisoId) && venta) {
				Cliente c = documento.getCliente();
				String vendedor = c.getVendedor() != null ? c.getVendedor().getCodigo() : "";
				String encargadoCuenta = c.getEncargadoCuenta();
				
				if (vendedor.equals(usuarioVend) || vendedor.equals(encargadoCuenta)) {
					antecedentes.add(antecedente);
				} 
			} else {
				if (documento.getCliente() != null) {
					antecedente.setCliente(new CodigoNombre(documento.getCliente()));
				} else if (documento.getProveedor() != null) {
					antecedente.setCliente(new CodigoNombre(documento.getProveedor()));
				}				
				antecedentes.add(antecedente);
			}
			
		}

		return antecedentes;
	}

	public List<AntecedentesArticulo> getLineasCompraCliente(String cliId, Date fromDate, Date toDate, int limit) {
		List<LineaDocumento> lineas = this.documentoDAOService.getLineasCompraCliente(cliId, fromDate, toDate, limit);

		ArrayList<AntecedentesArticulo> antecedentes = new ArrayList<AntecedentesArticulo>(lineas.size());

		for (LineaDocumento linea : lineas) {
			if (linea.getArticulo() == null) {
				continue;
			}
			AntecedentesArticulo antecedente = new AntecedentesArticulo();
			Documento documento = linea.getDocumento();
			antecedente.setFecha(documento.getFecha());
			antecedente.setArticulo(linea.getArticulo());
			antecedente.setConcepto(linea.getConcepto());
			antecedente.setDocumentoSerie(documento.getSerie());
			antecedente.setDocumentoNumero(documento.getNumero());
			antecedente.setCantidad(linea.getCantidad());
			antecedente.setTipoCambio(documento.getDocTCC());
			antecedente.setPrecioUnitario(linea.getPrecioUnitario());
			antecedente.setCosto(linea.getCosto());
			antecedente.setNeto(linea.getNeto());
			antecedente.setRenta(linea.getPorcentajeUtilidad());
			if (documento.getComprobante() != null) {
				antecedente.setComprobante(new CodigoNombre(documento.getComprobante()));
			}
			if (documento.getMoneda() != null) {
				antecedente.setMoneda(new CodigoNombre(documento.getMoneda()));
			}
			antecedentes.add(antecedente);
		}

		return antecedentes;
	}

	public List<LineaDocumento> getLineasCotizadas(String cliId, String artId, String cmpId, Date fromDate, Date toDate, int limit) {
		return this.documentoDAOService.getLineasCotizadas(cliId, artId, cmpId, fromDate, toDate, limit);
	}

	public List<DocumentoDTO> queryDocumentos(DocumentoQuery query) {
		return this.documentoDAOService.queryDocumentos(query);
	}

	public List<DocumentoDTO> pendientesCliente(String cliId) {
		return this.documentoDAOService.pendientesCliente(cliId);
	}

	public long countDocumentos(DocumentoQuery documentoQuery) {
		return this.documentoDAOService.countDocumentos(documentoQuery);
	}

	public Documento findDocumento(String docId) throws PermisosException {
		return this.documentoDAOService.findDocumento(docId);
	}

	public List<DocumentoDTO> getTrazabilidad(String docId) {
		return this.documentoDAOService.getTrazabilidad(docId);
	}

	private Documento replaceReadonlyEntities(Documento documento) {
		return (Documento) this.dto2ModelMappingService.getDozerBeanMapper().map(documento, Documento.class);
	}

	public BigDecimal getStock(String articulo, String deposito) {
		return documentoDAOService.getStock(articulo, deposito != null ? Short.parseShort(deposito) : 0);
	}

	public SerieNumero generarSerieNumero(String comprobanteId) {
		return documentoDAOService.generarSerieNumero(comprobanteId);
	}

	public void modificarArticuloPrecio(String codart, String pminorista, String pindustria, String pdistribuidor) throws PermisosException {
		documentoDAOService.modificarArticuloPrecio(codart, pminorista, pindustria, pdistribuidor);
	}

	public int modificarCostos(String codart, Date dateDesde, Date dateHasta, RUTINA_MODIFCOSTO_ENUM costoAnterior, BigDecimal valorCostoAnterior, BigDecimal costoNuevo, String monedaNuevoCosto,
			BigDecimal tcd) {
		return documentoDAOService.modificarCostos(codart, dateDesde, dateHasta, costoAnterior, valorCostoAnterior, costoNuevo, monedaNuevoCosto, tcd);
	}
	
	public List<ArticuloPrecioFabricaCosto> getPreciosArticuloDocumento(uy.com.tmwc.facturator.entity.Documento doc) {
		return documentoDAOService.getPreciosArticuloDocumento(doc);
	}
	
	public void updateArticulosPrecios(List<ArticuloPrecioFabricaCosto> lista, Boolean updateCosto) {
		documentoDAOService.updateArticulosPrecios(lista, updateCosto);
	}
	
	public List<ArticuloPrecioFabricaCosto> getCostosArticulos(uy.com.tmwc.facturator.entity.Documento doc) {
		return documentoDAOService.getCostoArticulos(doc);
	}
	
	public void updateArticulosCostos(List<ArticuloPrecioFabricaCosto> lista) {
		documentoDAOService.updateArticulosCostos(lista);
	}
	
	public List<ArticuloCompraVentaCosto> getCompraVentaCostos(uy.com.tmwc.facturator.entity.Documento doc) {
		return documentoDAOService.getCompraVentaCostos(doc);
	}

	public List<StockActual> getStockActual(String articuloId) {
		return this.documentoDAOService.getStockActual(articuloId);
	}
	
	public List<ArticuloCompraVentaCosto> getComprasPlazaCostos(Date fechaDesde, Date fechaHasta, Boolean mostrarTodas) {
		return this.documentoDAOService.getComprasPlazaCostos(fechaDesde, fechaHasta, mostrarTodas);
	}
	
	public void updateCostosArticuloDocumentos(List<ArticuloCompraVentaCosto> lista) throws PermisosException {
		this.documentoDAOService.updateCostosArticuloDocumentos(lista); 
	}
	
}