package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lfx_cotizaciones")
public class CotizacionesMonedas extends PersistentEntity<CotizacionesMonedasPK> implements Serializable, HasId<CotizacionesMonedasPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CotizacionesMonedasPK id;

	@Column(name = "dolarComp", precision = 10, scale = 7)
	private BigDecimal dolarCompra;

	@Column(name = "dolarVta", precision = 10, scale = 7)
	private BigDecimal dolarVenta;
	
	@Column(name = "euroComp", precision = 10, scale = 7)
	private BigDecimal euroCompra;

	@Column(name = "euroVta", precision = 10, scale = 7)
	private BigDecimal euroVenta;

	public CotizacionesMonedasPK getId() {
		return this.id;
	}

	public void setId(CotizacionesMonedasPK id) {
		this.id = id;
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
			id = new CotizacionesMonedasPK();
		}
	}

	public BigDecimal getDolarCompra() {
		return dolarCompra;
	}

	public void setDolarCompra(BigDecimal dolarCompra) {
		this.dolarCompra = dolarCompra;
	}

	public BigDecimal getDolarVenta() {
		return dolarVenta;
	}

	public void setDolarVenta(BigDecimal dolarVenta) {
		this.dolarVenta = dolarVenta;
	}

	public BigDecimal getEuroCompra() {
		return euroCompra;
	}

	public void setEuroCompra(BigDecimal euroCompra) {
		this.euroCompra = euroCompra;
	}

	public BigDecimal getEuroVenta() {
		return euroVenta;
	}

	public void setEuroVenta(BigDecimal euroVenta) {
		this.euroVenta = euroVenta;
	}
}