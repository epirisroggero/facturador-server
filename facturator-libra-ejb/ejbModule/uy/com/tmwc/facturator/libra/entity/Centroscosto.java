package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.facturator.dto.ICodigoNombre;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "centroscosto")
@CatalogEntity(useNamedQuery = true)
public class Centroscosto extends PersistentEntity<CentroscostoPK> implements Serializable, HasId<CentroscostoPK>, ICodigoNombre {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CentroscostoPK id;

	@SuppressWarnings("unused")
	@Column(name = "CenId", insertable = false, updatable = false)
	private String codigo;

	@Column(name = "CenNom")
	private String nombre;

	public Centroscosto(){
	}
			
	public Centroscosto(String codigo, String nombre) {
		setCodigo(codigo);

		this.nombre = nombre;
	}

	public CentroscostoPK getId() {
		return this.id;
	}

	public void setId(CentroscostoPK id) {
		this.id = id;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return this.id.getCenId();
	}

	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new CentroscostoPK();
		}
		this.id.setCenId(value);

	}

}