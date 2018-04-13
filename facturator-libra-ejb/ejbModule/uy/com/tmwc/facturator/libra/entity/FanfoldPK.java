package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the fanfold database table.
 * 
 */
@Embeddable
public class FanfoldPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="NumFoldId")
	private String numFoldId;

    public FanfoldPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getNumFoldId() {
		return this.numFoldId;
	}
	public void setNumFoldId(String numFoldId) {
		this.numFoldId = numFoldId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof FanfoldPK)) {
			return false;
		}
		FanfoldPK castOther = (FanfoldPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.numFoldId.equals(castOther.numFoldId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.numFoldId.hashCode();
		
		return hash;
    }
}