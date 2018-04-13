package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the agendatareas database table.
 * 
 */
@Embeddable
public class AgendatareaPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "AgeId")
	private int ageId;

	public AgendatareaPK() {
	}
	
	public AgendatareaPK(String empId, int ageId) {
		this.empId = empId;
		this.ageId = ageId;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public int getAgeId() {
		return this.ageId;
	}

	public void setAgeId(int ageId) {
		this.ageId = ageId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AgendatareaPK)) {
			return false;
		}
		AgendatareaPK castOther = (AgendatareaPK) other;
		
		return this.empId.equals(castOther.empId) && (this.ageId == castOther.ageId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.ageId;

		return hash;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.empId);
		sb.append(',');
		sb.append(this.ageId);
		sb.append(" ");
		sb.append(super.toString());
		return sb.toString();
	}
}