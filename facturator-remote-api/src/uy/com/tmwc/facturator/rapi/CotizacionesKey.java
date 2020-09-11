package uy.com.tmwc.facturator.rapi;

import java.io.Serializable;

public class CotizacionesKey implements Serializable {
	private static final long serialVersionUID = 1L;
	private String monedaCotizacion;
	private String moneda;
	private boolean esCompra; 

	public CotizacionesKey() {
	}

	public CotizacionesKey(String monedaCotizacion, String moneda, boolean esCompra) {
		this.monedaCotizacion = monedaCotizacion;
		this.moneda = moneda;
		this.esCompra = esCompra;
	}

	public String getMonedaCotizacion() {
		return this.monedaCotizacion;
	}

	public String getMoneda() {
		return this.moneda;
	}

	public boolean isEsCompra() {
		return this.esCompra;
	}

	public int hashCode() {
		int result = 1;
		result = 31 * result + (this.esCompra ? 1231 : 1237);
		result = 31 * result + (this.moneda == null ? 0 : this.moneda.hashCode());
		result = 31 * result + (this.monedaCotizacion == null ? 0 : this.monedaCotizacion.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CotizacionesKey other = (CotizacionesKey) obj;
		if (this.esCompra != other.esCompra)
			return false;
		if (this.moneda == null) {
			if (other.moneda != null)
				return false;
		} else if (!this.moneda.equals(other.moneda))
			return false;
		if (this.monedaCotizacion == null) {
			if (other.monedaCotizacion != null)
				return false;
		} else if (!this.monedaCotizacion.equals(other.monedaCotizacion))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return monedaCotizacion + "_" + moneda + "_" + esCompra;
	}

}