package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import uy.com.tmwc.facturator.dto.CodigoNombre;

public class Recibo implements Serializable {
	private static final long serialVersionUID = 1L;
	private String serie;
	private Long numero;
	private BigDecimal cancela;
	private BigDecimal paga;
	private BigDecimal descuentoReal;
	private CodigoNombre moneda;

	public Recibo() {
	}

	public Recibo(uy.com.tmwc.facturator.entity.Recibo recibo) {
		this.numero = recibo.getNumero();
		this.cancela = recibo.getCancela();
		this.paga = recibo.getPaga();
		this.descuentoReal = recibo.getDescuentosPorc();
	}

	public Long getNumero() {
		return this.numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public BigDecimal getCancela() {
		return this.cancela;
	}

	public void setCancela(BigDecimal cancela) {
		this.cancela = cancela;
	}

	public BigDecimal getPaga() {
		return this.paga;
	}

	public void setPaga(BigDecimal paga) {
		this.paga = paga;
	}

	public BigDecimal getDescuentoReal() {
		return this.descuentoReal;
	}

	public void setDescuentoReal(BigDecimal descuentoReal) {
		this.descuentoReal = descuentoReal;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public CodigoNombre getMoneda() {
		return this.moneda;
	}

	public void setMoneda(CodigoNombre moneda) {
		this.moneda = moneda;
	}
}