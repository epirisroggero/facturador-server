package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cuentasbancarias database table.
 * 
 */
@Embeddable
public class CuentasbancariaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="CuentaId")
	private short cuentaId;

    public CuentasbancariaPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public short getCuentaId() {
		return this.cuentaId;
	}
	public void setCuentaId(short cuentaId) {
		this.cuentaId = cuentaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CuentasbancariaPK)) {
			return false;
		}
		CuentasbancariaPK castOther = (CuentasbancariaPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& (this.cuentaId == castOther.cuentaId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + ((int) this.cuentaId);
		
		return hash;
    }
}