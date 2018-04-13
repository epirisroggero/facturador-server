package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the depto database table.
 * 
 */
@Embeddable
public class DepartamentoPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="DeptoId")
	private String deptoId;

    public DepartamentoPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getDeptoId() {
		return this.deptoId;
	}
	public void setDeptoId(String deptoId) {
		this.deptoId = deptoId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DepartamentoPK)) {
			return false;
		}
		DepartamentoPK castOther = (DepartamentoPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.deptoId.equals(castOther.deptoId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.deptoId.hashCode();
		
		return hash;
    }
}