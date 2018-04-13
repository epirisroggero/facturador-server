package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ArticuloPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "ArtId")
	private String artId;

	public ArticuloPK() {
	}

	public ArticuloPK(String empId, String artId) {
		this.empId = empId;
		this.artId = artId;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getArtId() {
		return this.artId;
	}

	public void setArtId(String artId) {
		this.artId = artId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ArticuloPK)) {
			return false;
		}
		ArticuloPK castOther = (ArticuloPK) other;

		return (this.empId.equals(castOther.empId))
				&& (this.artId.equals(castOther.artId));
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.artId.hashCode();

		return hash;
	}
}