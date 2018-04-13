package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the stockactual database table.
 * 
 */
@Embeddable
public class StockactualPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="DepIdSA")
	private short depIdSA;

	@Column(name="ArtIdSA")
	private String artIdSA;

	@Column(name="LoteIdSA")
	private String loteIdSA;

    @Temporal( TemporalType.DATE)
	@Column(name="LoteVenceSA")
	private java.util.Date loteVenceSA;

    public StockactualPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public short getDepIdSA() {
		return this.depIdSA;
	}
	public void setDepIdSA(short depIdSA) {
		this.depIdSA = depIdSA;
	}
	public String getArtIdSA() {
		return this.artIdSA;
	}
	public void setArtIdSA(String artIdSA) {
		this.artIdSA = artIdSA;
	}
	public String getLoteIdSA() {
		return this.loteIdSA;
	}
	public void setLoteIdSA(String loteIdSA) {
		this.loteIdSA = loteIdSA;
	}
	public java.util.Date getLoteVenceSA() {
		return this.loteVenceSA;
	}
	public void setLoteVenceSA(java.util.Date loteVenceSA) {
		this.loteVenceSA = loteVenceSA;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof StockactualPK)) {
			return false;
		}
		StockactualPK castOther = (StockactualPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& (this.depIdSA == castOther.depIdSA)
			&& this.artIdSA.equals(castOther.artIdSA)
			&& this.loteIdSA.equals(castOther.loteIdSA)
			&& this.loteVenceSA.equals(castOther.loteVenceSA);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + ((int) this.depIdSA);
		hash = hash * prime + this.artIdSA.hashCode();
		hash = hash * prime + this.loteIdSA.hashCode();
		hash = hash * prime + this.loteVenceSA.hashCode();
		
		return hash;
    }
}