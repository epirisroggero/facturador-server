package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

public abstract class Jefatura implements Serializable, Comparable<Jefatura> {
	private static final long serialVersionUID = 1L;
	private Vendedor jefe;
	private BigDecimal comisionPorDefecto;
	private Collection<ComisionJefatura> comisiones;

	public boolean aplica(Articulo articulo, Collection<Vendedor> participantes) {
		if (!aplica(articulo)) {
			return false;
		}

		for (Vendedor vendedor : participantes) {
			if (vendedor.equals(this.jefe)) {
				return false;
			}
		}

		return true;
	}

	protected abstract boolean aplica(Articulo paramArticulo);

	public abstract String getDescripcion();

	public BigDecimal getComisionVendedor(Vendedor vendedor) {
		for (ComisionJefatura comision : this.comisiones) {
			if (comision.getVendedor().equals(vendedor)) {
				return comision.getComision();
			}
		}
		return this.comisionPorDefecto;
	}

	public Vendedor getJefe() {
		return this.jefe;
	}

	public void setJefe(Vendedor jefe) {
		this.jefe = jefe;
	}

	public BigDecimal getComisionPorDefecto() {
		return this.comisionPorDefecto;
	}

	public void setComisionPorDefecto(BigDecimal comisionPorDefecto) {
		this.comisionPorDefecto = comisionPorDefecto;
	}

	public Collection<ComisionJefatura> getComisiones() {
		return this.comisiones;
	}

	public void setComisiones(Collection<ComisionJefatura> comisiones) {
		this.comisiones = comisiones;
	}

	public int compareTo(Jefatura o) {
		return getDescripcion().compareTo(o.getDescripcion());
	}
}