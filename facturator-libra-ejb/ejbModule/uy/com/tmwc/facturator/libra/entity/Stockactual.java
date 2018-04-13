package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.OptimisticLockType;


/**
 * The persistent class for the stockactual database table.
 * 
 */
@Entity
@Table(name="stockactual")
@org.hibernate.annotations.Entity(dynamicUpdate = true, optimisticLock = OptimisticLockType.ALL)
public class Stockactual implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private StockactualPK id;

	private BigDecimal SAcantidad;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "DepIdSA", referencedColumnName = "DepId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Deposito deposito;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @javax.persistence.JoinColumn(name = "ArtIdSA", referencedColumnName = "ArtId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Articulo articulo;

    public Stockactual() {
    }
    
    public void add(BigDecimal cantidad) {
    	if (SAcantidad == null) {
    		SAcantidad = cantidad;
    	} else {
    		SAcantidad = SAcantidad.add(cantidad);
    	}
    }

	public StockactualPK getId() {
		return this.id;
	}

	public void setId(StockactualPK id) {
		this.id = id;
	}
	
	public BigDecimal getSAcantidad() {
		return this.SAcantidad;
	}

	public void setSAcantidad(BigDecimal SAcantidad) {
		this.SAcantidad = SAcantidad;
	}

	public Deposito getDeposito() {
		return deposito;
	}

	public void setDeposito(Deposito deposito) {
		this.deposito = deposito;
		if (id == null) {
			id = new StockactualPK();
		}
		id.setDepIdSA(deposito != null ? deposito.getId().getDepId() : null);
	}

	public Articulo getArticulo() {
		return articulo;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
		if (id == null) {
			id = new StockactualPK();
		}
		id.setArtIdSA(articulo != null ? articulo.getCodigo() : null);
	}

}