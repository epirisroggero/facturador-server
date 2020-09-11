package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.entity.ComisionJefatura;
import uy.com.tmwc.facturator.utils.Maths;

public class RentaJefatura implements Serializable {
	private static final long serialVersionUID = 1L;
	private OrigenRenta origen;
	private CodigoNombre vendedor;
	private JefaturaInfo jefatura;
	private BigDecimal comision;

	public RentaJefatura() {
	}

	public RentaJefatura(OrigenRenta origen, JefaturaInfo jefatura, CodigoNombre vendedor,
			ComisionJefatura comisionJefatura) {
		this.jefatura = jefatura;
		this.vendedor = vendedor;
		this.origen = origen;
		this.comision = comisionJefatura.getComision();
	}

	public BigDecimal getRenta() {
		return Maths.calcularMontoDescuento(this.origen.getRentaNetaComercial(), this.comision);
	}

	public OrigenRenta getOrigen() {
		return this.origen;
	}

	public void setOrigen(OrigenRenta origen) {
		this.origen = origen;
	}

	public CodigoNombre getVendedor() {
		return this.vendedor;
	}

	public void setVendedor(CodigoNombre vendedor) {
		this.vendedor = vendedor;
	}

	public JefaturaInfo getJefatura() {
		return this.jefatura;
	}

	public void setJefatura(JefaturaInfo jefatura) {
		this.jefatura = jefatura;
	}

	public BigDecimal getComision() {
		return this.comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}
}