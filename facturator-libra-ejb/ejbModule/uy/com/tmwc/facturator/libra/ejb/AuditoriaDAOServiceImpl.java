package uy.com.tmwc.facturator.libra.ejb;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dozer.DozerBeanMapper;

import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.libra.entity.Auditoria;
import uy.com.tmwc.facturator.libra.entity.AuditoriaPK;
import uy.com.tmwc.facturator.libra.util.DozerMappingsService;
import uy.com.tmwc.facturator.rapi.UsuariosService;
import uy.com.tmwc.facturator.spi.AuditoriaDAOService;
import uy.com.tmwc.facturator.utils.MappingUtils;

@Stateless
@Local({ AuditoriaDAOService.class })
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class AuditoriaDAOServiceImpl extends ServiceBase implements AuditoriaDAOService {
	@PersistenceContext
	EntityManager em;

	@EJB
	DozerMappingsService mapService;

	@EJB
	UsuariosService usuariosService;

	public void persist(uy.com.tmwc.facturator.entity.Auditoria e) {
		Number nextAudId = (Number) em.createNamedQuery("Auditoria.nextAudId").setParameter("empId", getEmpId())
				.getSingleResult();
		if (nextAudId == null) {
			nextAudId = 1;
		}
		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		e.setUsuId(usuarioLogin.getCodigo());
		e.setUsuNom(usuarioLogin.getNombre());

		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		Auditoria lineaAuditoria = mapper.map(e, Auditoria.class);

		AuditoriaPK auditoriaPK = new AuditoriaPK();
		auditoriaPK.setAudId(nextAudId.intValue());
		auditoriaPK.setEmpId(getEmpId());

		lineaAuditoria.setId(auditoriaPK);
		lineaAuditoria.setAudFechaHora(new Date());

		em.persist(lineaAuditoria);
		this.em.flush();

	}

	public List<uy.com.tmwc.facturator.entity.Auditoria> getAuditoria(String docId) {
		@SuppressWarnings("unchecked")
		List<Auditoria> lineasAuditoriaDoc = this.em.createNamedQuery("Auditoria.auditoriaDocumento")
				.setParameter("empId", getEmpId()).setParameter("docId", docId).getResultList();

		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		List<uy.com.tmwc.facturator.entity.Auditoria> res = MappingUtils.map(
				uy.com.tmwc.facturator.entity.Auditoria.class, lineasAuditoriaDoc, mapper);

		return res;

	}

	public void merge(uy.com.tmwc.facturator.entity.Auditoria e) {
		// TODO Auto-generated method stub

	}

}
