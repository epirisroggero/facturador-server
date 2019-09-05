package uy.com.tmwc.facturator.entity;

public class UnidadesStock extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;
	
	private String unidadSim;
	

	public UnidadesStock() {
		super();
	}

	public UnidadesStock(String codigo, String nombre) {
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


	public String getUnidadSim() {
		return unidadSim;
	}

	public void setUnidadSim(String unidadSim) {
		this.unidadSim = unidadSim;
	}

}
