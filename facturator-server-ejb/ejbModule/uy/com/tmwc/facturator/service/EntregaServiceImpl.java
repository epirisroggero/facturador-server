package uy.com.tmwc.facturator.service;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.entity.Entrega;
import uy.com.tmwc.facturator.rapi.EntregaService;
import uy.com.tmwc.facturator.spi.EntregaDAOService;

@Name("entregaService")
@Stateless
public class EntregaServiceImpl implements EntregaService {

	@EJB EntregaDAOService entregaDAOService;
	
	public String persist(Entrega e) {
		return entregaDAOService.persist(e);
	}

	public void merge(Entrega e) {
		entregaDAOService.merge(e);
	}
	
	public Boolean remove(Entrega entrega) {
		return entregaDAOService.remove(entrega);
	}


}
