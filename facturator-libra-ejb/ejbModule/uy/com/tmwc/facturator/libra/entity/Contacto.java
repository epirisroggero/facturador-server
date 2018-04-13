package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import uy.com.tmwc.utils.orm.CatalogEntity;

/**
 * @author Desarrollador
 *
 */
@Entity
@CatalogEntity(useNamedQuery = true)
@Table(name = "contactos")
public class Contacto extends PersistentEntity<ContactoPK> implements Serializable, HasId<ContactoPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ContactoPK id;

	@Column(name = "CtoActivo")
	private String ctoActivo;

	@Temporal(TemporalType.DATE)
	@Column(name = "CtoAlta")
	private Date ctoAlta;

	@Lob
	@Column(name = "CtoBlob", nullable = true, length = 2147483647)
	private byte[] ctoBlob;

	@Column(name = "CtoBlobExt")
	private String ctoBlobExt;

	@Column(name = "CtoCelular")
	private String ctoCelular;

	@Column(name = "CtoCliente")
	private String ctoCliente;

	@Column(name = "CtoDireccion")
	private String ctoDireccion;

	@Column(name = "CtoDocumento")
	private String ctoDocumento;

	@Column(name = "CtoDocumentoTipo")
	private String ctoDocumentoTipo;

	@Column(name = "CtoEmail1")
	private String ctoEmail1;

	@Column(name = "CtoEmail2")
	private String ctoEmail2;

	@Column(name = "CtoFax")
	private String ctoFax;

	@Column(name = "CtoLocalidad")
	private String ctoLocalidad;

	@Column(name = "CtoNom", length=50, nullable=false)
	private String ctoNom;

	@Column(name = "CtoNombre", length=50, nullable=false)
	private String nombre;

	@Column(name = "CtoNombreCompleto")
	private String ctoNombreCompleto;

	@Lob
	@Column(name = "CtoNotas")
	private String ctoNotas;

	@Column(name = "CtoPostal")
	private String ctoPostal;

	@Column(name = "CtoProveedor")
	private String ctoProveedor;

	@Column(name = "CtoRSocial", length=100, nullable=true)
	private String ctoRSocial;

	@Column(name = "CtoRUT")
	private String ctoRUT;

	@Column(name = "CtoTelefono", length=50, nullable=true)
	private String ctoTelefono;

	@Column(name = "CtoWeb", length=100, nullable=true)
	private String ctoWeb;

	@Column(name = "DeptoIdCto")
	private String deptoIdCto;

	@Column(name = "GirIdCto")
	private String girIdCto;

	@Column(name = "GruCtoId")
	private String gruCtoId;

	@Column(name = "OriCtoIdCto")
	private String oriCtoIdCto;

	@Column(name = "PaisIdCto")
	private String paisIdCto;

	@Column(name = "UsuIdCto")
	private Short usuIdCto;

	@Column(name = "ZonaIdCto")
	private String zonaIdCto;

	@SuppressWarnings("unused")
	@Column(name = "CtoId", insertable = false, updatable = false)
	private String codigo;

	@CollectionOfElements()
	@JoinTable(name = "contactos2", joinColumns = {@JoinColumn(name = "EmpId"), @JoinColumn(name = "CtoId")})
	@org.hibernate.annotations.MapKey(columns = @Column(name = "CampoIdCto"))
	@Column(name = "CtoCampoValor")
	private Map<String, String> adicionales;
	
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "GruCtoId", referencedColumnName = "GruCtoId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Gruposcontacto grupoContacto;
	
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "DeptoIdCto", referencedColumnName = "DeptoId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Departamento departamento;
	
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "ZonaIdCto", referencedColumnName = "ZonaId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Zona zona;
	
	@Column(name = "CtoNotasEfactura")
	private String ctoNotasEfactura;
	
	@Column(name = "CtoDocumentoSigla", length=10, nullable=true)
	private String ctoDocumentoSigla = "C.I.";

	public Contacto() {
	}
	
	public Contacto(String id, String nombre, String rut, String telefono, String direccion, String rsocial, String localidad, String proveedor) {
		this(id, nombre, rut, telefono, direccion, rsocial, localidad, proveedor, null);
	}

	public Contacto(String id, String nombre, String rut, String telefono, String direccion, String rsocial, String localidad, String proveedor, String activo) {
		setCodigo(id);

		this.nombre = nombre;
		this.ctoRUT = rut;
		this.ctoTelefono = telefono;
		this.ctoDireccion = direccion;
		this.ctoRSocial = rsocial;
		this.ctoLocalidad = localidad;
		this.ctoProveedor = proveedor;
		
		if (activo != null) {
			this.ctoActivo = activo;
		}
		
	}

	public String getCodigo() {
		return this.id.getCtoId();
	}

	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new ContactoPK();
		}
		this.id.setCtoId(value);
	}

	public ContactoPK getId() {
		return this.id;
	}

	public void setId(ContactoPK id) {
		this.id = id;
	}

	public String getCtoActivo() {
		return this.ctoActivo;
	}

	public void setCtoActivo(String ctoActivo) {
		this.ctoActivo = ctoActivo;
	}

	public Date getCtoAlta() {
		return this.ctoAlta;
	}

	public void setCtoAlta(Date ctoAlta) {
		this.ctoAlta = ctoAlta;
	}

	public byte[] getCtoBlob() {
		return this.ctoBlob;
	}

	public void setCtoBlob(byte[] ctoBlob) {
		this.ctoBlob = ctoBlob;
	}

	public String getCtoBlobExt() {
		return this.ctoBlobExt;
	}

	public void setCtoBlobExt(String ctoBlobExt) {
		this.ctoBlobExt = ctoBlobExt;
	}

	public String getCtoCelular() {
		return this.ctoCelular;
	}

	public void setCtoCelular(String ctoCelular) {
		this.ctoCelular = ctoCelular;
	}

	public String getCtoCliente() {
		return this.ctoCliente;
	}

	public void setCtoCliente(String ctoCliente) {
		this.ctoCliente = ctoCliente;
	}

	public String getCtoDireccion() {
		return this.ctoDireccion;
	}

	public void setCtoDireccion(String ctoDireccion) {
		this.ctoDireccion = ctoDireccion;
	}

	public String getCtoDocumento() {
		return this.ctoDocumento;
	}

	public void setCtoDocumento(String ctoDocumento) {
		this.ctoDocumento = ctoDocumento;
	}

	public String getCtoDocumentoTipo() {
		return this.ctoDocumentoTipo;
	}

	public void setCtoDocumentoTipo(String ctoDocumentoTipo) {
		this.ctoDocumentoTipo = ctoDocumentoTipo;
	}

	public String getCtoEmail1() {
		return this.ctoEmail1;
	}

	public void setCtoEmail1(String ctoEmail1) {
		this.ctoEmail1 = ctoEmail1;
	}

	public String getCtoEmail2() {
		return this.ctoEmail2;
	}

	public void setCtoEmail2(String ctoEmail2) {
		this.ctoEmail2 = ctoEmail2;
	}

	public String getCtoFax() {
		return this.ctoFax;
	}

	public void setCtoFax(String ctoFax) {
		this.ctoFax = ctoFax;
	}

	public String getCtoLocalidad() {
		return this.ctoLocalidad;
	}

	public void setCtoLocalidad(String ctoLocalidad) {
		this.ctoLocalidad = ctoLocalidad;
	}

	public String getCtoNom() {
		return this.ctoNom;
	}

	public void setCtoNom(String ctoNom) {
		this.ctoNom = ctoNom;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String ctoNombre) {
		this.nombre = ctoNombre;
	}

	public String getCtoNombreCompleto() {
		return this.ctoNombreCompleto;
	}

	public void setCtoNombreCompleto(String ctoNombreCompleto) {
		this.ctoNombreCompleto = ctoNombreCompleto;
	}

	public String getCtoNotas() {
		return this.ctoNotas;
	}

	public void setCtoNotas(String ctoNotas) {
		this.ctoNotas = ctoNotas;
	}

	public String getCtoPostal() {
		return this.ctoPostal;
	}

	public void setCtoPostal(String ctoPostal) {
		this.ctoPostal = ctoPostal;
	}

	public String getCtoProveedor() {
		return this.ctoProveedor;
	}

	public void setCtoProveedor(String ctoProveedor) {
		this.ctoProveedor = ctoProveedor;
	}

	public String getCtoRSocial() {
		return this.ctoRSocial;
	}

	public void setCtoRSocial(String ctoRSocial) {
		this.ctoRSocial = ctoRSocial;
	}

	public String getCtoRUT() {
		return this.ctoRUT;
	}

	public void setCtoRUT(String ctoRUT) {
		this.ctoRUT = ctoRUT;
	}

	public String getCtoTelefono() {
		return this.ctoTelefono;
	}

	public void setCtoTelefono(String ctoTelefono) {
		this.ctoTelefono = ctoTelefono;
	}

	public String getCtoWeb() {
		return this.ctoWeb;
	}

	public void setCtoWeb(String ctoWeb) {
		this.ctoWeb = ctoWeb;
	}

	public String getDeptoIdCto() {
		return this.deptoIdCto;
	}

	public void setDeptoIdCto(String deptoIdCto) {
		this.deptoIdCto = deptoIdCto;
	}

	public String getGirIdCto() {
		return this.girIdCto;
	}

	public void setGirIdCto(String girIdCto) {
		this.girIdCto = girIdCto;
	}

	public String getGruCtoId() {
		return this.gruCtoId;
	}

	public void setGruCtoId(String gruCtoId) {
		this.gruCtoId = gruCtoId;
	}

	public String getOriCtoIdCto() {
		return this.oriCtoIdCto;
	}

	public void setOriCtoIdCto(String oriCtoIdCto) {
		this.oriCtoIdCto = oriCtoIdCto;
	}

	public String getPaisIdCto() {
		return this.paisIdCto;
	}

	public void setPaisIdCto(String paisIdCto) {
		this.paisIdCto = paisIdCto;
	}

	public Short getUsuIdCto() {
		return this.usuIdCto;
	}

	public void setUsuIdCto(Short usuIdCto) {
		this.usuIdCto = usuIdCto;
	}

	public String getZonaIdCto() {
		return this.zonaIdCto;
	}

	public void setZonaIdCto(String zonaIdCto) {
		this.zonaIdCto = zonaIdCto;
	}

	public Map<String, String> getAdicionales() {
		return adicionales;
	}

	public void setAdicionales(Map<String, String> adicionales) {
		this.adicionales = adicionales;
	}
	
	public Gruposcontacto getGrupo() {
		return grupoContacto;
	}

	public Departamento getDepartamento() {
		return departamento;
	}

	public void setDepartamento(Departamento departamento) {
		this.departamento = departamento;
	}

	public Zona getZona() {
		return zona;
	}

	public void setZona(Zona zona) {
		this.zona = zona;
	}

	public String getCtoNotasEfactura() {
		return ctoNotasEfactura;
	}

	public void setCtoNotasEfactura(String ctoNotasEfactura) {
		this.ctoNotasEfactura = ctoNotasEfactura;
	}

	public String getCtoDocumentoSigla() {
		return ctoDocumentoSigla;
	}

	public void setCtoDocumentoSigla(String ctoDocumentoSigla) {
		this.ctoDocumentoSigla = ctoDocumentoSigla;
	}
}