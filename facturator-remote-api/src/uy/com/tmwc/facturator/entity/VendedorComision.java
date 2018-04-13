package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;

public class VendedorComision {

	private BigDecimal porcentajeComision;
	
	private PreciosVenta preciosVenta;

	public BigDecimal getPorcentajeComision() {
		return porcentajeComision;
	}

	public void setPorcentajeComision(BigDecimal porcentajeComision) {
		this.porcentajeComision = porcentajeComision;
	}

	public PreciosVenta getPreciosVenta() {
		return preciosVenta;
	}

	public void setPreciosVenta(PreciosVenta preciosVenta) {
		this.preciosVenta = preciosVenta;
	}
	
}
