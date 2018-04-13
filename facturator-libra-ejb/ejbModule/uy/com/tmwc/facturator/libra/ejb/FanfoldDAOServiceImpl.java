package uy.com.tmwc.facturator.libra.ejb;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dozer.DozerBeanMapper;

import uy.com.tmwc.facturator.entity.Fanfold;
import uy.com.tmwc.facturator.libra.entity.FanfoldPK;
import uy.com.tmwc.facturator.libra.util.DozerMappingsService;
import uy.com.tmwc.facturator.spi.FanfoldDAOService;

@Stateless
@Local({ FanfoldDAOService.class })
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class FanfoldDAOServiceImpl extends ServiceBase implements FanfoldDAOService {

	@PersistenceContext
	EntityManager em;

	@EJB
	DozerMappingsService mapService;

	public String persist(Fanfold e) {		
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Fanfold fanfoldlibra = mapper.map(e, uy.com.tmwc.facturator.libra.entity.Fanfold.class);
		
		FanfoldPK fanfoldPK = new FanfoldPK();
		fanfoldPK.setEmpId(getEmpId());
		fanfoldPK.setNumFoldId(e.getCodigo());
		
		fanfoldlibra.setId(fanfoldPK);
		
		em.persist(fanfoldlibra);
		this.em.flush();
		
		return e.getCodigo();
	}

	public void merge(Fanfold e) {
		DozerBeanMapper mapper = mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Fanfold fanfold = mapper.map(e, uy.com.tmwc.facturator.libra.entity.Fanfold.class);
	
		this.em.merge(fanfold);
		this.em.flush();
	}
	
	public Boolean remove(Fanfold e) {
		if (e != null && e.getCodigo() != null) {
			DozerBeanMapper mapper = mapService.getDozerBeanMapper();
			uy.com.tmwc.facturator.libra.entity.Fanfold fanfoldEntity = mapper.map(e, uy.com.tmwc.facturator.libra.entity.Fanfold.class);
			fanfoldEntity = em.find(uy.com.tmwc.facturator.libra.entity.Fanfold.class, fanfoldEntity.getId());

			if (fanfoldEntity != null) {
				em.remove(fanfoldEntity);
				em.flush();	
			}
		}
		return true;
	}

}
