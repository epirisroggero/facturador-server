package uy.com.tmwc.facturator.service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.entity.Articulo;
import uy.com.tmwc.facturator.entity.ArticuloPartida;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.entity.Banco;
import uy.com.tmwc.facturator.entity.Caja;
import uy.com.tmwc.facturator.entity.Capitulo;
import uy.com.tmwc.facturator.entity.CategoriasClientes;
import uy.com.tmwc.facturator.entity.CentrosCosto;
import uy.com.tmwc.facturator.entity.Cliente;
import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.entity.Concepto;
import uy.com.tmwc.facturator.entity.Contacto;
import uy.com.tmwc.facturator.entity.Departamento;
import uy.com.tmwc.facturator.entity.Deposito;
import uy.com.tmwc.facturator.entity.Entrega;
import uy.com.tmwc.facturator.entity.FamiliaArticulos;
import uy.com.tmwc.facturator.entity.Fanfold;
import uy.com.tmwc.facturator.entity.FormaPago;
import uy.com.tmwc.facturator.entity.Giro;
import uy.com.tmwc.facturator.entity.GrupoContacto;
import uy.com.tmwc.facturator.entity.Iva;
import uy.com.tmwc.facturator.entity.Jefatura;
import uy.com.tmwc.facturator.entity.Localescomerciale;
import uy.com.tmwc.facturator.entity.Marca;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.Numerador;
import uy.com.tmwc.facturator.entity.Pais;
import uy.com.tmwc.facturator.entity.ParametrosAdministracion;
import uy.com.tmwc.facturator.entity.PlanPagos;
import uy.com.tmwc.facturator.entity.PreciosVenta;
import uy.com.tmwc.facturator.entity.Proveedor;
import uy.com.tmwc.facturator.entity.Rubro;
import uy.com.tmwc.facturator.entity.Tarea;
import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.entity.Vendedor;
import uy.com.tmwc.facturator.entity.Zona;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.spi.CatalogDAOService;

@Name("catalogService")
@Stateless
@Local({ CatalogService.class })
public class CatalogServiceImpl implements CatalogService {

	@EJB
	CatalogDAOService catalogService;
	private static final Map<String, Class<?>> simpleNameToClass = new HashMap<String, Class<?>>();

	static {
		add(Vendedor.class);
		add(Cliente.class);
		add(Contacto.class);
		add(Departamento.class);
		add(Deposito.class);
		add(PreciosVenta.class);
		add(Moneda.class);
		add(Articulo.class);
		add(Comprobante.class);
		add(Entrega.class);
		add(GrupoContacto.class);
		add(PlanPagos.class);
		add(Usuario.class);
		add(Tarea.class);
		add(Zona.class);
		add(Pais.class);
		add(Giro.class);
		add(CategoriasClientes.class);
		add(Capitulo.class);
		add(FamiliaArticulos.class);
		add(Proveedor.class);
		add(Caja.class);
		add(Iva.class);
		add(Fanfold.class);
		add(Localescomerciale.class);
		add(Numerador.class);
		add(Rubro.class);
		add(CentrosCosto.class);
		add(FormaPago.class);
		add(Banco.class);
		add(Concepto.class);
		add(Marca.class);
	}

	public <T extends CodigoNombreEntity> List<T> getCatalog(String catalog) {
		Class clazz = getCatalogClass(catalog);

		return this.catalogService.getCatalog(clazz);
	}

	public <T extends CodigoNombreEntity> List<T> getCatalog(String catalog, String query) {
		Class clazz = getCatalogClass(catalog);

		return this.catalogService.getCatalog(clazz, query);
	}

	public <T extends CodigoNombreEntity> List<T> getCatalog(String catalog, String query, int limit) {
		Class clazz = getCatalogClass(catalog);

		return this.catalogService.getCatalog(clazz, query, limit);
	}

	private Class<?> getCatalogClass(String catalog) {
		Class clazz = (Class) simpleNameToClass.get(catalog);
		if (clazz == null) {
			throw new IllegalArgumentException("Invalid catalog " + catalog);
		}
		return clazz;
	}

	private static void add(Class<?> clazz) {
		simpleNameToClass.put(clazz.getSimpleName(), clazz);
	}

	public ArticuloPrecio getPrecioArticulo(String preciosVenta, String articulo) {
		return this.catalogService.getPrecioArticulo(preciosVenta, articulo);
	}

	public ArticuloPartida getArticuloPartida(String artId, String partidaId) {
		return this.catalogService.getArticuloPartida(artId, partidaId);
	}

	public Collection<Jefatura> getJefaturas() {
		return this.catalogService.getJefaturas();
	}

	public ParametrosAdministracion getParametrosAdministracion() {
		return this.catalogService.getParametrosAdministracion();
	}

	public <T extends CodigoNombreEntity> List<T> queryCatalog(T example, int firstResult, int limit) {
		return this.catalogService.queryCatalog(example, firstResult, limit);
	}

	public <T extends CodigoNombreEntity> long queryCatalogCount(T example) {
		return this.catalogService.queryCatalogCount(example);
	}

	@SuppressWarnings("unchecked")
	public <D extends CodigoNombreEntity> D findCatalogEntity(String catalog, String codigo) {
		Class<D> clazz = (Class<D>) getCatalogClass(catalog);
		return catalogService.findCatalogEntity(clazz, codigo);
	}
}