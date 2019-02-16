package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import uy.com.tmwc.facturator.dto.ICodigoNombre;
import uy.com.tmwc.utils.orm.CatalogEntity;


/**
 * The persistent class for the categoriasarticulos database table.
 * 
 */
@Entity
@Table(name="categoriasarticulos")
@CatalogEntity
public class Categoriasarticulo extends PersistentEntity<CategoriasarticuloPK> implements Serializable, ICodigoNombre, HasId<CategoriasarticuloPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CategoriasarticuloPK id;

	@Column(name="CategArtNom")
	private String nombre;
	
	@SuppressWarnings("unused")
	@Column(name = "CATEGARTID", insertable = false, updatable = false)
	private String codigo;


    public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Categoriasarticulo() {
    }

	public Categoriasarticulo(CategoriasarticuloPK id, String categArtNom) {
		this.id = id;
		this.nombre = categArtNom;
	}

	public CategoriasarticuloPK getId() {
		return this.id;
	}

	public void setId(CategoriasarticuloPK id) {
		this.id = id;
	}
	
	public String getCodigo() {
		return this.id.getCategartid();
	}
	
	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new CategoriasarticuloPK();
		}
		this.id.setCategartid(value);
	}	



}