package uy.com.tmwc.facturator.service;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.entity.CotizacionesMonedas;
import uy.com.tmwc.facturator.rapi.MonedasCotizacionesService;
import uy.com.tmwc.facturator.spi.MonedasCotizacionesDAOService;

@Name("monedasCotizacionesService")
@Stateless
public class MonedasCotizacionesServiceImpl implements MonedasCotizacionesService {

	@EJB
	MonedasCotizacionesDAOService monedasCotizacionesDAOService;

	public void persist(CotizacionesMonedas e) {
		monedasCotizacionesDAOService.persist(e);
	}

	public void merge(CotizacionesMonedas e) {
		monedasCotizacionesDAOService.merge(e);
	}

	public CotizacionesMonedas getCotizacion(Date fecha) {
		return monedasCotizacionesDAOService.getCotizacion(fecha);
	}
	
	public List<CotizacionesMonedas> getCotizacionesMonedas(Date fromDate) {
		return monedasCotizacionesDAOService.getCotizacionesMonedas(fromDate);
	}

	public CotizacionesMonedas getUltimaCotizacion(Date fechaHasta) {
		return monedasCotizacionesDAOService.getUltimaCotizacion(fechaHasta);
		
	}

}
