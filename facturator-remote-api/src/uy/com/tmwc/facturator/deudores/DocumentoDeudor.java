package uy.com.tmwc.facturator.deudores;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;

import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.entity.Cliente;
import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.entity.Documento;

@SuppressWarnings("serial")
public class DocumentoDeudor implements Serializable {
	
	private String docId;
	private String fecha;
	private Date date;
	private Date fechaVencimiento;

	private Cliente deudor;
	private Comprobante comprobante;
	private long numero;
	private String serie;

	private Collection<ParticipacionVendedorDeuda> vendedores;
	private CodigoNombre planPago;
	private CodigoNombre moneda;
	private BigDecimal facturado;
	private BigDecimal cancelado;
	private BigDecimal adeudado;
	private BigDecimal descuento;
	private BigDecimal adeudadoNeto;
	private boolean tieneCuotaVencida;
	private int diasRetraso;	

	private Documento documento;


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

	public Comprobante getComprobante() {
		return this.comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
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
	
	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public boolean isTieneCuotaVencida() {
		return tieneCuotaVencida;
	}

	public void setTieneCuotaVencida(boolean tieneCuotaVencida) {
		this.tieneCuotaVencida = tieneCuotaVencida;
	}
	
	public int getDiasRetraso() {
		return diasRetraso;
	}

	public void setDiasRetraso(int diasRetraso) {
		this.diasRetraso = diasRetraso;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getSerie() {
		return serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}
	
	public Date getFechaVencimiento() {
		return fechaVencimiento;
	}

	public void setFechaVencimiento(Date fechaVencimiento) {
		this.fechaVencimiento = fechaVencimiento;
	}



}