package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Embeddable
public class CotizacionesMonedasPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId", unique = true, nullable = false, length = 10)
	private String empId;

	@Temporal(TemporalType.DATE)
	@Column(name = "Cdia", unique = true, nullable = false)
	private Date dia;

	public CotizacionesMonedasPK() {
	}

	public CotizacionesMonedasPK(String empId, Date dia) {
		this.empId = empId;
		this.dia = dia;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
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
		if (!(other instanceof CotizacionesMonedasPK)) {
			return false;
		}
		CotizacionesMonedasPK castOther = (CotizacionesMonedasPK) other;

		return (this.empId.equals(castOther.empId)) && (this.dia.equals(castOther.dia));
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;

		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.dia.hashCode();

		return hash;
	}
}