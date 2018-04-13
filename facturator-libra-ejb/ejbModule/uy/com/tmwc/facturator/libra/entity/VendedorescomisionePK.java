package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the vendedorescomisiones database table.
 * 
 */
@Embeddable
public class VendedorescomisionePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="VenId")
	private String venId;

	@Column(name="ComisionId")
	private int comisionId;

    public VendedorescomisionePK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getVenId() {
		return this.venId;
	}
	public void setVenId(String venId) {
		this.venId = venId;
	}
	public int getComisionId() {
		return this.comisionId;
	}
	public void setComisionId(int comisionId) {
		this.comisionId = comisionId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VendedorescomisionePK)) {
			return false;
		}
		VendedorescomisionePK castOther = (VendedorescomisionePK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.venId.equals(castOther.venId)
			&& (this.comisionId == castOther.comisionId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.venId.hashCode();
		hash = hash * prime + this.comisionId;
		
		return hash;
    }
}