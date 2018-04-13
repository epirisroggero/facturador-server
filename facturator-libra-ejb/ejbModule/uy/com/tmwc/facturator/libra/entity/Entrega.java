package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.facturator.dto.ICodigoNombre;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "lfx_entrega")
@CatalogEntity(prefix = "ent", abreviated = true)
public class Entrega extends PersistentEntity<EntregaPK> implements
   ICodigoNombre, Serializable, HasId<EntregaPK> {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private EntregaPK id;
	
	@SuppressWarnings("unused")
	@Column(name = "codigo", insertable = false, updatable = false)
	private String codigo;

	@Column(name = "EntNom", length=50, nullable=false)
	private String nombre;

	@Column(name = "costo", precision=10, scale=2)
	private BigDecimal costo;

	@Column(name = "relevancia", precision=10, scale=2)
	private BigDecimal relevancia;
	
	public Entrega() {
	}

	public EntregaPK getId() {
		return this.id;
	}

	public void setId(EntregaPK id) {
		this.id = id;
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getRelevancia() {
		return this.relevancia;
	}

	public void setRelevancia(BigDecimal relevancia) {
		this.relevancia = relevancia;
	}

	public String getCodigo() {
		return this.id.getEntId();
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
		if (this.id == null) {
			this.id = new EntregaPK();
		}
		this.id.setEntId(codigo);
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


}