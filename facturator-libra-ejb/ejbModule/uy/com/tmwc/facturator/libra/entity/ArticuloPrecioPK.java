package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ArticuloPrecioPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId", unique = true, nullable = false, length = 10)
	private String empId;

	@Column(name = "ArtId", unique = true, nullable = false, length = 20)
	private String artId;

	@Column(name = "PrecioBaseId", unique = true, nullable = false, length = 3)
	private String precioBaseId;
	
	public ArticuloPrecioPK() {
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

	public String getPrecioBaseId() {
		return this.precioBaseId;
	}

	public void setPrecioBaseId(String precioBaseId) {
		this.precioBaseId = precioBaseId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ArticuloPrecioPK)) {
			return false;
		}
		ArticuloPrecioPK castOther = (ArticuloPrecioPK) other;

		return (this.empId.equals(castOther.empId)) && (this.artId.equals(castOther.artId)) && (this.precioBaseId.equals(castOther.precioBaseId));
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.artId.hashCode();
		hash = hash * prime + this.precioBaseId.hashCode();

		return hash;
	}
}