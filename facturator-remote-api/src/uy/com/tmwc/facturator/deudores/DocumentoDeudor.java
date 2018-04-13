package uy.com.tmwc.facturator.deudores;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;

import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.entity.Cliente;

@SuppressWarnings("serial")
public class DocumentoDeudor implements Serializable {
	
	private String docId;
	private String fecha;
	private Cliente deudor;
	private CodigoNombre comprobante;
	private long numero;
	private Collection<ParticipacionVendedorDeuda> vendedores;
	private CodigoNombre planPago;
	private CodigoNombre moneda;
	private BigDecimal facturado;
	private BigDecimal cancelado;
	private BigDecimal adeudado;
	private BigDecimal descuento;
	private BigDecimal adeudadoNeto;
	private boolean tieneCuotaVencida;

	public DocumentoDeudor() {
	}
	
	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public Cliente getDeudor() {
		return this.deudor;
	}

	public void setDeudor(Cliente deudor) {
		this.deudor = deudor;
	}

	public CodigoNombre getComprobante() {
		return this.comprobante;
	}

	public void setComprobante(CodigoNombre comprobante) {
		this.comprobante = comprobante;
	}

	public long getNumero() {
		return this.numero;
	}

	public void setNumero(long numero) {
		this.numero = numero;
	}

	public CodigoNombre getPlanPago() {
		return this.planPago;
	}

	public void setPlanPago(CodigoNombre planPago) {
		this.planPago = planPago;
	}

	public CodigoNombre getMoneda() {
		return this.moneda;
	}

	public void setMoneda(CodigoNombre moneda) {
		this.moneda = moneda;
	}

	public BigDecimal getFacturado() {
		return this.facturado;
	}

	public void setFacturado(BigDecimal facturado) {
		this.facturado = facturado;
	}

	public BigDecimal getCancelado() {
		return this.cancelado;
	}

	public void setCancelado(BigDecimal cancelado) {
		this.cancelado = cancelado;
	}

	public BigDecimal getDescuento() {
		return this.descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public BigDecimal getAdeudadoNeto() {
		return this.adeudadoNeto;
	}

	public void setAdeudadoNeto(BigDecimal adeudadoNeto) {
		this.adeudadoNeto = adeudadoNeto;
	}

	public BigDecimal getAdeudado() {
		return adeudado;
	}

	public void setAdeudado(BigDecimal adeudado) {
		this.adeudado = adeudado;
	}

	public Collection<ParticipacionVendedorDeuda> getVendedores() {
		return vendedores;
	}

	public void setVendedores(Collection<ParticipacionVendedorDeuda> vendedores) {
		this.vendedores = vendedores;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public boolean isTieneCuotaVencida() {
		return tieneCuotaVencida;
	}

	public void setTieneCuotaVencida(boolean tieneCuotaVencida) {
		this.tieneCuotaVencida = tieneCuotaVencida;
	}
}