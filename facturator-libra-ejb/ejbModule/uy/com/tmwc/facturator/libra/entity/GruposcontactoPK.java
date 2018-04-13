package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the gruposcontactos database table.
 * 
 */
@Embeddable
public class GruposcontactoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="GruCtoId")
	private String gruCtoId;

    public GruposcontactoPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getGruCtoId() {
		return this.gruCtoId;
	}
	public void setGruCtoId(String gruCtoId) {
		this.gruCtoId = gruCtoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GruposcontactoPK)) {
			return false;
		}
		GruposcontactoPK castOther = (GruposcontactoPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.gruCtoId.equals(castOther.gruCtoId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.gruCtoId.hashCode();
		
		return hash;
    }
}