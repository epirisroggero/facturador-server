package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;

import uy.com.tmwc.utils.orm.CatalogEntity;

/**
 * The persistent class for the tareas database table.
 * 
 */
@Entity
@CatalogEntity(prefix = "tarea", abreviated = true)
@Table(name = "tareas")
@SecondaryTable(name = "lfx_tareas")
public class Tarea extends PersistentEntity<TareaPK> implements Serializable, HasId<TareaPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TareaPK id;

	@Column(name = "TareaNom")
	private String nombre;
	
	@SuppressWarnings("unused")
	@Column(name = "TareaId", insertable=false, updatable=false)
	private String codigo;

	@Column(name = "CapituloId", table = "lfx_tareas")
	private String capituloId;	
	

	public Tarea() {
	}

	public TareaPK getId() {
		return this.id;
	}

	public void setId(TareaPK id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return this.id.getTareaId();
	}	


	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new TareaPK();
		}
		this.id.setTareaId(value);

	}
	
	public String getCapituloId() {
		return capituloId;
	}

	public void setCapituloId(String capituloId) {
		this.capituloId = capituloId;
	}


}