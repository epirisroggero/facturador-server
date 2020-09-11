package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

@SuppressWarnings("serial")
public abstract class CodigoNombreEntity implements Serializable, Cloneable, Comparable<CodigoNombreEntity> {

	private String codigo;
	private String nombre;

	public CodigoNombreEntity() {
	}

	public Object clone() {
		CodigoNombreEntity obj = null;
		try {
			obj = (CodigoNombreEntity) super.clone();
		} catch (CloneNotSupportedException ex) {
			ex.printStackTrace();
		}
		return obj;
	}

	public CodigoNombreEntity(String codigo, String nombre) {
		this.codigo = codigo;
		this.nombre = nombre;
	}

	public int compareTo(CodigoNombreEntity o) {
		return this.codigo.compareTo(o.codigo);
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int hashCode() {
		int result = 1;
		result = 31 * result + (this.codigo == null ? 0 : this.codigo.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof CodigoNombreEntity))
			return false;
		CodigoNombreEntity other = (CodigoNombreEntity) obj;
		if (this.codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!this.codigo.equals(other.codigo))
			return false;
		return true;
	}
}