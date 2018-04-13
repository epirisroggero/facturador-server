package uy.com.tmwc.facturator.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;

public class DocumentoDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String docId;
	private String serie;

	private Long numero;
	private String usuarioId;
	private String autorizadoPor;
	private String emitidoPor;
	private String fecha;

	private CodigoNombre cliente;
	private CodigoNombre moneda;
	private CodigoNombre comprobante;

	private String registroFecha;
	private String registroHora;
	
	private String caeNombre;

	private String razonSocial;

	private BigDecimal costo;
	private BigDecimal subtotal;
	private BigDecimal total;
	private BigDecimal saldo;
	private BigDecimal iva;

	private boolean emitido;
	private boolean pendiente;

	private BigDecimal tipoComprobante;

	public DocumentoDTO() {
	}

	public DocumentoDTO(Object docId, String serie, Number numero, Object comprobanteCodigo, String comprobanteNombre, Object registroHora, Object registroFecha, Number usuarioId,
			String autorizadoPor, String emitidoPor) {

		this.docId = docId.toString();
		this.serie = serie;
		this.numero = (numero != null ? Long.valueOf(numero.longValue()) : null);
		this.comprobante = (comprobanteCodigo != null ? new CodigoNombre(comprobanteCodigo.toString(), comprobanteNombre) : null);
		this.registroFecha = registroFecha != null ? DateFormatUtils.format((Date) registroFecha, "dd-MM-yyyy") : null;
		this.registroHora = registroHora != null ? registroHora.toString() : null;
		this.usuarioId = usuarioId.toString();
		this.autorizadoPor = autorizadoPor;
		this.emitidoPor = emitidoPor;
	}

	public DocumentoDTO(Object docId, String serie, Number numero, Date fecha, String caeNom, Object clienteCodigo, String clienteNombre, Object monedaCodigo, String monedaNombre, Object comprobanteCodigo,
			String comprobanteNombre, BigDecimal subtotal, BigDecimal iva, BigDecimal total, BigDecimal saldo, String emitido, String pendiente, BigDecimal tipoComprobante, Object registroHora, Object registroFecha) {

		this(docId, serie, numero, fecha, caeNom, clienteCodigo, clienteNombre, null, monedaCodigo, monedaNombre, comprobanteCodigo, comprobanteNombre, null, subtotal, iva, total, saldo, emitido, pendiente,
				tipoComprobante, registroHora, registroFecha);
	}

	public DocumentoDTO(Object docId, String serie, Number numero, Date fecha, String caeNom, Object clienteCodigo, String clienteNombre, Object clienteRut, Object monedaCodigo, String monedaNombre,
			Object comprobanteCodigo, String comprobanteNombre, BigDecimal subtotal, BigDecimal iva, BigDecimal total, BigDecimal saldo, String emitido, String pendiente, BigDecimal tipoComprobante) {
		this(docId, serie, numero, fecha, caeNom, clienteCodigo, clienteNombre, clienteRut, monedaCodigo, monedaNombre, comprobanteCodigo, comprobanteNombre, null, subtotal, iva, total, saldo, emitido, pendiente,
				tipoComprobante, null, null);
	}

	public DocumentoDTO(Object docId, String serie, Number numero, Date fecha, String caeNom, Object clienteCodigo, String clienteNombre, Object clienteRut, Object monedaCodigo, String monedaNombre,
			Object comprobanteCodigo, String comprobanteNombre, BigDecimal costo, BigDecimal subtotal, BigDecimal iva, BigDecimal total, BigDecimal saldo, String emitido, String pendiente, BigDecimal tipoComprobante) {
		this(docId, serie, numero, fecha, caeNom, clienteCodigo, clienteNombre, clienteRut, monedaCodigo, monedaNombre, comprobanteCodigo, comprobanteNombre, costo, subtotal, iva, total, saldo, emitido, pendiente,
				tipoComprobante, null, null);
	}

	public DocumentoDTO(Object docId, String serie, Number numero, Date fecha, String caeNom, Object clienteCodigo, String clienteNombre, Object clienteRut, Object monedaCodigo, String monedaNombre,
			Object comprobanteCodigo, String comprobanteNombre, BigDecimal costo, BigDecimal subtotal, BigDecimal iva, BigDecimal total, BigDecimal saldo, String emitido, String pendiente, BigDecimal tipoComprobante,
			Object registroHora, Object registroFecha) {

		this.docId = docId.toString();
		this.serie = serie;
		this.numero = (numero != null ? Long.valueOf(numero.longValue()) : null);
		this.caeNombre = caeNom;
		this.cliente = (clienteCodigo != null ? new CodigoNombre(clienteCodigo.toString(), clienteNombre) : null);
		this.razonSocial = clienteRut != null ? clienteRut.toString() : null;
		this.moneda = (monedaCodigo != null ? new CodigoNombre(monedaCodigo.toString(), monedaNombre) : null);
		this.comprobante = (comprobanteCodigo != null ? new CodigoNombre(comprobanteCodigo.toString(), comprobanteNombre) : null);
		this.costo = costo;
		this.saldo = saldo;
		this.subtotal = subtotal;
		this.iva = iva;
		this.total = total;
		this.emitido = emitido.equals("S");
		this.pendiente = pendiente == null || pendiente.equals("S");
		this.tipoComprobante = tipoComprobante;
		this.registroFecha = registroFecha != null ? DateFormatUtils.format((Date) registroFecha, "dd-MM-yyyy") : null;
		this.registroHora = registroHora != null ? registroHora.toString() : null;
		this.fecha = fecha != null ? DateFormatUtils.format((Date) fecha, "dd-MM-yyyy") : null;

	}

	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Long getNumero() {
		return this.numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}

	public CodigoNombre getCliente() {
		return this.cliente;
	}

	public void setCliente(CodigoNombre cliente) {
		this.cliente = cliente;
	}

	public CodigoNombre getMoneda() {
		return this.moneda;
	}

	public void setMoneda(CodigoNombre moneda) {
		this.moneda = moneda;
	}

	public CodigoNombre getComprobante() {
		return this.comprobante;
	}

	public void setComprobante(CodigoNombre comprobante) {
		this.comprobante = comprobante;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public boolean isEmitido() {
		return emitido;
	}

	public void setEmitido(boolean emitido) {
		this.emitido = emitido;
	}

	public boolean isPendiente() {
		return pendiente;
	}

	public void setPendiente(boolean pendiente) {
		this.pendiente = pendiente;
	}

	public BigDecimal getTipoComprobante() {
		return tipoComprobante;
	}

	public void setTipoComprobante(BigDecimal tipoComprobante) {
		this.tipoComprobante = tipoComprobante;
	}

	public BigDecimal getCosto() {
		return costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getIva() {
		return iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getRegistroHora() {
		return registroHora;
	}

	public void setRegistroHora(String registroHora) {
		this.registroHora = registroHora;
	}

	public String getRegistroFecha() {
		return registroFecha;
	}

	public void setRegistroFecha(String registroFecha) {
		this.registroFecha = registroFecha;
	}

	public String getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(String usuarioId) {
		this.usuarioId = usuarioId;
	}

	public String getAutorizadoPor() {
		return autorizadoPor;
	}

	public void setAutorizadoPor(String autorizadoPor) {
		this.autorizadoPor = autorizadoPor;
	}

	public String getEmitidoPor() {
		return emitidoPor;
	}

	public void setEmitidoPor(String emitidoPor) {
		this.emitidoPor = emitidoPor;
	}

	public String getFecha() {
		return fecha;
	}

	public void setFecha(String fecha) {
		this.fecha = fecha;
	}

	public String getCaeNombre() {
		return caeNombre;
	}

	public void setCaeNombre(String caeNombre) {
		this.caeNombre = caeNombre;
	}

	public BigDecimal getSaldo() {
		return saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

}