package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class VinculosdocPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "DocIdVin1")
	private int docIdVin1;

	@Column(name = "DocIdVin2")
	private int docIdVin2;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public int getDocIdVin1() {
		return this.docIdVin1;
	}

	public void setDocIdVin1(int docIdVin1) {
		this.docIdVin1 = docIdVin1;
	}

	public int getDocIdVin2() {
		return this.docIdVin2;
	}

	public void setDocIdVin2(int docIdVin2) {
		this.docIdVin2 = docIdVin2;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VinculosdocPK)) {
			return false;
		}
		VinculosdocPK castOther = (VinculosdocPK) other;

		return (this.empId.equals(castOther.empId)) && (this.docIdVin1 == castOther.docIdVin1) && (this.docIdVin2 == castOther.docIdVin2);
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.docIdVin1;
		hash = hash * 31 + this.docIdVin2;

		return hash;
	}
}