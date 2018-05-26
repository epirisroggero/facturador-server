package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ParticipacionVendedorLPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId", length = 10)
	private String empId;

	@Column(name = "DocId")
	private int docId;

	@Column(name = "VenId", length = 3)
	private String venId;

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

	public String getVenId() {
		return this.venId;
	}

	public void setVenId(String venId) {
		this.venId = venId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ParticipacionVendedorLPK)) {
			return false;
		}
		ParticipacionVendedorLPK castOther = (ParticipacionVendedorLPK) other;
		return this.empId.equals(castOther.empId) && this.docId == castOther.docId && this.venId.equals(castOther.venId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.venId.hashCode();
		hash = hash * prime + this.docId;

		return hash;
	}

}