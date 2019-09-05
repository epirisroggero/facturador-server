package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CentroscostoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "CenId")
	private String cenId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getCenId() {
		return this.cenId;
	}

	public void setCenId(String cenId) {
		this.cenId = cenId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CentroscostoPK)) {
			return false;
		}
		CentroscostoPK castOther = (CentroscostoPK) other;

		return (this.empId.equals(castOther.empId)) && (this.cenId.equals(castOther.cenId));
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.cenId.hashCode();

		return hash;
	}
}