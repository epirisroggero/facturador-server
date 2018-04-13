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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "tcambio")
public class TipoCambio extends PersistentEntity<TipoCambioPK> implements Serializable, HasId<TipoCambioPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TipoCambioPK id;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @javax.persistence.JoinColumn(name = "MndId", referencedColumnName = "MndId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Moneda moneda;

	@Column(name = "TCimp1", precision = 10, scale = 7)
	private BigDecimal comercial;

	@Column(name = "TCimp2", precision = 10, scale = 7)
	private BigDecimal fiscal;

	public TipoCambioPK getId() {
		return this.id;
	}

	public void setId(TipoCambioPK id) {
		this.id = id;
	}

	public BigDecimal getComercial() {
		return this.comercial;
	}

	public void setComercial(BigDecimal comercial) {
		this.comercial = comercial;
	}

	public BigDecimal getFiscal() {
		return this.fiscal;
	}

	public void setFiscal(BigDecimal fiscal) {
		this.fiscal = fiscal;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}
	
	public Date getDia() {
		return id != null ? id.getDia() : null;
	}
	
	public void setDia(Date dia) {
		ensureId();
		id.setDia(dia);
	}

	private void ensureId() {
		if (id == null) {
			id = new TipoCambioPK();
		}
	}
}