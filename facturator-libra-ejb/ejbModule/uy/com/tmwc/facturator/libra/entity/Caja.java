package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "cajas")
@CatalogEntity
public class Caja extends PersistentEntity<CajaPK> implements Serializable,
		HasId<CajaPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CajaPK id;

	@Column(name = "CajaNom")
	private String nombre;

	@Column(name = "LocIdCaj")
	private short locIdCaj;

	public CajaPK getId() {
		return this.id;
	}

	public void setId(CajaPK id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String cajaNom) {
		this.nombre = cajaNom;
	}

	public short getLocIdCaj() {
		return this.locIdCaj;
	}

	public void setLocIdCaj(short locIdCaj) {
		this.locIdCaj = locIdCaj;
	}
}