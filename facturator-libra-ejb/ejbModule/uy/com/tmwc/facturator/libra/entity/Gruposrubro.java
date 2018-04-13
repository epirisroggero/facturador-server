package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gruposrubros database table.
 * 
 */
@Entity
@Table(name="gruposrubros")
public class Gruposrubro implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GruposrubroPK id;

	@Column(name="GruRubNom")
	private String gruRubNom;

    public Gruposrubro() {
    }

	public GruposrubroPK getId() {
		return this.id;
	}

	public void setId(GruposrubroPK id) {
		this.id = id;
	}
	
	public String getGruRubNom() {
		return this.gruRubNom;
	}

	public void setGruRubNom(String gruRubNom) {
		this.gruRubNom = gruRubNom;
	}

}