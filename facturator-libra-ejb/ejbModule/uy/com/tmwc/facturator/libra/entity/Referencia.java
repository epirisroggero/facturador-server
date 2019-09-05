package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.facturator.dto.ICodigoNombre;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "referencias")
@CatalogEntity(useNamedQuery = true)
public class Referencia extends PersistentEntity<ReferenciaPK> implements Serializable, ICodigoNombre  {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ReferenciaPK id;

	@SuppressWarnings("unused")
	@Column(name = "RefId", insertable = false, updatable = false)
	private String codigo;

	@Column(name = "RefNom")
	private String nombre;

	public Referencia() {
	}
	
	public Referencia(String codigo, String nombre) {
		setCodigo(codigo);

		this.nombre = nombre;
	}


	public ReferenciaPK getId() {
		return this.id;
	}

	public void setId(ReferenciaPK id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return this.id.getRefId();
	}

	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new ReferenciaPK();
		}
		this.id.setRefId(value);

	}

}