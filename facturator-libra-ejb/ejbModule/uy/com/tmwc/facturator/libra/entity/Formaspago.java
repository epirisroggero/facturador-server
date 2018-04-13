package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "formaspago")
@CatalogEntity
public class Formaspago extends PersistentEntity<FormaspagoPK> implements Serializable, HasId<FormaspagoPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FormaspagoPK id;

	@Column(name = "FormaPagoAbrevia")
	private String formaPagoAbrevia;

	@Column(name = "FormaPagoNom")
	private String nombre;

	@Column(name = "FormaPagoTipo")
	private String formaPagoTipo;

	public FormaspagoPK getId() {
		return this.id;
	}

	public void setId(FormaspagoPK id) {
		this.id = id;
	}

	public String getFormaPagoAbrevia() {
		return this.formaPagoAbrevia;
	}

	public void setFormaPagoAbrevia(String formaPagoAbrevia) {
		this.formaPagoAbrevia = formaPagoAbrevia;
	}

	public String getFormaPagoTipo() {
		return this.formaPagoTipo;
	}

	public void setFormaPagoTipo(String formaPagoTipo) {
		this.formaPagoTipo = formaPagoTipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
}