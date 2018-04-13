package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import uy.com.tmwc.utils.orm.CatalogEntity;


/**
 * The persistent class for the giros database table.
 * 
 */
@Entity
@Table(name="giros")
@CatalogEntity
public class Giro implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GiroPK id;

	@Column(name="GirNom")
	private String nombre;

    public Giro() {
    }

	public GiroPK getId() {
		return this.id;
	}

	public void setId(GiroPK id) {
		this.id = id;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}