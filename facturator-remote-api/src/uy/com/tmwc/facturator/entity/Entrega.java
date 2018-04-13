package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;

public class Entrega extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;
	
	private String empId;
	
	private BigDecimal costo;
	
	private BigDecimal relevancia;

	public BigDecimal getCostoOperativo(BigDecimal valorUnitarioEntrega) {
		return this.relevancia.multiply(valorUnitarioEntrega);
	}

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getMonedaCosto() {
		return Moneda.CODIGO_MONEDA_DOLAR;
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getRelevancia() {
		return this.relevancia;
	}

	public void setRelevancia(BigDecimal relevancia) {
		this.relevancia = relevancia;
	}
}