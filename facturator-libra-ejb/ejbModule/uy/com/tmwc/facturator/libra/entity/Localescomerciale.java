package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import uy.com.tmwc.utils.orm.CatalogEntity;

import java.math.BigInteger;


/**
 * The persistent class for the localescomerciales database table.
 * 
 */
@Entity
@Table(name="localescomerciales")
@CatalogEntity
public class Localescomerciale extends PersistentEntity<LocalescomercialePK> implements Serializable,
	HasId<LocalescomercialePK> {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LocalescomercialePK id;

	@Column(name="LocActivo")
	private String locActivo;

	@Column(name="LocCiudad")
	private String locCiudad;

	@Column(name="LocContacto")
	private String locContacto;

	@Column(name="LocDepartamento")
	private String locDepartamento;

	@Column(name="LocDGINombre")
	private String locDGINombre;

	@Column(name="LocDGINumero")
	private Short locDGINumero;

	@Column(name="LocDir")
	private String locDir;

	@Column(name="LocEfacEmpresa")
	private BigInteger locEfacEmpresa;

	@Column(name="LocEfacKey")
	private String locEfacKey;

	@Column(name="LocEmail")
	private String locEmail;

	@Column(name="LocFax")
	private String locFax;

	@Column(name="LocNom")
	private String nombre;

	@Column(name="LocNotas")
	private String locNotas;

	@Column(name="LocTel")
	private String locTel;

    public Localescomerciale() {
    }

	public LocalescomercialePK getId() {
		return this.id;
	}

	public void setId(LocalescomercialePK id) {
		this.id = id;
	}
	
	public String getLocActivo() {
		return this.locActivo;
	}

	public void setLocActivo(String locActivo) {
		this.locActivo = locActivo;
	}

	public String getLocCiudad() {
		return this.locCiudad;
	}

	public void setLocCiudad(String locCiudad) {
		this.locCiudad = locCiudad;
	}

	public String getLocContacto() {
		return this.locContacto;
	}

	public void setLocContacto(String locContacto) {
		this.locContacto = locContacto;
	}

	public String getLocDepartamento() {
		return this.locDepartamento;
	}

	public void setLocDepartamento(String locDepartamento) {
		this.locDepartamento = locDepartamento;
	}

	public String getLocDGINombre() {
		return this.locDGINombre;
	}

	public void setLocDGINombre(String locDGINombre) {
		this.locDGINombre = locDGINombre;
	}

	public Short getLocDGINumero() {
		return this.locDGINumero;
	}

	public void setLocDGINumero(Short locDGINumero) {
		this.locDGINumero = locDGINumero;
	}

	public String getLocDir() {
		return this.locDir;
	}

	public void setLocDir(String locDir) {
		this.locDir = locDir;
	}

	public BigInteger getLocEfacEmpresa() {
		return this.locEfacEmpresa;
	}

	public void setLocEfacEmpresa(BigInteger locEfacEmpresa) {
		this.locEfacEmpresa = locEfacEmpresa;
	}

	public String getLocEfacKey() {
		return this.locEfacKey;
	}

	public void setLocEfacKey(String locEfacKey) {
		this.locEfacKey = locEfacKey;
	}

	public String getLocEmail() {
		return this.locEmail;
	}

	public void setLocEmail(String locEmail) {
		this.locEmail = locEmail;
	}

	public String getLocFax() {
		return this.locFax;
	}

	public void setLocFax(String locFax) {
		this.locFax = locFax;
	}

	public String getLocNotas() {
		return this.locNotas;
	}

	public void setLocNotas(String locNotas) {
		this.locNotas = locNotas;
	}

	public String getLocTel() {
		return this.locTel;
	}

	public void setLocTel(String locTel) {
		this.locTel = locTel;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}