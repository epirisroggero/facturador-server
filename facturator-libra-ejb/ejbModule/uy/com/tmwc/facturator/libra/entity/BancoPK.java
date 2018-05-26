package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the bancos database table.
 * 
 */
@Embeddable
public class BancoPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "BancoId")
	private String bancoId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getBancoId() {
		return this.bancoId;
	}

	public void setBancoId(String bancoId) {
		this.bancoId = bancoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof BancoPK)) {
			return false;
		}
		BancoPK castOther = (BancoPK) other;
		return this.empId.equals(castOther.empId) && this.bancoId.equals(castOther.bancoId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.bancoId.hashCode();

		return hash;
	}
}