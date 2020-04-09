package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CajaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "CajaId")
	private short cajaId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public short getCajaId() {
		return this.cajaId;
	}

	public void setCajaId(short cajaId) {
		this.cajaId = cajaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CajaPK)) {
			return false;
		}
		CajaPK castOther = (CajaPK) other;

		return (this.empId.equals(castOther.empId)) && (this.cajaId == castOther.cajaId);
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.cajaId;

		return hash;
	}
}