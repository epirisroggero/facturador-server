package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ListaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "ListaId")
	private String listaId;

	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getListaId() {
		return this.listaId;
	}

	public void setListaId(String listaId) {
		this.listaId = listaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ListaPK)) {
			return false;
		}
		ListaPK castOther = (ListaPK) other;

		return (this.empId.equals(castOther.empId)) && (this.listaId.equals(castOther.listaId));
	}

	public int hashCode() {
		int hash = 17;
		hash = hash * 31 + this.empId.hashCode();
		hash = hash * 31 + this.listaId.hashCode();

		return hash;
	}
}