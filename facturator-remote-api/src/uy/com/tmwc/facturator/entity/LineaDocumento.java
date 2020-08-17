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
	private String articuloId;
	private String conceptoIdLin = "";

	private BigDecimal precio = BigDecimal.ZERO;
	private BigDecimal costo = BigDecimal.ZERO;
	private BigDecimal descuento = BigDecimal.ZERO;

	private Short depositoDestinoId = 0;
	private Short depositoOrigenId = 0;

	private Iva ivaLin;
	// private Short ivaId;

	private Documento documento;

	private BigDecimal precioDistribuidor;
	private BigDecimal coeficienteImp;

	private String contactoId;
	private Integer docRefId;

	private String rubIdlin;
	private String afilador;

	private BigDecimal diametro = BigDecimal.ZERO;
	private BigDecimal rotos = BigDecimal.ZERO;
	private BigDecimal cascados = BigDecimal.ZERO;

	private BigDecimal linDto1 = BigDecimal.ZERO;
	private BigDecimal linDto2 = BigDecimal.ZERO;
	private BigDecimal linDto3 = BigDecimal.ZERO;
	private BigDecimal linDto4 = BigDecimal.ZERO;

	private String marca;

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
		return getPrecioUnitario().multiply(BigDecimal.ONE.subtract(this.documento.getComprobante().getDescuentoPrometido().divide(Maths.ONE_HUNDRED)));
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
		if (documento.getComprobante().getCodigo().equals("122") || documento.getComprobante().getCodigo().equals("124")) {
			return false;
		}
		return (articulo != null && !documento.getComprobante().getAster() && !documento.getComprobante().isExento());
	}

	public BigDecimal getTasaIva() {
		if (!comprobanteComputaIva()) {
			return BigDecimal.ZERO;
		}
		if (documento.getComprobante().isGasto()) {
			return ivaLin != null ? ivaLin.getTasa() : BigDecimal.ZERO;
		} else {
			return articulo.getTasaIva();
		}

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

		if (articulo != null && !articulo.getCodigo().equals("GASTOS VARIOS")) {
			setIvaLin(articulo == null ? null : articulo.getIva());
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
		return utilidad.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : new BigDecimal(utilidad.doubleValue() * 100.0D / neto.doubleValue());
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

		if (articulo != null && documento != null && documento.getComprobante() != null && !documento.getComprobante().isGasto()) {
			setIvaLin(articulo == null ? null : articulo.getIva());
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
			return BigDecimal.ZERO;
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

	public Iva getIvaLin() {		
		return ivaLin;
	}
	
	public void setIvaLin(Iva ivaLin) {
		this.ivaLin = ivaLin;
	}

	public Short getIvaId() {
		if (ivaLin != null) {
			return new Short(ivaLin.getCodigo());
		}
		return null;
	}

	public void setIvaId(Short ivaId) {
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

	public String getRubIdlin() {
		return rubIdlin;
	}

	public void setRubIdlin(String rubIdlin) {
		this.rubIdlin = rubIdlin;
	}

	public String getAfilador() {
		return afilador;
	}

	public void setAfilador(String afilador) {
		this.afilador = afilador;
	}

	public BigDecimal getDiametro() {
		return diametro;
	}

	public void setDiametro(BigDecimal diametro) {
		this.diametro = diametro;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public BigDecimal getRotos() {
		return rotos;
	}

	public void setRotos(BigDecimal rotos) {
		this.rotos = rotos;
	}

	public BigDecimal getCascados() {
		return cascados;
	}

	public void setCascados(BigDecimal cascados) {
		this.cascados = cascados;
	}

	public BigDecimal getLinDto1() {
		return linDto1;
	}

	public void setLinDto1(BigDecimal linDto1) {
		this.linDto1 = linDto1;
	}

	public BigDecimal getLinDto2() {
		return linDto2;
	}

	public void setLinDto2(BigDecimal linDcto2) {
		this.linDto2 = linDcto2;
	}

	public BigDecimal getLinDto3() {
		return linDto3;
	}

	public void setLinDto3(BigDecimal linDcto3) {
		this.linDto3 = linDcto3;
	}

	public BigDecimal getLinDto4() {
		return linDto4;
	}

	public void setLinDto4(BigDecimal linDcto4) {
		this.linDto4 = linDcto4;
	}

	public String getArticuloId() {
		return articuloId;
	}

	public void setArticuloId(String articuloId) {
		this.articuloId = articuloId;
	}
	
	// Para E-Factura; retorna el iva del articulo. Se usa solo ah�
	public Iva getIvaArticulo() {		
		return articulo != null ? articulo.getIva() : null;
	}



}