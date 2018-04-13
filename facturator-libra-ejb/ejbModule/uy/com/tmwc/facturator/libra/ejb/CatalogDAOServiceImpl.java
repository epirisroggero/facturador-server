package uy.com.tmwc.facturator.libra.ejb;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dozer.DozerBeanMapper;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;

import uy.com.tmwc.facturator.entity.ArticuloPartida;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.facturator.entity.Jefatura;
import uy.com.tmwc.facturator.entity.ParametrosAdministracion;
import uy.com.tmwc.facturator.libra.entity.HasId;
import uy.com.tmwc.facturator.libra.entity.LibraExtensionEntity;
import uy.com.tmwc.facturator.libra.entity.Preciosventa;
import uy.com.tmwc.facturator.libra.util.DozerMappingsService;
import uy.com.tmwc.facturator.libra.util.JPAUtils;
import uy.com.tmwc.facturator.mapper.Mapper;
import uy.com.tmwc.facturator.mapper.SimpleClassMapper;
import uy.com.tmwc.facturator.spi.CatalogDAOService;
import uy.com.tmwc.facturator.utils.MappingUtils;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Stateless
@Local({ CatalogDAOService.class })
public class CatalogDAOServiceImpl extends ServiceBase implements CatalogDAOService {

	@PersistenceContext
	EntityManager entityManager;

	@EJB
	DozerMappingsService mapServ;

	private static final Map<Class<?>, Class<?>> classMapping = new HashMap<Class<?>, Class<?>>();
	private static final SimpleClassMapper jefaturasClassMapper;

	static {
		classMapping.put(uy.com.tmwc.facturator.entity.Articulo.class, uy.com.tmwc.facturator.libra.entity.Articulo.class);
		classMapping.put(uy.com.tmwc.facturator.entity.ArticuloPartida.class, uy.com.tmwc.facturator.libra.entity.ArticuloPartida.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Cliente.class, uy.com.tmwc.facturator.libra.entity.Cliente.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Contacto.class, uy.com.tmwc.facturator.libra.entity.Contacto.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Comprobante.class, uy.com.tmwc.facturator.libra.entity.Comprobante.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Deposito.class, uy.com.tmwc.facturator.libra.entity.Deposito.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Entrega.class, uy.com.tmwc.facturator.libra.entity.Entrega.class);
		classMapping.put(uy.com.tmwc.facturator.entity.FormaPago.class, uy.com.tmwc.facturator.libra.entity.Formaspago.class);
		classMapping.put(uy.com.tmwc.facturator.entity.GrupoContacto.class, uy.com.tmwc.facturator.libra.entity.Gruposcontacto.class);		
		classMapping.put(uy.com.tmwc.facturator.entity.Iva.class, uy.com.tmwc.facturator.libra.entity.Iva.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Moneda.class, uy.com.tmwc.facturator.libra.entity.Moneda.class);
		classMapping.put(uy.com.tmwc.facturator.entity.PlanPagos.class, uy.com.tmwc.facturator.libra.entity.Planpago.class);
		classMapping.put(uy.com.tmwc.facturator.entity.PreciosVenta.class, uy.com.tmwc.facturator.libra.entity.Preciosventa.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Vendedor.class, uy.com.tmwc.facturator.libra.entity.Vendedore.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Usuario.class, uy.com.tmwc.facturator.libra.entity.Usuario.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Tarea.class, uy.com.tmwc.facturator.libra.entity.Tarea.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Proveedor.class, uy.com.tmwc.facturator.libra.entity.Proveedor.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Numerador.class, uy.com.tmwc.facturator.libra.entity.Numerador.class);

		classMapping.put(uy.com.tmwc.facturator.entity.Zona.class, uy.com.tmwc.facturator.libra.entity.Zona.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Iva.class, uy.com.tmwc.facturator.libra.entity.Iva.class);
		classMapping.put(uy.com.tmwc.facturator.entity.FormaPago.class, uy.com.tmwc.facturator.libra.entity.Formaspago.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Pais.class, uy.com.tmwc.facturator.libra.entity.Pais.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Caja.class, uy.com.tmwc.facturator.libra.entity.Caja.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Fanfold.class, uy.com.tmwc.facturator.libra.entity.Fanfold.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Localescomerciale.class, uy.com.tmwc.facturator.libra.entity.Localescomerciale.class);

		classMapping.put(uy.com.tmwc.facturator.entity.Giro.class, uy.com.tmwc.facturator.libra.entity.Giro.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Departamento.class, uy.com.tmwc.facturator.libra.entity.Departamento.class);
		classMapping.put(uy.com.tmwc.facturator.entity.CategoriasClientes.class, uy.com.tmwc.facturator.libra.entity.Categoriasclientes.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Capitulo.class, uy.com.tmwc.facturator.libra.entity.Capitulo.class);
		classMapping.put(uy.com.tmwc.facturator.entity.FamiliaArticulos.class, uy.com.tmwc.facturator.libra.entity.Familia.class);
		classMapping.put(uy.com.tmwc.facturator.entity.ParametrosAdministracion.class, uy.com.tmwc.facturator.libra.entity.Parametrosadministracion.class);
		classMapping.put(uy.com.tmwc.facturator.entity.Rubro.class, uy.com.tmwc.facturator.libra.entity.Rubro.class);
		classMapping.put(uy.com.tmwc.facturator.entity.CentrosCosto.class, uy.com.tmwc.facturator.libra.entity.Centroscosto.class);

		jefaturasClassMapper = new SimpleClassMapper();
		jefaturasClassMapper.add(uy.com.tmwc.facturator.libra.entity.JefaturaArticulo.class, uy.com.tmwc.facturator.entity.JefaturaArticulo.class);
		jefaturasClassMapper.add(uy.com.tmwc.facturator.libra.entity.JefaturaFamiliaArticulos.class, uy.com.tmwc.facturator.entity.JefaturaFamiliaArticulos.class);
		jefaturasClassMapper.add(uy.com.tmwc.facturator.libra.entity.JefaturaProveedor.class, uy.com.tmwc.facturator.entity.JefaturaProveedor.class);
	}

	public <T, D> List<D> getCatalog(Class<D> clazz) {
		Class clazzA = getLibraEntity(clazz);

		return queryCatalog(clazzA, clazz, "", null);
	}

	public <T, D> List<D> getCatalog(Class<D> clazz, String query) {
		Class clazzA = getLibraEntity(clazz);

		return queryCatalog(clazzA, clazz, query, null);
	}

	public <T, D> List<D> getCatalog(Class<D> clazz, String query, int limit) {
		Class clazzA = getLibraEntity(clazz);

		return queryCatalog(clazzA, clazz, query, Integer.valueOf(limit));
	}

	public ArticuloPrecio getPrecioArticulo(String preciosVenta, String articulo) {
		Query query = this.entityManager.createNamedQuery("ArticuloPrecio.precioArticuloParaPrecioVenta").setParameter("empId", getEmpId()).setParameter("preciosVenta", Short.valueOf(Short.parseShort(preciosVenta)))
				.setParameter("articulo", articulo);
		
		Object []result = JPAUtils.getAtMostOne(query);
		if (result == null) 
			return null;
		
		uy.com.tmwc.facturator.libra.entity.ArticuloPrecio articuloPrecio = (uy.com.tmwc.facturator.libra.entity.ArticuloPrecio) result[0];
		Preciosventa precioVenta = (Preciosventa) result[1];
		
		BigDecimal precioBase = articuloPrecio.getPrecio();
		if (precioBase == null) 
			return null;
		
		boolean sumarUtilidadArticulo = precioVenta.getSumarUtilidadArticulo();
		BigDecimal costoUtilidad = articuloPrecio.getArticulo().getCostoUtilidad();
		BigDecimal precioVentaPorcentaje = precioVenta.getPrecioVentaPorcentaje();
		
		BigDecimal precio = Preciosventa.calcularPrecio(precioBase, sumarUtilidadArticulo, costoUtilidad, precioVentaPorcentaje);
		
		//El modelo del facturador no tiene el doble concepto de preciobase/precioventa, asi que consolidamos todo en uno.
		ArticuloPrecio articuloPrecioModel = (ArticuloPrecio) map(articuloPrecio, ArticuloPrecio.class);
		articuloPrecioModel.setPrecio(precio);
		
		return articuloPrecioModel;
	}
	
	public ArticuloPartida getArticuloPartida(String partidaId, String articuloId) {
		Query query = this.entityManager.createNamedQuery("ArticuloPartida.queryArticuloPartida")
				.setParameter("empId", getEmpId())
				.setParameter("partidaId", partidaId)
				.setParameter("articuloId", articuloId);
		
		Object[] result = JPAUtils.getAtMostOne(query);
		if (result == null) 
			return null;
		
		uy.com.tmwc.facturator.libra.entity.ArticuloPartida articuloPartida = (uy.com.tmwc.facturator.libra.entity.ArticuloPartida) result[0];
		
		ArticuloPartida articuloPartidaModel = (ArticuloPartida) map(articuloPartida, ArticuloPartida.class);		
		return articuloPartidaModel;
	}


	public <D extends CodigoNombreEntity> D findCatalogEntity(Class<D> clazz, String codigo) {
		Class<?> libraEntityClazz = getLibraEntity(clazz);
		Object entity;
		if (LibraExtensionEntity.class.isAssignableFrom(libraEntityClazz)) {
			entity = queryLibraExtensionEntity((Class<? extends LibraExtensionEntity>) libraEntityClazz, codigo);
		} else {
			Object id = createPersistentId(clazz, codigo);
			if (id != null) {
				entity = this.entityManager.find(libraEntityClazz, id);
			} else {
				return null;
			}
		}
		if (entity == null) {
			return null;
		}
		DozerBeanMapper mapper = this.mapServ.getDozerBeanMapper();
		return (D) mapper.map(entity, clazz);
	}

	public Collection<Jefatura> getJefaturas() {
		List list = this.entityManager.createNamedQuery("Jefatura.all").getResultList();

		Mapper mapper = new Mapper(jefaturasClassMapper);
		return mapper.mapCollection(list, Jefatura.class);
	}

	public <D> List<D> queryCatalog(D example, int firstResult, int limit) {
		Object converted = new Mapper().map(example, getLibraEntity(example.getClass()));

		Session session = (Session) this.entityManager.getDelegate();
		List list = session.createCriteria(converted.getClass()).add(createExampleCriteria(converted)).add(Expression.eq("id.empId", getEmpId())).setMaxResults(limit).setFirstResult(firstResult)
				.list();

		return (List) new Mapper().mapCollection(list, example.getClass());
	}

	private Example createExampleCriteria(Object example) {
		return Example.create(example).ignoreCase().enableLike(MatchMode.ANYWHERE).excludeZeroes();
	}

	public <D> long queryCatalogCount(D example) {
		Object converted = this.mapServ.getDozerBeanMapper().map(example, getLibraEntity(example.getClass()));
		Session session = (Session) this.entityManager.getDelegate();
		Object count = session.createCriteria(converted.getClass()).add(createExampleCriteria(converted)).setProjection(Projections.rowCount()).uniqueResult();

		return count == null ? 0L : ((Integer) count).longValue();
	}

	private <D extends CodigoNombreEntity> Object createPersistentId(Class<D> clazz, String codigo) {
		D source;
		try {
			source = (D) clazz.newInstance();
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
		source.setCodigo(codigo);
		DozerBeanMapper mapper = this.mapServ.getDozerBeanMapper();
		Class libraEntityClazz = getLibraEntity(clazz);
		HasId target = (HasId) mapper.map(source, libraEntityClazz);
		Object id = target.getId();
		return id;
	}

	public <T> T map(Object source, Class<T> clazz) {
		if (source == null) {
			return null;
		}
		DozerBeanMapper mapper = this.mapServ.getDozerBeanMapper();
		return mapper.map(source, clazz);
	}

	private <T extends LibraExtensionEntity> T queryLibraExtensionEntity(Class<T> clazz, String codigo) {
		CatalogEntity annotation = (CatalogEntity) clazz.getAnnotation(CatalogEntity.class);
		if (annotation == null) {
			throw new IllegalArgumentException("No es un " + CatalogEntity.class.getSimpleName());
		}

		if (annotation.useNamedQuery()) {
			throw new UnsupportedOperationException("not yet implemented");
		}
		String strQuery = "select a from " + clazz.getSimpleName() + " a where codigo=:codigo";
		if (annotation.restrictEmpId()) {
			strQuery = strQuery + " and a.id.empId=:empId";
		}

		Query q = this.entityManager.createQuery(strQuery).setParameter("codigo", codigo);

		if (annotation.restrictEmpId()) {
			q.setParameter("empId", getEmpId());
		}

		return (T) JPAUtils.getAtMostOne(q);
	}

	private <T, D> List<D> queryCatalog(Class<T> clazzA, Class<D> clazzB, String query, Integer limit) {
		CatalogEntity annotation = (CatalogEntity) clazzA.getAnnotation(CatalogEntity.class);
		Query q;
		if (annotation.useNamedQuery()) {
			q = this.entityManager.createNamedQuery(clazzA.getSimpleName() + "." + "query").setParameter("query", "%" + query + "%");
		} else {
			String nomField = annotation.prefix() + (annotation.abreviated() ? "Nom" : "Nombre");
			if (annotation.prefix().equals("")) {
				nomField = Character.toLowerCase(nomField.charAt(0)) + nomField.substring(1);
			}

			String strQuery = "select a from " + clazzA.getSimpleName() + " a where " + nomField + " like :query";
			if (annotation.restrictEmpId()) {
				strQuery = strQuery + " and a.id.empId=:empId";
			}
			strQuery = strQuery + " order by " + nomField;

			q = this.entityManager.createQuery(strQuery).setParameter("query", "%" + query + "%");
		}

		if (annotation.restrictEmpId()) {
			q.setParameter("empId", getEmpId());
		}

		if (limit != null) {
			q.setMaxResults(limit.intValue() + 1);
		}
		List list = q.getResultList();

		return map(clazzB, list);
	}

	private <D> Class<?> getLibraEntity(Class<D> clazz) {
		Class clazzA = (Class) classMapping.get(clazz);
		if (clazzA == null) {
			throw new RuntimeException("Unknown catalog " + clazz.getSimpleName());
		}
		return clazzA;
	}

	private <D, T> List<D> map(Class<D> clazzB, List<T> list) {
		DozerBeanMapper mapper = this.mapServ.getDozerBeanMapper();
		return MappingUtils.map(clazzB, list, mapper);
	}

	public ParametrosAdministracion getParametrosAdministracion() {		
		List list = this.entityManager.createNamedQuery("PatametrosAdministracion.fechatrabado").setParameter("empId", getEmpId())
			.setParameter("codigo", "1").getResultList();

		DozerBeanMapper mapper = this.mapServ.getDozerBeanMapper();
		
		List<ParametrosAdministracion> res = MappingUtils.map(ParametrosAdministracion.class, list, mapper);
		
		return res.get(0);

	}
}