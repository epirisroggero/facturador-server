package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FormaspagoPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "FormaPagoId")
	private short formaPagoId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public short getFormaPagoId() {
		return this.formaPagoId;
	}

	public void setFormaPagoId(short formaPagoId) {
		this.formaPagoId = formaPagoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FormaspagoPK)) {
			return false;
		}
		FormaspagoPK castOther = (FormaspagoPK) other;

		return (this.empId.equals(castOther.empId)) && (this.formaPagoId == castOther.formaPagoId);
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.formaPagoId;

		return hash;
	}
}