package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import uy.com.tmwc.facturator.utils.Maths;

public class ParticipacionVendedor implements Serializable {
	private static final long serialVersionUID = 1L;

	private Documento documento;
	private Vendedor vendedor;
	private BigDecimal porcentaje = BigDecimal.ZERO;

	public BigDecimal getCuotaparteOperativos() {
		BigDecimal costoOperativo = this.documento.getCostoOperativo();
		return costoOperativo != null ? calcularCuotaparte(costoOperativo) : null;
	}

	public BigDecimal getCuotaparteRentaComercial() {
		return calcularCuotaparte(this.documento.getRentaNetaComercial());
	}

	private BigDecimal calcularCuotaparte(BigDecimal monto) {
		return Maths.calcularMontoDescuento(monto, this.porcentaje);
	}

	public Vendedor getVendedor() {
		return this.vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public BigDecimal getPorcentaje() {
		return this.porcentaje;
	}

	public void setPorcentaje(BigDecimal porcentaje) {
		this.porcentaje = porcentaje;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public BigDecimal getCuotaparte(BigDecimal monto) {
		return calcularCuotaparte(monto);
	}

}