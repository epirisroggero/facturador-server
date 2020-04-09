package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DocfpPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "DocId")
	private int docId;

	@Column(name = "FPId")
	private short numero;

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

	public short getNumero() {
		return this.numero;
	}

	public void setNumero(short numero) {
		this.numero = numero;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DocfpPK)) {
			return false;
		}
		DocfpPK castOther = (DocfpPK) other;

		return (this.empId.equals(castOther.empId)) && (this.docId == castOther.docId) && (this.numero == castOther.numero);
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.docId;
		hash = hash * prime + this.numero;

		return hash;
	}
}