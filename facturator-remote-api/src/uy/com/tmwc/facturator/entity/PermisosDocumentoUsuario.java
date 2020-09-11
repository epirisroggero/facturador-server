package uy.com.tmwc.facturator.entity;

public class PermisosDocumentoUsuario {

	private boolean edicion = true; //El usuario puede editar el documento
	private boolean rentaReal; //El usuario puede ver la renta y costo 'real'
	private boolean rentaDistribuidor; //El usuario puede ver la renta como (precio - precio distribuidor)
	
	public boolean isEdicion() {
		return edicion;
	}
	public void setEdicion(boolean edicion) {
		this.edicion = edicion;
	}
	public boolean isRentaReal() {
		return rentaReal;
	}
	public void setRentaReal(boolean rentaReal) {
		this.rentaReal = rentaReal;
	}
	public boolean isRentaDistribuidor() {
		return rentaDistribuidor;
	}
	public void setRentaDistribuidor(boolean rentaDistribuidor) {
		this.rentaDistribuidor = rentaDistribuidor;
	}
}
