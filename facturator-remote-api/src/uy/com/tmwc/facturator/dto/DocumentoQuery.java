package uy.com.tmwc.facturator.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class DocumentoQuery implements Serializable {
	private static final long serialVersionUID = 1L;
	private int start;
	private int limit;

	private String serie;
	private BigInteger numero;
	private Long tipoComprobante;
	private String cliente;
	private String moneda;
	private String proveedor;
	private String articulo;
	private Date fechaDesde;
	private Date fechaHasta;
	private Boolean pendiente;
	private Boolean tieneSaldo;

	private Boolean emitido;
	
	private String orden = "DESC";
	
	private Boolean esSolicitud = Boolean.FALSE;
	
	private Boolean esRecibo = Boolean.FALSE;
	
	private Boolean esCheque = Boolean.FALSE;
	
	private String comprobantes;
	
	private String compsExcluidos;
	
	private String lineaConcepto;
	
	public DocumentoQuery() {
	}

	public DocumentoQuery(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}

	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return this.limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public BigInteger getNumero() {
		return numero;
	}

	public void setNumero(BigInteger numero) {
		this.numero = numero;
	}

	public Long getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(Long tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public Date getFechaDesde() {
		return fechaDesde;
	}

	public void setFechaDesde(Date fechaDesde) {
		this.fechaDesde = fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public void setFechaHasta(Date fechaHasta) {
		this.fechaHasta = fechaHasta;
	}

	public Boolean getPendiente() {
		return pendiente;
	}

	public void setPendiente(Boolean pendiente) {
		this.pendiente = pendiente;
	}

	public String getComprobantes() {
		return comprobantes;
	}

	public void setComprobantes(String comprobantes) {
		this.comprobantes = comprobantes;
	}

	public Boolean getEmitido() {
		return emitido;
	}

	public void setEmitido(Boolean emitido) {
		this.emitido = emitido;
	}

	public String getArticulo() {
		return articulo;
	}

	public void setArticulo(String articulo) {
		this.articulo = articulo;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

	public Boolean getEsSolicitud() {
		return esSolicitud;
	}

	public void setEsSolicitud(Boolean esSolicitud) {
		this.esSolicitud = esSolicitud;
	}

	public String getCompsExcluidos() {
		return compsExcluidos;
	}

	public void setCompsExcluidos(String compsExcluidos) {
		this.compsExcluidos = compsExcluidos;
	}

	public String getLineaConcepto() {
		return lineaConcepto;
	}

	public void setLineaConcepto(String lineaConcepto) {
		this.lineaConcepto = lineaConcepto;
	}

	public Boolean getEsRecibo() {
		return esRecibo;
	}

	public void setEsRecibo(Boolean esRecibo) {
		this.esRecibo = esRecibo;
	}

	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public Boolean getTieneSaldo() {
		return tieneSaldo;
	}

	public void setTieneSaldo(Boolean tieneSaldo) {
		this.tieneSaldo = tieneSaldo;
	}

	public Boolean getEsCheque() {
		return esCheque;
	}

	public void setEsCheque(Boolean esCheque) {
		this.esCheque = esCheque;
	}


}