package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProveedorPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "PrvId")
	private String prvId;

	public ProveedorPK() {
	}

	public ProveedorPK(String empId, String prvId) {
		this.empId = empId;
		this.prvId = prvId;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getPrvId() {
		return this.prvId;
	}

	public void setPrvId(String prvId) {
		this.prvId = prvId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProveedorPK)) {
			return false;
		}
		ProveedorPK castOther = (ProveedorPK) other;

		return (this.empId.equals(castOther.empId)) && (this.prvId.equals(castOther.prvId));
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.prvId.hashCode();

		return hash;
	}
}