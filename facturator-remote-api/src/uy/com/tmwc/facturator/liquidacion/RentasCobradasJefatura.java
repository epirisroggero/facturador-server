package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class RentasCobradasJefatura implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<RentaJefatura> detalle;

	private JefaturaInfo jefatura;

	private BigDecimal total;

	private Map<Integer, BigDecimal> totalPorVendedor;

	public List<RentaJefatura> getDetalle() {
		return detalle;
	}

	public void setDetalle(List<RentaJefatura> detalle) {
		this.detalle = detalle;
	}

	public JefaturaInfo getJefatura() {
		return jefatura;
	}

	public void setJefatura(JefaturaInfo jefatura) {
		this.jefatura = jefatura;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Map<Integer, BigDecimal> getTotalPorVendedor() {
		return totalPorVendedor;
	}

	public void setTotalPorVendedor(Map<Integer, BigDecimal> totalPorVendedor) {
		this.totalPorVendedor = totalPorVendedor;
	}

}