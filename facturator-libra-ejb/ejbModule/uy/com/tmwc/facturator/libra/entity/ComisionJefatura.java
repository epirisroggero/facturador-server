package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "lfx_comisionesjefaturas")
public class ComisionJefatura implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ComisionJefaturaPK id;


	@ManyToOne(optional = false)
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "jefaturaId", referencedColumnName = "jefaturaId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Jefatura jefatura;

	@NotFound(action = NotFoundAction.IGNORE)
	@ForeignKey(name = "lfx_comisionesJefaturas_vendedores")
	@ManyToOne(optional = false)
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "vendedorId", referencedColumnName = "VenId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Vendedore vendedor;

	@Column(precision = 5, scale = 2, nullable = false)
	private BigDecimal comision;
	
	public ComisionJefaturaPK getId() {
		return id;
	}

	public void setId(ComisionJefaturaPK id) {
		this.id = id;
	}

	public Jefatura getJefatura() {
		return this.jefatura;
	}

	public void setJefatura(Jefatura jefatura) {
		this.jefatura = jefatura;
	}

	public BigDecimal getComision() {
		return this.comision;
	}

	public void setComision(BigDecimal comision) {
		this.comision = comision;
	}

	public Vendedore getVendedor() {
		return this.vendedor;
	}
}