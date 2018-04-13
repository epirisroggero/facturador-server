package uy.com.tmwc.facturator.dto;

import java.io.Serializable;

public class ProveedoresQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private String zona;
	private String categoria;
	private String giro;
	private String razonSocial;
	
	private Boolean activo;

	public ProveedoresQuery() {
	}
	
	public ProveedoresQuery(String nombre) {
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

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
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