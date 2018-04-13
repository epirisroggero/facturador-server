package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "lfx_descuentoprometidocomprobante")
public class DescuentoPrometidoComprobante extends PersistentEntity<DescuentoPrometidoComprobantePK> implements Serializable, HasId<DescuentoPrometidoComprobantePK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DescuentoPrometidoComprobantePK id;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @JoinColumn(name = "cmpid", referencedColumnName = "CMPID", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EMPID", insertable = false, updatable = false) })
	private Comprobante comprobante;
 	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @JoinColumn(name = "categoriaCliente", referencedColumnName = "CategCliId", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Categoriasclientes categCliente;
	
	@Column(name = "cmpid")
	private short cmpid;

	@Column(name = "descuento")
	private BigDecimal descuento;
	
	@Column(name = "retraso")
	private int retraso;
	
	@Column(name = "categoriaCliente")
	private String categoriaCliente;

	
	public DescuentoPrometidoComprobante() {
	}

	public DescuentoPrometidoComprobantePK getId() {
		return id;
	}

	public void setId(DescuentoPrometidoComprobantePK id) {
		this.id = id;
	}

	public Comprobante getComprobante() {
		return comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public short getCmpid() {
		return this.cmpid;
	}

	public void setCmpid(short cmpid) {
		this.cmpid = cmpid;
	}

	public BigDecimal getDescuento() {
		return this.descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}

	public int getRetraso() {
		return this.retraso;
	}

	public void setRetraso(int retraso) {
		this.retraso = retraso;
	}

	public String getCategoriaCliente() {
		return this.categoriaCliente;
	}

	public void setCategoriaCliente(String categoriaCliente) {
		this.categoriaCliente = categoriaCliente;
	}
	
	public Categoriasclientes getCategCliente() {
		return categCliente;
	}

	public void setCategCliente(Categoriasclientes categCliente) {
		this.categCliente = categCliente;
	}

	public String getDpcId() {
		return id != null ? String.valueOf(id.getDpcId()) : null;
	}

}
