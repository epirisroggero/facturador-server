package uy.com.tmwc.facturator.dto;

import java.io.Serializable;

public class ContactoQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private String zona;
	private String razonSocial;
	private String giro;

	private Boolean activo = Boolean.TRUE;

	public ContactoQuery() {
	}
	
	public ContactoQuery(String nombre) {
		this.nombre = nombre;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}


	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getGiro() {
		return giro;
	}

	public void setGiro(String giro) {
		this.giro = giro;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}


}