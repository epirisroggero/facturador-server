package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ArticuloCompraVentaCosto extends CodigoNombreEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String fichaMonedaId;
	
	private String compraMonedaId;
	
	private String ventaMonedaId;

	private String comprobanteCompra;
	
	private String comprobanteVenta;

	private BigDecimal costo;
	
	private BigDecimal costoCompra;
	
	private BigDecimal costoCompraSinDescuentos;
	
	private BigDecimal costoCompraDescuento;
	
	private BigDecimal costoVenta;
	
	private String docCompraId;
	
	private String docVentaId;

	public String getComprobanteCompra() {
		return comprobanteCompra;
	}

	public void setComprobanteCompra(String comprobanteCompra) {
		this.comprobanteCompra = comprobanteCompra;
	}

	public String getComprobanteVenta() {
		return comprobanteVenta;
	}

	public void setComprobanteVenta(String comprobanteVenta) {
		this.comprobanteVenta = comprobanteVenta;
	}

	public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getCostoCompra() {
		return costoCompra;
	}

	public void setCostoCompra(BigDecimal costoCompra) {
		this.costoCompra = costoCompra;
	}

	public BigDecimal getCostoVenta() {
		return costoVenta;
	}

	public void setCostoVenta(BigDecimal costoVenta) {
		this.costoVenta = costoVenta;
	}

	public String getCompraMonedaId() {
		return compraMonedaId;
	}

	public void setCompraMonedaId(String compraMonedaId) {
		this.compraMonedaId = compraMonedaId;
	}

	public String getVentaMonedaId() {
		return ventaMonedaId;
	}

	public void setVentaMonedaId(String ventaMonedaId) {
		this.ventaMonedaId = ventaMonedaId;
	}

	public String getFichaMonedaId() {
		return fichaMonedaId;
	}

	public void setFichaMonedaId(String fichaMonedaId) {
		this.fichaMonedaId = fichaMonedaId;
	}

	public String getDocVentaId() {
		return docVentaId;
	}

	public void setDocVentaId(String docVentaId) {
		this.docVentaId = docVentaId;
	}

	public String getDocCompraId() {
		return docCompraId;
	}

	public void setDocCompraId(String docCompraId) {
		this.docCompraId = docCompraId;
	}

	public BigDecimal getCostoCompraSinDescuentos() {
		return costoCompraSinDescuentos;
	}

	public void setCostoCompraSinDescuentos(BigDecimal costoCompraSinDescuentos) {
		this.costoCompraSinDescuentos = costoCompraSinDescuentos;
	}

	public BigDecimal getCostoCompraDescuento() {
		return costoCompraDescuento;
	}

	public void setCostoCompraDescuento(BigDecimal costoCompraDescuento) {
		this.costoCompraDescuento = costoCompraDescuento;
	}

}
