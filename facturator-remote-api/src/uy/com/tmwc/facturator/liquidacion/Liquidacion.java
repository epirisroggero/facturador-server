package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.util.List;

public class Liquidacion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<LiquidacionVendedor> liquidaciones;

	public List<LiquidacionVendedor> getLiquidaciones() {
		return this.liquidaciones;
	}

	public void setLiquidaciones(List<LiquidacionVendedor> liquidaciones) {
		this.liquidaciones = liquidaciones;
	}
}