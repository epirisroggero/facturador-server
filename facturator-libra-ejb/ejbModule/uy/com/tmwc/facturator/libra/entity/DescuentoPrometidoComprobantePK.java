package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * The primary key class for the lfx_descuentoprometidocomprobante database table.
 * 
 */
@Embeddable
public class DescuentoPrometidoComprobantePK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "EmpId")
	private String empId;

	@Column(name = "DpcId")
	private int dpcId;

	public DescuentoPrometidoComprobantePK() {
	}
	
	public DescuentoPrometidoComprobantePK(String empId, int dpcId) {
		this.empId = empId;
		this.dpcId = dpcId;
	}

	
	public String getEmpId() {
		return this.empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public int getDpcId() {
		return this.dpcId;
	}

	public void setDpcId(int dpcId) {
		this.dpcId = dpcId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof DescuentoPrometidoComprobantePK)) {
			return false;
		}
		DescuentoPrometidoComprobantePK castOther = (DescuentoPrometidoComprobantePK) other;
		return this.empId.equals(castOther.empId) && (this.dpcId == castOther.dpcId);

	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + this.empId.hashCode();
		hash = hash * prime + this.dpcId;

		return hash;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.empId);
		sb.append(',');
		sb.append(this.dpcId);
		sb.append(" ");
		sb.append(super.toString());
		return sb.toString();
	}
}