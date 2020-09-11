package uy.com.tmwc.facturator.deudores;

import java.math.BigDecimal;

import uy.com.tmwc.facturator.dto.CodigoNombre;

public class MontoMoneda {

	private CodigoNombre moneda;
	
	private BigDecimal monto;

	public CodigoNombre getMoneda() {
		return moneda;
	}

	public void setMoneda(CodigoNombre moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getMonto() {
		return monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

}
