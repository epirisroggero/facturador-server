package uy.com.tmwc.facturator.utils;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeMap;

import uy.com.tmwc.facturator.entity.TipoCambio;
import uy.com.tmwc.facturator.rapi.Cotizaciones;

public class LogicaCotizacion {
	
	private String codigoPesos;
	private String codigoExtranjera;
	private GetTipoCambio getTipoCambioFunction;

	public LogicaCotizacion(String codigoPesos, String codigoExtranjera, GetTipoCambio getTipoCambioFunction) {
		this.codigoPesos = codigoPesos;
		this.codigoExtranjera = codigoExtranjera;
		this.getTipoCambioFunction = getTipoCambioFunction;
	}
	
	public LogicaCotizacion(String codigoPesos, String codigoExtranjera) {
		this(codigoPesos, codigoExtranjera, null);
	}
	
	public int obtenerUltimoDiaMes (int anio, int mes) {
		Calendar cal = Calendar.getInstance();
		cal.set(anio, mes-1, 1);
		return cal.getActualMaximum(Calendar.DAY_OF_MONTH);
	}

	public BigDecimal getMontoMayorCotizacion(Date fechaPrecio, String monedaPrecio, BigDecimal precio, String monedaFacturacion,
			Cotizaciones oCotizaciones, Boolean esRemito, GetTipoCambio getTipoCambioFunction) {
		BigDecimal tempGetMontoMayorCotizacion = null;

		if (precio == null) {
			return BigDecimal.ZERO;
		}

		if (monedaFacturacion.equals(monedaPrecio)) {
			if (monedaFacturacion.equals(codigoPesos)) {
				tempGetMontoMayorCotizacion = precio;
			} else {
				BigDecimal cotizacionReferencia = oCotizaciones.getCotizacion(codigoPesos, monedaPrecio, false); // obtener la cotización de venta
				if ((cotizacionReferencia == null) || (cotizacionReferencia.compareTo(BigDecimal.ZERO) == 0)) {
					return esRemito ? new BigDecimal(precio.doubleValue() / .9) : precio;
				}

				BigDecimal cotizacionAnterior = null;
				if (fechaPrecio != null && oCotizaciones.getFecha() != null && fechaPrecio.compareTo(oCotizaciones.getFecha()) < 0) {
					cotizacionAnterior = getTipoCambio(monedaFacturacion, codigoPesos, fechaPrecio, getTipoCambioFunction);
				}
				
				tempGetMontoMayorCotizacion = (cotizacionAnterior != null && cotizacionReferencia.compareTo(cotizacionAnterior) < 0) ? new BigDecimal(cotizacionAnterior
						.multiply(precio).doubleValue() / cotizacionReferencia.doubleValue()) : precio;

			}

			return esRemito ? new BigDecimal(tempGetMontoMayorCotizacion.doubleValue() / .9) : tempGetMontoMayorCotizacion;
		}

		BigDecimal precio1 = oCotizaciones.darPrecio(monedaFacturacion, precio, monedaPrecio);

		BigDecimal tcOld = getTipoCambio(monedaPrecio, monedaFacturacion, fechaPrecio, getTipoCambioFunction);
		BigDecimal precio2 = tcOld != null ? tcOld.multiply(precio) : BigDecimal.ZERO;

		BigDecimal result = precio1.compareTo(precio2) > 0 ? precio1 : precio2;		

		return esRemito ? new BigDecimal(result.doubleValue() / .9) : result;
	}
	
	public BigDecimal cambiar(BigDecimal monto, String monedaOrigen, String monedaDestino, Date fecha) {
		BigDecimal tc = getTipoCambio(monedaOrigen, monedaDestino, fecha, getTipoCambioFunction, BigDecimal.ZERO);
		if (tc == null) {
			return null;
		}
		return tc.multiply(monto);
	}

	public BigDecimal getTipoCambio(String monedaOrigen, String monedaDestino, Date fecha, GetTipoCambio getTipoCambioFunction) {
		return getTipoCambio(monedaOrigen, monedaDestino, fecha, getTipoCambioFunction, BigDecimal.ZERO);
	}
	
	public BigDecimal getTipoCambio(String monedaOrigen, String monedaDestino, Date fecha, GetTipoCambio getTipoCambioFunction, BigDecimal tcd) {
		if (monedaOrigen.equals(monedaDestino)) {
			return BigDecimal.ONE;
		}
		BigDecimal paso1;
		if (monedaOrigen.equals(codigoPesos)) {
			paso1 = BigDecimal.ONE;
		} else {
			if ((tcd.compareTo(BigDecimal.ZERO) != 0) && (monedaOrigen.equals(codigoExtranjera))) {
				paso1 = tcd;
			} else {
				BigDecimal tcfecha = getTipoCambioFunction.getTipoCambio(monedaOrigen, fecha);
				if (tcfecha == null) {
					return null;
				}
				paso1 = tcfecha;
			}
		}
		BigDecimal paso2;
		if (monedaDestino.equals(codigoPesos)) {
			paso2 = paso1;
		} else {
			if ((tcd.compareTo(BigDecimal.ZERO) != 0) && (monedaDestino.equals(codigoExtranjera))) {
				paso2 = new BigDecimal(paso1.doubleValue() / tcd.doubleValue());
			} else {
				BigDecimal tcfecha = getTipoCambioFunction.getTipoCambio(monedaDestino, fecha);
				if (tcfecha == null) {
					return null;
				}
				paso2 = new BigDecimal(paso1.doubleValue() / tcfecha.doubleValue());
			}
		}

		return paso2;
	}

	public interface GetTipoCambio {
		BigDecimal getTipoCambio(String monedaOrigen, Date fecha);
	}
	
	public static class InMemoryGetTipoCambio implements GetTipoCambio {

		private HashMap<String /* moneda */, TreeMap<Date, TipoCambio>>  index = new HashMap<String, TreeMap<Date, TipoCambio>>();
		
		public InMemoryGetTipoCambio(Collection<TipoCambio> tcs) {
			for (TipoCambio tipoCambio : tcs) {
				TreeMap<Date, TipoCambio> treeEntry = index.get(tipoCambio.getMoneda().getCodigo());
				if (treeEntry == null) {
					treeEntry = new TreeMap<Date, TipoCambio>();
					index.put(tipoCambio.getMoneda().getCodigo(), treeEntry);
				}
				treeEntry.put(tipoCambio.getDia(), tipoCambio);
			}
		}
		
		public BigDecimal getTipoCambio(String monedaOrigen, Date fecha) {
			TreeMap<Date, TipoCambio> treeEntry = index.get(monedaOrigen);
			if (treeEntry != null) {
				if (fecha == null) {
					fecha = new Date();
				}
				/*System.out.println("Fecha :: " + fecha);*/

				java.util.Map.Entry<Date, TipoCambio> entry = treeEntry.floorEntry(fecha);
				if (entry != null) { 
					return entry.getValue().getComercial();
				}
			}
			return BigDecimal.ONE;

		}
		
	}

}
