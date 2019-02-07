package uy.com.tmwc.facturator.spi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uy.com.tmwc.facturator.dto.DocumentoDTO;
import uy.com.tmwc.facturator.dto.DocumentoQuery;
import uy.com.tmwc.facturator.dto.ParticipacionEnCobranza;
import uy.com.tmwc.facturator.entity.ArticuloCompraVentaCosto;
import uy.com.tmwc.facturator.entity.ArticuloPrecioFabricaCosto;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.LineaDocumento;
import uy.com.tmwc.facturator.entity.ParticipacionAfilador;
import uy.com.tmwc.facturator.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.entity.ResumenEntrega;
import uy.com.tmwc.facturator.entity.SerieNumero;
import uy.com.tmwc.facturator.entity.StockActual;
import uy.com.tmwc.facturator.entity.VinculoDocumentos;
import uy.com.tmwc.facturator.rapi.PermisosException;
import uy.com.tmwc.facturator.rapi.RUTINA_MODIFCOSTO_ENUM;

public interface DocumentoDAOService {
	String persist(Documento paramDocumento) throws PermisosException;

	Boolean remove(Documento paramDocumento) throws PermisosException;

	Boolean finalizarMovimientoStock(Documento paramDocumento) throws PermisosException;

	Boolean finalizarCompra(Documento paramDocumento) throws PermisosException;

	void merge(Documento paramDocumento) throws PermisosException;

	void merge(Documento paramDocumento, Boolean verify) throws PermisosException;

	SerieNumero generarSerieNumero(String paramString);

	uy.com.tmwc.facturator.entity.Fanfold generarFanfold(String numFoldId);

	BigDecimal getTipoCambio(String paramString, Date paramDate);

	BigDecimal getTipoCambioFiscal(String paramString, Date paramDate);

	List<LineaDocumento> getAntecedentes(String paramString1, String paramString2, int paramInt, boolean venta) throws PermisosException;

	List<DocumentoDTO> queryDocumentos(DocumentoQuery paramDocumentoQuery);

	long countDocumentos(DocumentoQuery paramDocumentoQuery);

	Documento findDocumento(String paramString) throws PermisosException;

	List<DocumentoDTO> getTrazabilidad(String docId);

	List<DocumentoDTO> getSolicitudImportacion(String processId);

	List<ResumenEntrega> getResumenEntregas(Date paramDate1, Date paramDate2);

	List<ParticipacionVendedor> getParticipaciones(Date paramDate1, Date paramDate2, String[] compsIncluidos, String[] compsExcluidos);

	List<ParticipacionVendedor> getParticipacionesEnContados(Date fechaDesde, Date fechaHasta, Date fechaCorte);

	List<ParticipacionEnCobranza> getParticipacionesEnCobranzas(Date fechaDesde, Date fechaHasta, Date fechaCorte);

	Collection<VinculoDocumentos> getCobranzas(Date fechaDesde, Date fechaHasta, Date fechaCorte);
	
	void grabarCostosOperativos(Date fechaDesde, Date fechaHasta, Map<String, BigDecimal> paramMap);

	List<DocumentoDTO> pendientesCliente(String cliId);

	List<Documento> getDocumentosDeudores();

	BigDecimal getStock(String articulo, short deposito);

	List<Documento> getDocumentos(Date fechaDesde, Date fechaHasta, String moneda);

	int modificarCostos(String codart, Date dateDesde, Date dateHasta, RUTINA_MODIFCOSTO_ENUM costoAnterior, BigDecimal valorCostoAnterior, BigDecimal costoNuevo, String monedaNuevoCosto,
			BigDecimal tcd);

	List<LineaDocumento> getLineasCompraCliente(String cliId, Date fromDate, Date toDate, int limit);

	List<LineaDocumento> getLineasCotizadas(String cliente, String artId, String comprobante, Date fromDate, Date toDate, int limit);

	void modificarArticuloPrecio(String codart, String minorista, String industria, String distribuidor) throws PermisosException;

	List<ArticuloPrecioFabricaCosto> getPreciosArticuloDocumento(uy.com.tmwc.facturator.entity.Documento doc);

	void updateArticulosPrecios(List<ArticuloPrecioFabricaCosto> lista, Boolean updateCosto);

	List<StockActual> getStockActual(String articuloId);

	List<ArticuloPrecioFabricaCosto> getCostoArticulos(Documento doc);

	void updateArticulosCostos(List<ArticuloPrecioFabricaCosto> lista);

	List<ArticuloCompraVentaCosto> getCompraVentaCostos(uy.com.tmwc.facturator.entity.Documento doc);

	List<ArticuloCompraVentaCosto> getComprasPlazaCostos(Date fechaDesde, Date fechaHasta, Boolean mostrarTodas);

	void updateCostosArticuloDocumentos(List<ArticuloCompraVentaCosto> lista) throws PermisosException;

	Map<String, Object[]> getParticipacionesCobranza(Date fechaDesde, Date fechaHasta, String[] compsIncluidos, String[] compsExcluidos);
	
	Map<String, ArrayList<ParticipacionAfilador>> getParticipacionesAfilados(Date fechaDesde, Date fechaHasta, BigDecimal value);

}