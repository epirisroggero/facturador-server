package uy.com.tmwc.facturator.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Maths {
	public static final BigDecimal ONE_HUNDRED = new BigDecimal("100");

	public static double round(double value, int decimals) {
		double pow = value * Math.pow(10.0D, decimals);
		double rounded = (pow - Math.round(pow)) / Math.pow(10.0D, decimals);

		return rounded;
	}

	public static BigDecimal calcularMontoDescuento(BigDecimal monto, BigDecimal descuento) {
		if (monto == null || descuento == null) {
			return BigDecimal.ZERO;
		}
		return monto.multiply(descuento).divide(ONE_HUNDRED);
	}

	public static BigDecimal descontar(BigDecimal monto, BigDecimal porcentaje) {
		return monto.subtract(calcularMontoDescuento(monto, porcentaje));
	}

	public static BigDecimal calcularPorcentaje(BigDecimal total, BigDecimal monto) {
		if (monto == null || total == null || BigDecimal.ZERO.compareTo(total) == 0) {
			return BigDecimal.ZERO;
		}
		return monto.multiply(ONE_HUNDRED).divide(total, 2, RoundingMode.HALF_UP);
	}

	public static BigDecimal calcularTotalCancelado(BigDecimal monto, BigDecimal descuento) {
		if (monto == null || descuento == null || ONE_HUNDRED.compareTo(descuento) == 0) {
			return monto;
		}

		return monto.multiply(ONE_HUNDRED).divide(ONE_HUNDRED.subtract(descuento), 2, RoundingMode.HALF_UP);
	}

}