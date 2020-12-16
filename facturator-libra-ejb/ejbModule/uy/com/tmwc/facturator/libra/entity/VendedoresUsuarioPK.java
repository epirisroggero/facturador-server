package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the lfx_vendedores_usuario database table.
 * 
 */
@Embeddable
public class VendedoresUsuarioPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name="vendedorId")
	private String vendedorId;

	@Column(name="usuarioId")
	private short usuarioId;
	

	public VendedoresUsuarioPK() {
	}

	public String getVendedorId() {
		return this.vendedorId;
	}

	public void setVendedorId(String vendedorId) {
		this.vendedorId = vendedorId;
	}

	public short getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(short usuarioId) {
		this.usuarioId = usuarioId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof VendedoresUsuarioPK)) {
			return false;
		}
		VendedoresUsuarioPK castOther = (VendedoresUsuarioPK) other;
		return this.vendedorId.equals(castOther.vendedorId) && (this.usuarioId == castOther.usuarioId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.vendedorId.hashCode();
		hash = hash * prime + ((int) this.usuarioId);

		return hash;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}
}