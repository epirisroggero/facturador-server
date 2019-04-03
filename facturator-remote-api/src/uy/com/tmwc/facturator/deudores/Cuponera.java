package uy.com.tmwc.facturator.deudores;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import uy.com.tmwc.facturator.entity.Articulo;

public class Cuponera implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Date fecha;
	
	private BigDecimal precioTotal = BigDecimal.ZERO;
	
	private BigDecimal cantidadTotal = BigDecimal.ZERO;
	
	private BigDecimal precioUnitario = BigDecimal.ZERO;
	
	private BigDecimal stock = BigDecimal.ZERO;
	
	private Articulo articulo;

	private List<LineaCuponera> lineasCuponera;

	
	
	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getPrecioTotal() {
		return precioTotal;
	}

	public void setPrecioTotal(BigDecimal precioTotal) {
		this.precioTotal = precioTotal;
	}

	public BigDecimal getCantidadTotal() {
		return cantidadTotal;
	}

	public void setCantidadTotal(BigDecimal cantidadTotal) {
		this.cantidadTotal = cantidadTotal;
	}

	public BigDecimal getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public List<LineaCuponera> getLineasCuponera() {
		return lineasCuponera;
	}

	public void setLineasCuponera(List<LineaCuponera> lineasCuponera) {
		this.lineasCuponera = lineasCuponera;
	}
	
	public BigDecimal getStock() {
		return stock;
	}

	public void setStock(BigDecimal stock) {
		this.stock = stock;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}


}
