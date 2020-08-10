package uy.com.tmwc.facturator.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.entity.Articulo;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.entity.Cliente;
import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.entity.Deposito;
import uy.com.tmwc.facturator.entity.Entrega;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.PlanPagos;
import uy.com.tmwc.facturator.entity.PreciosVenta;
import uy.com.tmwc.facturator.entity.Vendedor;
import uy.com.tmwc.facturator.rapi.PruebaService;

@Name("pruebaService")
@Stateless
@Local(PruebaService.class)
public class PruebaServiceImpl implements PruebaService {

	@EJB
	uy.com.tmwc.facturator.spi.CatalogDAOService catalogService;

	private final static Map<String, Class<?>> simpleNameToClass = new HashMap<String, Class<?>>();

	static {
		add(Vendedor.class);
		add(Cliente.class);
		add(Deposito.class);
		add(PreciosVenta.class);
		add(Moneda.class);
		add(Articulo.class);
		add(Comprobante.class);
		add(Entrega.class);
		add(PlanPagos.class);
	}

	@SuppressWarnings("unchecked")
	public <T extends CodigoNombreEntity> List<T> getCatalog(String catalog) {
		Class<?> clazz = getCatalogClass(catalog);

		return (List<T>) catalogService.getCatalog(clazz);
	}

	@SuppressWarnings("unchecked")
	public <T extends CodigoNombreEntity> List<T> getCatalog(String catalog, String query) {
		Class<?> clazz = getCatalogClass(catalog);

		return (List<T>) catalogService.getCatalog(clazz, query);
	}

	@SuppressWarnings("unchecked")
	public <T extends CodigoNombreEntity> List<T> getCatalog(String catalog, String query, int limit) {
		Class<?> clazz = getCatalogClass(catalog);

		return (List<T>) catalogService.getCatalog(clazz, query, limit);
	}

	private Class<?> getCatalogClass(String catalog) {
		Class<?> clazz = simpleNameToClass.get(catalog);
		if (clazz == null) {
			throw new IllegalArgumentException("Catálogo inválodo " + catalog);
		}
		return clazz;
	}

	private static void add(Class<?> clazz) {
		simpleNameToClass.put(clazz.getSimpleName(), clazz);
	}

	public Vendedor getVendedor() {
		return null;
	}

	public ArticuloPrecio getPrecioArticulo(String preciosVenta, String articulo) {
		return catalogService.getPrecioArticulo(preciosVenta, articulo);
	}

}
