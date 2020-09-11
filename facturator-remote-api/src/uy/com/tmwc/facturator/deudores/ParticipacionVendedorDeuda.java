package uy.com.tmwc.facturator.deudores;

import java.math.BigDecimal;

import uy.com.tmwc.facturator.dto.CodigoNombre;

public class ParticipacionVendedorDeuda {

	private CodigoNombre vendedor;
	private BigDecimal porcentaje;

	public ParticipacionVendedorDeuda() {
		super();
	}

	public ParticipacionVendedorDeuda(CodigoNombre vendedor, BigDecimal porcentaje) {
		super();
		this.vendedor = vendedor;
		this.porcentaje = porcentaje;
	}

	public CodigoNombre getVendedor() {
		return vendedor;
	}

	public void setVendedor(CodigoNombre vendedor) {
		this.vendedor = vendedor;
	}

	public BigDecimal getPorcentaje() {
		return porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

}
