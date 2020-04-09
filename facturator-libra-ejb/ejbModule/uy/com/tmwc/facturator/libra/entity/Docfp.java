package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "docfp")
public class Docfp extends PersistentEntity<DocfpPK> implements Serializable, HasId<DocfpPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DocfpPK id;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "DocId", referencedColumnName = "DocId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Documento documento;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "FormaPagoId", referencedColumnName = "FormaPagoId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Formaspago formaPago;

	@SuppressWarnings("unused")
	@Column(name = "FormaPagoId")
	private short formaPagoId;

	@Column(name = "FPImporte")
	private BigDecimal importe;

	@Column(name = "FPTotal")
	private BigDecimal total;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "MndIdFP", referencedColumnName = "MndId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Moneda moneda;

	@Column(name = "MndIdFP")
	private short monedaId;

	public DocfpPK getId() {
		return this.id;
	}

	public void setId(DocfpPK id) {
		this.id = id;
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

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
		this.id.setDocId(documento == null ? 0 : documento.getId().getDocId());
	}

	public Formaspago getFormaPago() {
		return this.formaPago;
	}

	public void setFormaPago(Formaspago formaPago) {
		this.formaPago = formaPago;
		this.formaPagoId = (formaPago == null ? 0 : formaPago.getId().getFormaPagoId());
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
		this.monedaId = (moneda == null ? 0 : moneda.getId().getMndId());
	}

	public short getMonedaId() {
		return monedaId;
	}

	public void setMonedaId(short monedaId) {
		this.monedaId = monedaId;
	}
}