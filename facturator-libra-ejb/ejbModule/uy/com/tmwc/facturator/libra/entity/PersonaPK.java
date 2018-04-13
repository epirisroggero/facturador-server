package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PersonaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "CtoId")
	private String ctoId;
	
	@Column(name = "CtoPerNom")
	private String nombre;

	
	public PersonaPK() {
	}
			
	public PersonaPK(String empId, String ctoId, String ctoNombre) {
		this.empId = empId;
		this.ctoId = ctoId;
		this.nombre = ctoNombre;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getCtoId() {
		return this.ctoId;
	}

	public void setCtoId(String ctoId) {
		this.ctoId = ctoId;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PersonaPK)) {
			return false;
		}
		PersonaPK castOther = (PersonaPK) other;

		return (this.empId.equals(castOther.empId)) && (this.ctoId.equals(castOther.ctoId) && (this.nombre.equals(castOther.nombre)));
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.ctoId.hashCode();
		hash = hash * 31 + this.nombre.hashCode();

		return hash;
	}

}