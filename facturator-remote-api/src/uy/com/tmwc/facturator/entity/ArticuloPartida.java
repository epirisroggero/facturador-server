package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ArticuloPartida implements Serializable {

	private static final long serialVersionUID = 1L;

	private String artId;
	
	private Integer partidaId;
	
	private String partidaDocId;
	
	private String mndIdPartida;

	private BigDecimal partidaCantidad;

	private BigDecimal partidaCosto;
	
	private Date partidaFecha;

	private Articulo articulo;
	
	private Moneda moneda;
	
	public ArticuloPartida() {
		
	}

	public String getArtId() {
		return artId;
	}

	public void setArtId(String artId) {
		this.artId = artId;
	}

	public Integer getPartidaId() {
		return partidaId;
	}

	public void setPartidaId(Integer partidaId) {
		this.partidaId = partidaId;
	}

	public String getPartidaDocId() {
		return partidaDocId;
	}

	public void setPartidaDocId(String partidaDocId) {
		this.partidaDocId = partidaDocId;
	}

	public String getMndIdPartida() {
		return mndIdPartida;
	}

	public void setMndIdPartida(String mndIdPartida) {
		this.mndIdPartida = mndIdPartida;
	}

	public BigDecimal getPartidaCantidad() {
		return partidaCantidad;
	}

	public void setPartidaCantidad(BigDecimal partidaCantidad) {
		this.partidaCantidad = partidaCantidad;
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
