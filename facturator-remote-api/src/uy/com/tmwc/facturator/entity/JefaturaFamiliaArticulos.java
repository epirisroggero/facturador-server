package uy.com.tmwc.facturator.entity;

public class JefaturaFamiliaArticulos extends Jefatura {
	private static final long serialVersionUID = 1L;
	private FamiliaArticulos familia;

	protected boolean aplica(Articulo articulo) {
		return this.familia.equals(articulo.getFamilia());
	}

	public String getDescripcion() {
		return "FLIA " + this.familia.getNombre();
	}

	public FamiliaArticulos getFamilia() {
		return this.familia;
	}

	public void setFamilia(FamiliaArticulos familia) {
		this.familia = familia;
	}
}