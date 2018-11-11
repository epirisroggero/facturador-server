package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the marcas database table.
 * 
 */
@Embeddable
public class MarcaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="MarcaId")
	private String marcaId;

    public MarcaPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getMarcaId() {
		return this.marcaId;
	}
	public void setMarcaId(String marcaId) {
		this.marcaId = marcaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof MarcaPK)) {
			return false;
		}
		MarcaPK castOther = (MarcaPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.marcaId.equals(castOther.marcaId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.marcaId.hashCode();
		
		return hash;
    }
}