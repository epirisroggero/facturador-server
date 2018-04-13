package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import uy.com.tmwc.facturator.dto.CodigoNombre;

public class Factura implements Serializable {
	private static final long serialVersionUID = 1L;

	private Comprobante comprobante;
	private BigDecimal total;
	private BigDecimal rentaNetaComercial;
	private BigDecimal ventaNeta;
	private BigDecimal costoOperativo;
	private BigDecimal costo;
	private BigDecimal utilidad;
	private String serie;
	private Long numero;
	private Date fecha;
	private CodigoNombre cliente;
	private CodigoNombre moneda;
	private CodigoNombre entrega;

	public BigDecimal getRentaNetaComercial() {
		return this.rentaNetaComercial;
	}

	public void setRentaNetaComercial(BigDecimal rentaNetaComercial) {
		this.rentaNetaComercial = rentaNetaComercial;
	}

	public Comprobante getComprobante() {
		return this.comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getCostoOperativo() {
		return this.costoOperativo;
	}

	public void setCostoOperativo(BigDecimal costoOperativo) {
		this.costoOperativo = costoOperativo;
	}

	public Long getNumero() {
		return this.numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public CodigoNombre getCliente() {
		return this.cliente;
	}

	public void setCliente(CodigoNombre cliente) {
		this.cliente = cliente;
	}

	public CodigoNombre getMoneda() {
		return this.moneda;
	}

	public void setMoneda(CodigoNombre moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getUtilidad() {
		return this.utilidad;
	}

	public void setUtilidad(BigDecimal utilidad) {
		this.utilidad = utilidad;
	}

	public CodigoNombre getEntrega() {
		return this.entrega;
	}

	public void setEntrega(CodigoNombre entrega) {
		this.entrega = entrega;
	}

	public BigDecimal getVentaNeta() {
		return this.ventaNeta;
	}

	public void setVentaNeta(BigDecimal ventaNeta) {
		this.ventaNeta = ventaNeta;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}
}