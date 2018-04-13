package uy.com.tmwc.facturator.spi;

import java.util.List;

import uy.com.tmwc.facturator.dto.ArticuloDTO;
import uy.com.tmwc.facturator.dto.ArticuloQuery;
import uy.com.tmwc.facturator.entity.Articulo;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;

public abstract interface ArticulosDAOService {

	public abstract String persist(Articulo e);

	public abstract void merge(Articulo e);

	public abstract Boolean delete(Articulo e);

	public List<ArticuloDTO> queryArticulos(ArticuloQuery paramArticuloQuery);

	public List<ArticuloPrecio> queryArticuloPrecio(String articulo);

	public void updateArticuloPrecio(ArticuloPrecio e);

}
