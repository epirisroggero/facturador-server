package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VendedorePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "VenId")
	private String venId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getVenId() {
		return this.venId;
	}

	public void setVenId(String venId) {
		this.venId = venId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VendedorePK)) {
			return false;
		}
		VendedorePK castOther = (VendedorePK) other;

		return (this.empId.equals(castOther.empId)) && (this.venId.equals(castOther.venId));
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.venId.hashCode();

		return hash;
	}
}