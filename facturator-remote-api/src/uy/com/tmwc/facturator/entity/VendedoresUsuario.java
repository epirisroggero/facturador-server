package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

public class VendedoresUsuario implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String vendedorId;

	private short usuarioId;

	public String getVendedorId() {
		return vendedorId;
	}

	public void setVendedorId(String vendedorId) {
		this.vendedorId = vendedorId;
	}

	public short getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(short usuarioId) {
		this.usuarioId = usuarioId;
	}

	
}