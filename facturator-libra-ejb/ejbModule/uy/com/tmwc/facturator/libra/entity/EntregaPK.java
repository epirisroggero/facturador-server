package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the Entregas database table.
 * 
 */
@Embeddable
public class EntregaPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;
	
	@Column(name = "codigo")
	private String entId;

	public EntregaPK() {
	}

	public EntregaPK(String empId, String entId) {
		this.empId = empId;
		this.entId = entId;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getEntId() {
		return this.entId;
	}

	public void setEntId(String entId) {
		this.entId = entId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof EntregaPK)) {
			return false;
		}
		EntregaPK castOther = (EntregaPK) other;

		return this.empId.equals(castOther.empId)
				&& (this.entId == castOther.entId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.entId.hashCode();

		return hash;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.empId);
		sb.append(',');
		sb.append(this.entId);
		sb.append(" ");
		sb.append(super.toString());
		return sb.toString();
	}
}