package uy.com.tmwc.facturator.rapi;

import java.util.List;

import uy.com.tmwc.facturator.dto.ArticuloDTO;
import uy.com.tmwc.facturator.dto.ArticuloQuery;
import uy.com.tmwc.facturator.entity.Articulo;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;

public abstract interface ArticulosService {
	 
	public abstract String persist(Articulo e);
	 
	public abstract void merge(Articulo e);
	 
	public abstract Boolean delete(Articulo e);
		
	List<ArticuloDTO> queryArticulos(ArticuloQuery paramArticuloQuery);
	
	List<ArticuloPrecio> getArticuloPrecio(String articulo);
	
	void updateArticuloPrecio(ArticuloPrecio e);
}
