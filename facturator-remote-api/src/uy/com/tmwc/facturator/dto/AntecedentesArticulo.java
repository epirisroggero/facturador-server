package uy.com.tmwc.facturator.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import uy.com.tmwc.facturator.entity.Articulo;

public class AntecedentesArticulo implements Serializable {
	private static final long serialVersionUID = 1L;

	private CodigoNombre comprobante;
	private String docId;
	private String documentoSerie;
	private Long documentoNumero;
	private Date fecha;
	private CodigoNombre moneda;
	private Articulo articulo;

	private BigDecimal cantidad;
	private BigDecimal precioUnitario;
	private BigDecimal neto;
	private BigDecimal costo;
	private BigDecimal renta;
	private BigDecimal tipoCambio;

	private String concepto;

	private CodigoNombre cliente;

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public BigDecimal getNeto() {
		return this.neto;
	}

	public void setNeto(BigDecimal neto) {
		this.neto = neto;
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getRenta() {
		return this.renta;
	}

	public void setRenta(BigDecimal renta) {
		this.renta = renta;
	}

	public BigDecimal getTipoCambio() {
		return this.tipoCambio;
	}

	public void setTipoCambio(BigDecimal tipoCambio) {
		this.tipoCambio = tipoCambio;
	}

	public CodigoNombre getComprobante() {
		return this.comprobante;
	}

	public void setComprobante(CodigoNombre comprobante) {
		this.comprobante = comprobante;
	}

	public String getDocumentoSerie() {
		return this.documentoSerie;
	}

	public void setDocumentoSerie(String documentoSerie) {
		this.documentoSerie = documentoSerie;
	}

	public Long getDocumentoNumero() {
		return this.documentoNumero;
	}

	public void setDocumentoNumero(Long documentoNumero) {
		this.documentoNumero = documentoNumero;
	}

	public CodigoNombre getMoneda() {
		return this.moneda;
	}

	public void setMoneda(CodigoNombre moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getPrecioUnitario() {
		return this.precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public CodigoNombre getCliente() {
		return cliente;
	}

	public void setCliente(CodigoNombre cliente) {
		this.cliente = cliente;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}
}