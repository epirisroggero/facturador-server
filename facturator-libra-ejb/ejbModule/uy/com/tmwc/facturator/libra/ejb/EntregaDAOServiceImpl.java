package uy.com.tmwc.facturator.libra.ejb;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dozer.DozerBeanMapper;

import uy.com.tmwc.facturator.entity.Entrega;
import uy.com.tmwc.facturator.libra.entity.EntregaPK;
import uy.com.tmwc.facturator.libra.util.DozerMappingsService;
import uy.com.tmwc.facturator.spi.EntregaDAOService;

@Stateless
@Local({ EntregaDAOService.class })
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EntregaDAOServiceImpl extends ServiceBase implements EntregaDAOService {

	@PersistenceContext
	EntityManager em;

	@EJB
	DozerMappingsService mapService;

	public String persist(Entrega e) {		
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Entrega entregalibra = mapper.map(e, uy.com.tmwc.facturator.libra.entity.Entrega.class);
		
		EntregaPK entregaPK = new EntregaPK();
		entregaPK.setEntId(e.getCodigo());
		entregaPK.setEmpId(getEmpId());
		
		entregalibra.setId(entregaPK);
		
		em.persist(entregalibra);
		this.em.flush();
		
		return e.getCodigo();
	}

	public void merge(Entrega e) {
		DozerBeanMapper mapper = mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Entrega entrega = mapper.map(e, uy.com.tmwc.facturator.libra.entity.Entrega.class);
		
		this.em.merge(entrega);
		this.em.flush();
	}
	
	public Boolean remove(Entrega e) {
		if (e != null && e.getCodigo() != null) {
			DozerBeanMapper mapper = mapService.getDozerBeanMapper();
			uy.com.tmwc.facturator.libra.entity.Entrega entregaEntity = mapper.map(e, uy.com.tmwc.facturator.libra.entity.Entrega.class);
			entregaEntity = em.find(uy.com.tmwc.facturator.libra.entity.Entrega.class, entregaEntity.getId());

			if (entregaEntity != null) {
				em.remove(entregaEntity);
				em.flush();	
			}
		}
		return true;
	}

}
