package uy.com.tmwc.facturator.service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.entity.PreciosVenta;
import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.rapi.PermisosException;
import uy.com.tmwc.facturator.rapi.UserPrincipalLocator;
import uy.com.tmwc.facturator.rapi.UsuariosService;
import uy.com.tmwc.facturator.spi.UsuariosDAOService;

@Stateless
@Local(UsuariosService.class)
@Remote(UsuariosService.class)
public class UsuariosServiceImpl implements UsuariosService {
	
	private static final int LISTA_PRECIO_REVENTA = 4;
	private static final int LISTA_PRECIO_INDUSTRIA = 2;
	private static final int LISTA_PRECIO_MINORISTA = 3;
	private static final int LISTA_PRECIO_MINIMO_VENTA = 5;
	private static final int LISTA_PRECIO_DISTRIBUIDOR = 1;
	private static HashMap<String, Collection<Integer>> permisoUsuario2ListasPrecio;
	
	private static HashMap<String, Collection<Integer>> permisoUsuario2Comprobantes;
	
	@EJB
	UsuariosDAOService usuariosDAOService;

	static {
		HashMap<String, Collection<Integer>> listasPorUsuario = new HashMap<String, Collection<Integer>>();
		listasPorUsuario.put(Usuario.USUARIO_SUPERVISOR, Arrays.asList(new Integer[] {LISTA_PRECIO_MINIMO_VENTA, LISTA_PRECIO_DISTRIBUIDOR, LISTA_PRECIO_REVENTA, LISTA_PRECIO_INDUSTRIA, LISTA_PRECIO_MINORISTA}));
		listasPorUsuario.put(Usuario.USUARIO_VENDEDOR_JUNIOR, Arrays.asList(new Integer[] {LISTA_PRECIO_REVENTA, LISTA_PRECIO_INDUSTRIA, LISTA_PRECIO_MINORISTA}));
		listasPorUsuario.put(Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR, Arrays.asList(new Integer[] {LISTA_PRECIO_INDUSTRIA, LISTA_PRECIO_MINORISTA, LISTA_PRECIO_DISTRIBUIDOR, LISTA_PRECIO_REVENTA}));
		listasPorUsuario.put(Usuario.USUARIO_VENDEDOR_SENIOR, Arrays.asList(new Integer[] {LISTA_PRECIO_REVENTA, LISTA_PRECIO_INDUSTRIA, LISTA_PRECIO_MINORISTA}));
		listasPorUsuario.put(Usuario.USUARIO_ADMINISTRADOR, Arrays.asList(new Integer[] {LISTA_PRECIO_MINIMO_VENTA, LISTA_PRECIO_DISTRIBUIDOR, LISTA_PRECIO_REVENTA, LISTA_PRECIO_INDUSTRIA, LISTA_PRECIO_MINORISTA})); //TODO: Revisar con Mauro
		listasPorUsuario.put(Usuario.USUARIO_FACTURACION, Arrays.asList(new Integer[] {LISTA_PRECIO_MINIMO_VENTA, LISTA_PRECIO_DISTRIBUIDOR, LISTA_PRECIO_REVENTA, LISTA_PRECIO_INDUSTRIA, LISTA_PRECIO_MINORISTA})); //TODO: Revisar con Mauro
		listasPorUsuario.put(Usuario.USUARIO_TITO, Arrays.asList(new Integer[] {LISTA_PRECIO_MINORISTA}));
		listasPorUsuario.put(Usuario.USUARIO_ALIADOS_COMERCIALES, Arrays.asList(new Integer[] {LISTA_PRECIO_INDUSTRIA, LISTA_PRECIO_MINORISTA}));
		permisoUsuario2ListasPrecio = listasPorUsuario;
		
		HashMap<String, Collection<Integer>> map = new HashMap<String, Collection<Integer>>();
		map.put(Usuario.USUARIO_TITO, Arrays.asList(new Integer[] {70, 71, 72, 73, 80, 81, 82, 90, 91, 92, 100, 110, 120, 130, 131, 132})); 
		
		List<Integer> comprobantesComerciales = Arrays.asList(new Integer[] {70, 80, 90, 1 /* cotizacion */, 10 /* orden de venta */, 11 /* solicitud nota de credito */, 100, 110, 120, 130});

		map.put(Usuario.USUARIO_SUPERVISOR, Arrays.asList(new Integer[] {Integer.MAX_VALUE}));
		map.put(Usuario.USUARIO_VENDEDOR_JUNIOR, comprobantesComerciales);
		map.put(Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR, comprobantesComerciales); 
		map.put(Usuario.USUARIO_VENDEDOR_SENIOR, comprobantesComerciales); 
		map.put(Usuario.USUARIO_ALIADOS_COMERCIALES, comprobantesComerciales); 
		map.put(Usuario.USUARIO_ADMINISTRADOR, Arrays.asList(new Integer[] {Integer.MAX_VALUE})); 
		map.put(Usuario.USUARIO_FACTURACION, Arrays.asList(new Integer[] {Integer.MAX_VALUE})); 
		map.put(Usuario.USUARIO_ALIADOS_COMERCIALES, comprobantesComerciales); 
		permisoUsuario2Comprobantes = map;
	}
	
	@EJB
	CatalogService catalogService;

	public List<PreciosVenta> getPreciosVentaUsuario() {
		List<PreciosVenta> todos = catalogService.getCatalog(PreciosVenta.class.getSimpleName());
		Usuario usuarioLogin = getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		boolean esSupervisor = usuarioLogin.isSupervisor();

		Collection<Integer> filterList = permisoUsuario2ListasPrecio.get(esSupervisor ? Usuario.USUARIO_SUPERVISOR : permisoId);
		ArrayList<PreciosVenta> filtered = new ArrayList<PreciosVenta>();
		if (filterList == null) {
			return filtered;
		}
		for (PreciosVenta pv : todos) {
			int pvId = Integer.parseInt(pv.getCodigo());
			if (filterList.contains(pvId)) {
				filtered.add(pv);
			}
		}
		return filtered;
	}
		
	public Usuario getUsuarioLogin() {
		Principal userPpal = UserPrincipalLocator.userPrincipalTL.get();	
		if (userPpal == null) {
			return null;
		}
		String name = userPpal.getName();
		return catalogService.findCatalogEntity(Usuario.class.getSimpleName(), name);
	}

	public Collection<Comprobante> getComprobantesPermitidosUsuario() {
		List<Comprobante> todos = catalogService.getCatalog(Comprobante.class.getSimpleName());
		Usuario usuarioLogin = getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		boolean esSupervisor = usuarioLogin.isSupervisor();

		Collection<Integer> filterList = permisoUsuario2Comprobantes.get(esSupervisor ? Usuario.USUARIO_SUPERVISOR : permisoId);
		ArrayList<Comprobante> filtered = new ArrayList<Comprobante>();
		if (filterList == null) {
			return filtered;
		}
		if (filterList.size() == 1 && filterList.iterator().next().equals(Integer.MAX_VALUE)) {
			return todos;
		}
		for (Comprobante c : todos) {
			int cCodigo = Integer.parseInt(c.getCodigo());
			if (filterList.contains(cCodigo)) {
				filtered.add(c);
			}
		}
		return filtered;		
	}
	
	public Collection<Integer> getCodigosComprobantesPermitidosUsuario() {
		Usuario usuarioLogin = getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		boolean esSupervisor = usuarioLogin.isSupervisor();

		return permisoUsuario2Comprobantes.get(esSupervisor ? Usuario.USUARIO_SUPERVISOR : permisoId);
	}
	
	public void updateClaveSup(String userId, String clave) throws PermisosException {
		Usuario usuarioLogin = getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		boolean esSupervisor = usuarioLogin.isSupervisor();
		
		if (esSupervisor || Usuario.USUARIO_SUPERVISOR.equals(permisoId)) {
			this.usuariosDAOService.updateClaveSup(userId, clave);
		} else {
			throw new PermisosException("No tiene permisos.");
		}
	}
	
	public void updateEmail(String userId, String email) {
		this.usuariosDAOService.updateEmail(userId, email);
	}

	public void update(Usuario user) {
		this.usuariosDAOService.update(user);
	}

	public Collection<Usuario> getUsuariosSupervisores() {
		List<Usuario> todos = catalogService.getCatalog(Usuario.class.getSimpleName());

		ArrayList<Usuario> filtered = new ArrayList<Usuario>();
		for (Usuario u : todos) {
			if (u.isSupervisor()/* || u.getPermisoId().equals(Usuario.USUARIO_SUPERVISOR)*/) {
				filtered.add(u);
			}
		}
		return filtered;		
	}


}
