package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocumentoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "DocId")
	private int docId;

	public DocumentoPK() {
	}

	public DocumentoPK(String empId, int docId) {
		this.empId = empId;
		this.docId = docId;
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DocumentoPK)) {
			return false;
		}
		DocumentoPK castOther = (DocumentoPK) other;

		return (this.empId.equals(castOther.empId)) && (this.docId == castOther.docId);
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.docId;

		return hash;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.empId);
		sb.append(',');
		sb.append(this.docId);
		sb.append(" ");
		sb.append(super.toString());
		return sb.toString();
	}
}