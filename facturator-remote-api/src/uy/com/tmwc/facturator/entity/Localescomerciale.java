package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigInteger;

public class Localescomerciale extends CodigoNombreEntity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String locActivo;

	private String locCiudad;

	private String locContacto;

	private String locDepartamento;

	private String locDGINombre;

	private Short locDGINumero;

	private String locDir;

	private BigInteger locEfacEmpresa;

	private String locEfacKey;

	private String locEmail;

	private String locFax;

	private String locNotas;

	private String locTel;

	public String getLocActivo() {
		return locActivo;
	}

	public void setLocActivo(String locActivo) {
		this.locActivo = locActivo;
	}

	public String getLocCiudad() {
		return locCiudad;
	}

	public void setLocCiudad(String locCiudad) {
		this.locCiudad = locCiudad;
	}

	public String getLocContacto() {
		return locContacto;
	}

	public void setLocContacto(String locContacto) {
		this.locContacto = locContacto;
	}

	public String getLocDepartamento() {
		return locDepartamento;
	}

	public void setLocDepartamento(String locDepartamento) {
		this.locDepartamento = locDepartamento;
	}

	public String getLocDGINombre() {
		return locDGINombre;
	}

	public void setLocDGINombre(String locDGINombre) {
		this.locDGINombre = locDGINombre;
	}

	public Short getLocDGINumero() {
		return locDGINumero;
	}

	public void setLocDGINumero(Short locDGINumero) {
		this.locDGINumero = locDGINumero;
	}

	public String getLocDir() {
		return locDir;
	}

	public void setLocDir(String locDir) {
		this.locDir = locDir;
	}

	public BigInteger getLocEfacEmpresa() {
		return locEfacEmpresa;
	}

	public void setLocEfacEmpresa(BigInteger locEfacEmpresa) {
		this.locEfacEmpresa = locEfacEmpresa;
	}

	public String getLocEfacKey() {
		return locEfacKey;
	}

	public void setLocEfacKey(String locEfacKey) {
		this.locEfacKey = locEfacKey;
	}

	public String getLocEmail() {
		return locEmail;
	}

	public void setLocEmail(String locEmail) {
		this.locEmail = locEmail;
	}

	public String getLocFax() {
		return locFax;
	}

	public void setLocFax(String locFax) {
		this.locFax = locFax;
	}

	public String getLocNotas() {
		return locNotas;
	}

	public void setLocNotas(String locNotas) {
		this.locNotas = locNotas;
	}

	public String getLocTel() {
		return locTel;
	}

	public void setLocTel(String locTel) {
		this.locTel = locTel;
	}


}
