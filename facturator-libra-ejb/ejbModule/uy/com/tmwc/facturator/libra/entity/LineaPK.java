package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class LineaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "DocId")
	private int docId;

	@Column(name = "LinId")
	private int linId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public int getDocId() {
		return this.docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public int getLinId() {
		return this.linId;
	}

	public void setLinId(int linId) {
		this.linId = linId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LineaPK)) {
			return false;
		}
		LineaPK castOther = (LineaPK) other;

		return (this.empId.equals(castOther.empId)) && (this.docId == castOther.docId) && (this.linId == castOther.linId);
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.docId;
		hash = hash * 31 + this.linId;

		return hash;
	}
}