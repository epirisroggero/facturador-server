package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the Giros database table.
 * 
 */
@Embeddable
public class GiroPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="GirId")
	private String girId;

    public GiroPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getGirId() {
		return this.girId;
	}
	public void setGirId(String girId) {
		this.girId = girId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GiroPK)) {
			return false;
		}
		GiroPK castOther = (GiroPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.girId.equals(castOther.girId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.girId.hashCode();
		
		return hash;
    }
}