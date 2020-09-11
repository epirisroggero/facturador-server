package uy.com.tmwc.facturator.rapi;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Cotizaciones implements Serializable {
	private static final long serialVersionUID = 1L;
	private HashMap<String, BigDecimal> map = new HashMap<String, BigDecimal>();
	private Date fecha;

	public void agregarCotizacion(String monedaCotizacion, String moneda, boolean esCompra, BigDecimal valor) {
		this.map.put(new CotizacionesKey(monedaCotizacion, moneda, esCompra).toString(), valor);
	}

	public BigDecimal getCotizacion(String monedaCotizacion, String moneda, boolean esCompra) {
		return (BigDecimal) this.map.get(new CotizacionesKey(monedaCotizacion, moneda, esCompra).toString());
	}

	public BigDecimal darPrecio(String monedaOrigen, BigDecimal montoDestino, String monedaDestino) {
		BigDecimal c = getCotizacion(monedaDestino, monedaOrigen, true);
		if (c != null) {
			return new BigDecimal(montoDestino.doubleValue() / c.doubleValue());
		}

		c = getCotizacion(monedaOrigen, monedaDestino, false);
		if (c != null) {
			return montoDestino.multiply(c);
		}

		return BigDecimal.ZERO;
	}

	public HashMap<String, String> getMap() {
		// return map;
		return null; // TODO
	}

	/**
	 * Se usa String en lugar de BigDecimal para facilitar la conversion de as3 a java
	 * 
	 */
	public void setMap(HashMap<String, String> map) {
		if (map != null) {
			HashMap<String, BigDecimal> tmpmap = new HashMap<String, BigDecimal>();
			for (Map.Entry<String, String> mapentry : map.entrySet()) {
				tmpmap.put(mapentry.getKey(), new BigDecimal(mapentry.getValue()));
			}
			this.map = tmpmap;
		} else {
			this.map = null;
		}

	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

}