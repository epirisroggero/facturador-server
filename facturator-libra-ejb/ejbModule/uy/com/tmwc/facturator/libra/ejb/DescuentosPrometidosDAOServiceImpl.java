package uy.com.tmwc.facturator.libra.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dozer.DozerBeanMapper;

import uy.com.tmwc.facturator.entity.DescuentoPrometidoComprobante;
import uy.com.tmwc.facturator.libra.entity.DescuentoPrometidoComprobantePK;
import uy.com.tmwc.facturator.libra.util.DozerMappingsService;
import uy.com.tmwc.facturator.mapper.Mapper;
import uy.com.tmwc.facturator.spi.DescuentosPrometidosDAOService;

@Stateless
@Local({ DescuentosPrometidosDAOService.class })
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DescuentosPrometidosDAOServiceImpl extends ServiceBase implements DescuentosPrometidosDAOService {

	@PersistenceContext
	EntityManager em;

	@EJB
	DozerMappingsService mapService;

	public String persist(DescuentoPrometidoComprobante e) {
		Number nextId = (Number) em.createNamedQuery("DescuentosPrometidos.nextId").setParameter("empId", getEmpId())
				.getSingleResult();
		if (nextId == null) {
			nextId = 1;
		}
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.DescuentoPrometidoComprobante libraDescuentoPrometido = mapper.map(e,
				uy.com.tmwc.facturator.libra.entity.DescuentoPrometidoComprobante.class);

		DescuentoPrometidoComprobantePK descuentoPK = new DescuentoPrometidoComprobantePK();
		descuentoPK.setDpcId(nextId.intValue());
		descuentoPK.setEmpId(getEmpId());

		libraDescuentoPrometido.setId(descuentoPK);

		this.em.persist(libraDescuentoPrometido);
		this.em.flush();

		return String.valueOf(nextId);
	}

	public void merge(DescuentoPrometidoComprobante e) {
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.DescuentoPrometidoComprobante libraDescuentoPrometido = mapper.map(e,
				uy.com.tmwc.facturator.libra.entity.DescuentoPrometidoComprobante.class);

		this.em.merge(libraDescuentoPrometido);
		this.em.flush();
	}

	public Boolean remove(DescuentoPrometidoComprobante e) {
		if (e != null && e.getDpcId() > 0) {
			DozerBeanMapper mapper = mapService.getDozerBeanMapper();
			uy.com.tmwc.facturator.libra.entity.DescuentoPrometidoComprobante libraDescuentoPrometido = mapper.map(e,
					uy.com.tmwc.facturator.libra.entity.DescuentoPrometidoComprobante.class);

			libraDescuentoPrometido = em.find(uy.com.tmwc.facturator.libra.entity.DescuentoPrometidoComprobante.class,
					libraDescuentoPrometido.getId());

			if (libraDescuentoPrometido != null) {
				libraDescuentoPrometido.getComprobante().getDescuentosPrometidos().remove(libraDescuentoPrometido);

				em.remove(libraDescuentoPrometido);
				em.flush();
			}
		}
		return true;
	}

	public List<DescuentoPrometidoComprobante> getDescuentosPrometidos(String cmpId, String categoriaCliente) {
		Short comprobante = new Short(cmpId);

		List<?> ormResult = this.em.createNamedQuery("DescuentosPrometidos.obtenerDescuentosPrometidos")
				.setParameter("empId", getEmpId()).setParameter("cmpId", comprobante)
				.setParameter("categoriaCliente", categoriaCliente).getResultList();

		List<DescuentoPrometidoComprobante> resultado = new ArrayList<DescuentoPrometidoComprobante>(
				new Mapper().mapCollection(ormResult, DescuentoPrometidoComprobante.class));

		return resultado;
	}

}
