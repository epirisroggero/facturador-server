package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class TipoCambioPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId", unique = true, nullable = false, length = 10)
	private String empId;

	@Column(name = "MndId", unique = true, nullable = false)
	private short mndId;

	@Temporal(TemporalType.DATE)
	@Column(name = "TCdia", unique = true, nullable = false)
	private Date dia;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public short getMndId() {
		return this.mndId;
	}

	public void setMndId(short mndId) {
		this.mndId = mndId;
	}

	public Date getDia() {
		return this.dia;
	}

	public void setDia(Date dia) {
		this.dia = dia;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TipoCambioPK)) {
			return false;
		}
		TipoCambioPK castOther = (TipoCambioPK) other;

		return (this.empId.equals(castOther.empId)) && (this.mndId == castOther.mndId) && (this.dia.equals(castOther.dia));
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.mndId;
		hash = hash * 31 + this.dia.hashCode();

		return hash;
	}
}