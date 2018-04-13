package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import uy.com.tmwc.facturator.utils.Dates;

public class CuotaDocumento implements Serializable {
	private static final long serialVersionUID = 1L;
	private Documento documento;
	private int numero;
	private Date fecha;
	private BigDecimal importe;

	public CuotaDocumento() {
	}

	public CuotaDocumento(Documento documento, int numero, Date fecha, BigDecimal importe) {
		this.documento = documento;
		this.numero = numero;
		this.fecha = fecha;
		this.importe = importe;
	}

	public int getRetrasoDias(Date to) {
		return Dates.getDaysBetween(this.fecha, to);
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public int getNumero() {
		return this.numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	// alias to help in the mapping
	public BigDecimal getTotal() {
		return getImporte();
	}

	// alias to help in the mapping
	public void setTotal(BigDecimal value) {
		setImporte(value);
	}

	public BigDecimal getImporte() {
		return this.importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}
}