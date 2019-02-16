package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.utils.orm.CatalogEntity;


/**
 * The persistent class for the marcas database table.
 * 
 */
@Entity
@CatalogEntity
@Table(name="marcas")
public class Marca extends PersistentEntity<MarcaPK> implements Serializable, HasId<MarcaPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private MarcaPK id;

	@Column(name="MarcaNom")
	private String nombre;

	@SuppressWarnings("unused")
	@Column(name = "MarcaId", insertable = false, updatable = false)
	private String codigo;

    public Marca() {
    }
    
	public Marca(MarcaPK id, String marcaNom) {
		this.id = id;
		this.nombre = marcaNom;
	}

	public MarcaPK getId() {
		return this.id;
	}

	public void setId(MarcaPK id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getCodigo() {
		return String.valueOf(this.id.getMarcaId());
	}

	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new MarcaPK();
		}
		this.id.setMarcaId(value);
	}	

}