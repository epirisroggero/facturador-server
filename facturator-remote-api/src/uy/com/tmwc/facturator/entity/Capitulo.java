package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

public class Capitulo extends CodigoNombreEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String descripcion;
	
	public Capitulo() {
		super();
	}

	public Capitulo(String codigo, String nombre) {
		super(codigo, nombre);
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public int hashCode() {
		int result = super.hashCode();

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj)) {
			return false;
		}
		return (obj instanceof Capitulo);
	}

}
