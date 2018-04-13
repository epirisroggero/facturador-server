package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the vinculosfp database table.
 * 
 */
@Entity
@Table(name="vinculosfp")
public class Vinculosfp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VinculosfpPK id;

	@Column(name="VinIE")
	private String vinIE;

    public Vinculosfp() {
    }

	public VinculosfpPK getId() {
		return this.id;
	}

	public void setId(VinculosfpPK id) {
		this.id = id;
	}
	
	public String getVinIE() {
		return this.vinIE;
	}

	public void setVinIE(String vinIE) {
		this.vinIE = vinIE;
	}

}