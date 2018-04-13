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
@Table(name = "articulos1")
public class ArticuloPartida extends PersistentEntity<ArticuloPartidaPK> implements Serializable, HasId<ArticuloPartidaPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ArticuloPartidaPK id;
	
	@Column(name = "PartidaDocId")
	private Integer partidaDocId;

	@Column(name = "MndIdPartida")
	private short mndIdPrecio;

	@Column(name = "PartidaCosto")
	private BigDecimal partidaCosto;

	@Temporal(TemporalType.DATE)
	@Column(name = "PartidaFecha")
	private Date partidaFecha;

	@Column(name = "PartidaConcepto")
	private String partidaConcepto;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "ArtId", referencedColumnName = "ArtId", insertable = false, updatable = false) })
	private Articulo articulo;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "MndIdPartida", referencedColumnName = "MndId", insertable = false, updatable = false) })
	private Moneda moneda;

	
	public ArticuloPartidaPK getId() {
		return id;
	}

	public void setId(ArticuloPartidaPK paramT) {
		this.id = paramT;
	}

	public Integer getPartidaDocId() {
		return partidaDocId;
	}

	public void setPartidaDocId(Integer partidaDocId) {
		this.partidaDocId = partidaDocId;
	}

	public short getMndIdPrecio() {
		return mndIdPrecio;
	}

	public void setMndIdPrecio(short mndIdPrecio) {
		this.mndIdPrecio = mndIdPrecio;
	}

	public BigDecimal getPartidaCosto() {
		return partidaCosto;
	}

	public void setPartidaCosto(BigDecimal partidaCosto) {
		this.partidaCosto = partidaCosto;
	}

	public Date getPartidaFecha() {
		return partidaFecha;
	}

	public void setPartidaFecha(Date partidaFecha) {
		this.partidaFecha = partidaFecha;
	}

	public String getPartidaConcepto() {
		return partidaConcepto;
	}

	public void setPartidaConcepto(String partidaConcepto) {
		this.partidaConcepto = partidaConcepto;
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}
	
}
