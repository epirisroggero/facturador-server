package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.facturator.dto.ICodigoNombre;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "categoriasclientes")
@CatalogEntity
public class Categoriasclientes extends PersistentEntity<CategoriasclientesPK> implements Serializable, ICodigoNombre, HasId<CategoriasclientesPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CategoriasclientesPK id;
	
	@Column(name = "CategCliNom", nullable = false, length = 40)
	private String nombre;

	public Categoriasclientes() {
	}

	public Categoriasclientes(CategoriasclientesPK id, String categCliNom) {
		this.id = id;
		this.nombre = categCliNom;
	}

	public CategoriasclientesPK getId() {
		return this.id;
	}

	public void setId(CategoriasclientesPK id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String categCliNom) {
		this.nombre = categCliNom;
	}
	
	public String getCodigo() {
		return this.id.getCategCliId();
	}



}
