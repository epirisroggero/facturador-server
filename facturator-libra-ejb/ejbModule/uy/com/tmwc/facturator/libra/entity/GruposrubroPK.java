package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the gruposrubros database table.
 * 
 */
@Embeddable
public class GruposrubroPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="GruRubId")
	private String gruRubId;

    public GruposrubroPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getGruRubId() {
		return this.gruRubId;
	}
	public void setGruRubId(String gruRubId) {
		this.gruRubId = gruRubId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GruposrubroPK)) {
			return false;
		}
		GruposrubroPK castOther = (GruposrubroPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.gruRubId.equals(castOther.gruRubId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.gruRubId.hashCode();
		
		return hash;
    }
}