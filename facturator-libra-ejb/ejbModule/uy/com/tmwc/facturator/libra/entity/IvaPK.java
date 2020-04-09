package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IvaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "IvaId")
	private short ivaId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public short getIvaId() {
		return this.ivaId;
	}

	public void setIvaId(short ivaId) {
		this.ivaId = ivaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof IvaPK)) {
			return false;
		}
		IvaPK castOther = (IvaPK) other;

		return (this.empId.equals(castOther.empId)) && (this.ivaId == castOther.ivaId);
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.ivaId;

		return hash;
	}
}