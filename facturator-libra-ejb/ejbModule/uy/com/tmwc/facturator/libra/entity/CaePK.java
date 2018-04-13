package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the cae database table.
 * 
 */
@Embeddable
public class CaePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	private short CAEid;

    public CaePK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public short getCAEid() {
		return this.CAEid;
	}
	public void setCAEid(short CAEid) {
		this.CAEid = CAEid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CaePK)) {
			return false;
		}
		CaePK castOther = (CaePK)other;
		return 
			this.empId.equals(castOther.empId)
			&& (this.CAEid == castOther.CAEid);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + ((int) this.CAEid);
		
		return hash;
    }
}