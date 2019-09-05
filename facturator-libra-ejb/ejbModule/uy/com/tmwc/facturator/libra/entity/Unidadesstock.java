package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import uy.com.tmwc.facturator.dto.ICodigoNombre;
import uy.com.tmwc.utils.orm.CatalogEntity;


/**
 * The persistent class for the unidadesstock database table.
 * 
 */
@Entity
@Table(name="unidadesstock")
@CatalogEntity(useNamedQuery = true)
public class Unidadesstock extends PersistentEntity<UnidadesstockPK> implements Serializable, ICodigoNombre   {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UnidadesstockPK id;

	@SuppressWarnings("unused")
	@Column(name = "UnidadId", insertable = false, updatable = false)
	private String codigo;

	@Column(name = "UnidadNom")
	private String nombre;
	
	@Column(name="UnidadSim")
	private String unidadSim;

    public Unidadesstock() {
    }
    
	public Unidadesstock(String codigo, String nombre) {
		setCodigo(codigo);

		this.nombre = nombre;
	}


	public UnidadesstockPK getId() {
		return this.id;
	}

	public void setId(UnidadesstockPK id) {
		this.id = id;
	}
	
	public String getUnidadSim() {
		return this.unidadSim;
	}

	public void setUnidadSim(String unidadSim) {
		this.unidadSim = unidadSim;
	}
	
	public String getCodigo() {
		return this.id.getUnidadId();
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
		if (this.id == null) {
			this.id = new UnidadesstockPK();
		}
		this.id.setUnidadId(codigo);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


}