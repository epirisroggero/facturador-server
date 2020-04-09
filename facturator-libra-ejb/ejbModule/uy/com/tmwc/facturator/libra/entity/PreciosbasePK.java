package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PreciosbasePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "PrecioBaseId")
	private String precioBaseId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getPrecioBaseId() {
		return this.precioBaseId;
	}

	public void setPrecioBaseId(String precioBaseId) {
		this.precioBaseId = precioBaseId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PreciosbasePK)) {
			return false;
		}
		PreciosbasePK castOther = (PreciosbasePK) other;

		return (this.empId.equals(castOther.empId)) && (this.precioBaseId.equals(castOther.precioBaseId));
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.precioBaseId.hashCode();

		return hash;
	}
}