package uy.com.tmwc.facturator.dto;

import java.io.Serializable;

public class ArticuloQuery implements Serializable {
	private static final long serialVersionUID = 1L;

	private String proveedor;
	
	private String familias;

	private Boolean activo = true;


	public ArticuloQuery() {
	}
	
	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getProveedor() {
		return proveedor;
	}

	public void setNombre(String nombre) {
		this.proveedor = nombre;
	}

	public String getFamilias() {
		return familias;
	}

	public void setFamilias(String familias) {
		this.familias = familias;
	}

	public void setProveedor(String proveedor) {
		this.proveedor = proveedor;
	}

}
