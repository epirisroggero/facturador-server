package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ParametrosadministracionPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "ParAdmId")
	private String parAdmId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getParAdmId() {
		return parAdmId;
	}

	public void setParAdmId(String parAdmId) {
		this.parAdmId = parAdmId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ParametrosadministracionPK)) {
			return false;
		}
		ParametrosadministracionPK castOther = (ParametrosadministracionPK) other;

		return (this.empId.equals(castOther.empId))
				&& (this.parAdmId.equals(castOther.parAdmId));
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.parAdmId.hashCode();

		return hash;
	}

}