package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RubroPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "RubId")
	private String rubId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getRubId() {
		return this.rubId;
	}

	public void setRubId(String rubId) {
		this.rubId = rubId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof RubroPK)) {
			return false;
		}
		RubroPK castOther = (RubroPK) other;

		return (this.empId.equals(castOther.empId)) && (this.rubId.equals(castOther.rubId));
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.rubId.hashCode();

		return hash;
	}
}