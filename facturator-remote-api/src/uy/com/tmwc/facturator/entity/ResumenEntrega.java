package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResumenEntrega implements Serializable {
	private static final long serialVersionUID = 1L;
	private Entrega entrega;
	private long cantidad;

	public ResumenEntrega() {
	}

	public ResumenEntrega(Entrega entrega, long cantidad) {
		this.entrega = entrega;
		this.cantidad = cantidad;
	}

	public BigDecimal getPeso() {
		return this.entrega.getRelevancia().multiply(
				new BigDecimal(this.cantidad));
	}

	public Entrega getEntrega() {
		return this.entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	public long getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(long cantidad) {
		this.cantidad = cantidad;
	}
}