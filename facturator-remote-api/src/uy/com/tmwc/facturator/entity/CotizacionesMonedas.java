package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;
import java.util.Date;

public class CotizacionesMonedas {

	private Date dia;
	
	private BigDecimal dolarCompra;
	private BigDecimal dolarVenta;
	
	private BigDecimal euroCompra;
	private BigDecimal euroVenta;
	
	public CotizacionesMonedas() {
		super();
	}
	
	public CotizacionesMonedas(Date dia, BigDecimal dCompra, BigDecimal dVenta, BigDecimal eCompra, BigDecimal eVenta) {
		super();
		this.dia = dia;
		
		this.dolarCompra = dCompra;
		this.dolarVenta = dVenta;
	
		this.euroCompra = eCompra;
		this.euroVenta = eVenta;
	}
	
	public Date getDia() {
		return dia;
	}
	
	public void setDia(Date dia) {
		this.dia = dia;
	}

	public BigDecimal getDolarCompra() {
		return dolarCompra;
	}

	public void setDolarCompra(BigDecimal dolarCompra) {
		this.dolarCompra = dolarCompra;
	}

	public BigDecimal getDolarVenta() {
		return dolarVenta;
	}

	public void setDolarVenta(BigDecimal dolarVenta) {
		this.dolarVenta = dolarVenta;
	}

	public BigDecimal getEuroCompra() {
		return euroCompra;
	}

	public void setEuroCompra(BigDecimal euroCompra) {
		this.euroCompra = euroCompra;
	}

	public BigDecimal getEuroVenta() {
		return euroVenta;
	}

	public void setEuroVenta(BigDecimal euroVenta) {
		this.euroVenta = euroVenta;
	}

}
