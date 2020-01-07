package uy.com.tmwc.facturator.rapi;

import java.util.Collection;
import java.util.List;

import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.Jefatura;
import uy.com.tmwc.facturator.entity.ParametrosAdministracion;
import uy.com.tmwc.facturator.entity.Vendedor;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("remote/catalogService")
public abstract interface CatalogService extends RemoteService
{
  public static final Class<Vendedor> catalogVendedor = Vendedor.class;
  
  public static final Class<Documento> catalogDocumento = Documento.class;

  public abstract <T extends CodigoNombreEntity> List<T> getCatalog(String paramString);

  public abstract <T extends CodigoNombreEntity> List<T> getCatalog(String paramString1, String paramString2);

  public abstract <T extends CodigoNombreEntity> List<T> getCatalog(String paramString1, String paramString2, int paramInt);

  public abstract <T extends CodigoNombreEntity> List<T> queryCatalog(T paramT, int paramInt1, int paramInt2);

  public abstract <T extends CodigoNombreEntity> long queryCatalogCount(T paramT);
  
  public abstract <D extends CodigoNombreEntity> D findCatalogEntity(String catalog, String codigo);

  public abstract ArticuloPrecio getPrecioArticulo(String preciosVenta, String articulo);

  public abstract Collection<Jefatura> getJefaturas();
  
  public abstract ParametrosAdministracion getParametrosAdministracion();
}