package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ClientePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "CliId")
	private String cliId;

	public ClientePK() {
	}
			
	public ClientePK(String empId, String cliId) {
		this.empId = empId;
		this.cliId = cliId;
	}

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getCliId() {
		return this.cliId;
	}

	public void setCliId(String cliId) {
		this.cliId = cliId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ClientePK)) {
			return false;
		}
		ClientePK castOther = (ClientePK) other;

		return (this.empId.equals(castOther.empId)) && (this.cliId.equals(castOther.cliId));
	}

	public int hashCode() {
		int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.cliId.hashCode();

		return hash;
	}
}