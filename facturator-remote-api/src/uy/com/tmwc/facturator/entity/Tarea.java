package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

public class Tarea extends CodigoNombreEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String capituloId;

	public Tarea() {
		super();
	}

	public Tarea(String codigo, String nombre) {
		super(codigo, nombre);
	}

	
	public String getCapituloId() {
		return capituloId;
	}

	public void setCapituloId(String capitulo) {
		this.capituloId = capitulo;
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
		return (obj instanceof Tarea);
	}

}
