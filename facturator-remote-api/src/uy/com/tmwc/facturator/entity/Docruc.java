package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

public class Docruc implements Serializable {

	private static final long serialVersionUID = 1L;

	public Docruc() {
	}

	private int docId;

	private short docRutId;

	private Documento documento;

	private String ciudad;

	private String direccion;

	private String nombre;

	private String rut;

	private String telefono;

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}

	public short getDocRutId() {
		return docRutId;
	}

	public void setDocRutId(short docRutId) {
		this.docRutId = docRutId;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

}
