package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the zonas database table.
 * 
 */
@Embeddable
public class ZonaPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="ZonaId")
	private String zonaId;

    public ZonaPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public String getZonaId() {
		return this.zonaId;
	}
	public void setZonaId(String zonaId) {
		this.zonaId = zonaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ZonaPK)) {
			return false;
		}
		ZonaPK castOther = (ZonaPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& this.zonaId.equals(castOther.zonaId);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.zonaId.hashCode();
		
		return hash;
    }
}