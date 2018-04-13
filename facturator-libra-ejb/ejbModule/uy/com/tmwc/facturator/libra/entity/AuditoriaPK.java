package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the lfx_auditoria database table.
 * 
 */
@Embeddable
public class AuditoriaPK implements Serializable {
	// default serial version id, required for serializable_ classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "id")
	private int audId;

	public AuditoriaPK() {
	}
	
	public AuditoriaPK(String empId, int audId) {
		this.empId = empId;
		this.audId = audId;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public int getAudId() {
		return this.audId;
	}

	public void setAudId(int audId) {
		this.audId = audId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AuditoriaPK)) {
			return false;
		}
		AuditoriaPK castOther = (AuditoriaPK) other;
		
		return this.empId.equals(castOther.empId) && (this.audId == castOther.audId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.audId;

		return hash;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.empId);
		sb.append(',');
		sb.append(this.audId);
		sb.append(" ");
		sb.append(super.toString());
		return sb.toString();
	}
}