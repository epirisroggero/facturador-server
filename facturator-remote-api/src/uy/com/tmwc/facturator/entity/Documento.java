package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import uy.com.tmwc.facturator.utils.Maths;

public class Documento extends DocumentoBase implements Serializable {
	private static final long serialVersionUID = 1L;
	private Vendedor vendedor;

	private Short cajaId;

	private PreciosVenta preciosVenta;
	private Deposito depositoOrigen;
	private Deposito depositoDestino;
	private Entrega entrega;
	private ComisionesDocumento comisiones;
	private LineasDocumento lineas;
	private PlanPagos planPagos;
	private PlanPagos condicion;
	private CuotasDocumento cuotasDocumento;
	private BigDecimal saldo;
	private Set<VinculoDocumentos> recibosVinculados;
	private Set<VinculoDocumentos> facturasVinculadas;
	private Set<VinculosFP> vinculosfp;
	private Set<DocumentoFormaPago> pagos;
	private BigDecimal costoOperativo;

	private BigDecimal _iva;
	//private BigDecimal _subTotal;
	private BigDecimal _costo;

	private boolean emitido = false;
	private String pendiente;

	private String agencia;
	private String reparto;
	private Integer cantidadBultos;
	private String ordenCompra;
	private String ordenVenta;
	private String chofer;
	private String nroEnvio;
	private String localidad;
	private String documento;

	private String fanfold1 = "";
	private String fanfold2 = "";
	private String fanfold3 = "";
	private String fanfold4 = "";

	private String dirEntrega;
	private short usuarioId;
	private PermisosDocumentoUsuario permisosDocumentoUsuario;

	private BigDecimal costoEstimadoEntrega = BigDecimal.ZERO;
	private BigDecimal descuentosPorc = BigDecimal.ZERO;
	private BigDecimal descuentos = BigDecimal.ZERO;

	private String processId;
	private String prevDocId;
	private String nextDocId;

	private String prevDocSerieNro;

	private String usuIdAut;
	private String emitidoPor;

	private String numCmpId;
	private String docMensaje;

	// FACTURA ELECTR�NICA

	private String docCFEetapa;
	private Integer docCFEId;
	private String docCFEFileName;

	private Short docCFEstatus; // 0 = sin status, 1 = ok, 2 = chequear, 3 = error e-factura
	private Short docCFEstatusAcuse;

	private String serieCFEIdDoc;
	private BigDecimal numCFEIdDoc;

	private String indGlobalCFERef;
	private String tipoCFERef;
	private String serieCFERef;
	private BigDecimal numCFERef;
	private String razonCFERef;
	private Date fechaCFERef;

	private String codSeguridadCFE;

	private byte[] docBlob;
	private String docBlobExt;

	private byte[] codigoQR;

	private Date CAEemision;
	private Integer CAEdesde;
	private Integer CAEhasta;
	private String CAEnom;
	private BigInteger CAEnro;
	private String CAEserie;
	private Short tipoCFEid;
	private Date CAEvencimiento;

	private Moneda docRecMda;
	private BigDecimal docRecNeto;
	private BigDecimal docRenFin;

	private String titular;
	private String bancoIdDoc;
	private String concepto;
	
	private String centroCostosId;
	private String docCenCostosId;
	private String referencia;
	
	private String puntoVentaId;
	private CentrosCosto centroCostos;
	
	private Documento notaCreditoFinanciera;

	public Documento() {
		this.comisiones = new ComisionesDocumento(this);
		this.lineas = new LineasDocumento(this);
		this.cuotasDocumento = new CuotasDocumento(this);
	}

	public Documento(Comprobante c) {
		this();
		setComprobante(c);

		setDepositoOrigen(c.getDepositoOrigen());
		setDepositoDestino(c.getDepositoDestino());
	}

	public void tomarCamposDelCliente() {
		if (this.cliente == null) {
			return;
		}
		this.planPagos = this.cliente.getPlanPagos();
		this.condicion = this.cliente.getPlanPagos();
		this.vendedor = this.cliente.getVendedor();
		this.preciosVenta = this.cliente.getPreciosVenta();

		if (this.cliente.getContacto() != null) {
			this.rut = this.cliente.getContacto().getCtoRUT();
			this.razonSocial = this.cliente.getContacto().getCtoRSocial();
			this.direccion = this.cliente.getContacto().getCtoDireccion();
			this.telefono = this.cliente.getContacto().getCtoTelefono();
		}
	}
	
	public static Documento getNuevoDocumento(Comprobante comprobante) {
		Documento doc = new Documento(comprobante);

		Date currentDate = new Date();
		
		doc.setFecha(currentDate);
		doc.setFecha2(currentDate);
		doc.setRegistroFecha(currentDate);
		doc.setRegistroHora(currentDate);

		return doc;
	}
	 
	public Documento copyData(Documento doc) {
		doc.setProveedor(proveedor);
		doc.setMoneda(moneda);
		
		doc.setPrevDocSerieNro(prevDocSerieNro);
		doc.setDireccion(direccion);
		doc.setLocalidad(localidad);
		doc.setRut(rut);
		doc.setTelefono(telefono);
		doc.setDocTCF(docTCF);
		doc.setDocTCC(docTCC);
		doc.setRazonSocial(razonSocial);
					
		doc.setUsuIdAut(usuIdAut);
		doc.setCentroCostosId(centroCostosId);
		doc.setDocCenCostosId(docCenCostosId);
		doc.setReferencia(referencia);
		doc.setNotas(notas);
		
		doc.setSubTotal(subTotal);
		doc.setIva(_iva);
		doc.setTotal(total);
		doc.setCosto(_costo);

		doc.setLineas(lineas);
		doc.getLineas().setDocumento(doc);
		for (LineaDocumento linea : lineas.getLineas()) {
			linea.setDocumento(doc);
		}		
		return doc;
	}

	public BigDecimal getTotal() {
		if (this.total != null) {
			return this.total;
		}
		return getTotalRedondeado();
	}

	private BigDecimal getTotalRedondeado() {
		return getRedondeo(getTotalExacto());
	}

	private BigDecimal getRedondeo(BigDecimal exacto) {
		if (this.moneda == null) {
			return null;
		}
		return exacto.setScale(this.moneda.getRedondeo(), BigDecimal.ROUND_HALF_UP);
	}

	public BigDecimal getTotalExacto() {
		List<LineaDocumento> items = this.lineas.getLineas();
		BigDecimal sum = BigDecimal.ZERO;
		for (LineaDocumento lineaDocumento : items) {
			if (lineaDocumento.getArticulo() == null) {
				continue;
			}
			sum = sum.add(lineaDocumento.getTotal());
		}
		return sum;
	}

	public BigDecimal getSubTotal() {
		if (this.subTotal != null) {
			return this.subTotal;
		}
		return calcularSubTotal();
	}
	
	private BigDecimal calcularSubTotal() {
		if (comprobante.isGasto()) {
			return BigDecimal.ZERO;
		}		
		BigDecimal sum = BigDecimal.ZERO;
		for (LineaDocumento lineaDocumento : lineas.getLineas()) {
			if (lineaDocumento.getArticulo() == null) {
				continue;
			}
			sum = sum.add(lineaDocumento.getSubTotal());
		}
		return sum;
	}

	public BigDecimal getCostoTotal() {
		return calcularCostoTotal();
	}
	
	private BigDecimal calcularCostoTotal() {
		if (comprobante.isGasto()) {
			return BigDecimal.ZERO;
		}		
		BigDecimal sum = BigDecimal.ZERO;
		for (LineaDocumento lineaDocumento : lineas.getLineas()) {
			if (lineaDocumento.getArticulo() == null) {
				continue;
			}
			sum = sum.add(lineaDocumento.getCostoTotal());
		}
		return sum;
	}	
	
	public Boolean comprobanteComputaIva() {
		if (comprobante.getCodigo().equals("122") || comprobante.getCodigo().equals("124")) {
			return false;
		}
		return !(comprobante.isExento() || comprobante.getAster());
	}

	public BigDecimal calcularIva() {
		if (!comprobanteComputaIva()) {
			return BigDecimal.ZERO;
		} else {
			List<LineaDocumento> items = this.lineas.getLineas();
			BigDecimal sum = BigDecimal.ZERO;
			for (LineaDocumento lineaDocumento : items) {
				if (lineaDocumento.getArticulo() == null) {
					continue;
				}
				sum = sum.add(lineaDocumento.getIva());
			}
			return sum;
		}
	}

	/*
	 * public BigDecimal getDescuentos() { List<LineaDocumento> items =
	 * this.lineas.getLineas(); BigDecimal sum = BigDecimal.ZERO; for
	 * (LineaDocumento lineaDocumento : items) { if
	 * (lineaDocumento.getArticulo() == null) { continue; } sum =
	 * sum.add(lineaDocumento.getImporteDescuentoTotal()); } return sum; }
	 */

	public BigDecimal getRedondeo() {
		BigDecimal exacto = getTotalExacto();
		BigDecimal redondeado = getTotalRedondeado();
		return exacto.subtract(redondeado);
	}

	public BigDecimal getUtilidadEstimada() {
		BigDecimal totalcuesta = BigDecimal.ZERO;
		BigDecimal totalventa = BigDecimal.ZERO;

		for (LineaDocumento linea : this.lineas.getLineas()) {
			if (linea.getArticulo() == null) {
				continue;
			}
			totalcuesta = totalcuesta.add(linea.getCostoTotal());
			totalventa = totalventa.add(linea.getNetoTotal());
		}
		if (this.entrega != null) {
			totalcuesta = totalcuesta.add(this.costoEstimadoEntrega);
		}
		if (totalventa.compareTo(BigDecimal.ZERO) != 0) {
			return totalventa.subtract(totalcuesta).multiply(Maths.ONE_HUNDRED).divide(totalventa, 2, 4);
		}
		return null;
	}

	public BigDecimal getUtilidad() {
		BigDecimal ventaNeta = getVentaNeta();
		if (ventaNeta.compareTo(BigDecimal.ZERO) == 0) {
			return null;
		}
		return getRentaNetaComercial().multiply(Maths.ONE_HUNDRED).divide(ventaNeta, 2, 4);
	}

	public void establecerFormaPago() {
		if ((getComprobante().isMueveCaja()) && ((this.pagos == null) || (this.pagos.size() == 0))) {
			this.pagos = new HashSet<DocumentoFormaPago>();
			this.pagos.add(new DocumentoFormaPago(this));
		}
	}

	public void sanityCheck() {
		if (!this.comprobante.isCredito() && (this.planPagos != null || !this.cuotasDocumento.isEmpty())) {
			throw new RuntimeException("El comprobante no acepta cuotificacion");
		}

		// Sacar el chequeo provisoriamente para el comprobantes cotizaci�n que esta mal definido como venta...
		if (!this.comprobante.getCodigo().equals("1") && !this.comprobante.isRecibo()) {
			boolean mueveCaja = this.comprobante.isMueveCaja();
			boolean tieneFP = (this.pagos != null) && (this.pagos.size() > 0);
			if ((mueveCaja ^ tieneFP))
				throw new RuntimeException("Solo los comprobantes que mueven caja tienen pagos");
		}
	}

	public void validate() throws ValidationException {
		if (this.comprobante.isCredito()) {
			if (planPagos == null) {
				throw new ValidationException("La condici�n (plan de pagos) es obligatoria");
			}
			this.cuotasDocumento.validate();
		}
		for (LineaDocumento linea : this.lineas.getLineas())
			if (linea.getArticulo() == null)
				throw new ValidationException("La l�nea " + linea.getNumeroLinea() + " no especifica art�culo.");

		if (this.comprobante.isVenta() || this.comprobante.getCodigo().equals(System.getProperty("facturator.comprobantes.ordenVenta"))) {
			if (entrega == null) {
				throw new ValidationException("El campo entrega es obligatorio");
			}
		}
	}

	public boolean isEmitible() {
		return this.comprobante.isEmitible();
	}

	public boolean isEmitido() {
		return this.emitido;
	}

	public Boolean isPendiente() {
		return this.pendiente == null || !this.pendiente.equals("N");
	}

	public String getPendiente() {
		return this.pendiente;
	}

	public void setPendiente(String pendiente) {
		this.pendiente = pendiente;
	}


	public BigDecimal getAdeudadoNetoRecibo() {
		BigDecimal deuda = getDeuda();
		if (descuentosPorc != null) {
			return deuda.multiply(Maths.ONE_HUNDRED.subtract(descuentosPorc)).divide(Maths.ONE_HUNDRED);
		}
		return deuda;
	}

	public BigDecimal calcularDeuda(Date today) {
		if (comprobante.getTipo() == Comprobante.NOTA_CREDITO) {
			return getDeuda();
		} else {
			return this.cuotasDocumento.calcularDeuda(today, getTotal().subtract(getSaldo()), cliente.getCategCliId());
		}
	}

	public boolean isTieneCuotaVencida(Date today) {
		if (comprobante.getTipo() == Comprobante.NOTA_CREDITO) {
			return false;
		} else {
			return this.cuotasDocumento.isTieneCuotaVencida(today, getTotal().subtract(getSaldo()));
		}
	}
	
	public int getDiasRetraso(Date today) {
		if (comprobante.getTipo() == Comprobante.NOTA_CREDITO) {
			return 0;
		} else {
			return this.cuotasDocumento.getDiasRetraso(today, getTotal().subtract(getSaldo()));
		}
	}


	public int getSigno() {
		return aplicarSigno(BigDecimal.ONE).intValue();
	}

	private BigDecimal aplicarSigno(BigDecimal abs) {
		if (comprobante.getTipo() == Comprobante.NOTA_CREDITO) {
			return abs.negate();
		} else {
			return abs;
		}
	}

	public BigDecimal calcularMontoDescuentoEsperado(BigDecimal monto) {
		return this.comprobante.calcularMontoDescuentoPrometido(monto);
	}

	public BigDecimal getCosto() {
		if (this._costo != null) {
			return this._costo;
		}

		BigDecimal sum = BigDecimal.ZERO;
		for (LineaDocumento linea : this.lineas.getLineas()) {
			if (linea.getArticulo() == null) {
				continue;
			}
			sum = sum.add(linea.getCostoTotal());
		}
		return sum;
	}

	public void invalidarRedundancia() {
		this._costo = null;
		this.subTotal = null;
		this.total = null;
	}

	public void toEmitido() {
		checkEmitido();
		if (!isTieneSerieNumero()) {
			throw new RuntimeException("Se intento emitir el documento " + this.docId + " sin asignarle una serie y número");
		}
		this.emitido = true;
	}

	private void checkEmitido() {
		if (isEmitido())
			throw new RuntimeException("El documento " + this.docId + " ya fue emitido");
	}

	public void toEmitido(SerieNumero serieNumero) {
		checkEmitido();
		if (isTieneSerieNumero()) {
			throw new RuntimeException("Se intento reasignar serie y numero al documento " + this.docId);
		}
		this.serie = serieNumero.getSerie();
		this.numero = serieNumero.getNumero();

		this.emitido = true;
	}

	public void cambiarEntrega(Entrega entrega, BigDecimal costoEntrega) {
		setEntrega(entrega);
		this.costoEstimadoEntrega = costoEntrega;
	}

	public Vendedor getVendedor() {
		return this.vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public PreciosVenta getPreciosVenta() {
		return this.preciosVenta;
	}

	public void setPreciosVenta(PreciosVenta preciosVenta) {
		this.preciosVenta = preciosVenta;
	}

	public Deposito getDepositoOrigen() {
		return this.depositoOrigen;
	}

	public void setDepositoOrigen(Deposito depositoOrigen) {
		this.depositoOrigen = depositoOrigen;
	}

	public Deposito getDepositoDestino() {
		return this.depositoDestino;
	}

	public void setDepositoDestino(Deposito depositoDestino) {
		this.depositoDestino = depositoDestino;
	}

	public Entrega getEntrega() {
		return this.entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
	}

	public ComisionesDocumento getComisiones() {
		return this.comisiones;
	}

	public void setComisiones(ComisionesDocumento comisiones) {
		this.comisiones = comisiones;
	}

	public PlanPagos getPlanPagos() {
		return this.planPagos;
	}

	public void setPlanPagos(PlanPagos planPagos) {
		this.planPagos = planPagos;
	}

	public PlanPagos getCondicion() {
		return condicion;
	}

	public void setCondicion(PlanPagos condicion) {
		this.condicion = condicion;
	}

	public LineasDocumento getLineas() {
		return this.lineas;
	}

	public void setLineas(List<LineaDocumento> lineas) {
		this.lineas.setLineas(lineas);
	}

	public void setLineas(LineasDocumento lineas) {
		if (lineas != null) {
			this.lineas = lineas;
		}
	}

	public Integer getCantidadBultos() {
		return this.cantidadBultos;
	}

	public void setCantidadBultos(Integer cantidadBultos) {
		this.cantidadBultos = cantidadBultos;
	}

	public CuotasDocumento getCuotasDocumento() {
		if (cuotasDocumento == null) {
			cuotasDocumento = new CuotasDocumento();
		}
		return this.cuotasDocumento;
	}

	public void setCuotasDocumento(CuotasDocumento cuotasDocumento) {
		this.cuotasDocumento = cuotasDocumento;
	}

	public Set<DocumentoFormaPago> getPagos() {
		return this.pagos;
	}

	public void setPagos(Set<DocumentoFormaPago> pagos) {
		this.pagos = pagos;
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public BigDecimal getRentaNetaComercial() {
		BigDecimal ventaNeta = getVentaNeta();
		BigDecimal costo = getCosto();
		BigDecimal costoCSigno = this.comprobante.isDevolucion() ? costo.negate() : costo;
		return ventaNeta.subtract(costoCSigno);
	}
	
	public BigDecimal getRentaNetaDistComercial(){
		BigDecimal costoDistribuidor = getCostoDistribuidor();
		
		// No tiene precio distribuidor.
		if (costoDistribuidor == null) {
			return BigDecimal.ZERO;
		}		
		BigDecimal ventaNeta = getVentaNeta();
		BigDecimal costoDistCSigno = this.comprobante.isDevolucion() ? costoDistribuidor.negate() : costoDistribuidor;
		BigDecimal renta = ventaNeta.subtract(costoDistCSigno);
		return renta;
	}
	
	public BigDecimal getRentaDistribuidor() {
		BigDecimal costoDistribuidor = getCostoDistribuidor();
		if (costoDistribuidor == null) {
			return null;
		}
		BigDecimal vn = getVentaNeta();
		BigDecimal renta = vn.subtract(costoDistribuidor);
		return renta;
	}	

	public BigDecimal getVentaNeta() {
		BigDecimal subTotal = getSubTotal();
		BigDecimal ventaNeta = this.comprobante.aplicarDescuentoPrometido(subTotal, cliente.getCategCliId());
		return this.comprobante.isDevolucion() ? ventaNeta.negate() : ventaNeta;
	}

	public BigDecimal getCostoOperativo() {
		return this.costoOperativo;
	}

	public void setCostoOperativo(BigDecimal costoOperativo) {
		this.costoOperativo = costoOperativo;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public void setCosto(BigDecimal costo) {
		this._costo = costo;
	}

	public void setParticipaciones(List<ParticipacionVendedor> participaciones) {
		this.comisiones.setParticipaciones(participaciones);
	}

	public List<ParticipacionVendedor> getParticipaciones() {
		return this.comisiones.getParticipaciones();
	}

	public void setEmitido(boolean emitido) {
		this.emitido = emitido;
	}


	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getReparto() {
		return this.reparto;
	}

	public void setReparto(String reparto) {
		this.reparto = reparto;
	}

	public String getOrdenCompra() {
		return this.ordenCompra;
	}

	public void setOrdenCompra(String ordenCompra) {
		this.ordenCompra = ordenCompra;
	}

	public String getOrdenVenta() {
		return this.ordenVenta;
	}

	public void setOrdenVenta(String ordenVenta) {
		this.ordenVenta = ordenVenta;
	}

	public String getChofer() {
		return chofer;
	}

	public void setChofer(String chofer) {
		this.chofer = chofer;
	}

	public BigDecimal getCostoEstimadoEntrega() {
		return this.costoEstimadoEntrega;
	}

	public void setCostoEstimadoEntrega(BigDecimal costoEstimadoEntrega) {
		this.costoEstimadoEntrega = costoEstimadoEntrega;
	}

	public BigDecimal getCancelado() {
		return getTotal().subtract(getSaldo());
	}

	public void setCuotas(List<CuotaDocumento> cuotas) {
		this.cuotasDocumento = new CuotasDocumento(this);
		this.cuotasDocumento.setCuotas(cuotas);
	}

	public List<CuotaDocumento> getCuotas() {
		return cuotasDocumento != null ? cuotasDocumento.getCuotas() : null;
	}

	public BigDecimal getDeuda() {
		return aplicarSigno(getSaldo());
	}

	public String getNroEnvio() {
		return nroEnvio;
	}

	public void setNroEnvio(String nroEnvio) {
		this.nroEnvio = nroEnvio;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDirEntrega() {
		return dirEntrega;
	}

	public void setDirEntrega(String dirEntrega) {
		this.dirEntrega = dirEntrega;
	}

	public short getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(short usuarioId) {
		this.usuarioId = usuarioId;
	}

	public PermisosDocumentoUsuario getPermisosDocumentoUsuario() {
		return permisosDocumentoUsuario;
	}

	public void setPermisosDocumentoUsuario(PermisosDocumentoUsuario permisosDocumentoUsuario) {
		this.permisosDocumentoUsuario = permisosDocumentoUsuario;
	}

	public BigDecimal getDescuentosPorc() {
		return descuentosPorc;
	}

	public void setDescuentosPorc(BigDecimal descuentosPorc) {
		this.descuentosPorc = descuentosPorc;
	}

	public BigDecimal getIva() {
		return _iva;
	}

	public void setIva(BigDecimal _iva) {
		this._iva = _iva;
	}

	private BigDecimal getCostoDistribuidor() {
		if (lineas == null || lineas.getLineas() == null) {
			return BigDecimal.ZERO;
		}

		BigDecimal cd = BigDecimal.ZERO;
		for (LineaDocumento linea : lineas.getLineas()) {
			BigDecimal lcd = linea.getCostoDistribuidor();
			cd = cd.add(lcd);
		}
		return cd;
	}


	public String getDocumento() {
		return documento;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public Short getCajaId() {
		return cajaId;
	}

	public void setCajaId(Short cajaId) {
		this.cajaId = cajaId;
	}

	public String getPrevDocId() {
		return prevDocId;
	}

	public void setPrevDocId(String prevDocId) {
		this.prevDocId = prevDocId;
	}

	public String getPrevDocSerieNro() {
		return prevDocSerieNro;
	}

	public void setPrevDocSerieNro(String prevDocSerieNro) {
		this.prevDocSerieNro = prevDocSerieNro;
	}

	public String getUsuIdAut() {
		return usuIdAut;
	}

	public void setUsuIdAut(String usuIdAut) {
		this.usuIdAut = usuIdAut;
	}

	public String getEmitidoPor() {
		return emitidoPor;
	}

	public void setEmitidoPor(String emitidoPor) {
		this.emitidoPor = emitidoPor;
	}

	public String getFanfold1() {
		return fanfold1;
	}

	public void setFanfold1(String fanfold1) {
		this.fanfold1 = fanfold1;
	}

	public String getFanfold2() {
		return fanfold2;
	}

	public void setFanfold2(String fanfold2) {
		this.fanfold2 = fanfold2;
	}

	public String getFanfold3() {
		return fanfold3;
	}

	public void setFanfold3(String fanfold3) {
		this.fanfold3 = fanfold3;
	}

	public String getNumCmpId() {
		return numCmpId;
	}

	public void setNumCmpId(String numCmpId) {
		this.numCmpId = numCmpId;
	}

	public String getFanfold4() {
		return fanfold4;
	}

	public void setFanfold4(String fanfold4) {
		this.fanfold4 = fanfold4;
	}

	public String getDocCFEetapa() {
		return docCFEetapa;
	}

	public void setDocCFEetapa(String docCFEetapa) {
		this.docCFEetapa = docCFEetapa;
	}

	public Integer getDocCFEId() {
		return docCFEId;
	}

	public void setDocCFEId(Integer docCFEId) {
		this.docCFEId = docCFEId;
	}

	public String getTipoCFERef() {
		return tipoCFERef;
	}

	public void setTipoCFERef(String tipoCFERef) {
		this.tipoCFERef = tipoCFERef;
	}

	public String getSerieCFEIdDoc() {
		return serieCFEIdDoc;
	}

	public void setSerieCFEIdDoc(String serieCFEIdDoc) {
		this.serieCFEIdDoc = serieCFEIdDoc;
	}

	public BigDecimal getNumCFEIdDoc() {
		return numCFEIdDoc;
	}

	public void setNumCFEIdDoc(BigDecimal numCFEIdDoc) {
		this.numCFEIdDoc = numCFEIdDoc;
	}

	public Short getDocCFEstatus() {
		return docCFEstatus;
	}

	public void setDocCFEstatus(Short docCFEstatus) {
		this.docCFEstatus = docCFEstatus;
	}

	public Short getDocCFEstatusAcuse() {
		return docCFEstatusAcuse;
	}

	public void setDocCFEstatusAcuse(Short docCFEstatusAcuse) {
		this.docCFEstatusAcuse = docCFEstatusAcuse;
	}

	public String getSerieCFERef() {
		return serieCFERef;
	}

	public void setSerieCFERef(String serieCFERef) {
		this.serieCFERef = serieCFERef;
	}

	public BigDecimal getNumCFERef() {
		return numCFERef;
	}

	public void setNumCFERef(BigDecimal numCFERef) {
		this.numCFERef = numCFERef;
	}

	public String getRazonCFERef() {
		return razonCFERef;
	}

	public void setRazonCFERef(String razonCFERef) {
		this.razonCFERef = razonCFERef;
	}

	public String getIndGlobalCFERef() {
		return indGlobalCFERef;
	}

	public void setIndGlobalCFERef(String indGlobalCFERef) {
		this.indGlobalCFERef = indGlobalCFERef;
	}

	public Date getFechaCFERef() {
		return fechaCFERef;
	}

	public void setFechaCFERef(Date fechaCFERef) {
		this.fechaCFERef = fechaCFERef;
	}

	public String getCodSeguridadCFE() {
		return codSeguridadCFE;
	}

	public void setCodSeguridadCFE(String codSeguridadCFE) {
		this.codSeguridadCFE = codSeguridadCFE;
	}

	public String getDocBlobExt() {
		return docBlobExt;
	}

	public void setDocBlobExt(String docBlobExt) {
		this.docBlobExt = docBlobExt;
	}

	public byte[] getDocBlob() {
		return docBlob;
	}

	public void setDocBlob(byte[] docBlob) {
		this.docBlob = docBlob;
	}

	public byte[] getCodigoQR() {
		return codigoQR;
	}

	public void setCodigoQR(byte[] codigoQR) {
		this.codigoQR = codigoQR;
	}

	public String getCAEnom() {
		return CAEnom;
	}

	public void setCAEnom(String cAEnom) {
		CAEnom = cAEnom;
	}

	public BigInteger getCAEnro() {
		return CAEnro;
	}

	public void setCAEnro(BigInteger cAEnro) {
		CAEnro = cAEnro;
	}

	public String getCAEserie() {
		return CAEserie;
	}

	public void setCAEserie(String cAEserie) {
		CAEserie = cAEserie;
	}

	public Date getCAEemision() {
		return CAEemision;
	}

	public void setCAEemision(Date cAEemision) {
		CAEemision = cAEemision;
	}

	public Integer getCAEdesde() {
		return CAEdesde;
	}

	public void setCAEdesde(Integer cAEdesde) {
		CAEdesde = cAEdesde;
	}

	public Integer getCAEhasta() {
		return CAEhasta;
	}

	public void setCAEhasta(Integer cAEhasta) {
		CAEhasta = cAEhasta;
	}
	
	public Date getCAEvencimiento() {
		return CAEvencimiento;
	}

	public void setCAEvencimiento(Date cAEvencimiento) {
		CAEvencimiento = cAEvencimiento;
	}

	public Short getTipoCFEid() {
		return tipoCFEid;
	}

	public void setTipoCFEid(Short tipoCFEid) {
		this.tipoCFEid = tipoCFEid;
	}

	public Set<VinculoDocumentos> getFacturasVinculadas() {
		return facturasVinculadas;
	}

	public void setFacturasVinculadas(Set<VinculoDocumentos> facturasVinculadas) {
		this.facturasVinculadas = facturasVinculadas;
	}

	public Set<VinculoDocumentos> getRecibosVinculados() {
		return this.recibosVinculados;
	}

	public void setRecibosVinculados(Set<VinculoDocumentos> recibosVinculados) {
		this.recibosVinculados = recibosVinculados;
	}

	public BigDecimal getDescuentos() {
		return descuentos;
	}

	public void setDescuentos(BigDecimal descuentos) {
		this.descuentos = descuentos;
	}

	public Moneda getDocRecMda() {
		return docRecMda;
	}

	public void setDocRecMda(Moneda docRecMda) {
		this.docRecMda = docRecMda;
	}

	public BigDecimal getDocRecNeto() {
		return docRecNeto;
	}

	public void setDocRecNeto(BigDecimal docRecNeto) {
		this.docRecNeto = docRecNeto;
	}

	public BigDecimal getDocRenFin() {
		return docRenFin;
	}

	public void setDocRenFin(BigDecimal docRenFin) {
		this.docRenFin = docRenFin;
	}

	public String getTitular() {
		return titular;
	}

	public void setDocTitular(String docTitular) {
		this.titular = docTitular;
	}

	public String getBancoIdDoc() {
		return bancoIdDoc;
	}

	public void setBancoIdDoc(String bancoIdDoc) {
		this.bancoIdDoc = bancoIdDoc;
	}

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String docConcepto) {
		this.concepto = docConcepto;
	}

	public Set<VinculosFP> getVinculosfp() {
		return vinculosfp;
	}

	public void setVinculosfp(Set<VinculosFP> vinculosfp) {
		this.vinculosfp = vinculosfp;
	}

	public String getDocMensaje() {
		return docMensaje;
	}

	public void setDocMensaje(String docMensaje) {
		this.docMensaje = docMensaje;
	}

	public String getDocCFEFileName() {
		return docCFEFileName;
	}

	public void setDocCFEFileName(String docCFEFilename) {
		this.docCFEFileName = docCFEFilename;
	}
	
	public String getNextDocId() {
		return nextDocId;
	}

	public void setNextDocId(String nextDocId) {
		this.nextDocId = nextDocId;
	}
	
	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}
	
	public String getCentroCostosId() {
		return centroCostosId;
	}

	public void setCentroCostosId(String centroCostosId) {
		this.centroCostosId = centroCostosId;
	}

	public CentrosCosto getCentroCostos() {
		return centroCostos;
	}

	public void setCentroCostos(CentrosCosto centroCostos) {
		this.centroCostos = centroCostos;
	}
	
	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getDocCenCostosId() {
		return docCenCostosId;
	}

	public void setDocCenCostosId(String docCenCostosId) {
		this.docCenCostosId = docCenCostosId;
	}
	
	public String getPuntoVentaId() {
		return puntoVentaId;
	}

	public void setPuntoVentaId(String puntoVentaId) {
		this.puntoVentaId = puntoVentaId;
	}

	public Documento getNotaCreditoFinanciera() {
		return notaCreditoFinanciera;
	}

	public void setNotaCreditoFinanciera(Documento notaCreditoFinanciera) {
		this.notaCreditoFinanciera = notaCreditoFinanciera;
	}


}