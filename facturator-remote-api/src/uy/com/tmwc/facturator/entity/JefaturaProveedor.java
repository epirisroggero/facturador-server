package uy.com.tmwc.facturator.entity;

public class JefaturaProveedor extends Jefatura {
	private static final long serialVersionUID = 1L;
	private Proveedor proveedor;

	protected boolean aplica(Articulo articulo) {
		return this.proveedor.equals(articulo.getProveedor());
	}

	public String getDescripcion() {
		return "PROV " + this.proveedor.getNombre();
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
}