package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class DocumentoBase implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String docId;
	protected String serie;
	protected Long numero;
	
	protected transient Date fecha;
	protected Date fecha2;
	protected String fechaStr;
	protected BigDecimal docTCF = BigDecimal.ZERO;
	protected BigDecimal docTCC = BigDecimal.ZERO;
	protected BigDecimal coeficienteImp = BigDecimal.ONE;
	protected Cliente cliente;
	protected Proveedor proveedor;
	protected Moneda moneda;
	protected String razonSocial;
	protected String direccion;
	protected String tipoDoc = "R";

	protected String rut;
	protected String telefono;
	
	protected String docVinculado;
	protected String conciliado;

	protected Comprobante comprobante;
	protected String notas;
	protected Date registroFecha;
	protected Date registroHora;
	protected BigDecimal total;

	
	private byte[] docBlob;
	private String docBlobExt;
	

	public DocumentoBase() {
		this.docTCF = BigDecimal.ZERO;
		this.docTCC = BigDecimal.ZERO;
		this.coeficienteImp = BigDecimal.ONE;
	}

	public boolean isDatosClienteModificados() {
		return (!stringEquals(this.razonSocial, this.cliente.getContacto().getCtoRSocial())) || (!stringEquals(this.telefono, this.cliente.getContacto().getCtoTelefono()))
				|| (!stringEquals(this.direccion, this.cliente.getContacto().getCtoDireccion())) || (!stringEquals(this.rut, this.cliente.getContacto().getCtoRUT()));
	}

	private static boolean stringEquals(String a, String b) {
		if ((a == null) && (b == null))
			return true;
		if ((a == null) || (b == null)) {
			return false;
		}
		return a.equals(b);
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

	public SerieNumero getSerieNumero() {
		if (isTieneSerieNumero()) {
			return new SerieNumero(this.serie, this.numero);
		}
		return null;
	}

	public boolean isTieneSerieNumero() {
		return this.numero != null; // alcanza con mirar la presencia de un numero. Parece que hay Numeradores con serie null.
	}

	
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
		
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
		fechaStr = dt1.format(fecha);
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRut() {
		return this.rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}
	
	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Comprobante getComprobante() {
		return this.comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public String getNotas() {
		return this.notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getDocId() {
		return this.docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public Date getRegistroFecha() {
		return this.registroFecha;
	}

	public void setRegistroFecha(Date registroFecha) {
		this.registroFecha = registroFecha;
	}

	public Date getRegistroHora() {
		return this.registroHora;
	}

	public void setRegistroHora(Date registroHora) {
		this.registroHora = registroHora;
	}

	public Date getFecha2() {
		return fecha2;
	}

	public void setFecha2(Date fecha2) {
		this.fecha2 = fecha2;
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public String getFechaStr() {
		if (fecha != null) {
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			fechaStr = dt1.format(fecha);
		}
		return fechaStr;
	}

	public void setFechaStr(String fechaStr) {
		this.fechaStr = fechaStr;
		
		try {
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			fecha = dt1.parse(fechaStr);			
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	public BigDecimal getCoeficienteImp() {
		return coeficienteImp;
	}

	public void setCoeficienteImp(BigDecimal coeficienteImp) {
		this.coeficienteImp = coeficienteImp;
	}

	public String getTipoDoc() {
		return tipoDoc;
	}

	public void setTipoDoc(String tipoDoc) {
		this.tipoDoc = tipoDoc;
	}

	public byte[] getDocBlob() {
		return docBlob;
	}

	public void setDocBlob(byte[] docBlob) {
		this.docBlob = docBlob;
	}

	public String getDocBlobExt() {
		return docBlobExt;
	}

	public void setDocBlobExt(String docBlobExt) {
		this.docBlobExt = docBlobExt;
	}

	public BigDecimal getDocTCC() {
		return docTCC;
	}

	public void setDocTCC(BigDecimal docTCC) {
		this.docTCC = docTCC;
	}
	
	public BigDecimal getDocTCF() {
		return this.docTCF;
	}

	public void setDocTCF(BigDecimal docTCF) {
		this.docTCF = docTCF;
	}

	public String getDocVinculado() {
		return docVinculado;
	}
	
	public void setDocVinculado(String docVinculado) {
		this.docVinculado = docVinculado;
	}
	
	public String getConciliado() {
		return conciliado;
	}
	
	public void setConciliado(String conciliado) {
		this.conciliado = conciliado;
	}
	
	public abstract BigDecimal getTotal();

	public abstract void setTotal(BigDecimal total);

}