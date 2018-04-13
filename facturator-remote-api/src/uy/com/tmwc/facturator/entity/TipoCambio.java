package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;
import java.util.Date;

public class TipoCambio {

	private Date dia;
	private Moneda moneda;
	private BigDecimal comercial;
	
	public TipoCambio() {
		super();
	}
	
	public TipoCambio(Date dia, Moneda moneda, BigDecimal comercial) {
		super();
		this.dia = dia;
		this.moneda = moneda;
		this.comercial = comercial;
	}
	public Date getDia() {
		return dia;
	}
	public void setDia(Date dia) {
		this.dia = dia;
	}
	public Moneda getMoneda() {
		return moneda;
	}
	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
	public BigDecimal getComercial() {
		return comercial;
	}
	public void setComercial(BigDecimal comercial) {
		this.comercial = comercial;
	}
	
}
