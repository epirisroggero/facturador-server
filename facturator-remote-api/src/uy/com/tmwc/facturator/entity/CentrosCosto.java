package uy.com.tmwc.facturator.entity;

public class CentrosCosto extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;
	
	public CentrosCosto() {
		super();
	}

	public CentrosCosto(String codigo, String nombre) {
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
		return (obj instanceof CentrosCosto);
	}



}
