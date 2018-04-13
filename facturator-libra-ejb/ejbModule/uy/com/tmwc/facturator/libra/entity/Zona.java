package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import uy.com.tmwc.utils.orm.CatalogEntity;


/**
 * The persistent class for the zonas database table.
 * 
 */
@Entity
@Table(name="zonas")
@CatalogEntity
public class Zona implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ZonaPK id;

	@Column(name="ZonaNom")
	private String nombre;

    public Zona() {
    }

	public ZonaPK getId() {
		return this.id;
	}

	public void setId(ZonaPK id) {
		this.id = id;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String zonaNom) {
		this.nombre = zonaNom;
	}

}