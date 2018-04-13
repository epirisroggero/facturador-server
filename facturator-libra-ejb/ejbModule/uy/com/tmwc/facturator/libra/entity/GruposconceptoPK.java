package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the gruposconceptos database table.
 * 
 */
@Embeddable
public class GruposconceptoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="GrupoCptId")
	private String grupoCptId;

    public GruposconceptoPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getGrupoCptId() {
		return this.grupoCptId;
	}
	public void setGrupoCptId(String grupoCptId) {
		this.grupoCptId = grupoCptId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GruposconceptoPK)) {
			return false;
		}
		GruposconceptoPK castOther = (GruposconceptoPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.grupoCptId.equals(castOther.grupoCptId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.grupoCptId.hashCode();
		
		return hash;
    }
}