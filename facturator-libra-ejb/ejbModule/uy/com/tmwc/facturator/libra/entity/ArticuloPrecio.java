package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import uy.com.tmwc.facturator.libra.util.BooleanDozerConverter;

@Entity
@Table(name = "articulos4")
public class ArticuloPrecio extends PersistentEntity<ArticuloPrecioPK> implements Serializable, HasId<ArticuloPrecioPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ArticuloPrecioPK id;

	@Column(name = "ArtPrecio", precision = 10, scale = 5)
	private BigDecimal precio;

	@Column(name = "ArtPrecioIVA", precision = 10, scale = 5)
	private BigDecimal precioIVA;

	@Column(name = "MndIdPrecio")
	private short mndIdPrecio;

	@Column(name = "PrecioBaseConIVA")
	private String precioBaseConIVA;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "PrecioBaseId", referencedColumnName = "PrecioBaseId", insertable = false, updatable = false) })
	private Preciosbase precioBase;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "ArtId", referencedColumnName = "ArtId", insertable = false, updatable = false) })
	private Articulo articulo;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "MndIdPrecio", referencedColumnName = "MndId", insertable = false, updatable = false) })
	private Moneda moneda;

	public Preciosbase getPrecioBase() {
		return this.precioBase;
	}

	public Articulo getArticulo() {
		return this.articulo;
	}

	public ArticuloPrecioPK getId() {
		return this.id;
	}

	public void setId(ArticuloPrecioPK id) {
		this.id = id;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getPrecioIVA() {
		return this.precioIVA;
	}

	public void setPrecioIVA(BigDecimal precioIVA) {
		this.precioIVA = precioIVA;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}


	public short getMndIdPrecio() {
		return mndIdPrecio;
	}

	public void setMndIdPrecio(short mndIdPrecio) {
		this.mndIdPrecio = mndIdPrecio;
	}
	
	public boolean getPrecioBaseConIVA() {
		return BooleanDozerConverter.toBooleanValue(precioBaseConIVA);
	}

	public void setPrecioBaseConIVA(boolean precioBaseConIVA) {
		this.precioBaseConIVA = BooleanDozerConverter.toString(precioBaseConIVA);
	}


}