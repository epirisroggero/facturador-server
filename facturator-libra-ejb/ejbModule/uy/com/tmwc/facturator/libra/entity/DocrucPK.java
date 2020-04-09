package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocrucPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "DocId")
	private int docId;

	@Column(name = "DocRutId")
	private short docRutId;

	public DocrucPK() {
	}

	public DocrucPK(String empId, int docId) {
		this.empId = empId;
		this.docId = docId;
		this.docRutId = 1;
	}

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

	public short getDocRutId() {
		return this.docRutId;
	}

	public void setDocRutId(short docRutId) {
		this.docRutId = docRutId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DocrucPK)) {
			return false;
		}
		DocrucPK castOther = (DocrucPK) other;

		return (this.empId.equals(castOther.empId)) && (this.docId == castOther.docId) && (this.docRutId == castOther.docRutId);
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.docId;
		hash = hash * prime + this.docRutId;

		return hash;
	}
}