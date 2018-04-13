package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tareas database table.
 * 
 */
@Embeddable
public class TareaPK implements Serializable {
	// default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "TareaId")
	private String tareaId;

	public TareaPK() {
	}
	
	public TareaPK(String empId, String tareaId) {
		this.empId = empId;
		this.tareaId = tareaId;
	}


	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getTareaId() {
		return this.tareaId;
	}

	public void setTareaId(String tareaId) {
		this.tareaId = tareaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof TareaPK)) {
			return false;
		}
		TareaPK castOther = (TareaPK) other;
		return this.empId.equals(castOther.empId)
				&& this.tareaId.equals(castOther.tareaId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.tareaId.hashCode();

		return hash;
	}
}