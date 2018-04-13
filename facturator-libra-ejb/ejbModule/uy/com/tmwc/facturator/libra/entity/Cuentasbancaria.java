package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the cuentasbancarias database table.
 * 
 */
@Entity
@Table(name="cuentasbancarias")
public class Cuentasbancaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CuentasbancariaPK id;

	@Column(name="BancoIdCue")
	private String bancoIdCue;

	@Column(name="CuentaActiva")
	private String cuentaActiva;

	@Column(name="CuentaCheques")
	private String cuentaCheques;

	@Column(name="CuentaNom")
	private String cuentaNom;

	@Column(name="CuentaNotas")
	private String cuentaNotas;

	@Column(name="CuentaNro")
	private String cuentaNro;

	@Column(name="CuentaTitular")
	private String cuentaTitular;

	@Column(name="MndIdCue")
	private short mndIdCue;

	@Column(name="RubIdCue")
	private String rubIdCue;

    public Cuentasbancaria() {
    }

	public CuentasbancariaPK getId() {
		return this.id;
	}

	public void setId(CuentasbancariaPK id) {
		this.id = id;
	}
	
	public String getBancoIdCue() {
		return this.bancoIdCue;
	}

	public void setBancoIdCue(String bancoIdCue) {
		this.bancoIdCue = bancoIdCue;
	}

	public String getCuentaActiva() {
		return this.cuentaActiva;
	}

	public void setCuentaActiva(String cuentaActiva) {
		this.cuentaActiva = cuentaActiva;
	}

	public String getCuentaCheques() {
		return this.cuentaCheques;
	}

	public void setCuentaCheques(String cuentaCheques) {
		this.cuentaCheques = cuentaCheques;
	}

	public String getCuentaNom() {
		return this.cuentaNom;
	}

	public void setCuentaNom(String cuentaNom) {
		this.cuentaNom = cuentaNom;
	}

	public String getCuentaNotas() {
		return this.cuentaNotas;
	}

	public void setCuentaNotas(String cuentaNotas) {
		this.cuentaNotas = cuentaNotas;
	}

	public String getCuentaNro() {
		return this.cuentaNro;
	}

	public void setCuentaNro(String cuentaNro) {
		this.cuentaNro = cuentaNro;
	}

	public String getCuentaTitular() {
		return this.cuentaTitular;
	}

	public void setCuentaTitular(String cuentaTitular) {
		this.cuentaTitular = cuentaTitular;
	}

	public short getMndIdCue() {
		return this.mndIdCue;
	}

	public void setMndIdCue(short mndIdCue) {
		this.mndIdCue = mndIdCue;
	}

	public String getRubIdCue() {
		return this.rubIdCue;
	}

	public void setRubIdCue(String rubIdCue) {
		this.rubIdCue = rubIdCue;
	}

}