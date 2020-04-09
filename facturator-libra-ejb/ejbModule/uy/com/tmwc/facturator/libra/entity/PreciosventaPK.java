package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PreciosventaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "PrecioVentaId")
	private short precioVentaId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public short getPrecioVentaId() {
		return this.precioVentaId;
	}

	public void setPrecioVentaId(short precioVentaId) {
		this.precioVentaId = precioVentaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PreciosventaPK)) {
			return false;
		}
		PreciosventaPK castOther = (PreciosventaPK) other;

		return (this.empId.equals(castOther.empId)) && (this.precioVentaId == castOther.precioVentaId);
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.precioVentaId;

		return hash;
	}
}