package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

public class Vendedor extends CodigoNombreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<VendedorComision> comisiones;
	
	public Vendedor() {
	}

	public Vendedor(String codigo, String nombre) {
		super(codigo, nombre);
	}
	
	public BigDecimal getPorcentajeComision(String codigo) {
		if (comisiones == null) {
			return null;
		}
		for (VendedorComision c : comisiones) {
			if (c.getPreciosVenta() != null && c.getPreciosVenta().getCodigo().equals(codigo)) {
				return c.getPorcentajeComision();
			}
		}
		return null;
	}

	public int hashCode() {
		int result = super.hashCode();

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj)) {
			return false;
		}
		return (obj instanceof Vendedor);
	}

	public List<VendedorComision> getComisiones() {
		return comisiones;
	}

	public void setComisiones(List<VendedorComision> comisiones) {
		this.comisiones = comisiones;
	}

	public String toString() {
		return getCodigo() + "-" + getNombre();  
	}

	
}