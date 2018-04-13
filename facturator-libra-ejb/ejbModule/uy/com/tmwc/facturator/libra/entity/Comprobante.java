package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import uy.com.tmwc.facturator.dto.ICodigoNombre;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "comprobantes")
@CatalogEntity(useNamedQuery = true, abreviated = true, prefix = "cmp")
public class Comprobante extends PersistentEntity<ComprobantePK> implements Serializable, ICodigoNombre, HasId<ComprobantePK> {
	private static final long serialVersionUID = 1L;
	
	public static final int VENTA_CREDITO = uy.com.tmwc.facturator.entity.Comprobante.VENTA_CREDITO;
	public static final int NOTA_CREDITO = uy.com.tmwc.facturator.entity.Comprobante.NOTA_CREDITO;
	public static final int VENTA_CONTADO = uy.com.tmwc.facturator.entity.Comprobante.VENTA_CONTADO;
	public static final int DEVOLUCION_CONTADO = uy.com.tmwc.facturator.entity.Comprobante.DEVOLUCION_CONTADO;
	public static final int RECIBO_COBRO = uy.com.tmwc.facturator.entity.Comprobante.RECIBO_COBRO;
	
	public static final int COMPRA_CREDITO = uy.com.tmwc.facturator.entity.Comprobante.COMPRA_CREDITO;
	public static final int COMPRA_CONTADO = uy.com.tmwc.facturator.entity.Comprobante.COMPRA_CONTADO;


	@EmbeddedId
	private ComprobantePK id;

	@Column(name = "CMPABREVIA")
	private String cmpabrevia;

	@Column(name = "CMPASIULT")
	private BigDecimal cmpasiult;

	@Column(name = "CMPFICHA")
	private String cmpficha;

	@Column(name = "CMPFIFO")
	private String cmpfifo;

	@Column(name = "CMPGASTOS")
	private String cmpgastos;

	@Column(name = "CMPIDABREVIA")
	private String cmpidabrevia;

	@Column(name = "CmpIdNom", length=70, nullable=false)
	private String cmpidnom;

	@Column(name = "CMPIVA")
	private String cmpiva;

	@Column(name = "CMPLIBROS")
	private String cmplibros;

	@Column(name = "CmpNom", length=40, nullable=false)
	private String cmpNom;

	@Column(name = "CMPPENDIENTE")
	private String cmppendiente;

	@Column(name = "CmpRubroSi")
	private String cmpRubroSi;

	@Column(name = "CMPSINSTOCK")
	private String cmpsinstock;

	@Column(name = "CMPTIPO")
	private BigDecimal tipo;

	@Column(name = "CmpTipoNom", length=40, nullable=false)
	private String cmptiponom;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "DEPIDCMPDESTINO", referencedColumnName = "DepId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EMPID", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Deposito depositoDestino;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "DEPIDCMPORIGEN", referencedColumnName = "DepId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EMPID", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Deposito depositoOrigen;

	@Column(name = "FORMAPAGOIDCMP")
	private BigDecimal formapagoidcmp;

	@Column(name = "FORMATOIDCMP")
	private String formatoidcmp;

	@Column(name = "LOCIDCMP")
	private BigDecimal locidcmp;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "NUMCMPID", referencedColumnName = "NumCmpId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EMPID", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Numerador numerador;

	@Column(name="NUMCMPID")
	private String numCmpId;

	@OneToMany(cascade = { javax.persistence.CascadeType.ALL }, mappedBy = "comprobante", fetch = FetchType.EAGER)
	@Cascade({ org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
	@OrderBy("retraso")
	private List<DescuentoPrometidoComprobante> descuentosPrometidos;
	
	
	@Column(name = "CmpEntrega", length=1, nullable=false)
	private String cmpEntrega = "N";
	
	@Column(name = "CmpContingencia", length=1, nullable=false)
	private String cmpContingencia = "N";
	
	@Column(name = "CmpExportacion", length=1, nullable=false)
	private String cmpExportacion = "N";
	
	@Column(name = "CmpNotaDebito", length=1, nullable=false)
	private String cmpNotaDebito = "N";
	
	@Column(name = "CmpEfactura", length=1, nullable=false)
	private String cmpEfactura = "N";
	
	@Column(name = "CmpActivo", length=1, nullable=false)
	private String cmpActivo = "S";
	
	@Column(name = "CmpRemitoInterno", length=1, nullable=false)
	private String cmpRemitoInterno = "N";
	
	@Column(name = "CmpNotas", length=1024, nullable=true)
	private String cmpNotas;
	
	
	public ComprobantePK getId() {
		return this.id;
	}

	public void setId(ComprobantePK id) {
		this.id = id;
	}

	public BigDecimal getTipo() {
		return this.tipo;
	}

	public void setTipo(BigDecimal tipo) {
		this.tipo = tipo;
	}

	public String getCmpabrevia() {
		return this.cmpabrevia;
	}

	public void setCmpabrevia(String cmpabrevia) {
		this.cmpabrevia = cmpabrevia;
	}

	public BigDecimal getCmpasiult() {
		return this.cmpasiult;
	}

	public void setCmpasiult(BigDecimal cmpasiult) {
		this.cmpasiult = cmpasiult;
	}

	public String getCmpficha() {
		return this.cmpficha;
	}

	public void setCmpficha(String cmpficha) {
		this.cmpficha = cmpficha;
	}

	public String getCmpfifo() {
		return this.cmpfifo;
	}

	public void setCmpfifo(String cmpfifo) {
		this.cmpfifo = cmpfifo;
	}

	public String getCmpgastos() {
		return this.cmpgastos;
	}

	public void setCmpgastos(String cmpgastos) {
		this.cmpgastos = cmpgastos;
	}

	public String getCmpidabrevia() {
		return this.cmpidabrevia;
	}

	public void setCmpidabrevia(String cmpidabrevia) {
		this.cmpidabrevia = cmpidabrevia;
	}

	public String getCmpidnom() {
		return this.cmpidnom;
	}

	public void setCmpidnom(String cmpidnom) {
		this.cmpidnom = cmpidnom;
	}

	public String getCmpiva() {
		return this.cmpiva;
	}

	public void setCmpiva(String cmpiva) {
		this.cmpiva = cmpiva;
	}

	public String getCmplibros() {
		return this.cmplibros;
	}

	public void setCmplibros(String cmplibros) {
		this.cmplibros = cmplibros;
	}

	public String getCmpnom() {
		return this.cmpNom;
	}

	public void setCmpnom(String cmpnom) {
		this.cmpNom = cmpnom;
	}

	public String getCmppendiente() {
		return this.cmppendiente;
	}

	public void setCmppendiente(String cmppendiente) {
		this.cmppendiente = cmppendiente;
	}

	public String getCmpRubroSi() {
		return this.cmpRubroSi;
	}

	public void setCmpRubroSi(String cmpRubroSi) {
		this.cmpRubroSi = cmpRubroSi;
	}

	public String getCmpsinstock() {
		return this.cmpsinstock;
	}

	public void setCmpsinstock(String cmpsinstock) {
		this.cmpsinstock = cmpsinstock;
	}

	public String getCmptiponom() {
		return this.cmptiponom;
	}

	public void setCmptiponom(String cmptiponom) {
		this.cmptiponom = cmptiponom;
	}

	public BigDecimal getFormapagoidcmp() {
		return this.formapagoidcmp;
	}

	public void setFormapagoidcmp(BigDecimal formapagoidcmp) {
		this.formapagoidcmp = formapagoidcmp;
	}

	public String getFormatoidcmp() {
		return this.formatoidcmp;
	}

	public void setFormatoidcmp(String formatoidcmp) {
		this.formatoidcmp = formatoidcmp;
	}

	public BigDecimal getLocidcmp() {
		return this.locidcmp;
	}

	public void setLocidcmp(BigDecimal locidcmp) {
		this.locidcmp = locidcmp;
	}

	public Deposito getDepositoDestino() {
		return this.depositoDestino;
	}

	public void setDepositoDestino(Deposito depositoDestino) {
		this.depositoDestino = depositoDestino;
	}

	public Deposito getDepositoOrigen() {
		return this.depositoOrigen;
	}

	public void setDepositoOrigen(Deposito depositoOrigen) {
		this.depositoOrigen = depositoOrigen;
	}

	public List<DescuentoPrometidoComprobante> getDescuentosPrometidos() {
		return this.descuentosPrometidos;
	}

	public void setDescuentosPrometidos(List<DescuentoPrometidoComprobante> descuentosPrometidos) {
		this.descuentosPrometidos = descuentosPrometidos;
	}

	public Numerador getNumerador() {
		return this.numerador;
	}

	public String getCodigo() {
		return String.valueOf(this.id.getCmpid());
	}

	public String getNombre() {
		return this.cmpNom;
	}

	public void setExento(boolean e) {
	}
	
	public boolean isExento() {
		return (cmpiva == null) || (cmpiva != null && cmpiva.equals("E"));
	}

	public String getCmpEntrega() {
		return cmpEntrega;
	}

	public void setCmpEntrega(String cmpEntrega) {
		this.cmpEntrega = cmpEntrega;
	}

	public String getCmpContingencia() {
		return cmpContingencia;
	}

	public void setCmpContingencia(String cmpContingencia) {
		this.cmpContingencia = cmpContingencia;
	}

	public String getCmpExportacion() {
		return cmpExportacion;
	}

	public void setCmpExportacion(String cmpExportacion) {
		this.cmpExportacion = cmpExportacion;
	}

	public String getCmpNotaDebito() {
		return cmpNotaDebito;
	}

	public void setCmpNotaDebito(String cmpNotaDebito) {
		this.cmpNotaDebito = cmpNotaDebito;
	}

	public String getCmpRemitoInterno() {
		return cmpRemitoInterno;
	}

	public void setCmpRemitoInterno(String cmpRemitoInterno) {
		this.cmpRemitoInterno = cmpRemitoInterno;
	}

	public String getCmpNotas() {
		return cmpNotas;
	}

	public void setCmpNotas(String cmpNotas) {
		this.cmpNotas = cmpNotas;
	}

	public String getCmpEfactura() {
		return cmpEfactura;
	}

	public void setCmpEfactura(String cmpEfactura) {
		this.cmpEfactura = cmpEfactura;
	}

	public String getCmpActivo() {
		return cmpActivo;
	}

	public void setCmpActivo(String cmpActivo) {
		this.cmpActivo = cmpActivo;
	}

	public String getNumCmpId() {
		return numCmpId;
	}

	public void setNumCmpId(String numCmpId) {
		this.numCmpId = numCmpId;
	}
	
}