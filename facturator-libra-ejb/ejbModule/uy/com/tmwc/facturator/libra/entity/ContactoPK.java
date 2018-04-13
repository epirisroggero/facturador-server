package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ContactoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "CtoId")
	private String ctoId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getCtoId() {
		return this.ctoId;
	}

	public void setCtoId(String ctoId) {
		this.ctoId = ctoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ContactoPK)) {
			return false;
		}
		ContactoPK castOther = (ContactoPK) other;

		return (this.empId.equals(castOther.empId)) && (this.ctoId.equals(castOther.ctoId));
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.ctoId.hashCode();

		return hash;
	}
}