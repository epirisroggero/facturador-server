package uy.com.tmwc.facturator.dto;

import java.io.Serializable;

public class ArticuloDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String codigo;
	private String nombre;
	private String familia;
	private String codigoOrigen;

	public ArticuloDTO() {
	}

	public ArticuloDTO(Object cliCodigo, String cliNombre, String familia) {
		this.codigo = cliCodigo.toString();
		this.nombre = cliNombre;
		this.familia = familia;
	}

	public ArticuloDTO(Object cliCodigo, String cliNombre, String familia, String codigoOrigen) {
		this.codigo = cliCodigo.toString();
		this.nombre = cliNombre;
		this.familia = familia;
		this.codigoOrigen = codigoOrigen;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFamilia() {
		return familia;
	}

	public void setFamilia(String familia) {
		this.familia = familia;
	}

	public String getCodigoOrigen() {
		return codigoOrigen;
	}

	public void setCodigoOrigen(String codigoOrigen) {
		this.codigoOrigen = codigoOrigen;
	}


}