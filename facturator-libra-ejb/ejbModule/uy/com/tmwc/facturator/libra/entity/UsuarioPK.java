package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioPK implements Serializable {
	private static final long serialVersionUID = 1L;

	public UsuarioPK() {
	}

	public UsuarioPK(String empId, short usuId) {
		this.empId = empId;
		this.usuId = usuId;
	}

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "UsuId")
	private short usuId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public short getUsuId() {
		return this.usuId;
	}

	public void setUsuId(short usuId) {
		this.usuId = usuId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UsuarioPK)) {
			return false;
		}
		UsuarioPK castOther = (UsuarioPK) other;

		return (this.empId.equals(castOther.empId))
				&& (this.usuId == castOther.usuId);
	}

	public int hashCode() {
		// int prime = 31;
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.usuId;

		return hash;
	}
}