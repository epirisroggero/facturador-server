package uy.com.tmwc.facturator.spi;

import java.util.Collection;
import java.util.List;

import uy.com.tmwc.facturator.entity.ArticuloPartida;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.facturator.entity.Jefatura;
import uy.com.tmwc.facturator.entity.ParametrosAdministracion;

public abstract interface CatalogDAOService {
	public abstract <D extends CodigoNombreEntity> D findCatalogEntity(Class<D> paramClass, String paramString);

	public abstract <T, D> List<D> getCatalog(Class<D> paramClass);

	public abstract <T, D> List<D> getCatalog(Class<D> paramClass, String paramString);

	public abstract <T, D> List<D> getCatalog(Class<D> paramClass, String paramString, int paramInt);

	public abstract <D> List<D> queryCatalog(D paramD, int paramInt1, int paramInt2);

	public abstract <D> long queryCatalogCount(D paramD);

	public abstract ArticuloPrecio getPrecioArticulo(String paramString1, String paramString2);

	public abstract ArticuloPartida getArticuloPartida(String artId, String partidaId);

	public abstract Collection<Jefatura> getJefaturas();

	public abstract ParametrosAdministracion getParametrosAdministracion();
}