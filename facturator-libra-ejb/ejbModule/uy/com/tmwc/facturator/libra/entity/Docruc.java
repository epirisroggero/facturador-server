package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "docruc")
public class Docruc extends PersistentEntity<DocrucPK> implements Serializable, HasId<DocrucPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocrucPK id;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "DocId", referencedColumnName = "DocId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Documento documento;

	@Column(name = "DocRutCiudad")
	private String ciudad;

	@Column(name = "DocRutDireccion")
	private String direccion;

	@Column(name = "DocRutNombre")
	private String nombre;

	@Column(name = "DocRutRUT")
	private String rut;

	@Column(name = "DocRutTelefono")
	private String telefono;

	@Column(name = "DocRutTipoDoc")
	private String docRutTipoDoc = "R";

	@Column(name = "DocRutEntrega")
	private String docRutEntrega;

	@Column(name = "DocRutEmail")
	private String docRutEmail;

	@Column(name = "PaisIdRut")
	private String paisIdRut;

	@Column(name = "DocRutDepto")
	private String docRutDepto;

	@Column(name = "DocRutCP")
	private String docRutCP;

	@Column(name = "DocRutSigla")
	private String docRutSigla = "RUT";

	public DocrucPK getId() {
		return this.id;
	}

	public void setId(DocrucPK id) {
		this.id = id;
	}

	public String getCiudad() {
		return this.ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRut() {
		return this.rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public String getDocRutTipoDoc() {
		return docRutTipoDoc;
	}

	public void setDocRutTipoDoc(String docRutTipoDoc) {
		this.docRutTipoDoc = docRutTipoDoc;
	}

	public String getDocRutEntrega() {
		return docRutEntrega;
	}

	public void setDocRutEntrega(String docRutEntrega) {
		this.docRutEntrega = docRutEntrega;
	}

	public String getDocRutEmail() {
		return docRutEmail;
	}

	public void setDocRutEmail(String docRutEmail) {
		this.docRutEmail = docRutEmail;
	}

	public String getDocRutDepto() {
		return docRutDepto;
	}

	public void setDocRutDepto(String docRutDepto) {
		this.docRutDepto = docRutDepto;
	}

	public String getDocRutCP() {
		return docRutCP;
	}

	public void setDocRutCP(String docRutCP) {
		this.docRutCP = docRutCP;
	}

	public String getDocRutSigla() {
		return docRutSigla;
	}

	public void setDocRutSigla(String docRutSigla) {
		this.docRutSigla = docRutSigla;
	}
}