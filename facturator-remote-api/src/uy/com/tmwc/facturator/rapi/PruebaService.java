package uy.com.tmwc.facturator.rapi;

import java.util.List;

import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.Vendedor;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("remote/catalogService") //BUSCAME
public interface PruebaService extends RemoteService {

	public static final Class<Vendedor> catalogVendedor = Vendedor.class;
	public static final Class<Documento> catalogDocumento = Documento.class;
	
	<T extends CodigoNombreEntity> List<T> getCatalog(String catalog);
	
	<T extends CodigoNombreEntity> List<T> getCatalog(String catalog, String query);

	<T extends CodigoNombreEntity> List<T> getCatalog(String catalog, String query, int limit);
	
	ArticuloPrecio getPrecioArticulo(String preciosVenta, String articulo); 
}
