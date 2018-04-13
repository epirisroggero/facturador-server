package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ArticuloPrecio implements Serializable {

	private static final long serialVersionUID = 1L;

	private String artId;
	private String precioBaseId;
	private String mndIdPrecio;
	private boolean precioBaseConIVA;

	private BigDecimal precio;
	private BigDecimal precioIVA;

	private Articulo articulo;
	private Moneda moneda;

	public ArticuloPrecio() {
	}

	public ArticuloPrecio(String artId, String precioBaseId, BigDecimal precio, Short monedaId, String precioBaseConIVA) {
		this.artId = artId;
		this.precioBaseId = precioBaseId;
		this.precio = precio;
		this.mndIdPrecio = monedaId.toString();
		this.precioBaseConIVA = precioBaseConIVA.equals("S");
	}

	public BigDecimal getImporteArticuloSinIVA() {
		if (precioBaseConIVA) {
			Iva iva = this.articulo.getIva();
			if (iva == null) {
				return null;
			}
			return iva.calcularNeto(this.precio);
		}
		return this.precio;
	}

	public Articulo getArticulo() {
		return this.articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public String getArtId() {
		return artId;
	}

	public void setArtId(String artId) {
		this.artId = artId;
	}

	public String getPrecioBaseId() {
		return precioBaseId;
	}

	public void setPrecioBaseId(String precioBaseId) {
		this.precioBaseId = precioBaseId;
	}

	public String getMndIdPrecio() {
		return mndIdPrecio;
	}

	public void setMndIdPrecio(String monedaId) {
		this.mndIdPrecio = monedaId;
	}

	public BigDecimal getPrecioIVA() {
		return precioIVA;
	}

	public void setPrecioIVA(BigDecimal precioIva) {
		this.precioIVA = precioIva;
	}

	public boolean getPrecioBaseConIVA() {
		return precioBaseConIVA;
	}

	public void setPrecioBaseConIVA(boolean precioBaseConIva) {
		this.precioBaseConIVA = precioBaseConIva;
	}
}