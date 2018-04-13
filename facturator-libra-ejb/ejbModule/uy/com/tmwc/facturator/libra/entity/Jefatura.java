package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.ForeignKey;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo", discriminatorType = DiscriminatorType.CHAR)
@Table(name = "lfx_jefaturas")
@DiscriminatorValue("X")
public abstract class Jefatura implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private JefaturaPK id;

	@NotFound(action = NotFoundAction.IGNORE)
	@ForeignKey(name = "lfx_participacionVendedor_vendedores")
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "jefe_id", referencedColumnName = "VenId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Vendedore jefe;

	@Column(length = 3, nullable = false)
	private String jefe_id;

	@Column(precision = 5, scale = 2, nullable = false)
	private BigDecimal comisionPorDefecto;

	@OneToMany(mappedBy = "jefatura")
	private Set<ComisionJefatura> comisiones;

	public Vendedore getJefe() {
		return this.jefe;
	}

	public BigDecimal getComisionPorDefecto() {
		return this.comisionPorDefecto;
	}

	public void setComisionPorDefecto(BigDecimal comisionPorDefecto) {
		this.comisionPorDefecto = comisionPorDefecto;
	}

	public Set<ComisionJefatura> getComisiones() {
		return this.comisiones;
	}
}