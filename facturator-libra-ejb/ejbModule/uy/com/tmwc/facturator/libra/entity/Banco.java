package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import uy.com.tmwc.utils.orm.CatalogEntity;


/**
 * The persistent class for the bancos database table.
 * 
 */
@Entity
@Table(name="bancos")
@CatalogEntity
public class Banco extends PersistentEntity<BancoPK> implements Serializable, HasId<BancoPK>  {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BancoPK id;

	@Column(name="BancoAbrevia")
	private String bancoAbrevia;

	@Column(name="BancoNom")
	private String nombre;

	@Column(name="BancoNotas")
	private String bancoNotas;

	@Column(name="BancoTipo")
	private String bancoTipo;

	public BancoPK getId() {
		return this.id;
	}

	public void setId(BancoPK id) {
		this.id = id;
	}
	
	public String getBancoAbrevia() {
		return this.bancoAbrevia;
	}

	public void setBancoAbrevia(String bancoAbrevia) {
		this.bancoAbrevia = bancoAbrevia;
	}

	public String getBancoNotas() {
		return this.bancoNotas;
	}

	public void setBancoNotas(String bancoNotas) {
		this.bancoNotas = bancoNotas;
	}

	public String getBancoTipo() {
		return this.bancoTipo;
	}

	public void setBancoTipo(String bancoTipo) {
		this.bancoTipo = bancoTipo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}