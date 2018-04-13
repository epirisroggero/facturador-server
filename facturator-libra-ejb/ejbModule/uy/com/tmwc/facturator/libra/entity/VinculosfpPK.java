package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the vinculosfp database table.
 * 
 */
@Embeddable
public class VinculosfpPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="EmpId")
	private String empId;

	@Column(name="DocIdFP1")
	private int docIdFP1;

	@Column(name="DocIdFP2")
	private int docIdFP2;

	@Column(name="VinFPTipo")
	private short vinFPTipo;

    public VinculosfpPK() {
    }
	public String getEmpId() {
		return this.empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	public int getDocIdFP1() {
		return this.docIdFP1;
	}
	public void setDocIdFP1(int docIdFP1) {
		this.docIdFP1 = docIdFP1;
	}
	public int getDocIdFP2() {
		return this.docIdFP2;
	}
	public void setDocIdFP2(int docIdFP2) {
		this.docIdFP2 = docIdFP2;
	}
	public short getVinFPTipo() {
		return this.vinFPTipo;
	}
	public void setVinFPTipo(short vinFPTipo) {
		this.vinFPTipo = vinFPTipo;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VinculosfpPK)) {
			return false;
		}
		VinculosfpPK castOther = (VinculosfpPK)other;
		return 
			this.empId.equals(castOther.empId)
			&& (this.docIdFP1 == castOther.docIdFP1)
			&& (this.docIdFP2 == castOther.docIdFP2)
			&& (this.vinFPTipo == castOther.vinFPTipo);

    }
    
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.docIdFP1;
		hash = hash * prime + this.docIdFP2;
		hash = hash * prime + ((int) this.vinFPTipo);
		
		return hash;
    }
}