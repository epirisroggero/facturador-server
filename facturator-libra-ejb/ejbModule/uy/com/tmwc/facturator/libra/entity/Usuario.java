package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "usuarios")
@CatalogEntity(prefix = "usu", abreviated = true)
@SecondaryTable(name = "lfx_usuarios")
public class Usuario extends PersistentEntity<UsuarioPK> implements Serializable, HasId<UsuarioPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuarioPK id;

	@Column(name = "CenIdUsu")
	private String cenIdUsu;

	@Column(name = "UsuId", insertable = false, updatable = false)
	private short codigo;

	@Column(name = "PermisoId")
	private String permisoId;

	@Temporal(TemporalType.DATE)
	@Column(name = "UsuCaducidad")
	private Date usuCaducidad;

	@Column(name = "UsuESultimo")
	private BigInteger usuESultimo;

	@Column(name = "UsuHorarioDesde")
	private String usuHorarioDesde;

	@Column(name = "UsuHorarioHasta")
	private String usuHorarioHasta;

	@Column(name = "UsuNom")
	private String nombre;

	@Lob
	@Column(name = "UsuNotas")
	private String usuNotas; 

	@Column(name = "UsuPassword")
	private String usuPassword;

	@Column(name = "UsuTipo")
	private String usuTipo;

	@Column(name = "UsuWebSesion")
	private String usuWebSesion;
	
	@Column(name = "UsuVersion")
	private String usuVersion;

	@Column(name = "UsuActivo")
	private String usuActivo;
	
	@Lob
	@Column(table = "lfx_usuarios", name = "UsuBlob", nullable = true, length = 2147483647)
	private byte[] usuBlob;

	@OneToMany(mappedBy = "usuario")
	private List<VendedoresUsuario> vendedoresUsuario;
	
	@Column(table = "lfx_usuarios", length = 50)
	private String claveSup;
	
	@Column(table = "lfx_usuarios", length = 50)
	private String usuEmail;

	@Column(table = "lfx_usuarios", length = 3)
	private String venId;

	@Column(table = "lfx_usuarios", name = "UsuCargo")
	private String usuCargo;

	@Column(table = "lfx_usuarios", name = "UsuCelular")
	private String usuCelular;

	
	public Usuario() {
	}
	
	public void provideId(String empId, short usuId) {
		this.id = new UsuarioPK(empId, usuId);
	}
	
	public UsuarioPK getId() {
		return this.id;
	}

	public void setId(UsuarioPK id) {
		this.id = id;
	}

	public String getCenIdUsu() {
		return this.cenIdUsu;
	}

	public void setCenIdUsu(String cenIdUsu) {
		this.cenIdUsu = cenIdUsu;
	}

	public String getPermisoId() {
		return this.permisoId;
	}

	public void setPermisoId(String permisoId) {
		this.permisoId = permisoId;
	}

	public Date getUsuCaducidad() {
		return this.usuCaducidad;
	}

	public void setUsuCaducidad(Date usuCaducidad) {
		this.usuCaducidad = usuCaducidad;
	}

	public BigInteger getUsuESultimo() {
		return this.usuESultimo;
	}

	public void setUsuESultimo(BigInteger usuESultimo) {
		this.usuESultimo = usuESultimo;
	}

	public String getUsuHorarioDesde() {
		return this.usuHorarioDesde;
	}

	public void setUsuHorarioDesde(String usuHorarioDesde) {
		this.usuHorarioDesde = usuHorarioDesde;
	}

	public String getUsuHorarioHasta() {
		return this.usuHorarioHasta;
	}

	public void setUsuHorarioHasta(String usuHorarioHasta) {
		this.usuHorarioHasta = usuHorarioHasta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUsuNotas() {
		return this.usuNotas;
	}

	public void setUsuNotas(String usuNotas) {
		this.usuNotas = usuNotas;
	}

	public String getUsuPassword() {
		return this.usuPassword;
	}

	public void setUsuPassword(String usuPassword) {
		this.usuPassword = usuPassword;
	}

	public String getUsuTipo() {
		return this.usuTipo;
	}

	public void setUsuTipo(String usuTipo) {
		this.usuTipo = usuTipo;
	}

	public String getUsuWebSesion() {
		return this.usuWebSesion;
	}

	public void setUsuWebSesion(String usuWebSesion) {
		this.usuWebSesion = usuWebSesion;
	}

	public String getCodigo() {
		return String.valueOf(this.id.getUsuId());
	}

	public void setCodigo(String value) {
		try {
			this.codigo = Short.valueOf(Short.parseShort(value));
			if (getId() == null) {
				setId(new UsuarioPK());
			}
			getId().setUsuId(this.codigo); 
		} catch (NumberFormatException localNumberFormatException) {
		}
	}

	public List<VendedoresUsuario> getVendedoresUsuario() {
		return vendedoresUsuario;
	}

	public String getUsuVersion() {
		return usuVersion;
	}

	public void setUsuVersion(String usuVersion) {
		this.usuVersion = usuVersion;
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

	public void setVendedoresUsuario(List<VendedoresUsuario> vendedoresUsuario) {
		this.vendedoresUsuario = vendedoresUsuario;
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