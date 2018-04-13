package uy.com.tmwc.facturator.entity;

import java.io.Serializable;


public class Numerador extends CodigoNombreEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Short locIdNum;
	
	private String nombre;
	
	private String serie;

	private long numero;
	
	public Numerador() {
	}	

 
	public Short getLocIdNum() {
		return locIdNum;
	}

	public void setLocIdNum(Short locIdNum) {
		this.locIdNum = locIdNum;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public long getNumero() {
		return numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

}
