package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "vinculosdoc")
@SecondaryTable(name = "lfx_vinculosdoc")
public class Vinculosdoc extends PersistentEntity<VinculosdocPK> implements Serializable, HasId<VinculosdocPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VinculosdocPK id;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "DocIdVin1", referencedColumnName = "DocId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Documento factura;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "DocIdVin2", referencedColumnName = "DocId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Documento recibo;

	@Column(name = "VinMonto", nullable = false)
	private BigDecimal monto;
	
	@Column(table = "lfx_vinculosdoc", name = "DescuentoPorc")
	private BigDecimal descuentoPorc = BigDecimal.ZERO;

	@Column(table = "lfx_vinculosdoc", name = "VinNeto")
	private BigDecimal neto = BigDecimal.ZERO;

	@Column(table = "lfx_vinculosdoc", name = "VinRtaFin")
	private BigDecimal vinRtaFin = BigDecimal.ZERO;

	public VinculosdocPK getId() {
		return this.id;
	}

	public void setId(VinculosdocPK id) {
		this.id = id;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public Documento getFactura() {
		return this.factura;
	}

	public void setFactura(Documento factura) {
		this.factura = factura;
	}

	public Documento getRecibo() {
		return this.recibo;
	}

	public void setRecibo(Documento recibo) {
		this.recibo = recibo;
	}

	public BigDecimal getDescuentoPorc() {
		return descuentoPorc;
	}

	public void setDescuentoPorc(BigDecimal descuentoPorc) {
		this.descuentoPorc = descuentoPorc;
	}

	public BigDecimal getNeto() {
		return neto;
	}

	public void setNeto(BigDecimal neto) {
		this.neto = neto;
	}

	public BigDecimal getVinRtaFin() {
		return vinRtaFin;
	}

	public void setVinRtaFin(BigDecimal vinRtaFin) {
		this.vinRtaFin = vinRtaFin;
	}
}