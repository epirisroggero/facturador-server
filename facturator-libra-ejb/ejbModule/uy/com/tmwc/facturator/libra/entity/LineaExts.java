package uy.com.tmwc.facturator.libra.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "lfx_lineasdocumento")
public class LineaExts {

	@EmbeddedId
	private LineaPK id;

	@Column(name = "coeficienteImp")
	private BigDecimal coeficienteImp;

	@Column(name = "precioDistribuidor")
	private BigDecimal precioDistribuidor;

	@Column(name = "CtoId")
	private String contactoId;
	
	@Column(name = "DocRefId")
	private Integer docRefId;
	
	public void setId(LineaPK id) {
		this.id = id;
	}

	public LineaPK getId() {
		return id;
	}

	public void setPrecioDistribuidor(BigDecimal precioDistribuidor) {
		this.precioDistribuidor = precioDistribuidor;
	}

	public BigDecimal getPrecioDistribuidor() {
		return precioDistribuidor;
	}

	public BigDecimal getCoeficienteImp() {
		return coeficienteImp;
	}

	public void setCoeficienteImp(BigDecimal coeficienteImp) {
		this.coeficienteImp = coeficienteImp;
	}

	public String getContactoId() {
		return contactoId;
	}

	public void setContactoId(String contactoId) {
		this.contactoId = contactoId;
	}

	public Integer getDocRefId() {
		return docRefId;
	}

	public void setDocRefId(Integer docRefId) {
		this.docRefId = docRefId;
	}
}
