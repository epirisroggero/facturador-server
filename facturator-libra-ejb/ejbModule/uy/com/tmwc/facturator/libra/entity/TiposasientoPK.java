package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TiposasientoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId", unique = true, nullable = false, length = 10)
	private String empId;

	@Column(name = "TipAsId", unique = true, nullable = false, length = 3)
	private String tipAsId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getTipAsId() {
		return this.tipAsId;
	}

	public void setTipAsId(String tipAsId) {
		this.tipAsId = tipAsId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TiposasientoPK)) {
			return false;
		}
		TiposasientoPK castOther = (TiposasientoPK) other;

		return (this.empId.equals(castOther.empId)) && (this.tipAsId.equals(castOther.tipAsId));
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.tipAsId.hashCode();

		return hash;
	}
}