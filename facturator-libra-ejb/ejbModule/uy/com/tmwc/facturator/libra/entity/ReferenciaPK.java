package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ReferenciaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="RefId")
	private String refId;

    public ReferenciaPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getRefId() {
		return this.refId;
	}
	public void setRefId(String refId) {
		this.refId = refId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ReferenciaPK)) {
			return false;
		}
		ReferenciaPK castOther = (ReferenciaPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.refId.equals(castOther.refId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.refId.hashCode();
		
		return hash;
    }
}