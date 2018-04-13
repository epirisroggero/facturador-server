package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import uy.com.tmwc.facturator.dto.ICodigoNombre;


/**
 * The persistent class for the gruposcontactos database table.
 * 
 */
@Entity
@Table(name="gruposcontactos")
public class Gruposcontacto implements Serializable, ICodigoNombre, HasId<GruposcontactoPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GruposcontactoPK id;

	@Column(name="GruCtoNom")
	private String nombre;

    public Gruposcontacto() {
    }

	public GruposcontactoPK getId() {
		return this.id;
	}

	public void setId(GruposcontactoPK id) {
		this.id = id;
	}
	
	public String getCodigo() {
		return id != null ? id.getGruCtoId() : null;
	}
	
	public void setCodigo(String value) {
		if (id == null) {
			id = new GruposcontactoPK();
		}
		id.setGruCtoId(value);
	}

	public String getNombre() {
		return nombre;
	}

}