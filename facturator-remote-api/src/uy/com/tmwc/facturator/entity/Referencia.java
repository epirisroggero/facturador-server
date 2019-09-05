package uy.com.tmwc.facturator.entity;

public class Referencia extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;
	
	public Referencia() {
		super();
	}

	public Referencia(String codigo, String nombre) {
		super(codigo, nombre);
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
		return (obj instanceof Referencia);
	}



}
