package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import uy.com.tmwc.utils.orm.CatalogEntity;

/**
 * @author epiris
 *
 */
@Entity
@CatalogEntity(useNamedQuery = true)
@Table(name = "contactos1")
public class Persona extends PersistentEntity<PersonaPK> implements Serializable, HasId<PersonaPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PersonaPK id;
	
	@SuppressWarnings("unused")
	@Column(name = "CtoId", insertable = false, updatable = false)
	private String codigo;

	@Column(name = "CtoPerNom", insertable = false, updatable = false)
	private String nombre;

	@Column(name = "CtoPerCargo")
	private String ctoPerCargo;

	@Column(name = "CtoPerEmail")
	private String ctoPerEmail;

	@Column(name = "CtoPerTelefono")
	private String ctoPerTelefono;

	@Column(name = "CtoPerCelular")
	private String ctoPerCelular;

	@Temporal(TemporalType.DATE)
	@Column(name = "CtoPerCumple")
	private Date ctoPerCumple;
	
	@Lob
	@Column(name = "CtoPerNotas")
	private String ctoPerNotas;



	public Persona() {
	}

	public Persona(String id, String nombre, String cargo, String email, String telefono, String celular, Date cumple, String notas) {
		setCodigo(id);

		this.nombre = nombre;
		this.ctoPerCargo = cargo;
		this.ctoPerCelular = celular;
		this.ctoPerCumple = cumple;
		this.ctoPerEmail = email;
		this.ctoPerNotas = notas;
		this.ctoPerTelefono = telefono;
	}

	public String getCodigo() {
		return this.id.getCtoId();
	}

	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new PersonaPK();
		}
		this.id.setCtoId(value);
	}

	public PersonaPK getId() {
		return this.id;
	}

	public void setId(PersonaPK id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String ctoNombre) {
		this.nombre = ctoNombre;
	}

	public String getCtoPerCargo() {
		return ctoPerCargo;
	}

	public void setCtoPerCargo(String ctoPerCargo) {
		this.ctoPerCargo = ctoPerCargo;
	}

	public String getCtoPerEmail() {
		return ctoPerEmail;
	}

	public void setCtoPerEmail(String ctoPerEmail) {
		this.ctoPerEmail = ctoPerEmail;
	}

	public String getCtoPerTelefono() {
		return ctoPerTelefono;
	}

	public void setCtoPerTelefono(String ctoPerTelefono) {
		this.ctoPerTelefono = ctoPerTelefono;
	}

	public String getCtoPerCelular() {
		return ctoPerCelular;
	}

	public void setCtoPerCelular(String ctoPerCelular) {
		this.ctoPerCelular = ctoPerCelular;
	}

	public Date getCtoPerCumple() {
		return ctoPerCumple;
	}

	public void setCtoPerCumple(Date ctoPerCumple) {
		this.ctoPerCumple = ctoPerCumple;
	}

	public String getCtoPerNotas() {
		return ctoPerNotas;
	}

	public void setCtoPerNotas(String ctoPerNotas) {
		this.ctoPerNotas = ctoPerNotas;
	}
	
	public void provideId(String empId, String ctoId, String nombre) {
		this.id = new PersonaPK(empId, ctoId, nombre);
	}



}