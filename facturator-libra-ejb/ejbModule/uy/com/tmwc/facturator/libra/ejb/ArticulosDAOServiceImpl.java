package uy.com.tmwc.facturator.libra.ejb;

import java.math.BigDecimal;
import java.text.Format;
import java.util.HashMap;
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
import org.jboss.ejb.plugins.cmp.jdbc.SQLUtil;

import uy.com.tmwc.facturator.dto.ArticuloDTO;
import uy.com.tmwc.facturator.dto.ArticuloQuery;
import uy.com.tmwc.facturator.entity.Articulo;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.entity.Iva;
import uy.com.tmwc.facturator.libra.entity.ArticuloPK;
import uy.com.tmwc.facturator.libra.entity.ArticuloPrecioPK;
import uy.com.tmwc.facturator.libra.util.DozerMappingsService;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.spi.ArticulosDAOService;

@Stateless
@Local({ ArticulosDAOService.class })
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ArticulosDAOServiceImpl extends ServiceBase implements ArticulosDAOService {

	@PersistenceContext
	EntityManager em;

	@EJB
	DozerMappingsService mapService;
	
	@EJB
	CatalogService catalogService;


	private static final String ARTICULOS_SUBQUERY = 
		"FROM " +
			"Articulo a " +
		"WHERE " +
			"a.id.empId = :empId " +
			"AND (a.activo = :activo OR :activo is null) "; 
			
	private static final String ARTICULO_PRECIO = 
		"FROM ArticuloPrecio a " +
		"WHERE " +
			"a.id.empId = :empId " +
			"AND (a.id.artId = :artId) "; 

	
	public String persist(Articulo e) {
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Articulo articulolibra = mapper.map(e, uy.com.tmwc.facturator.libra.entity.Articulo.class);
		
		ArticuloPK articuloPK = new ArticuloPK();
		articuloPK.setArtId(e.getCodigo());
		articuloPK.setEmpId(getEmpId());
		
		articulolibra.setId(articuloPK);
		
		if (e.getAbrevia() != null) {
			articulolibra.setAbrevia(e.getAbrevia().trim());
		}
		if (e.getNombre() != null) {
			articulolibra.setNombre(e.getNombre().trim());
		}
		
		HashMap<String, String> adicionales = new HashMap<String, String>();
		if (e.getVideoYoutube() != null) {
			adicionales.put("103", e.getVideoYoutube().trim());
		}
		if (e.getVideoYoutube() != null) {
			adicionales.put("113", e.getVideoYoutube2().trim());
		}
		if (e.getVideoYoutube() != null) {
			adicionales.put("114", e.getVideoYoutube3().trim());
		}

		if (e.getPeso() != null) {
			adicionales.put("105", e.getPeso());
		}
		if (e.getNotaInterna() != null) {
			adicionales.put("115", e.getNotaInterna());
		}

		articulolibra.setAdicionales(adicionales);
		
		em.persist(articulolibra);
		this.em.flush();
		
		return e.getCodigo();
	}

	public void merge(Articulo e) {
		DozerBeanMapper mapper = mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Articulo libraArticulo = mapper.map(e, uy.com.tmwc.facturator.libra.entity.Articulo.class);
		
		HashMap<String, String> adicionales = new HashMap<String, String>();
		if (e.getVideoYoutube() != null) {
			adicionales.put("103", e.getVideoYoutube());
		}
		if (e.getVideoYoutube() != null) {
			adicionales.put("113", e.getVideoYoutube2());
		}
		if (e.getVideoYoutube() != null) {
			adicionales.put("114", e.getVideoYoutube3());
		}
		if (e.getPeso() != null) {
			adicionales.put("105", e.getPeso());
		}
		if (e.getNotaInterna() != null) {
			adicionales.put("115", e.getNotaInterna());
		}

		libraArticulo.setAdicionales(adicionales);
				
		this.em.merge(libraArticulo);
		this.em.flush();		

	}

	public Boolean delete(Articulo e) {		
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Articulo libraArticulo = (uy.com.tmwc.facturator.libra.entity.Articulo) mapper.map(e,
				uy.com.tmwc.facturator.libra.entity.Articulo.class);
		
		libraArticulo.provideId(getEmpId(), e.getCodigo());

		uy.com.tmwc.facturator.libra.entity.Articulo articuloEntity = null;
		articuloEntity = em.find(uy.com.tmwc.facturator.libra.entity.Articulo.class, libraArticulo.getId());

		if (articuloEntity != null) {
			em.remove(articuloEntity);
			em.flush();	
		}		
		
		return true;		
	}

	public List<ArticuloDTO> queryArticulos(ArticuloQuery query) {
    	final StringBuilder sb = new StringBuilder(
    			"SELECT new uy.com.tmwc.facturator.dto.ArticuloDTO(" +
    			"a.codigo, " +
    			"a.nombre, " +
    			"a.familiaId, " +
    			"a.codigoOrigen" +
    			") ");
    	
    	sb.append(ARTICULOS_SUBQUERY);
    	sb.append((query.getProveedor() != null && query.getProveedor().length() > 0)  ? "AND (a.proveedor.codigo = :cProveedor) " : " ");		
    	
    	String familias = query.getFamilias();
    	if (familias != null && familias.length() > 0) {
    		sb.append(" AND a.familiaId IN (").append(familias).append(") ");
    	}    	
    	sb.append(" order by a.nombre asc");

	
		Query q = this.em.createQuery(sb.toString());
		
		q.setParameter("empId", getEmpId());
		if (query.getProveedor() != null) {
			q.setParameter("cProveedor", query.getProveedor());
		}
		//BooleanDozerConverter.toString(query.getActivo())
		q.setParameter("activo", query.getActivo() == true ? "S" : null);
		
		List<ArticuloDTO> list = q.getResultList();
		return list;

	}


	public List<ArticuloPrecio> queryArticuloPrecio(String articulo) {
    	final StringBuilder sb = new StringBuilder(
    			"SELECT new uy.com.tmwc.facturator.entity.ArticuloPrecio(" +
    				"a.id.artId, " +
    				"a.id.precioBaseId, " +
    				"a.precio, " +
    				"a.mndIdPrecio, " +
    				"a.precioBaseConIVA" +
    				") ");
		
    	sb.append(ARTICULO_PRECIO);
    	
    	Query q = this.em.createQuery(sb.toString());
		
		q.setParameter("empId", getEmpId());
		q.setParameter("artId", articulo);
		
		List<ArticuloPrecio> list = q.getResultList();
		return list;
	}
	
	public void updateArticuloPrecio(ArticuloPrecio e) {
		ArticuloPrecioPK articuloPrecioPK = new ArticuloPrecioPK();
		articuloPrecioPK.setEmpId(getEmpId());
		articuloPrecioPK.setArtId(e.getArtId());
		articuloPrecioPK.setPrecioBaseId(e.getPrecioBaseId());
		
		uy.com.tmwc.facturator.libra.entity.ArticuloPrecio artPrecioEntity = em.find(uy.com.tmwc.facturator.libra.entity.ArticuloPrecio.class, articuloPrecioPK);
		if (artPrecioEntity == null) {
			persist(e);
		} else {
			merge(e);
		}
	}
	
	private void persist(ArticuloPrecio e) {
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();

		ArticuloPK articuloPK = new ArticuloPK();
		articuloPK.setArtId(e.getArtId());
		articuloPK.setEmpId(getEmpId());

		uy.com.tmwc.facturator.libra.entity.Articulo articuloEntity = em.find(uy.com.tmwc.facturator.libra.entity.Articulo.class, articuloPK);
		Short ivaIdArt = articuloEntity.getIvaIdArt();
		
		BigDecimal precioIva = BigDecimal.ZERO;
		if (ivaIdArt.intValue() > 0) {
			Iva iva = catalogService.findCatalogEntity(Iva.class.getSimpleName(), ivaIdArt.toString());
			precioIva = iva.calcularPrecioIvaInc(e.getPrecio());
		} else {
			precioIva = BigDecimal.ZERO; 
		}
		
		uy.com.tmwc.facturator.libra.entity.ArticuloPrecio articuloPrecioLibra = mapper.map(e, uy.com.tmwc.facturator.libra.entity.ArticuloPrecio.class);
		ArticuloPrecioPK articuloPrecioPK = new ArticuloPrecioPK();
		articuloPrecioPK.setEmpId(getEmpId());
		articuloPrecioPK.setArtId(e.getArtId());
		articuloPrecioPK.setPrecioBaseId(e.getPrecioBaseId());
		
		articuloPrecioLibra.setPrecioIVA(precioIva);
		articuloPrecioLibra.setId(articuloPrecioPK);
		
		em.persist(articuloPrecioLibra);
		this.em.flush();
	}

	private void merge(ArticuloPrecio e) {		
		ArticuloPK articuloPK = new ArticuloPK();
		articuloPK.setArtId(e.getArtId());
		articuloPK.setEmpId(getEmpId());

		uy.com.tmwc.facturator.libra.entity.Articulo articuloEntity = em.find(uy.com.tmwc.facturator.libra.entity.Articulo.class, articuloPK);
		Short ivaIdArt = articuloEntity.getIvaIdArt();
		
		BigDecimal precioIva = BigDecimal.ZERO;
		if (ivaIdArt.intValue() > 0) {
			Iva iva = catalogService.findCatalogEntity(Iva.class.getSimpleName(), ivaIdArt.toString());
			precioIva = iva.calcularPrecioIvaInc(e.getPrecio());
		} else {
			precioIva = BigDecimal.ZERO; 
		}

		DozerBeanMapper mapper = mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.ArticuloPrecio articuloPrecioLibra = mapper.map(e, uy.com.tmwc.facturator.libra.entity.ArticuloPrecio.class);
		articuloPrecioLibra.setPrecioIVA(precioIva);
		
		this.em.merge(articuloPrecioLibra);
		this.em.flush();		

	}

	

}
