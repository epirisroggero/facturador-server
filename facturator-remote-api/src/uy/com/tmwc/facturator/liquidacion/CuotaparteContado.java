package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import uy.com.tmwc.facturator.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.utils.Maths;

public class CuotaparteContado implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Factura factura;
	private BigDecimal participacion;

	public CuotaparteContado() {
	}

	public CuotaparteContado(ParticipacionVendedor pv, Factura factura) {
		this.factura = factura;
		this.participacion = pv.getPorcentaje();
	}

	public BigDecimal getCuotaparteRentaComercial() {
		return getCuotaparte(this.factura.getRentaNetaComercial());
	}

	private BigDecimal getCuotaparte(BigDecimal monto) {
		return Maths.calcularMontoDescuento(monto, this.participacion);
	}
}