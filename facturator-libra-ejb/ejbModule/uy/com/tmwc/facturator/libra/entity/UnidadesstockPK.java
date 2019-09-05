package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the unidadesstock database table.
 * 
 */
@Embeddable
public class UnidadesstockPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="UnidadId")
	private String unidadId;

    public UnidadesstockPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getUnidadId() {
		return this.unidadId;
	}
	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UnidadesstockPK)) {
			return false;
		}
		UnidadesstockPK castOther = (UnidadesstockPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.unidadId.equals(castOther.unidadId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.unidadId.hashCode();
		
		return hash;
    }
}