package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@CatalogEntity
@Table(name = "lfx_tareacapitulo")

public class Capitulo implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CapituloPK id;

	@Column(name = "Nombre")
	private String nombre;

	@Column(name = "Descripcion")
	private String descripcion;

	@SuppressWarnings("unused")
	@Column(name = "CapituloId", insertable=false, updatable=false)
	private String codigo;

	public CapituloPK getId() {
		return this.id;
	}

	public void setId(CapituloPK id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getCodigo() {
		return this.id.getCapituloId();
	}	

	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new CapituloPK();
		}
		this.id.setCapituloId(value);
	}



}