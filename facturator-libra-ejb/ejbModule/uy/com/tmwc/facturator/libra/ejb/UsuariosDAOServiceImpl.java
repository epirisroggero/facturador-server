package uy.com.tmwc.facturator.libra.ejb;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.libra.entity.UsuarioPK;
import uy.com.tmwc.facturator.libra.util.DozerMappingsService;
import uy.com.tmwc.facturator.spi.UsuariosDAOService;


@Stateless
@Local({ UsuariosDAOService.class })
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class UsuariosDAOServiceImpl extends ServiceBase implements UsuariosDAOService {

	@PersistenceContext
	EntityManager em;

	@EJB
	DozerMappingsService mapService;

	public void updateClaveSup(String usuId, String clave) {
		uy.com.tmwc.facturator.libra.entity.Usuario current = (uy.com.tmwc.facturator.libra.entity.Usuario) this.em.find(
				uy.com.tmwc.facturator.libra.entity.Usuario.class, new UsuarioPK(getEmpId(), Short.parseShort(usuId)));

		current.setClaveSup(clave);

		this.em.merge(current);
		this.em.flush();
	}

	public void updateEmail(String usuId, String email) {
		uy.com.tmwc.facturator.libra.entity.Usuario current = (uy.com.tmwc.facturator.libra.entity.Usuario) this.em.find(
				uy.com.tmwc.facturator.libra.entity.Usuario.class, new UsuarioPK(getEmpId(), Short.parseShort(usuId)));

		current.setUsuEmail(email);

		this.em.merge(current);
		this.em.flush();		
	}
	
	public void update(Usuario usuario) {
		uy.com.tmwc.facturator.libra.entity.Usuario current = (uy.com.tmwc.facturator.libra.entity.Usuario) this.em.find(
				uy.com.tmwc.facturator.libra.entity.Usuario.class, new UsuarioPK(getEmpId(), Short.parseShort(usuario.getCodigo())));

		current.setNombre(usuario.getNombre());
		current.setUsuEmail(usuario.getUsuEmail());
		current.setUsuNotas(usuario.getUsuNotas());
		current.setUsuTipo(usuario.getUsuTipo());
		current.setPermisoId(usuario.getPermisoId());
		current.setVenId(usuario.getVenId());		

		this.em.merge(current);
		this.em.flush();
	}


}
