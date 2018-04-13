package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.utils.orm.CatalogEntity;

/**
 * The persistent class for the zonas database table.
 * 
 */
@Entity
@Table(name = "paises")
@CatalogEntity
public class Pais extends CodigoNombreEntity implements Serializable, HasId<PaisPK>  {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PaisPK id;

	@Column(name = "PaisNom")
	private String nombre;

	@Column(name = "PaisISO")
	private String paisISO;

	public Pais() {
	}

	public PaisPK getId() {
		return this.id;
	}

	public void setId(PaisPK id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPaisISO() {
		return paisISO;
	}

	public void setPaisISO(String paisISO) {
		this.paisISO = paisISO;
	}

}