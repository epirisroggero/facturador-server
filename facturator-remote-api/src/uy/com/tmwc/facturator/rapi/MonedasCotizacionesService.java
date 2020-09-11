package uy.com.tmwc.facturator.rapi;

import java.util.Date;
import java.util.List;

import uy.com.tmwc.facturator.entity.CotizacionesMonedas;

import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("remote/monedasCotizacionesService")
public interface MonedasCotizacionesService {

	public abstract void persist(CotizacionesMonedas e);

	public abstract void merge(CotizacionesMonedas e);

	public abstract CotizacionesMonedas getCotizacion(Date fecha);

	public abstract CotizacionesMonedas getUltimaCotizacion(Date fecha);

	public abstract List<CotizacionesMonedas> getCotizacionesMonedas(Date fromDate);

}
