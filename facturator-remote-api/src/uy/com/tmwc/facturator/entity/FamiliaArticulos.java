package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

public class FamiliaArticulos extends CodigoNombreEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  public FamiliaArticulos() {
		super();
	}

	public FamiliaArticulos(String codigo, String nombre) {
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
		return (obj instanceof FamiliaArticulos);
	}
  
}