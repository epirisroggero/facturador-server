package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;

public abstract class OrigenRenta implements Serializable {
	private static final long serialVersionUID = 1L;

	public abstract BigDecimal getRentaNetaComercial();
}