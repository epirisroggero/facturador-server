package uy.com.tmwc.facturator.dto;

import uy.com.tmwc.facturator.entity.CodigoNombreEntity;

public class CodigoNombre extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;

	public CodigoNombre() {
	}

	public CodigoNombre(CodigoNombreEntity source) {
		super(source.getCodigo(), source.getNombre());
	}

	public CodigoNombre(ICodigoNombre source) {
		super(source.getCodigo(), source.getNombre());
	}

	public CodigoNombre(String codigo, String nombre) {
		super(codigo, nombre);
	}
}