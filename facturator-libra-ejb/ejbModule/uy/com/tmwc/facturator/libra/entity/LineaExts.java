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

	@Column(name = "LinAfilador")
	private String afilador;
	
	@Column(name = "LinMarca")
	private String marca;

	@Column(name = "LinDiametro")
	private BigDecimal diametro;

	@Column(name = "LinRotos")
	private BigDecimal rotos;

	@Column(name = "LinCascados")
	private BigDecimal cascados;
	
	@Column(name = "LinDcto1")
	private BigDecimal dcto1;
	
	@Column(name = "LinDcto2")
	private BigDecimal dcto2;
	
	@Column(name = "LinDcto3")
	private BigDecimal dcto3;
	
	@Column(name = "LinDcto4")
	private BigDecimal dcto4;

	
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

	public String getAfilador() {
		return afilador;
	}

	public void setAfilador(String afilador) {
		this.afilador = afilador;
	}
	
	public BigDecimal getDiametro() {
		return diametro;
	}

	public void setDiametro(BigDecimal diametro) {
		this.diametro = diametro;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public BigDecimal getRotos() {
		return rotos;
	}

	public void setRotos(BigDecimal rotos) {
		this.rotos = rotos;
	}

	public BigDecimal getCascados() {
		return cascados;
	}

	public void setCascados(BigDecimal cascados) {
		this.cascados = cascados;
	}
	
	public BigDecimal getDcto1() {
		return dcto1;
	}

	public void setDcto1(BigDecimal dcto1) {
		this.dcto1 = dcto1;
	}

	public BigDecimal getDcto2() {
		return dcto2;
	}

	public void setDcto2(BigDecimal dcto2) {
		this.dcto2 = dcto2;
	}

	public BigDecimal getDcto3() {
		return dcto3;
	}

	public void setDcto3(BigDecimal dcto3) {
		this.dcto3 = dcto3;
	}

	public BigDecimal getDcto4() {
		return dcto4;
	}

	public void setDcto4(BigDecimal dcto4) {
		this.dcto4 = dcto4;
	}


}
