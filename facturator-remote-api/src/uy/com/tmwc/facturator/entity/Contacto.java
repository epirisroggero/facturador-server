package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.util.Date;

public class Contacto extends CodigoNombreEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String ctoEmail1;

	private String ctoEmail2;
	
	private byte[] ctoBlob;
	
	private String ctoBlobExt;
	
	private String ctoDireccion;
	
	private String ctoTelefono;
	
	private String ctoCelular;
	
	private String ctoFax;
	
	private String girIdCto;
		
	private String deptoIdCto;
	
	private String ctoNombreCompleto;
	
	private String ctoNom;
	
	private String ctoRSocial;
	
	private String ctoRUT;

	private String ctoCliente;

	private String ctoProveedor;

	private Date ctoAlta;

	private String ctoActivo;

	private String ctoLocalidad;
	
	private String paisIdCto;
	
	private String ctoDocumentoTipo;
	
	private String zonaIdCto;
	
	private String ctoNotas;
	
	private String ctoWeb;

	private String oriCtoIdCto;

	private String gruCtoId;

	private String ctoPostal;

	private String ctoDocumento; 

	private Short usuIdCto; 
	
	private String ctoNotasEfactura;
	
	private String ctoDocumentoSigla;

	
	public String getCtoNombreCompleto() {
		return ctoNombreCompleto;
	}

	public void setCtoNombreCompleto(String ctoNombreCompleto) {
		this.ctoNombreCompleto = ctoNombreCompleto;
	}

	public String getCtoRSocial() {
		return ctoRSocial;
	}

	public void setCtoRSocial(String ctoRSocial) {
		this.ctoRSocial = ctoRSocial;
	}

	public String getCtoRUT() {
		return ctoRUT;
	}

	public void setCtoRUT(String ctoRUT) {
		this.ctoRUT = ctoRUT;
	}

	public String getCtoProveedor() {
		return ctoProveedor;
	}

	public void setCtoProveedor(String ctoProveedor) {
		this.ctoProveedor = ctoProveedor;
	}

	public Date getCtoAlta() {
		return ctoAlta;
	}

	public void setCtoAlta(Date ctoAlta) {
		this.ctoAlta = ctoAlta;
	}

	public String getCtoActivo() {
		return ctoActivo;
	}

	public void setCtoActivo(String ctoActivo) {
		this.ctoActivo = ctoActivo;
	}

	public String getCtoLocalidad() {
		return ctoLocalidad;
	}

	public void setCtoLocalidad(String ctoLocalidad) {
		this.ctoLocalidad = ctoLocalidad;
	}

	public String getPaisIdCto() {
		return paisIdCto;
	}

	public void setPaisIdCto(String paisIdCto) {
		this.paisIdCto = paisIdCto;
	}

	public String getCtoDocumentoTipo() {
		return ctoDocumentoTipo;
	}

	public void setCtoDocumentoTipo(String ctoDocumentoTipo) {
		this.ctoDocumentoTipo = ctoDocumentoTipo;
	}

	public String getDeptoIdCto() {
		return deptoIdCto;
	}

	public void setDeptoIdCto(String deptoIdCto) {
		this.deptoIdCto = deptoIdCto;
	}

	public String getCtoDireccion() {
		return ctoDireccion;
	}

	public void setCtoDireccion(String ctoDireccion) {
		this.ctoDireccion = ctoDireccion;
	}

	public String getCtoCelular() {
		return ctoCelular;
	}

	public void setCtoCelular(String ctoCelular) {
		this.ctoCelular = ctoCelular;
	}

	public String getCtoTelefono() {
		return ctoTelefono;
	}

	public void setCtoTelefono(String ctoTelefono) {
		this.ctoTelefono = ctoTelefono;
	}

	public String getCtoCliente() {
		return ctoCliente;
	}

	public void setCtoCliente(String ctoCliente) {
		this.ctoCliente = ctoCliente;
	}

	public String getCtoEmail1() {
		return ctoEmail1;
	}

	public void setCtoEmail1(String ctoEmail1) {
		this.ctoEmail1 = ctoEmail1;
	}

	public String getCtoEmail2() {
		return ctoEmail2;
	}

	public void setCtoEmail2(String ctoEmail2) {
		this.ctoEmail2 = ctoEmail2;
	}

	public byte[] getCtoBlob() {
		return ctoBlob;
	}

	public void setCtoBlob(byte[] ctoBlob) {
		this.ctoBlob = ctoBlob;
	}

	public Contacto() {
		super();
	}

	public Contacto(String codigo, String nombre) {
		super(codigo, nombre);
	}

	public String getCtoBlobExt() {
		return ctoBlobExt;
	}

	public void setCtoBlobExt(String ctoBlobExt) {
		this.ctoBlobExt = ctoBlobExt;
	}

	public String getCtoFax() {
		return ctoFax;
	}

	public void setCtoFax(String ctoFax) {
		this.ctoFax = ctoFax;
	}

	public String getGirIdCto() {
		return girIdCto;
	}

	public void setGirIdCto(String girIdCto) {
		this.girIdCto = girIdCto;
	}

	public String getZonaIdCto() {
		return zonaIdCto;
	}

	public void setZonaIdCto(String zonaIdCto) {
		this.zonaIdCto = zonaIdCto;
	}
	
	public int hashCode() {
		int result = super.hashCode();

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj)) {
			return false;
		}
		return (obj instanceof Contacto);
	}

	public String getCtoNom() {
		return ctoNom;
	}

	public void setCtoNom(String ctoNom) {
		this.ctoNom = ctoNom;
	}

	public String getCtoNotas() {
		return ctoNotas;
	}

	public void setCtoNotas(String ctoNotas) {
		this.ctoNotas = ctoNotas;
	}

	public String getCtoWeb() {
		return ctoWeb;
	}

	public void setCtoWeb(String ctoWeb) {
		this.ctoWeb = ctoWeb;
	}

	public String getOriCtoIdCto() {
		return oriCtoIdCto;
	}

	public void setOriCtoIdCto(String oriCtoIdCto) {
		this.oriCtoIdCto = oriCtoIdCto;
	}

	public String getGruCtoId() {
		return gruCtoId;
	}

	public void setGruCtoId(String gruCtoId) {
		this.gruCtoId = gruCtoId;
	}

	public String getCtoPostal() {
		return ctoPostal;
	}

	public void setCtoPostal(String ctoPostal) {
		this.ctoPostal = ctoPostal;
	}

	public String getCtoDocumento() {
		return ctoDocumento;
	}

	public void setCtoDocumento(String ctoDocumento) {
		this.ctoDocumento = ctoDocumento;
	}

	public Short getUsuIdCto() {
		return usuIdCto;
	}

	public void setUsuIdCto(Short usuIdCto) {
		this.usuIdCto = usuIdCto;
	}

	public String getCtoNotasEfactura() {
		return ctoNotasEfactura;
	}

	public void setCtoNotasEfactura(String ctoNotasEfactura) {
		this.ctoNotasEfactura = ctoNotasEfactura;
	}

	public String getCtoDocumentoSigla() {
		return ctoDocumentoSigla;
	}

	public void setCtoDocumentoSigla(String ctoDocumentoSigla) {
		this.ctoDocumentoSigla = ctoDocumentoSigla;
	}


}
