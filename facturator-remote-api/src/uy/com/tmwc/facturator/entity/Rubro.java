package uy.com.tmwc.facturator.entity;

public class Rubro extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;
	
	public Rubro() {
		super();
	}

	public Rubro(String codigo, String nombre) {
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
		return (obj instanceof Rubro);
	}


}
