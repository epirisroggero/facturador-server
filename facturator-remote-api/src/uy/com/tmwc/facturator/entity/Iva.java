package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import uy.com.tmwc.facturator.utils.Maths;

public class Iva extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;
	private BigDecimal tasa;

	public BigDecimal calcularNeto(BigDecimal precio) {
		BigDecimal divisor = BigDecimal.ONE.add(this.tasa.divide(Maths.ONE_HUNDRED));
		return precio.divide(divisor, 2, RoundingMode.HALF_UP);
	}
	
	public BigDecimal calcularPrecioIvaInc(BigDecimal precio) {
		BigDecimal producto = BigDecimal.ONE.add(this.tasa.divide(Maths.ONE_HUNDRED));
		return precio.multiply(producto).setScale(2, RoundingMode.HALF_UP);
	}

	public BigDecimal getTasa() {
		return this.tasa;
	}

	public void setTasa(BigDecimal tasa) {
		this.tasa = tasa;
	}
}