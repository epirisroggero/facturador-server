package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@CatalogEntity
@Table(name = "familias")
public class Familia extends PersistentEntity<FamiliaPK> implements Serializable, HasId<FamiliaPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FamiliaPK id;

	@Column(name = "FamiliaIdNom", nullable = false, length = 55)
	private String familiaIdNom;

	@Column(name = "FamiliaImputable", length = 1)
	private String imputable;

	@Column(name = "FamiliaNom", nullable = false, length = 40)
	private String nombre;
	
	@SuppressWarnings("unused")
	@Column(name = "FamiliaId", insertable = false, updatable = false)
	private String codigo;

	public Familia() {
	}
	
	public Familia(String id, String nombre) {
		setCodigo(id);
		this.nombre = nombre;
	}

	public FamiliaPK getId() {
		return this.id;
	}

	public void setId(FamiliaPK id) {
		this.id = id;
	}

	public String getFamiliaIdNom() {
		return this.familiaIdNom;
	}

	public void setFamiliaIdNom(String familiaIdNom) {
		this.familiaIdNom = familiaIdNom;
	}

	public String getImputable() {
		return this.imputable;
	}

	public void setImputable(String imputable) {
		this.imputable = imputable;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return String.valueOf(this.id.getFamiliaId());
	}

	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new FamiliaPK();
		}
		this.id.setFamiliaId(value);
	}

}