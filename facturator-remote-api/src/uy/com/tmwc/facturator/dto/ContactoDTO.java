package uy.com.tmwc.facturator.dto;

import java.io.Serializable;


public class ContactoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String nombre;
	private String razonSocial;

	private String direccion;
	private String telefono;
	private String celular;
	
	private String email;
	private String zona;
	private String localidad;
	private String depto;
	private String rut;
	
	private Boolean activo;
	
	public ContactoDTO() {
	}

	public ContactoDTO(Object ctoCodigo, String ctoNombre) {
		this.codigo = ctoCodigo.toString();
		this.nombre = ctoNombre;
	}

	public ContactoDTO(Object cliCodigo, String cliNombre, String ctoRazonSocial, String ctoDireccion, String ctoTelefono, String ctoCelular, String ctoEmail, 
		String ctoZona, String ctoLocalida, String ctoDepto, Boolean activo) {
		
		this.codigo = cliCodigo.toString();
		this.nombre = cliNombre;
		
		this.razonSocial = ctoRazonSocial;
		this.direccion = ctoDireccion;
		this.telefono = ctoTelefono;
		this.celular = ctoCelular;
		
		this.email = ctoEmail;
		this.zona = ctoZona;
		this.localidad = ctoLocalida;
		this.depto = ctoDepto;
		
		this.activo = activo;
		
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}


}