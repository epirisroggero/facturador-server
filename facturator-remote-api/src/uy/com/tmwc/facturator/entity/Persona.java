package uy.com.tmwc.facturator.entity;

import java.util.Date;


public class Persona extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;
	
	private String ctoPerCargo;

	private String ctoPerEmail;

	private String ctoPerTelefono;

	private String ctoPerCelular;

	private Date ctoPerCumple;

	private String ctoPerNotas;

	public Persona() {
	}

	
	public Persona(String codigo, String nombre, String cargo, String email, String telefono, String celular, String notas) {
		super(codigo, nombre);
		
		this.ctoPerCargo = cargo;
		this.ctoPerEmail = email;
		this.ctoPerTelefono = telefono;
		this.ctoPerCelular = celular;
		this.ctoPerNotas = notas;
	}


	public String getCtoPerCargo() {
		return ctoPerCargo;
	}

	public void setCtoPerCargo(String ctoPerCargo) {
		this.ctoPerCargo = ctoPerCargo;
	}

	public String getCtoPerEmail() {
		return ctoPerEmail;
	}

	public void setCtoPerEmail(String ctoPerEmail) {
		this.ctoPerEmail = ctoPerEmail;
	}

	public String getCtoPerTelefono() {
		return ctoPerTelefono;
	}

	public void setCtoPerTelefono(String ctoPerTelefono) {
		this.ctoPerTelefono = ctoPerTelefono;
	}

	public String getCtoPerCelular() {
		return ctoPerCelular;
	}

	public void setCtoPerCelular(String ctoPerCelular) {
		this.ctoPerCelular = ctoPerCelular;
	}

	public Date getCtoPerCumple() {
		return ctoPerCumple;
	}

	public void setCtoPerCumple(Date ctoPerCumple) {
		this.ctoPerCumple = ctoPerCumple;
	}

	public String getCtoPerNotas() {
		return ctoPerNotas;
	}

	public void setCtoPerNotas(String ctoPerNotas) {
		this.ctoPerNotas = ctoPerNotas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}


}