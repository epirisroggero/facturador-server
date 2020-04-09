package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ComprobantePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EMPID")
	private String empId;

	@Column(name = "CMPID")
	private long cmpid;

	public ComprobantePK() {
	}

	public ComprobantePK(String empId, long cmpid) {
		this.empId = empId;
		this.cmpid = cmpid;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empid) {
		this.empId = empid;
	}

	public long getCmpid() {
		return this.cmpid;
	}

	public void setCmpid(long cmpid) {
		this.cmpid = cmpid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ComprobantePK)) {
			return false;
		}
		ComprobantePK castOther = (ComprobantePK) other;

		return (this.empId.equals(castOther.empId)) && (this.cmpid == castOther.cmpid);
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + (int) (this.cmpid ^ this.cmpid >>> 32);

		return hash;
	}
}