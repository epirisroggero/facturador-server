package uy.com.tmwc.facturator.rapi;

import java.util.Collection;
import java.util.List;

import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.entity.PreciosVenta;
import uy.com.tmwc.facturator.entity.Usuario;

public interface UsuariosService {

	List<PreciosVenta> getPreciosVentaUsuario();

	Collection<Comprobante> getComprobantesPermitidosUsuario();

	Usuario getUsuarioLogin();

	Collection<Integer> getCodigosComprobantesPermitidosUsuario();

	void updateClaveSup(String userId, String claveSup) throws PermisosException;

	void updateEmail(String userId, String email);

	void update(Usuario usuario) throws PermisosException;

	Collection<Usuario> getUsuariosSupervisores();
}
