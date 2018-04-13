package uy.com.tmwc.facturator.dto;

import java.io.Serializable;
import java.math.BigDecimal;

public class CreditoInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String cliente;
	
	private BigDecimal deuda;
	private BigDecimal topeCredito;
	private BigDecimal solicitado;
	
	public CreditoInfo() {
		super();
	}
	
	public CreditoInfo(String cliente, BigDecimal deuda, BigDecimal topeCredito, BigDecimal solicitado) {
		super();
		this.cliente = cliente;
		this.deuda = deuda;
		this.topeCredito = topeCredito;
		this.solicitado = solicitado;
	}
	public String getCliente() {
		return cliente;
	}
	public void setCliente(String cliente) {
		this.cliente = cliente;
	}
	public BigDecimal getDeuda() {
		return deuda;
	}
	public void setDeuda(BigDecimal deuda) {
		this.deuda = deuda;
	}
	public BigDecimal getTopeCredito() {
		return topeCredito;
	}
	public void setTopeCredito(BigDecimal topeCredito) {
		this.topeCredito = topeCredito;
	}
	public BigDecimal getSolicitado() {
		return solicitado;
	}
	public void setSolicitado(BigDecimal solicitado) {
		this.solicitado = solicitado;
	}
	
}
