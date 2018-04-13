package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;


public class StockActual { 
	private static final long serialVersionUID = 1L;
	
	private String empId;

	private short depIdSA;

	private String artIdSA;

	private String loteIdSA;

    private java.util.Date loteVenceSA;

	private Articulo articulo;
	
	private Deposito deposito;
	
	private BigDecimal SAcantidad;


	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
	}

	public BigDecimal getSAcantidad() {
		return SAcantidad;
	}

	public void setSAcantidad(BigDecimal sAcantidad) {
		SAcantidad = sAcantidad;
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public short getDepIdSA() {
		return depIdSA;
	}

	public void setDepIdSA(short depIdSA) {
		this.depIdSA = depIdSA;
	}

	public String getArtIdSA() {
		return artIdSA;
	}

	public void setArtIdSA(String artIdSA) {
		this.artIdSA = artIdSA;
	}

	public String getLoteIdSA() {
		return loteIdSA;
	}

	public void setLoteIdSA(String loteIdSA) {
		this.loteIdSA = loteIdSA;
	}

	public java.util.Date getLoteVenceSA() {
		return loteVenceSA;
	}

	public void setLoteVenceSA(java.util.Date loteVenceSA) {
		this.loteVenceSA = loteVenceSA;
	}
	
}
