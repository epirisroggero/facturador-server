package uy.com.tmwc.facturator.libra.ejb;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dozer.DozerBeanMapper;

import uy.com.tmwc.facturator.entity.CotizacionesMonedas;
import uy.com.tmwc.facturator.libra.entity.CotizacionesMonedasPK;
import uy.com.tmwc.facturator.libra.util.DozerMappingsService;
import uy.com.tmwc.facturator.spi.MonedasCotizacionesDAOService;
import uy.com.tmwc.facturator.utils.MappingUtils;

@Stateless
@Local({ MonedasCotizacionesDAOService.class })
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class MonedasCotizacionesDAOServiceImpl extends ServiceBase implements MonedasCotizacionesDAOService {
	@PersistenceContext
	EntityManager em;

	@EJB
	DozerMappingsService mapService;

	public void persist(CotizacionesMonedas e) {
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();

		uy.com.tmwc.facturator.libra.entity.CotizacionesMonedas cotizacionlibra = mapper.map(e, uy.com.tmwc.facturator.libra.entity.CotizacionesMonedas.class);

		CotizacionesMonedasPK CotizacionesMonedasPK = new CotizacionesMonedasPK();
		CotizacionesMonedasPK.setEmpId(getEmpId());
		CotizacionesMonedasPK.setDia(e.getDia());

		cotizacionlibra.setId(CotizacionesMonedasPK);

		em.persist(cotizacionlibra);
		this.em.flush();
	}

	public void merge(CotizacionesMonedas e) {
		DozerBeanMapper mapper = mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.CotizacionesMonedas CotizacionesMonedas = mapper.map(e, uy.com.tmwc.facturator.libra.entity.CotizacionesMonedas.class);

		this.em.merge(CotizacionesMonedas);
		this.em.flush();
	}

	public CotizacionesMonedas getCotizacion(Date fecha) {
		uy.com.tmwc.facturator.libra.entity.CotizacionesMonedas doc = (uy.com.tmwc.facturator.libra.entity.CotizacionesMonedas) this.em.find(
				uy.com.tmwc.facturator.libra.entity.CotizacionesMonedas.class, new CotizacionesMonedasPK(getEmpId(), fecha));
		if (doc == null) {
			return null;
		}
		CotizacionesMonedas mapped = (uy.com.tmwc.facturator.entity.CotizacionesMonedas) this.mapService.getDozerBeanMapper().map(doc, uy.com.tmwc.facturator.entity.CotizacionesMonedas.class);

		return mapped;
	}
	
	public CotizacionesMonedas getUltimaCotizacion(Date fechaHasta) {
		uy.com.tmwc.facturator.libra.entity.CotizacionesMonedas doc = null;
		
		while (doc == null) {
			doc = (uy.com.tmwc.facturator.libra.entity.CotizacionesMonedas) this.em.find(
					uy.com.tmwc.facturator.libra.entity.CotizacionesMonedas.class, new CotizacionesMonedasPK(getEmpId(), fechaHasta));
			
			if (doc == null) {
				Calendar cal = Calendar.getInstance();
				cal.setTime(fechaHasta);
				cal.add(Calendar.DATE, -1);
				
				fechaHasta = cal.getTime();
			}
		}

		SimpleDateFormat df = new SimpleDateFormat();
		//System.out.println("######### Fecha cotización >> " + df.format(fechaHasta));
		CotizacionesMonedas mapped = (uy.com.tmwc.facturator.entity.CotizacionesMonedas) this.mapService.getDozerBeanMapper().map(doc, uy.com.tmwc.facturator.entity.CotizacionesMonedas.class);

		return mapped;
	}

	

	public List<CotizacionesMonedas> getCotizacionesMonedas(Date fromDate) {
		Query query = this.em.createNamedQuery("Cotizaciones.cotizacionesMonedasQuery").setParameter("empId", getEmpId()).setParameter("fromDate", fromDate);

		List<CotizacionesMonedas> list = query.getResultList();
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		List res = MappingUtils.map(uy.com.tmwc.facturator.entity.CotizacionesMonedas.class, list, mapper);
		
		return res;

	}

}
