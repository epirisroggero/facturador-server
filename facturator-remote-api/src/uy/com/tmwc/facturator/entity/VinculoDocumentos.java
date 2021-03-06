package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import uy.com.tmwc.facturator.utils.Maths;

public class VinculoDocumentos implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int docIdVin1;

	private int docIdVin2;
	
	private Documento factura; 
	
	private Documento recibo;
	
	private BigDecimal monto; 
	
	private BigDecimal descuentoPorc = BigDecimal.ZERO;

	private BigDecimal neto = BigDecimal.ZERO;
	
	private BigDecimal vinRtaFin = BigDecimal.ZERO;

	public BigDecimal getMontoDescuentoEsperado() {
		return this.factura.calcularMontoDescuentoEsperado(this.monto);
	}

	public BigDecimal getMontoDescuentoReal() {
		BigDecimal porcDto = this.recibo.getDescuentosPorc();
		if (porcDto == null) {
			porcDto = BigDecimal.ZERO;
		}
		return Maths.calcularMontoDescuento(this.monto, porcDto);
	}
	
	public BigDecimal getMontoCobrado() {
		BigDecimal porcDto = this.recibo.getDescuentosPorc();
		if (porcDto == null) {
			porcDto = BigDecimal.ZERO;
		}
		return Maths.descontar(monto, porcDto);
	}
	

	public BigDecimal getRentaFinanciera() {
		BigDecimal montoDtoReal = getMontoDescuentoReal();
		BigDecimal rentaFinanciera = getMontoDescuentoEsperado().subtract(montoDtoReal);
		if (recibo.getComprobante().isAster()) {
			return rentaFinanciera.divide(new BigDecimal(.64), 2, RoundingMode.HALF_EVEN);
		} else {
			return rentaFinanciera;
		}
	}

	public BigDecimal getPorcentajeCancelacion() {
		return Maths.calcularPorcentaje(this.factura.getTotal(), this.monto);
	}
	
	public BigDecimal getMontoCancelado() {
		return this.monto.subtract(this.factura.getTotal().multiply(getPorcentajeCancelacion()).divide(Maths.ONE_HUNDRED, 2, RoundingMode.HALF_EVEN));
	}

	public BigDecimal getCuotaparteRentaComercial() {
		return Maths.calcularMontoDescuento(this.factura.getRentaNetaComercial(), getPorcentajeCancelacion());
	}

	public BigDecimal getCuotaparteRentaDistComercial() {
		return Maths.calcularMontoDescuento(this.factura.getRentaNetaDistComercial(), getPorcentajeCancelacion());
	}

	public BigDecimal getCuotaparteOperativos() {
		return Maths.calcularMontoDescuento(this.factura.getCostoOperativo(), getPorcentajeCancelacion());
	}

	public Documento getFactura() {
		return this.factura;
	}

	public void setFactura(Documento factura) {
		this.factura = factura;
	}

	public Documento getRecibo() {
		return this.recibo;
	}

	public void setRecibo(Documento recibo) {
		this.recibo = recibo;
	}

	public BigDecimal getMonto() {
		return this.monto;
	}

	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

	public int getDocIdVin1() {
		return docIdVin1;
	}

	public void setDocIdVin1(int docIdVin1) {
		this.docIdVin1 = docIdVin1;
	}

	public int getDocIdVin2() {
		return docIdVin2;
	}

	public void setDocIdVin2(int docIdVin2) {
		this.docIdVin2 = docIdVin2;
	}

	public BigDecimal getDescuentoPorc() {
		return descuentoPorc;
	}

	public void setDescuentoPorc(BigDecimal descuentoPorc) {
		this.descuentoPorc = descuentoPorc;
	}

	public BigDecimal getNeto() {
		return neto;
	}

	public void setNeto(BigDecimal neto) {
		this.neto = neto;
	}

	public BigDecimal getVinRtaFin() {
		return vinRtaFin;
	}

	public void setVinRtaFin(BigDecimal vinRtaFin) {
		this.vinRtaFin = vinRtaFin;
	}
}