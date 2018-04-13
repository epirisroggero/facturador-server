package uy.com.tmwc.facturator.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.dto.ArticuloDTO;
import uy.com.tmwc.facturator.dto.ArticuloQuery;
import uy.com.tmwc.facturator.entity.Articulo;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.rapi.ArticulosService;
import uy.com.tmwc.facturator.spi.ArticulosDAOService;

@Name("articulosService")
@Stateless
@Local(ArticulosService.class)
@Remote(ArticulosService.class)
public class ArticulosServiceImpl implements ArticulosService {

	@EJB ArticulosDAOService articulosDAOService;
	
	public String persist(Articulo e) {
		return articulosDAOService.persist(e);
	}

	public void merge(Articulo e) {
		articulosDAOService.merge(e);
	}

	public Boolean delete(Articulo e) {
		return articulosDAOService.delete(e);
	}

	public List<ArticuloDTO> queryArticulos(ArticuloQuery paramArticuloQuery) {
		return articulosDAOService.queryArticulos(paramArticuloQuery);
	}
	
	public List<ArticuloPrecio> getArticuloPrecio(String articulo) {
		return articulosDAOService.queryArticuloPrecio(articulo);
	}
	
	public void updateArticuloPrecio(ArticuloPrecio e) {
		articulosDAOService.updateArticuloPrecio(e);
	}
}
