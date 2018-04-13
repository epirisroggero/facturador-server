package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the localescomerciales database table.
 * 
 */
@Embeddable
public class LocalescomercialePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="LocId")
	private short locId;

    public LocalescomercialePK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public short getLocId() {
		return this.locId;
	}
	public void setLocId(short locId) {
		this.locId = locId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LocalescomercialePK)) {
			return false;
		}
		LocalescomercialePK castOther = (LocalescomercialePK)other;
		return 
			this.empId.equals(castOther.empId)
			&& (this.locId == castOther.locId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + ((int) this.locId);
		
		return hash;
    }
}