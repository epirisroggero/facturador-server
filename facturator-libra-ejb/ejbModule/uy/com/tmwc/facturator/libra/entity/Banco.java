package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the bancos database table.
 * 
 */
@Entity
@Table(name="bancos")
public class Banco implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private BancoPK id;

	@Column(name="BancoAbrevia")
	private String bancoAbrevia;

	@Column(name="BancoNom")
	private String bancoNom;

	@Column(name="BancoNotas")
	private String bancoNotas;

	@Column(name="BancoTipo")
	private String bancoTipo;

    public Banco() {
    }

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

	public String getBancoNom() {
		return this.bancoNom;
	}

	public void setBancoNom(String bancoNom) {
		this.bancoNom = bancoNom;
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

}