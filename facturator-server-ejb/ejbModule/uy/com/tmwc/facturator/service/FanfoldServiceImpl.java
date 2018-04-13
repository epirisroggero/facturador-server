package uy.com.tmwc.facturator.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.entity.Fanfold;
import uy.com.tmwc.facturator.rapi.FanfoldService;
import uy.com.tmwc.facturator.spi.FanfoldDAOService;

@Name("FanfoldService")
@Stateless
public class FanfoldServiceImpl implements FanfoldService {

	@EJB FanfoldDAOService fanfoldDAOService;
	
	public String persist(Fanfold e) {
		return fanfoldDAOService.persist(e);
	}

	public void merge(Fanfold e) {
		fanfoldDAOService.merge(e);
	}
	
	public Boolean remove(Fanfold Fanfold) {
		return fanfoldDAOService.remove(Fanfold);
	}


}
