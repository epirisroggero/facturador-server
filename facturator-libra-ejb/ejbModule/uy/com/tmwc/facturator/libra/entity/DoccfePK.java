package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the doccfe database table.
 * 
 */
@Embeddable
public class DoccfePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="DocCFEId")
	private int docCFEId;

    public DoccfePK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public int getDocCFEId() {
		return this.docCFEId;
	}
	public void setDocCFEId(int docCFEId) {
		this.docCFEId = docCFEId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DoccfePK)) {
			return false;
		}
		DoccfePK castOther = (DoccfePK)other;
		return 
			this.empId.equals(castOther.empId)
			&& (this.docCFEId == castOther.docCFEId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.docCFEId;
		
		return hash;
    }
}