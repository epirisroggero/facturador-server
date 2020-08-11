package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import uy.com.tmwc.facturator.rapi.UsuariosService;

/**
 * 
 * ----Permisos----
 * CREACION: Un usuario no puede crear documentos cuyo comprobante no este en {@link UsuariosService#getCodigosComprobantesPermitidosUsuario()}
 * MODIFICACION: Un usuario no puede modificar documentos a los cuales no tiene permiso de creacion. Aun con ese permiso, hay usuarios que solo pueden
 * 					modificar documentos creados por el mismo (ver {@link Usuario#getImpedirModificacionDocumentosAjenos()})
 * ACCESO:  no esta claro.
 * 
 * FILTROS EN MODULO FACTURACION SEGUN PERMISOS USUARIO
 * Los vendedores (o 'comerciales') solo ven documentos que hayan creado, en los que participen, o de sus clientes.
 * 
 */
public class Usuario extends CodigoNombreEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final String USUARIO_VENDEDOR_JUNIOR = "V";
	public static final String USUARIO_VENDEDOR_DISTRIBUIDOR = "V2";
	public static final String USUARIO_VENDEDOR_SENIOR = "V3";
	public static final String USUARIO_SUPERVISOR = "S";
	public static final String USUARIO_FACTURACION = "F";
	public static final String USUARIO_TITO = "5";
	public static final String USUARIO_ADMINISTRADOR = "A";
	public static final String USUARIO_ALIADOS_COMERCIALES = "AC";
	
	private String usuNotas;
	
	private String usuTipo;
	
	private String permisoId;
	
	private String usuActivo;
	
	private String claveSup;
	
	private String usuEmail;
	
	private String venId;
	
	private String usuCargo;
	
	private String usuCelular;
	
	private byte[] usuBlob;
	
	private PermisosDocumentoUsuario permisosDocumentoUsuario;
	
	private List<VendedoresUsuario> vendedoresUsuario;
	
	public String getPermisoId() {
		return permisoId;
	}

	public void setPermisoId(String permisoId) {
		this.permisoId = permisoId;
	}

	public Usuario() {
		super();
	}

	public Usuario(String codigo, String nombre) {
		super(codigo, nombre);
	}

	public int hashCode() {
		int result = super.hashCode();

		return result;
	}

	public String getUsuNotas() {
		return usuNotas;
	}

	public void setUsuNotas(String usuNotas) {
		this.usuNotas = usuNotas;
	}
	
	public String getUsuTipo() {
		return usuTipo;
	}

	public void setUsuTipo(String usuTipo) {
		this.usuTipo = usuTipo;
	}
	
	public boolean getUsuarioModoMostrador() {
		return USUARIO_VENDEDOR_JUNIOR.equals(permisoId);
	}

	public void setUsuarioModoMostrador(boolean value) {
	}
	
	public boolean getUsuarioModoDistribuidor() {
		return USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId);
	}

	public boolean getUsuarioModoVendedorSenior() {
		return USUARIO_VENDEDOR_SENIOR.equals(permisoId);
	}

	public void setUsuarioModoDistribuidor(boolean value) {
	}

	public Collection<PermisoFacturador> getPermisos() {
		if (isSupervisor() || USUARIO_SUPERVISOR.equals(permisoId)) {
			return Arrays.asList(PermisoFacturador.values());
		} else if (USUARIO_TITO.equals(permisoId)) {
			return new ArrayList<PermisoFacturador>();
		} else if (USUARIO_VENDEDOR_JUNIOR.equals(permisoId)) {
			return Arrays.asList(new PermisoFacturador[] {PermisoFacturador.Deudores});
		} else if (USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId)) {
			return Arrays.asList(new PermisoFacturador[] {PermisoFacturador.Deudores});
		} else if (USUARIO_VENDEDOR_SENIOR.equals(permisoId)) {
			return Arrays.asList(new PermisoFacturador[] {PermisoFacturador.Deudores});
		} else if (USUARIO_ADMINISTRADOR.equals(permisoId)) {
			return Arrays.asList(new PermisoFacturador[] { PermisoFacturador.CostosYUtilidad, PermisoFacturador.Deudores,
					PermisoFacturador.RutinaCargaCostos, PermisoFacturador.Informes});
		} else if (USUARIO_FACTURACION.equals(permisoId)) {
			return Arrays.asList(new PermisoFacturador[] { PermisoFacturador.CostosYUtilidad, PermisoFacturador.Deudores,
					PermisoFacturador.RutinaCargaCostos});
		} else if (USUARIO_ALIADOS_COMERCIALES.equals(permisoId)) {
			return Arrays.asList(new PermisoFacturador[] {PermisoFacturador.Deudores});
		}
		return new ArrayList<PermisoFacturador>();
	}
	
	public void setPermisos(Collection<PermisoFacturador> permisos) {		
	}


	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj)) {
			return false;
		}
		return (obj instanceof Usuario);
	}

	public boolean getImpedirModificacionDocumentosAjenos() {
		if (isSupervisor()) {
			return false;
		}

		// Chequear permisos		
		return !ArrayUtils.contains(new String[] { USUARIO_FACTURACION, USUARIO_ADMINISTRADOR, USUARIO_SUPERVISOR }, permisoId);
	}

	public PermisosDocumentoUsuario getPermisosDocumentoUsuario() {
		return permisosDocumentoUsuario;
	}

	public void setPermisosDocumentoUsuario(PermisosDocumentoUsuario permisosDocumentoUsuario) {
		this.permisosDocumentoUsuario = permisosDocumentoUsuario;
	}

	public String getUsuActivo() {
		return usuActivo;
	}

	public void setUsuActivo(String usuActivo) {
		this.usuActivo = usuActivo;
	}

	public String getClaveSup() {
		return claveSup;
	}

	public void setClaveSup(String claveSup) {
		this.claveSup = claveSup;
	}

	public String getUsuEmail() {
		return usuEmail;
	}

	public void setUsuEmail(String usuEmail) {
		this.usuEmail = usuEmail;
	}

	public String getVenId() {
		return venId;
	}

	public void setVenId(String venId) {
		this.venId = venId;
	}

	public List<VendedoresUsuario> getVendedoresUsuario() {
		return vendedoresUsuario;
	}

	public void setVendedoresUsuario(List<VendedoresUsuario> vendedoresUsuario) {
		this.vendedoresUsuario = vendedoresUsuario;
	}
	
	public boolean isSupervisor() {
		return usuTipo != null && usuTipo.equals("S"); 
	}

	public String getUsuCargo() {
		return usuCargo;
	}

	public void setUsuCargo(String usuCargo) {
		this.usuCargo = usuCargo;
	}

	public String getUsuCelular() {
		return usuCelular;
	}

	public void setUsuCelular(String usuCelular) {
		this.usuCelular = usuCelular;
	}

	public byte[] getUsuBlob() {
		return usuBlob;
	}

	public void setUsuBlob(byte[] usuBlob) {
		this.usuBlob = usuBlob;
	}
	
}
