package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the gruposconceptos database table.
 * 
 */
@Entity
@Table(name="gruposconceptos")
public class Gruposconcepto implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GruposconceptoPK id;

	@Column(name="GrupoCptNom")
	private String grupoCptNom;

    public Gruposconcepto() {
    }

	public GruposconceptoPK getId() {
		return this.id;
	}

	public void setId(GruposconceptoPK id) {
		this.id = id;
	}
	
	public String getGrupoCptNom() {
		return this.grupoCptNom;
	}

	public void setGrupoCptNom(String grupoCptNom) {
		this.grupoCptNom = grupoCptNom;
	}

}