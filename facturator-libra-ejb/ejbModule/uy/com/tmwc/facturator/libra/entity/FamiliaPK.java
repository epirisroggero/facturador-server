package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FamiliaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId", unique = true, nullable = false, length = 10)
	private String empId;

	@Column(name = "FamiliaId", unique = true, nullable = false, length = 10)
	private String familiaId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getFamiliaId() {
		return this.familiaId;
	}

	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FamiliaPK)) {
			return false;
		}
		FamiliaPK castOther = (FamiliaPK) other;

		return (this.empId.equals(castOther.empId)) && (this.familiaId.equals(castOther.familiaId));
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.familiaId.hashCode();

		return hash;
	}
}