package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.utils.orm.CatalogEntity;

/**
 * The persistent class for the departamentos database table.
 * 
 */
@Entity
@Table(name="departamentos")
@CatalogEntity
public class Departamento implements Serializable, HasId<DepartamentoPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DepartamentoPK id;

	@Column(name="DeptoNom")
	private String nombre;

    public Departamento() {
    }
    
	public DepartamentoPK getId() {
		return this.id;
	}

	public void setId(DepartamentoPK id) {
		this.id = id;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String deptoNom) {
		this.nombre = deptoNom;
	}


}
