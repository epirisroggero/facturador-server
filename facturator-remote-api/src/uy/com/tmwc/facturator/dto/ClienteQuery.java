package uy.com.tmwc.facturator.dto;

import java.io.Serializable;

public class ClienteQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	private String nombre;
	private String vendedor;
	private String zona;
	private String categoria;
	private String especialista;
	private String encargadoCuenta;
	private String razonSocial;
	private String giro;

	private Boolean activo = Boolean.TRUE;

	public ClienteQuery() {
	}
	
	public ClienteQuery(String nombre) {
		this.nombre = nombre;
	}


	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getVendedor() {
		return vendedor;
	}

	public void setVendedor(String vendedor) {
		this.vendedor = vendedor;
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

	public String getEspecialista() {
		return especialista;
	}

	public void setEspecialista(String especialista) {
		this.especialista = especialista;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getEncargadoCuenta() {
		return encargadoCuenta;
	}

	public void setEncargadoCuenta(String encargadoCuenta) {
		this.encargadoCuenta = encargadoCuenta;
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