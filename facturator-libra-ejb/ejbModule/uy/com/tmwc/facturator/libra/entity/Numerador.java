package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "numeradores")
@CatalogEntity
public class Numerador extends PersistentEntity<NumeradorPK> implements Serializable, HasId<NumeradorPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NumeradorPK id;

	@Column(name = "LocIdNum")
	private Short locIdNum;

	@Column(name = "NumCmpNom")
	private String nombre;

	@Version
	@Column(name = "NumCmpNumero")
	private long numero;

	@Column(name = "NumCmpSerie")
	private String serie;

	public NumeradorPK getId() {
		return this.id;
	}

	public void setId(NumeradorPK id) {
		this.id = id;
	}

	public Short getLocIdNum() {
		return this.locIdNum;
	}

	public void setLocIdNum(Short locIdNum) {
		this.locIdNum = locIdNum;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getNumero() {
		return this.numero;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}
}