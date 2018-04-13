package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the paiss database table.
 * 
 */
@Embeddable
public class PaisPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="PaisId")
	private String paisId;

    public PaisPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getPaisId() {
		return this.paisId;
	}
	public void setPaisId(String paisId) {
		this.paisId = paisId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PaisPK)) {
			return false;
		}
		PaisPK castOther = (PaisPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.paisId.equals(castOther.paisId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.paisId.hashCode();
		
		return hash;
    }
}