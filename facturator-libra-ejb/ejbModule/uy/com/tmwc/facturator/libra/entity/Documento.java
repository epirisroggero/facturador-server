package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import uy.com.tmwc.facturator.libra.util.Constantes;

@Entity
@Table(name = "documentos")
@SecondaryTable(name = "lfx_documentos")
public class Documento extends PersistentEntity<DocumentoPK> implements Serializable, HasId<DocumentoPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocumentoPK id;

	@Column(name = "BancoIdDoc")
	private String bancoIdDoc = "";

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CajaIdDoc", referencedColumnName = "CajaId", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Caja caja;

	@Column(name = "CajaIdDoc")
	private Short cajaId;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CenIdDoc", referencedColumnName = "CenId", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Centroscosto centroCostos;

	@Column(name = "CenIdDoc")
	private String centroCostosId = "";

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "CliIdDoc", referencedColumnName = "CliId", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Cliente cliente;

	@Column(table = "lfx_documentos")
	private Integer cantidadBultos;

	@Column(table = "lfx_documentos")
	private String prevDocId;

	@Column(table = "lfx_documentos")
	private String nextDocId;

	@Column(table = "lfx_documentos")
	private String processId;

	@Column(table = "lfx_documentos")
	private String prevDocSerieNro;

	@Column(table = "lfx_documentos")
	private String usuIdAut;

	@Column(table = "lfx_documentos")
	private String emitidoPor;

	@Column(table = "lfx_documentos", name = "DocTCC")
	private BigDecimal docTCC;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @javax.persistence.JoinColumn(table = "lfx_documentos", name = "entrega_id", referencedColumnName = "codigo", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(table = "lfx_documentos", name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Entrega entrega;

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "documento")
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private List<ParticipacionVendedor> participaciones;

	@SuppressWarnings("unused")
	@Column(name = "CliIdDoc")
	private String clienteId;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @JoinColumn(name = "CmpIdDoc", referencedColumnName = "CMPID", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EMPID", insertable = false, updatable = false) })
	private Comprobante comprobante;

	@Column(name = "CmpIdDoc")
	private long comprobanteId;

	@Column(name = "CuentaId")
	private Short cuentaId;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "DepIdDocDestino", referencedColumnName = "DepId", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Deposito depositoDestino;

	@Column(name = "DepIdDocDestino")
	private Short depositoDestinoId = 0;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "DepIdDocOrigen", referencedColumnName = "DepId", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Deposito depositoOrigen;

	@Column(name = "DepIdDocOrigen")
	private Short depositoOrigenId = 0;

	@Column(name = "DocArtId")
	private String artId = "";

	@Column(name = "DocAsId")
	private String asId = "";

	@Column(name = "DocAutorizacion")
	private String autorizacion = "";

	@Lob
	@Column(name = "DocBlob")
	private byte[] blob;

	@Column(name = "DocBlobExt")
	private String blobExt = "";

	@Column(name = "DocConcepto")
	private String concepto = "";

	@Column(name = "DocConciliado")
	private String conciliado = "";

	@Column(name = "DocDescuentos")
	private BigDecimal descuentos = BigDecimal.ZERO;

	@Column(name = "DocDescuentosPorc")
	private BigDecimal descuentosPorc = BigDecimal.ZERO;

	@Column(name = "DocEmitido")
	private String emitido = "";

	@Column(name = "DocEstado")
	private String estado = "";

	@Temporal(TemporalType.DATE)
	@Column(name = "DocEstadoFecha")
	private Date estadoFecha;

	@Column(name = "DocFanfold1")
	private String fanfold1 = "";

	@Column(name = "DocFanfold2")
	private String fanfold2 = "";

	@Column(name = "DocFanfold3")
	private String fanfold3 = "";

	@Column(name = "DocFanfold4")
	private String fanfold4 = "";

	@Column(name = "DocFanfold5")
	private String fanfold5 = "";

	@Temporal(TemporalType.DATE)
	@Column(name = "DocFecha1")
	private Date fecha;

	@Temporal(TemporalType.DATE)
	@Column(name = "DocFecha2")
	private Date fecha2;

	@Column(name = "DocIVA")
	private BigDecimal iva;

	@Lob
	@Column(name = "DocNotas")
	private String notas = "";

	@Column(name = "DocOrigen")
	private String origen = "EXTERNO";

	@Column(name = "DocPendiente")
	private String pendiente = "";

	@Column(name = "DocPlan")
	private String plan = "";

	@Column(name = "DocRedondeo")
	private BigDecimal redondeo;

	@Column(name = "DocReferencia")
	private String referencia = "";

	@Temporal(TemporalType.DATE)
	@Column(name = "DocRegistroFecha")
	private Date registroFecha;

	@Column(name = "DocRegistroHora")
	private String registroHora;

	private static final String REGISTRO_HORA_FORMAT = "HH:mm:ss";

	/**
	 * Este campo N15,2 guarda el Saldo Pendiente de cada factura cr�dito. El
	 * sistema trabaja de la siguiente forma: Cuando se ingresa una nueva
	 * factura cr�dito (venta o compra) en DocTotal y en DocSaldo se graba el
	 * mismo importe, es decir, el importe total de la factura. A medida que se
	 * van realizando pagos por medio de recibos o notas de cr�dito, el saldo de
	 * la factura va disminuyendo, es decir, el importe grabado en DocSaldo va
	 * disminuyendo hasta quedar en cero.
	 */
	@Column(name = "DocSaldo")
	private BigDecimal saldo;

	@Column(name = "DocSerie")
	private String serie;

	@Column(name = "DocNumero")
	private BigInteger numero;

	@Column(name = "DocSubTotal")
	private BigDecimal subTotal;

	@Column(name = "DocTarjetaNro")
	private String tarjetaNro = "";

	@Column(name = "DocTC")
	private BigDecimal docTCF;

	@Column(name = "DocTipo")
	private short tipo;

	@Column(name = "DocTitular")
	private String titular;

	@Column(name = "DocTotal")
	private BigDecimal total;

	@Column(name = "DocUltFP")
	private Short ultFP = 0;

	@SuppressWarnings("unused")
	@Column(name = "DocUltLin")
	private Integer ultLin;

	@Column(name = "DocVinculado")
	private String docVinculado = "";

	@Column(name = "IvaIdDoc")
	private Short ivaIdDoc;

	@Column(name = "LocIdDoc")
	private short localId;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "MndIdDoc", referencedColumnName = "MndId", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Moneda moneda;

	@SuppressWarnings("unused")
	@Column(name = "MndIdDoc")
	private Short monedaId;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "PPidDoc", referencedColumnName = "PPid", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Planpago planPagos;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(table = "lfx_documentos", name = "condicionId", referencedColumnName = "PPid", insertable = false, updatable = false),
			@JoinColumn(table = "lfx_documentos", name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Planpago condicion;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(table = "lfx_documentos", name = "DocRecMdaId", referencedColumnName = "MndId", insertable = false, updatable = false),
			@JoinColumn(table = "lfx_documentos", name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Moneda docRecMda;

	@Column(table = "lfx_documentos", name = "DocRecMdaId")
	private Short docRecMdaId;

	@SuppressWarnings("unused")
	@Column(name = "PPidDoc")
	private String planPagosId;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "PrecioVentaIdDoc", referencedColumnName = "PrecioVentaId", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Preciosventa preciosVenta;

	@SuppressWarnings("unused")
	@Column(name = "PrecioVentaIdDoc")
	private Short preciosVentaId = 0;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "PrvIdDoc", referencedColumnName = "PrvId", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Proveedor proveedor;

	@SuppressWarnings("unused")
	@Column(name = "PrvIdDoc")
	private String proveedorId = "";

	@Column(name = "TextoIdDoc")
	private String textoIdDoc = "";

	@Column(name = "UsuIdDoc")
	private short usuarioId;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "VenIdDoc", referencedColumnName = "VenId", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Vendedore vendedor;

	@SuppressWarnings("unused")
	@Column(name = "VenIdDoc")
	private String vendedorId;

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "documento")
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	@OrderBy("id.linId")
	private List<Linea> lineas;

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "documento")
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	@OrderBy("id.cuotaId")
	private List<Doccuota> cuotas;

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "factura")
	private Set<Vinculosdoc> recibosVinculados;

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "recibo")
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private Set<Vinculosdoc> facturasVinculadas;

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "documento")
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private Set<Docfp> pagos;

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "documento")
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	private Set<Docruc> docruc;

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "docFP1")
	/* @Cascade({ org.hibernate.annotations.CascadeType.DELETE_ORPHAN }) */
	private Set<Vinculosfp> vinculosfp;

	@Column(name = "DocMensaje")
	private String docMensaje = "";

	@Temporal(TemporalType.DATE)
	@Column(name = "DocEntregaFecha")
	private Date docEntregaFecha;

	@Column(name = "DocEntregaReparto")
	private String docEntregaReparto = "";

	@Column(name = "DocCFEstatus")
	private Short docCFEstatus;

	@Column(name = "DocCFEstatusAcuse")
	private Short docCFEstatusAcuse;

	@Column(name = "TipoCFEidDoc")
	private Short tipoCFEidDoc;

	@Column(name = "CAEiddDoc")
	private Short cAEiddDoc;

	@Column(name = "DocIncoterm")
	private String docIncoterm = "";

	@Column(name = "DocExpTransporte")
	private Short docExpTransporte = 0;

	@Column(name = "DocModExp")
	private Short docModExp = 0;

	@Column(name = "ConceptoIdDoc")
	private String conceptoIdDoc = "";

	@Column(name = "DocDocId")
	private Integer docDocId = 0;

	@Column(name = "DocCFEetapa")
	private String docCFEetapa = "";

	@Column(name = "DocCFEId")
	private Integer docCFEId;

	@Column(name = "DocShoppingEstado")
	private long docShoppingEstado = 0;

	@Column(name = "DocShoppingIntervencion")
	private String docShoppingIntervencion = "";

	@Column(table = "lfx_documentos")
	private Long entrega_id;

	@Column(table = "lfx_documentos", precision = 10, scale = 2)
	private BigDecimal costoOperativo;

	@Column(table = "lfx_documentos", precision = 10, scale = 2)
	private BigDecimal costo;

	@Column(table = "lfx_documentos", length = 50)
	private String agencia;

	@Column(table = "lfx_documentos", length = 50)
	private String reparto;

	@Column(table = "lfx_documentos", length = 50)
	private String ordenCompra;

	@Column(table = "lfx_documentos", length = 50)
	private String ordenVenta;

	@Column(table = "lfx_documentos", length = 50)
	private String chofer;

	@Column(table = "lfx_documentos", length = 50)
	private String nroEnvio;

	@Column(table = "lfx_documentos", length = 50)
	private String localidad;

	@Column(table = "lfx_documentos", length = 50)
	private String departamento;

	@Column(table = "lfx_documentos", length = 255)
	private String dirEntrega;

	@Column(table = "lfx_documentos", length = 3)
	private String condicionId;

	@Column(table = "lfx_documentos")
	private BigDecimal costoEstimadoEntrega;

	@Column(table = "lfx_documentos", name = "TipoCFERef")
	private String tipoCFERef;

	@Column(table = "lfx_documentos", name = "IndGlobalCFERef")
	private String indGlobalCFERef;

	@Column(table = "lfx_documentos", name = "SerieCFERef")
	private String serieCFERef;

	@Column(table = "lfx_documentos", name = "NumCFERef")
	private BigDecimal numCFERef;

	@Column(table = "lfx_documentos", name = "FechaCFERef")
	private Date fechaCFERef;

	@Column(table = "lfx_documentos", name = "RazonCFERef")
	private String razonCFERef;

	@Column(table = "lfx_documentos", name = "SerieCFEIdDoc")
	private String serieCFEIdDoc;

	@Column(table = "lfx_documentos", name = "NumCFEIdDoc")
	private BigDecimal numCFEIdDoc;

	@Column(table = "lfx_documentos", name = "CodSeguridadCFE")
	private String codSeguridadCFE;

	@Column(table = "lfx_documentos", precision = 10, scale = 2)
	private BigDecimal coeficienteImp;

	@Column(table = "lfx_documentos", name = "CodigoQR")
	private byte[] codigoQR;

	@Column(table = "lfx_documentos", name = "CAEdesde")
	private Integer CAEdesde;

	@Column(table = "lfx_documentos", name = "CAEemision")
	private Date CAEemision;

	@Column(table = "lfx_documentos", name = "CAEhasta")
	private Integer CAEhasta;

	@Column(table = "lfx_documentos", name = "CAEnom")
	private String CAEnom;

	@Column(table = "lfx_documentos", name = "CAEnro")
	private BigInteger CAEnro;

	@Column(table = "lfx_documentos", name = "CAEserie")
	private String CAEserie;

	@Column(table = "lfx_documentos", name = "DocCFEFileName")
	private String docCFEFileName;

	@Column(table = "lfx_documentos", name = "TipoCFEid")
	private Short tipoCFEid;

	@Column(table = "lfx_documentos", name = "DocRecNeto")
	private BigDecimal docRecNeto;

	@Column(table = "lfx_documentos", name = "DocRtaFin")
	private BigDecimal docRenFin;

	@Column(table = "lfx_documentos", name = "DocCenIdTmp")
	private String docCenCostosId = "";
	
	@Column(table = "lfx_documentos", name = "CAEvencimiento")
	private Date CAEvencimiento;
	

	public Documento() {
		this.estadoFecha = Constantes.DEFAULT_DATE;
		this.docEntregaFecha = Constantes.DEFAULT_DATE;

		this.docCFEstatus = new Short("0");
		this.cuentaId = new Short("0");
		this.docCFEstatusAcuse = new Short("0");
		this.tipoCFEidDoc = new Short("0");
		this.cAEiddDoc = new Short("0");
		this.ultFP = new Short("0");
		this.ivaIdDoc = new Short("0");

		this.docDocId = new Integer("0");
		this.docCFEId = new Integer("0");

	}

	public void provideId(String empId, int docId) {
		this.id = new DocumentoPK(empId, docId);
		Docruc docruc = getDocruc();
		if (docruc != null) {
			docruc.setId(new DocrucPK(empId, docId));
		}
		if (this.lineas != null) {
			for (Linea linea : this.lineas) {
				LineaPK lineapk = linea.getId();
				lineapk.setDocId(docId);
			}
		}

		if (this.participaciones != null) {
			for (ParticipacionVendedor pv : this.participaciones) {
				pv.setDocumento(this);
			}
		}

		if (this.cuotas != null) {
			for (Doccuota c : this.cuotas) {
				c.setDocumento(this);
			}
		}

		if (this.pagos != null)
			for (Docfp fpago : this.pagos)
				fpago.setDocumento(this);
	}

	public void generarRedundancia() {
		if (this.cuotas != null)
			for (Doccuota cuota : this.cuotas) {
				if (this.cliente != null) {
					cuota.setCtoId(this.cliente.getId().getCliId());
					cuota.setCtoTipo("C");
				} else if (this.proveedor != null) {
					cuota.setCtoId(this.proveedor.getId().getPrvId());
					cuota.setCtoTipo("P");
				}
				cuota.setEntrega("");
				cuota.setMoneda(this.moneda);
				cuota.setSaldo(cuota.getTotal());
			}
	}

	public Long getEntrega_id() {
		return entrega_id;
	}

	public void setEntrega_id(Long entrega_id) {
		this.entrega_id = entrega_id;
	}

	public boolean usuarioInvolucrado(String usuario) {
		if (usuario.equals(String.valueOf(this.usuarioId))) {
			return true;
		}
		if (cliente != null && cliente.getVendedor() != null && usuario.equals(String.valueOf(cliente.getVendedor().getUsuarioId()))) {
			return true;
		}
		List<ParticipacionVendedor> parts = getParticipaciones();
		if (parts != null && parts.size() > 0) {
			for (ParticipacionVendedor participacionVendedor : parts) {
				if (usuario.equals(String.valueOf(participacionVendedor.getVendedor().getUsuarioId()))) {
					return true;
				}
			}
		}
		return false;
	}

	public String getCondicionId() {
		return condicionId;
	}

	public void setCondicionId(String condicionId) {
		this.condicionId = condicionId;
	}

	public void setNotas(String notas) {
		this.notas = notas == null ? "" : notas;
	}

	public void setBlobExt(String blobExt) {
		this.blobExt = blobExt == null ? "" : blobExt;
	}

	public void setTitular(String titular) {
		this.titular = titular == null ? "" : titular;
	}

	public DocumentoPK getId() {
		return this.id;
	}

	public void setId(DocumentoPK id) {
		this.id = id;
	}

	public String getBancoIdDoc() {
		return this.bancoIdDoc;
	}

	public Caja getCaja() {
		return this.caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
		this.cajaId = (caja == null ? null : Short.valueOf(caja.getId().getCajaId()));
	}

	public Centroscosto getCentroCostos() {
		return this.centroCostos;
	}

	public void setCentroCostos(Centroscosto centroCostos) {
		this.centroCostos = centroCostos;
		this.centroCostosId = (centroCostos == null ? "" : centroCostos.getId().getCenId());
	}
	
	public String getDocCenCostosId() {
		return docCenCostosId;
	}

	public void setDocCenCostosId(String docCenCostosId) {
		this.docCenCostosId = docCenCostosId;
	}

	public Cliente getCliente() {
		return this.cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
		this.clienteId = (cliente == null ? "" : cliente.getId().getCliId());
	}

	public Comprobante getComprobante() {
		return this.comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
		this.comprobanteId = (comprobante == null ? null : Long.valueOf(comprobante.getId().getCmpid())).longValue();

		this.tipo = (comprobante == null ? 0 : comprobante.getTipo().shortValueExact());
	}

	public void setBancoIdDoc(String bancoIdDoc) {
		this.bancoIdDoc = bancoIdDoc == null ? "" : bancoIdDoc;
	}

	public void setTarjetaNro(String tarjetaNro) {
		this.tarjetaNro = tarjetaNro == null ? "" : tarjetaNro;
	}

	public void setAutorizacion(String autorizacion) {
		this.autorizacion = autorizacion == null ? "" : autorizacion;
	}

	public void setPlan(String plan) {
		this.plan = plan == null ? "" : plan;
	}

	public void setAsId(String asId) {
		this.asId = (asId == null ? "" : asId);
	}

	public void setConciliado(String conciliado) {
		this.conciliado = (conciliado == null ? "" : conciliado);
	}

	public void setDocVinculado(String vinculado) {
		this.docVinculado = (vinculado == null ? "" : vinculado);
	}

	public Deposito getDepositoDestino() {
		return this.depositoDestino;
	}

	public void setDepositoDestino(Deposito depositoDestino) {
		this.depositoDestino = depositoDestino;
		this.depositoDestinoId = (depositoDestino == null ? null : Short.valueOf(depositoDestino.getId().getDepId()));
	}

	public Deposito getDepositoOrigen() {
		return this.depositoOrigen;
	}

	public void setDepositoOrigen(Deposito depositoOrigen) {
		this.depositoOrigen = depositoOrigen;
		this.depositoOrigenId = (depositoOrigen == null ? null : Short.valueOf(depositoOrigen.getId().getDepId()));
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
		this.monedaId = (moneda == null ? null : Short.valueOf(moneda.getId().getMndId()));
	}

	public void setPlanPagos(Planpago planPagos) {
		this.planPagos = planPagos;
		this.planPagosId = (planPagos == null ? "" : planPagos.getId().getPPid());
	}

	public void setCondicion(Planpago condicion) {
		this.condicion = condicion;
		this.condicionId = (condicion == null ? null : condicion.getId().getPPid());
	}

	public void setPreciosVenta(Preciosventa preciosVenta) {
		this.preciosVenta = preciosVenta;
		this.preciosVentaId = (preciosVenta == null ? 0 : Short.valueOf(preciosVenta.getId().getPrecioVentaId()));
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
		this.proveedorId = (proveedor == null ? "" : proveedor.getId().getPrvId());
	}

	public void setVendedor(Vendedore vendedor) {
		this.vendedor = vendedor;
		this.vendedorId = (vendedor == null ? "" : vendedor.getId().getVenId());
	}

	public void setLineas(List<Linea> lineas) {
		this.lineas = lineas;
		this.ultLin = Integer.valueOf(lineas == null ? 0 : lineas.size());
	}

	public void setArtId(String artId) {
		this.artId = (artId == null ? "" : artId);
	}

	public void setPendiente(String pendiente) {
		this.pendiente = pendiente;
	}

	public void setFanfold1(String value) {
		this.fanfold1 = (value == null ? "" : value);
	}

	public void setFanfold2(String value) {
		this.fanfold2 = (value == null ? "" : value);
	}

	public void setFanfold3(String value) {
		this.fanfold3 = value == null ? "" : value;
	}

	public void setFanfold4(String value) {
		this.fanfold4 = value == null ? "" : value;
	}

	public void setFanfold5(String value) {
		this.fanfold5 = value == null ? "" : value;
	}

	public void setTextoIdDoc(String textoIdDoc) {
		this.textoIdDoc = textoIdDoc == null ? "" : textoIdDoc;
	}

	public String getAsId() {
		return this.asId;
	}

	public String getAutorizacion() {
		return this.autorizacion;
	}

	public byte[] getBlob() {
		return this.blob;
	}

	public void setBlob(byte[] blob) {
		this.blob = blob;
	}

	public String getBlobExt() {
		return this.blobExt;
	}

	public String getConciliado() {
		return this.conciliado;
	}

	public BigDecimal getDescuentos() {
		return this.descuentos;
	}

	public void setDescuentos(BigDecimal descuentos) {
		this.descuentos = descuentos;
	}

	public BigDecimal getDescuentosPorc() {
		return this.descuentosPorc;
	}

	public void setDescuentosPorc(BigDecimal descuentosPorc) {
		this.descuentosPorc = descuentosPorc;
	}

	public String getEmitido() {
		return this.emitido;
	}

	public void setEmitido(String emitido) {
		this.emitido = emitido;
	}

	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getEstadoFecha() {
		return this.estadoFecha;
	}

	public String getFanfold1() {
		return this.fanfold1;
	}

	public String getFanfold2() {
		return this.fanfold2;
	}

	public String getFanfold3() {
		return this.fanfold3;
	}

	public String getFanfold4() {
		return this.fanfold4;
	}

	public String getFanfold5() {
		return this.fanfold5;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Date getFecha2() {
		return this.fecha2;
	}

	public void setFecha2(Date fecha2) {
		this.fecha2 = fecha2;
	}

	public BigDecimal getIva() {
		return this.iva;
	}

	public void setIva(BigDecimal iva) {
		this.iva = iva;
	}

	public String getNotas() {
		return this.notas;
	}

	public String getOrigen() {
		return this.origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getPendiente() {
		return this.pendiente;
	}

	public String getPlan() {
		return this.plan;
	}

	public BigDecimal getRedondeo() {
		return this.redondeo;
	}

	public void setRedondeo(BigDecimal redondeo) {
		this.redondeo = redondeo;
	}

	public Date getRegistroFecha() {
		return this.registroFecha;
	}

	public void setRegistroFecha(Date registroFecha) {
		this.registroFecha = registroFecha;
	}

	public Date getRegistroHora() {
		try {
			return new SimpleDateFormat(REGISTRO_HORA_FORMAT).parse(this.registroHora);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public void setRegistroHora(Date registroHora) {
		this.registroHora = new SimpleDateFormat(REGISTRO_HORA_FORMAT).format(registroHora);
	}

	public BigDecimal getSaldo() {
		return this.saldo;
	}

	public void setSaldo(BigDecimal saldo) {
		this.saldo = saldo;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public BigInteger getNumero() {
		return this.numero;
	}

	public void setNumero(BigInteger numero) {
		this.numero = numero;
	}

	public BigDecimal getSubTotal() {
		return this.subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public String getTarjetaNro() {
		return this.tarjetaNro;
	}

	public short getTipo() {
		return this.tipo;
	}

	public void setTipo(short tipo) {
		this.tipo = tipo;
	}

	public String getTitular() {
		return this.titular;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public String getDocVinculado() {
		return this.docVinculado;
	}

	public String getTextoIdDoc() {
		return this.textoIdDoc;
	}

	public List<Linea> getLineas() {
		return this.lineas;
	}

	public List<Doccuota> getCuotas() {
		return this.cuotas;
	}

	public void setCuotas(List<Doccuota> cuotas) {
		this.cuotas = cuotas;
	}

	public Set<Vinculosdoc> getRecibosVinculados() {
		return this.recibosVinculados;
	}

	public void setRecibosVinculados(Set<Vinculosdoc> recibosVinculados) {
		this.recibosVinculados = recibosVinculados;
	}

	public Set<Vinculosdoc> getFacturasVinculadas() {
		return this.facturasVinculadas;
	}

	public void setFacturasVinculadas(Set<Vinculosdoc> facturasVinculadas) {
		this.facturasVinculadas = facturasVinculadas;
	}

	public Set<Docfp> getPagos() {
		return this.pagos;
	}

	public void setPagos(Set<Docfp> pagos) {
		this.pagos = pagos;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public short getLocalId() {
		return this.localId;
	}

	public void setLocalId(short localId) {
		this.localId = localId;
	}

	public Planpago getPlanPagos() {
		return this.planPagos;
	}

	public Planpago getCondicion() {
		return this.condicion;
	}

	public Preciosventa getPreciosVenta() {
		return this.preciosVenta;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public Vendedore getVendedor() {
		return this.vendedor;
	}

	public String getArtId() {
		return this.artId;
	}

	public List<ParticipacionVendedor> getParticipaciones() {
		return this.participaciones;
	}

	public void setParticipaciones(List<ParticipacionVendedor> participaciones) {
		this.participaciones = participaciones;
	}

	public Integer getCantidadBultos() {
		return this.cantidadBultos;
	}

	public void setCantidadBultos(Integer cantidadBultos) {
		this.cantidadBultos = cantidadBultos;
	}

	public Entrega getEntrega() {
		return this.entrega;
	}

	public void setEntrega(Entrega entrega) {
		this.entrega = entrega;
		this.entrega_id = (entrega == null ? null : Long.valueOf(entrega.getId().getEntId()));
	}

	public Docruc getDocruc() {
		if (this.docruc == null) {
			return null;
		}
		return this.docruc.size() == 0 ? null : (Docruc) this.docruc.iterator().next();
	}

	public void setDocruc(Docruc value) {
		if (this.docruc == null)
			this.docruc = new HashSet<Docruc>();
		else {
			this.docruc.clear();
		}
		this.docruc.add(value);
	}

	public short getUsuarioId() {
		return this.usuarioId;
	}

	public void setUsuarioId(short usuarioId) {
		this.usuarioId = usuarioId;
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getCostoOperativo() {
		return this.costoOperativo;
	}

	public void setCostoOperativo(BigDecimal costoOperativo) {
		this.costoOperativo = costoOperativo;
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

	public String getAgencia() {
		return this.agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
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

	public String getDocId() {
		return id != null ? String.valueOf(id.getDocId()) : null;
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

	public String getDepartamento() {
		return departamento;
	}

	public void setDepartamento(String departamento) {
		this.departamento = departamento;
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

	public Boolean comprobanteComputaIva() {
		if (comprobante.getCodigo().equals("122") || comprobante.getCodigo().equals("124")) {
			return false;
		}
		return !(comprobante.isExento());
	}

	public BigDecimal getCoeficienteImp() {
		return coeficienteImp != null ? coeficienteImp : BigDecimal.ONE;
	}

	public void setCoeficienteImp(BigDecimal coeficienteImp) {
		this.coeficienteImp = coeficienteImp;
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

	public Short getTipoCFEidDoc() {
		return tipoCFEidDoc;
	}

	public void setTipoCFEidDoc(Short tipoCFEidDoc) {
		this.tipoCFEidDoc = tipoCFEidDoc;
	}

	public Integer getDocCFEId() {
		return docCFEId;
	}

	public void setDocCFEId(Integer docCFEId) {
		this.docCFEId = docCFEId;
	}

	public String getDocMensaje() {
		return docMensaje;
	}

	public void setDocMensaje(String docMensaje) {
		this.docMensaje = docMensaje;
	}

	public Date getDocEntregaFecha() {
		return docEntregaFecha;
	}

	public void setDocEntregaFecha(Date docEntregaFecha) {
		this.docEntregaFecha = docEntregaFecha;
	}

	public String getDocEntregaReparto() {
		return docEntregaReparto;
	}

	public void setDocEntregaReparto(String docEntregaReparto) {
		this.docEntregaReparto = docEntregaReparto;
	}

	public Short getDocCFEstatusAcuse() {
		return docCFEstatusAcuse;
	}

	public void setDocCFEstatusAcuse(Short docCFEstatusAcuse) {
		this.docCFEstatusAcuse = docCFEstatusAcuse;
	}

	public Short getcAEiddDoc() {
		return cAEiddDoc;
	}

	public void setcAEiddDoc(Short cAEiddDoc) {
		this.cAEiddDoc = cAEiddDoc;
	}

	public String getDocCFEetapa() {
		return docCFEetapa;
	}

	public void setDocCFEetapa(String docCFEetapa) {
		this.docCFEetapa = docCFEetapa;
	}

	public String getTipoCFERef() {
		return tipoCFERef;
	}

	public void setTipoCFERef(String tipoCFERef) {
		this.tipoCFERef = tipoCFERef;
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

	public byte[] getCodigoQR() {
		return codigoQR;
	}

	public void setCodigoQR(byte[] codigoQR) {
		this.codigoQR = codigoQR;
	}

	public Date getCAEemision() {
		return CAEemision;
	}

	public void setCAEemision(Date cAEemison) {
		CAEemision = cAEemison;
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

	public long getComprobanteId() {
		return comprobanteId;
	}

	public void setComprobanteId(long comprobanteId) {
		this.comprobanteId = comprobanteId;
	}

	public Short getCuentaId() {
		return cuentaId;
	}

	public void setCuentaId(Short cuentaId) {
		this.cuentaId = cuentaId;
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

	public String getConcepto() {
		return concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Short getUltFP() {
		return ultFP;
	}

	public void setUltFP(Short ultFP) {
		this.ultFP = ultFP;
	}

	public Short getIvaIdDoc() {
		return ivaIdDoc;
	}

	public void setIvaIdDoc(Short ivaIdDoc) {
		this.ivaIdDoc = ivaIdDoc;
	}

	public String getDocIncoterm() {
		return docIncoterm;
	}

	public void setDocIncoterm(String docIncoterm) {
		this.docIncoterm = docIncoterm;
	}

	public Short getDocExpTransporte() {
		return docExpTransporte;
	}

	public void setDocExpTransporte(Short docExpTransporte) {
		this.docExpTransporte = docExpTransporte;
	}

	public Short getDocModExp() {
		return docModExp;
	}

	public void setDocModExp(Short docModExp) {
		this.docModExp = docModExp;
	}

	public String getConceptoIdDoc() {
		return conceptoIdDoc;
	}

	public void setConceptoIdDoc(String conceptoIdDoc) {
		this.conceptoIdDoc = conceptoIdDoc;
	}

	public Integer getDocDocId() {
		return docDocId;
	}

	public void setDocDocId(Integer docDocId) {
		this.docDocId = docDocId;
	}

	public long getDocShoppingEstado() {
		return docShoppingEstado;
	}

	public void setDocShoppingEstado(long docShoppingEstado) {
		this.docShoppingEstado = docShoppingEstado;
	}

	public String getDocShoppingIntervencion() {
		return docShoppingIntervencion;
	}

	public void setDocShoppingIntervencion(String docShoppingIntervencion) {
		this.docShoppingIntervencion = docShoppingIntervencion;
	}

	public String getCentroCostosId() {
		return centroCostosId;
	}

	public void setCentroCostosId(String centroCostosId) {
		this.centroCostosId = centroCostosId;
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

	public Short getTipoCFEid() {
		return tipoCFEid;
	}

	public void setTipoCFEid(Short tipoCFEid) {
		this.tipoCFEid = tipoCFEid;
	}

	public BigDecimal getDocTCC() {
		return docTCC;
	}

	public void setDocTCC(BigDecimal docTCC) {
		this.docTCC = docTCC;
	}

	public BigDecimal getDocTCF() {
		return docTCF;
	}

	public void setDocTCF(BigDecimal docTCF) {
		this.docTCF = docTCF;
	}

	public Short getDocRecMdaId() {
		return docRecMdaId;
	}

	public void setDocRecMdaId(Short docRecMdaId) {
		this.docRecMdaId = docRecMdaId;
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

	public Moneda getDocRecMda() {
		return docRecMda;
	}

	public void setDocRecMda(Moneda docRecMda) {
		this.docRecMda = docRecMda;
		this.docRecMdaId = (docRecMda == null ? null : Short.valueOf(docRecMda.getId().getMndId()));
	}

	public Set<Vinculosfp> getVinculosfp() {
		return vinculosfp;
	}

	public void setVinculosfp(Set<Vinculosfp> vinculosfp) {
		this.vinculosfp = vinculosfp;
	}

	public String getDocCFEFileName() {
		return docCFEFileName;
	}

	public void setDocCFEFileName(String docCFEFileName) {
		this.docCFEFileName = docCFEFileName;
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
	
	public Date getCAEvencimiento() {
		return CAEvencimiento;
	}

	public void setCAEvencimiento(Date cAEvencimiento) {
		CAEvencimiento = cAEvencimiento;
	}

}