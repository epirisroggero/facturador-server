package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class NumeradorPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "NumCmpId")
	private String numCmpId;

	public NumeradorPK() {
	}

	public NumeradorPK(String empId, String numCmpId) {
		this.empId = empId;
		this.numCmpId = numCmpId;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getNumCmpId() {
		return this.numCmpId;
	}

	public void setNumCmpId(String numCmpId) {
		this.numCmpId = numCmpId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NumeradorPK)) {
			return false;
		}
		NumeradorPK castOther = (NumeradorPK) other;

		return (this.empId.equals(castOther.empId)) && (this.numCmpId.equals(castOther.numCmpId));
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.numCmpId.hashCode();

		return hash;
	}
}