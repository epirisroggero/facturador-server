package uy.com.tmwc.facturator.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.entity.Auditoria;
import uy.com.tmwc.facturator.rapi.AuditoriaService;
import uy.com.tmwc.facturator.spi.AuditoriaDAOService;

@Name("auditoriaService")
@Stateless
@Local(AuditoriaService.class)
@Remote(AuditoriaService.class)
public class AuditoriaServiceImpl implements AuditoriaService {

	@EJB AuditoriaDAOService auditoriaDAOService;
	
	public void alta(Auditoria e) {
		auditoriaDAOService.persist(e);
	}

	public List<Auditoria> getAuditoria(String docId) {
		return auditoriaDAOService.getAuditoria(docId);
	}

}
