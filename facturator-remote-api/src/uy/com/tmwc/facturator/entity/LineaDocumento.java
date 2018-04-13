package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;

import uy.com.tmwc.facturator.utils.Maths;

public class LineaDocumento implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int numeroLinea;
	private BigDecimal cantidad = BigDecimal.ZERO;
	private Articulo articulo;
	
	private String concepto;
	private String notas;
	private String conceptoIdLin = "";

	private BigDecimal precio = BigDecimal.ZERO;
	private BigDecimal costo = BigDecimal.ZERO;
	private BigDecimal descuento = BigDecimal.ZERO;

	private Short depositoDestinoId = 0;
	private Short depositoOrigenId = 0;
	
	private Iva ivaArticulo;
	private Short ivaId;

	private Documento documento;
	
	private BigDecimal precioDistribuidor;
	private BigDecimal coeficienteImp;
	
	private String contactoId;
	private Integer docRefId;
	

	public BigDecimal getTotal() {
		return getSubTotal().add(getIva());
	}

	public BigDecimal getSubTotal() {
		return getPrecioUnitario().multiply(this.cantidad);
	}

	public BigDecimal getPrecioUnitario() {
		if (this.precio != null) {
			return this.precio.subtract(getImporteDescuento());
		}
		return BigDecimal.ZERO;
	}

	public BigDecimal getImporteDescuento() {
		if (this.precio != null && this.descuento != null) {
			return this.precio.multiply(this.descuento).divide(Maths.ONE_HUNDRED);
		}
		return BigDecimal.ZERO;
	}

	public BigDecimal getImporteDescuentoTotal() {
		return getImporteDescuento().multiply(getCantidad());
	}

	public BigDecimal getNeto() {
		return getPrecioUnitario().multiply(
				BigDecimal.ONE.subtract(this.documento.getComprobante().getDescuentoPrometido().divide(Maths.ONE_HUNDRED)));
	}

	public BigDecimal getNetoTotal() {
		return getNeto().multiply(this.cantidad);
	}

	public void setNeto(BigDecimal value) {
		BigDecimal documentoPrometido = this.documento.getComprobante().getDescuentoPrometido();
		BigDecimal quot = Maths.ONE_HUNDRED.subtract(this.descuento).multiply(Maths.ONE_HUNDRED.subtract(documentoPrometido));

		this.precio = Maths.ONE_HUNDRED.multiply(Maths.ONE_HUNDRED).multiply(value).divide(quot, 2, RoundingMode.HALF_UP);
	}

	public void elegirArticulo(Articulo articulo) {
		this.articulo = articulo;
		if (articulo != null)
			this.concepto = articulo.getNombre();
	}

	public BigDecimal getIva() {
		return getSubTotal().multiply(getTasaIva()).divide(Maths.ONE_HUNDRED, 2, RoundingMode.HALF_UP);
	}
	
	public Boolean comprobanteComputaIva() {
		if (documento.getComprobante().getCodigo().equals("122") || documento.getComprobante().getCodigo().equals("124") ) {
			return false;
		}
		return (articulo != null && !documento.getComprobante().getAster() && !documento.getComprobante().isExento());
	}

	public BigDecimal getTasaIva() {
		if (!comprobanteComputaIva()) {
			return BigDecimal.ZERO;
		}
		return this.articulo.getTasaIva();
	}

	public BigDecimal getPesoDocumento() {
		BigDecimal stLinea = getSubTotal();
		BigDecimal stDocumento = this.documento.getSubTotal();
		if (stDocumento.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return stLinea.divide(stDocumento, 8, RoundingMode.HALF_UP);
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public Articulo getArticulo() {
		return this.articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
		if (articulo != null && documento != null) {
			setIvaArticulo(articulo == null ? null : articulo.getIva());
		}
	}
	
	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		if (precio == null) {
			this.precio = BigDecimal.ZERO;
		}
		this.precio = precio;
	}

	public void setPrecioUnitarioSinDescuentoPrometido(BigDecimal precio) {
		double t = 1.0D - this.documento.getComprobante().getDescuentoPrometido().doubleValue() / 100.0D;
		if (t != 0.0D)
			this.precio = new BigDecimal(precio.doubleValue() / t);
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public BigDecimal getCostoTotal() {
		return this.costo == null ? BigDecimal.ZERO : this.costo.multiply(this.cantidad);
	}

	public void setCosto(BigDecimal costo) {
		if (costo == null) {
			this.costo = BigDecimal.ZERO;
		}
		this.costo = costo;
	}

	public BigDecimal getDescuento() {
		return this.descuento == null ? BigDecimal.ZERO : this.descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		if (descuento == null) {
			this.descuento = BigDecimal.ZERO;
		}
		this.descuento = descuento;
	}

	public BigDecimal getPorcentajeUtilidad() {
		BigDecimal neto = getNeto();
		if (neto.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		BigDecimal utilidad = getUtilidad();
		return utilidad.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : new BigDecimal(utilidad.doubleValue() * 100.0D
				/ neto.doubleValue());
	}

	public BigDecimal getUtilidad() {
		BigDecimal neto = getNeto();
		BigDecimal costo = getCosto();
		
		if (neto == null || costo == null) {
			return BigDecimal.ZERO;
		}
		return neto.subtract(costo);
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;

		if (articulo != null && documento != null) {
			setIvaArticulo(articulo == null ? null : articulo.getIva());
		}

	}

	public int getNumeroLinea() {
		return this.numeroLinea;
	}

	public void setNumeroLinea(int numeroLinea) {
		this.numeroLinea = numeroLinea;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public void setPrecioDistribuidor(BigDecimal precioDistribuidor) {
		this.precioDistribuidor = precioDistribuidor;
	}

	public BigDecimal getPrecioDistribuidor() {
		return precioDistribuidor;
	}

	public BigDecimal getCostoDistribuidor() {
		if (precioDistribuidor == null) {
			return null;
		}
		return precioDistribuidor.multiply(this.cantidad);
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getConceptoIdLin() {
		return conceptoIdLin;
	}

	public void setConceptoIdLin(String conceptoIdLin) {
		this.conceptoIdLin = conceptoIdLin;
	}

	public Short getDepositoDestinoId() {
		return depositoDestinoId;
	}

	public void setDepositoDestinoId(Short depositoDestinoId) {
		this.depositoDestinoId = depositoDestinoId;
	}

	public Short getDepositoOrigenId() {
		return depositoOrigenId;
	}

	public void setDepositoOrigenId(Short depositoOrigenId) {
		this.depositoOrigenId = depositoOrigenId;
	}
	
	public void setIvaArticulo(Iva ivaArticulo) {
		this.ivaArticulo = ivaArticulo;
	}

	public Iva getIvaArticulo() {
		return ivaArticulo;
	}

	public Short getIvaId() {
		return ivaId;
	}

	public void setIvaId(Short ivaId) {
		this.ivaId = ivaId;
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

}