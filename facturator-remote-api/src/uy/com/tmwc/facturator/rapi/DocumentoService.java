package uy.com.tmwc.facturator.rapi;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import uy.com.tmwc.facturator.dto.AntecedentesArticulo;
import uy.com.tmwc.facturator.dto.DocumentoDTO;
import uy.com.tmwc.facturator.dto.DocumentoQuery;
import uy.com.tmwc.facturator.dto.StockActualDTO;
import uy.com.tmwc.facturator.entity.ArticuloCompraVentaCosto;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.entity.ArticuloPrecioFabricaCosto;
import uy.com.tmwc.facturator.entity.Auditoria;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.LineaDocumento;
import uy.com.tmwc.facturator.entity.SerieNumero;
import uy.com.tmwc.facturator.entity.StockActual;
import uy.com.tmwc.facturator.entity.ValidationException;

public interface DocumentoService {
	
	String alta(Documento paramDocumento, Auditoria auditoria) throws ValidationException, PermisosException;
	
	Boolean borrar(Documento paramDocumento) throws ValidationException, PermisosException;

	Boolean borrar(Documento paramDocumento, Boolean force) throws ValidationException, PermisosException;
	
	void modificar(Documento paramDocumento, Auditoria auditoria) throws ValidationException, PermisosException;

	SerieNumero emitir(Documento paramDocumento, String fanfoldId) throws ValidationException, PermisosException;

	Boolean finalizarMovimientoStock(Documento paramDocumento) throws PermisosException;
	
	Boolean finalizarCompra(Documento paramDocumento) throws PermisosException;

	Boolean finalizarRecibo(Documento paramDocumento) throws PermisosException;
	
	Boolean finalizarGasto(Documento paramDocumento) throws PermisosException;
	
	BigDecimal getTipoCambio(String monedaOrigen, String monedaDestino, Date fecha);
	
	BigDecimal getTipoCambioFiscal(String monedaId, Date fecha);

	BigDecimal getMontoMayorCotizacion(String articulo, Date fechaPrecio, String monedaPrecio, BigDecimal precio,
			String monedaFacturacion, Boolean esRemito, Cotizaciones oCotizaciones);

	BigDecimal getPrecioSugerido(String articulo, String preciosVenta, String monedaFacturacion, Cotizaciones oCotizaciones);

	List<AntecedentesArticulo> getAntecedentes(String articulo, String cliente, int limit, boolean venta) throws PermisosException;

	List<DocumentoDTO> queryDocumentos(DocumentoQuery paramDocumentoQuery);
	
	long countDocumentos(DocumentoQuery paramDocumentoQuery);

	Documento findDocumento(String docId) throws PermisosException;

	List<DocumentoDTO> pendientesCliente(String cliId);
	
	List<DocumentoDTO> getTrazabilidad(String docId);

	BigDecimal getStock(String articulo, String deposito);
	
	Documento guardar(Documento paramDocumento) throws ValidationException, PermisosException;
	
	Documento guardar(Documento paramDocumento, Auditoria auditoria) throws ValidationException, PermisosException;
	
	SerieNumero generarSerieNumero(String comprobanteId);
	
	int modificarCostos(String codart, Date dateDesde, Date dateHasta, RUTINA_MODIFCOSTO_ENUM costoAnterior, BigDecimal valorCostoAnterior, BigDecimal costoNuevo, String monedaNuevoCosto, BigDecimal tcd);
	
	List<AntecedentesArticulo> getLineasCompraCliente(String cliId, Date fromDate, Date toDate, int limit);
	
	Boolean borrarCotizaciones(String cotizaciones) throws ValidationException, PermisosException;
	
	List<LineaDocumento> getLineasCotizadas(String cliId, String artId, String cmpId, Date fromDate, Date toDate, int limit);
	
	ArticuloPrecio getArticuloPrecio(String articulo, String preciosVenta) throws PermisosException;
	
	void modificarArticuloPrecio(String codart, String minorista, String industria, String distribuidor) throws PermisosException;
	
	List<ArticuloPrecioFabricaCosto> getPreciosArticuloDocumento(uy.com.tmwc.facturator.entity.Documento doc);
	
	void updateArticulosPrecios(List<ArticuloPrecioFabricaCosto> lista, Boolean updateCosto);
	
	List<StockActual> getStockActual(String articuloId);
	
	List<StockActualDTO> getStockEnDeposito(String depositoId);
	
	List<ArticuloPrecioFabricaCosto> getCostosArticulos(uy.com.tmwc.facturator.entity.Documento doc);
	
	void updateArticulosCostos(List<ArticuloPrecioFabricaCosto> lista);
	
	List<ArticuloCompraVentaCosto> getCompraVentaCostos(uy.com.tmwc.facturator.entity.Documento doc);

	List<ArticuloCompraVentaCosto> getComprasPlazaCostos(Date fechaDesde, Date fechaHasta, Boolean mostrarTodas);
	
	void updateCostosArticuloDocumentos(List<ArticuloCompraVentaCosto> lista) throws PermisosException;
	
	Boolean updateNotaCreditoFinancieraEnRecibo(Documento documento, String ncfId) throws PermisosException;

}