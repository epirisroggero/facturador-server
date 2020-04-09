package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import uy.com.tmwc.facturator.dto.ICodigoNombre;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "monedas")
@CatalogEntity
public class Moneda extends PersistentEntity<MonedaPK> implements Serializable, ICodigoNombre, HasId<MonedaPK> {
	private static final long serialVersionUID = 1L;

	public static final String CODIGO_MONEDA_PESOS = uy.com.tmwc.facturator.entity.Moneda.CODIGO_MONEDA_PESOS;
	public static final String CODIGO_MONEDA_DOLAR = uy.com.tmwc.facturator.entity.Moneda.CODIGO_MONEDA_DOLAR;
	public static final String CODIGO_MONEDA_EUROS = uy.com.tmwc.facturator.entity.Moneda.CODIGO_MONEDA_EUROS;
	public static final String CODIGO_MONEDA_PESOS_ASTER = uy.com.tmwc.facturator.entity.Moneda.CODIGO_MONEDA_PESOS_ASTER;
	public static final String CODIGO_MONEDA_DOLAR_ASTER = uy.com.tmwc.facturator.entity.Moneda.CODIGO_MONEDA_DOLAR_ASTER;
	public static final String CODIGO_MONEDA_EUROS_ASTER = uy.com.tmwc.facturator.entity.Moneda.CODIGO_MONEDA_EUROS_ASTER;

	@EmbeddedId
	private MonedaPK id;

	@SuppressWarnings("unused")
	@Column(name = "MndId", insertable = false, updatable = false)
	private Short codigo;

	@Column(name = "MndAbrevia")
	private String mndAbrevia;

	@Column(name = "MndInteres")
	private BigDecimal mndInteres;

	@Column(name = "MndNom")
	private String nombre;

	@Column(name = "MndRedondeo")
	private Short redondeo;

	@Column(name = "MndRubDCganancias")
	private String mndRubDCganancias;

	@Column(name = "MndRubDCperdidas")
	private String MndRubDCperdidas;

	@Column(name = "MndSim")
	private String simbolo;

	@Column(name = "MndTCmax")
	private BigDecimal mndTCmax;

	@Column(name = "MndTCmin")
	private BigDecimal mndTCmin;

	public MonedaPK getId() {
		return this.id;
	}

	public void setId(MonedaPK id) {
		this.id = id;
	}

	public String getMndAbrevia() {
		return this.mndAbrevia;
	}

	public void setMndAbrevia(String mndAbrevia) {
		this.mndAbrevia = mndAbrevia;
	}

	public BigDecimal getMndInteres() {
		return this.mndInteres;
	}

	public void setMndInteres(BigDecimal mndInteres) {
		this.mndInteres = mndInteres;
	}

	public short getRedondeo() {
		return this.redondeo != null ? redondeo : 0; // en libra, redondeo=null
														// es 'Sin decimales'.
	}

	public void setRedondeo(short redondeo) {
		this.redondeo = redondeo;
	}

	public String getMndRubDCganancias() {
		return this.mndRubDCganancias;
	}

	public void setMndRubDCganancias(String mndRubDCganancias) {
		this.mndRubDCganancias = mndRubDCganancias;
	}

	public String getMndRubDCperdidas() {
		return this.MndRubDCperdidas;
	}

	public void setMndRubDCperdidas(String MndRubDCperdidas) {
		this.MndRubDCperdidas = MndRubDCperdidas;
	}

	public String getSimbolo() {
		return this.simbolo;
	}

	public void setSimbolo(String mndSim) {
		this.simbolo = mndSim;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public BigDecimal getMndTCmax() {
		return this.mndTCmax;
	}

	public void setMndTCmax(BigDecimal mndTCmax) {
		this.mndTCmax = mndTCmax;
	}

	public BigDecimal getMndTCmin() {
		return this.mndTCmin;
	}

	public void setMndTCmin(BigDecimal mndTCmin) {
		this.mndTCmin = mndTCmin;
	}

	public String getCodigo() {
		return String.valueOf(this.id.getMndId());
	}

	public void setCodigo(String value) {
		try {
			this.codigo = Short.valueOf(Short.parseShort(value));
		} catch (NumberFormatException localNumberFormatException) {
		}
	}
}