package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class CapituloPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId", length = 10, nullable = false)
	private String empId;

	@Column(name = "CapituloId", insertable=false, updatable=false)
	private String capituloId;

	public String getCapituloId() {
		return capituloId;
	}

	public void setCapituloId(String capitulo) {
		this.capituloId = capitulo;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CapituloPK)) {
			return false;
		}
		CapituloPK castOther = (CapituloPK) other;
		return this.empId.equals(castOther.empId)
				&& this.capituloId.equals(castOther.capituloId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.capituloId.hashCode();

		return hash;
	}

}