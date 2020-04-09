package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PlanpagoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;
	private String PPid;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getPPid() {
		return this.PPid;
	}

	public void setPPid(String PPid) {
		this.PPid = PPid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof PlanpagoPK)) {
			return false;
		}
		PlanpagoPK castOther = (PlanpagoPK) other;

		return (this.empId.equals(castOther.empId)) && (this.PPid.equals(castOther.PPid));
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.PPid.hashCode();

		return hash;
	}
}