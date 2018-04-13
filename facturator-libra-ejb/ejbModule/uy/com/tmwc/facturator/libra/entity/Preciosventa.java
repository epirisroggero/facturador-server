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

import uy.com.tmwc.facturator.libra.util.BooleanDozerConverter;
import uy.com.tmwc.facturator.utils.Maths;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "preciosventa")
@CatalogEntity(abreviated = true, prefix = "precioVenta")
public class Preciosventa extends PersistentEntity<PreciosventaPK> implements Serializable, HasId<PreciosventaPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PreciosventaPK id;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "PrecioBaseId", referencedColumnName = "PrecioBaseId", insertable = false, updatable = false) })
	private Preciosbase precioBase;

	@SuppressWarnings("unused")
	@Column(name = "PrecioBaseId")
	private String precioBaseId;

	@Column(name = "PrecioVentaAbrevia")
	private String precioVentaAbrevia;

	@Column(name = "PrecioVentaNom")
	private String precioVentaNom;

	@Column(name = "PrecioVentaPorcentaje")
	private BigDecimal precioVentaPorcentaje;

	@Column(name = "PrecioVentaUtilidad")
	private String precioVentaUtilidad;

	@Temporal(TemporalType.DATE)
	@Column(name = "PrecioVentaVigencia")
	private Date precioVentaVigencia;

	public void setPreciosBase(Preciosbase preciosBase) {
		this.precioBase = preciosBase;
		this.precioBaseId = (preciosBase == null ? null : preciosBase.getId().getPrecioBaseId());
	}

	public Preciosbase getPreciosBase() {
		return this.precioBase;
	}

	public PreciosventaPK getId() {
		return this.id;
	}

	public void setId(PreciosventaPK id) {
		this.id = id;
	}

	public String getPrecioVentaAbrevia() {
		return this.precioVentaAbrevia;
	}

	public void setPrecioVentaAbrevia(String precioVentaAbrevia) {
		this.precioVentaAbrevia = precioVentaAbrevia;
	}

	public String getPrecioVentaNom() {
		return this.precioVentaNom;
	}

	public void setPrecioVentaNom(String precioVentaNom) {
		this.precioVentaNom = precioVentaNom;
	}

	public BigDecimal getPrecioVentaPorcentaje() {
		return this.precioVentaPorcentaje;
	}

	public void setPrecioVentaPorcentaje(BigDecimal precioVentaPorcentaje) {
		this.precioVentaPorcentaje = precioVentaPorcentaje;
	}

	public boolean getSumarUtilidadArticulo() {
		return BooleanDozerConverter.toBooleanValue(precioVentaUtilidad);
	}

	public void setSumarUtilidadArticulo(boolean precioVentaUtilidad) {
		this.precioVentaUtilidad = BooleanDozerConverter.toString(precioVentaUtilidad);
	}

	public Date getPrecioVentaVigencia() {
		return this.precioVentaVigencia;
	}

	public void setPrecioVentaVigencia(Date precioVentaVigencia) {
		this.precioVentaVigencia = precioVentaVigencia;
	}

	public static BigDecimal calcularPrecio(BigDecimal precioBase, boolean sumarUtilidadArticulo, BigDecimal costoUtilidad,
			BigDecimal precioVentaPorcentaje) {
		BigDecimal incremento = BigDecimal.ONE;
		if (sumarUtilidadArticulo && costoUtilidad != null) {
			BigDecimal utilidadArticulo = costoUtilidad;
			incremento = incremento.add(utilidadArticulo.divide(Maths.ONE_HUNDRED));
		}
		if (precioVentaPorcentaje != null) {
			incremento = incremento.add(precioVentaPorcentaje.divide(Maths.ONE_HUNDRED));
		}
		BigDecimal precio = precioBase.multiply(incremento);
		return precio;
	}

	public String getCodigo() {
		return id != null ? String.valueOf(id.getPrecioVentaId()) : null;
	}
}