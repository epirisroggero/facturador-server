package uy.com.tmwc.facturator.entity;

public class JefaturaArticulo extends Jefatura {
	private static final long serialVersionUID = 1L;
	private Articulo articulo;

	protected boolean aplica(Articulo articulo) {
		return this.articulo.equals(articulo);
	}

	public String getDescripcion() {
		return "ART " + this.articulo.getCodigo();
	}

	public Articulo getArticulo() {
		return this.articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}
}