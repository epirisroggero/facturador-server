package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class DocumentoFormaPago implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int docId;
	
	private short numero;
	
	private Documento documento;
	
	private FormaPago formaPago;
	
	private Moneda moneda;
	
	private BigDecimal importe;
	
	private BigDecimal total;

	public DocumentoFormaPago() {
	}

	public DocumentoFormaPago(Documento documento) {
		this.documento = documento;
		this.numero = 1;
		this.formaPago = FormaPago.EFECTIVO;
		if (documento.getComprobante().isRecibo()) {
			this.importe = documento.getDocRecNeto();
			this.moneda = documento.getDocRecMda();
			this.total = documento.getTotal();
		} else {
			this.importe = documento.getTotal();
			this.moneda = documento.getMoneda();
			this.total = documento.getTotal();
			
		}
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public FormaPago getFormaPago() {
		return this.formaPago;
	}

	public void setFormaPago(FormaPago formaPago) {
		this.formaPago = formaPago;
	}

	public short getNumero() {
		return this.numero;
	}

	public void setNumero(short numero) {
		this.numero = numero;
	}

	public BigDecimal getImporte() {
		return this.importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public int hashCode() {
		int prime = 31;
		int result = 1;
		result = prime * result + (this.documento == null ? 0 : this.documento.hashCode());
		result = prime * result + (this.formaPago == null ? 0 : this.formaPago.hashCode());
		result = prime * result + this.numero;
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof DocumentoFormaPago))
			return false;
		DocumentoFormaPago other = (DocumentoFormaPago) obj;
		if (this.documento == null) {
			if (other.documento != null)
				return false;
		} else if (!this.documento.equals(other.documento))
			return false;
		if (this.formaPago == null) {
			if (other.formaPago != null)
				return false;
		} else if (!this.formaPago.equals(other.formaPago)) {
			return false;
		}
		return this.numero == other.numero;
	}

	public int getDocId() {
		return docId;
	}

	public void setDocId(int docId) {
		this.docId = docId;
	}
	
}