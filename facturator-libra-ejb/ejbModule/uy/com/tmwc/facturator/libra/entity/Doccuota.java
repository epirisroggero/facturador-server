package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "doccuotas")
public class Doccuota extends PersistentEntity<DoccuotaPK> implements Serializable, HasId<DoccuotaPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DoccuotaPK id;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "DocId", referencedColumnName = "DocId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Documento documento;

	@Column(name = "CuotaCtoId")
	private String ctoId;

	@Column(name = "CuotaCtoTipo")
	private String ctoTipo;

	@Column(name = "CuotaEntrega")
	private String entrega;

	@Temporal(TemporalType.DATE)
	@Column(name = "CuotaFecha")
	private Date fecha;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "CuotaMoneda", referencedColumnName = "MndId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Moneda moneda;

	@SuppressWarnings("unused")
	@Column(name = "CuotaMoneda")
	private short monedaId;

	@Column(name = "CuotaSaldo")
	private BigDecimal saldo;

	@Column(name = "CuotaTotal")
	private BigDecimal total;

	public DoccuotaPK getId() {
		return this.id;
	}

	public void setId(DoccuotaPK id) {
		this.id = id;
	}

	public String getCtoId() {
		return this.ctoId;
	}

	public void setCtoId(String ctoId) {
		this.ctoId = ctoId;
	}

	public String getCtoTipo() {
		return this.ctoTipo;
	}

	public void setCtoTipo(String ctoTipo) {
		this.ctoTipo = ctoTipo;
	}

	public String getEntrega() {
		return this.entrega;
	}

	public void setEntrega(String entrega) {
		this.entrega = entrega;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
		this.id.setDocId(documento == null ? 0 : documento.getId().getDocId());
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
		if (moneda == null)
			this.monedaId = 0;
		else
			this.monedaId = moneda.getId().getMndId();
	}

}