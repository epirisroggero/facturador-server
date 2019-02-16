package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the categoriasarticulos database table.
 * 
 */
@Embeddable
public class CategoriasarticuloPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EMPID")
	private String empId;

	@Column(name="CATEGARTID")
	private String categartid;

    public CategoriasarticuloPK() {
    }
    
	public CategoriasarticuloPK(String empId, String categArtId) {
		this.empId = empId;
		this.categartid = categArtId;
	}

	public String getEmpId() {
		return this.empId;
	}
	
	public void setEmpId(String empid) {
		this.empId = empid;
	}
	
	public String getCategartid() {
		return this.categartid;
	}
	
	public void setCategartid(String categartid) {
		this.categartid = categartid;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof CategoriasarticuloPK)) {
			return false;
		}
		CategoriasarticuloPK castOther = (CategoriasarticuloPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.categartid.equals(castOther.categartid);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.categartid.hashCode();
		
		return hash;
    }
}