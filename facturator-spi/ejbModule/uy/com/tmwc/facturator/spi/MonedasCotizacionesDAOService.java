package uy.com.tmwc.facturator.spi;

import java.util.Date;
import java.util.List;

import uy.com.tmwc.facturator.entity.CotizacionesMonedas;

public abstract interface MonedasCotizacionesDAOService {

	public abstract void persist(CotizacionesMonedas e);

	public abstract void merge(CotizacionesMonedas e);

	public abstract CotizacionesMonedas getCotizacion(Date fecha);
	
	public abstract List<CotizacionesMonedas> getCotizacionesMonedas(Date fromDate);

	public abstract CotizacionesMonedas getUltimaCotizacion(Date fechaHasta);
	
}
