package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import uy.com.tmwc.facturator.dto.ICodigoNombre;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "rubros")
@CatalogEntity(useNamedQuery = true)
public class Rubro extends PersistentEntity<RubroPK> implements Serializable, HasId<RubroPK>, ICodigoNombre  {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RubroPK id;
	
	@SuppressWarnings("unused")
	@Column(name = "RubId", insertable = false, updatable = false)
	private String codigo;

	@Column(name = "GruRubId")
	private String gruRubId;

	@Column(name = "MndIdRub")
	private short mndIdRub;

	@ManyToOne
	@JoinColumns({ @javax.persistence.JoinColumn(name = "MndIdRub", referencedColumnName = "MndId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Moneda moneda;

	@Column(name = "RubColumna")
	private short columna;

	@Column(name = "RubDC")
	private String dc;

	@Column(name = "RubFila")
	private short fila;

	@Column(name = "RubIdNom")
	private String idNom;

	@Column(name = "RubImputable")
	private String imputable;

	@Column(name = "RubInflacion")
	private String inflacion;

	@Column(name = "RubNom")
	private String nombre;

	@Column(name = "RubPresentacion")
	private String presentacion;

	
	public Rubro(String codigo, String nombre) {
		setCodigo(codigo);
		
		this.nombre = nombre;
	}	

	public RubroPK getId() {
		return this.id;
	}

	public void setId(RubroPK id) {
		this.id = id;
	}

	public String getGruRubId() {
		return this.gruRubId;
	}

	public void setGruRubId(String gruRubId) {
		this.gruRubId = gruRubId;
	}

	public Moneda getMoneda() {
		return this.moneda;
	}

	public short getColumna() {
		return this.columna;
	}

	public void setColumna(short columna) {
		this.columna = columna;
	}

	public String getDc() {
		return this.dc;
	}

	public void setDc(String dc) {
		this.dc = dc;
	}

	public short getFila() {
		return this.fila;
	}

	public void setFila(short fila) {
		this.fila = fila;
	}

	public String getIdNom() {
		return this.idNom;
	}

	public void setIdNom(String idNom) {
		this.idNom = idNom;
	}

	public String getImputable() {
		return this.imputable;
	}

	public void setImputable(String imputable) {
		this.imputable = imputable;
	}

	public String getInflacion() {
		return this.inflacion;
	}

	public void setInflacion(String inflacion) {
		this.inflacion = inflacion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPresentacion() {
		return this.presentacion;
	}

	public void setPresentacion(String presentacion) {
		this.presentacion = presentacion;
	}

	public short getMndIdRub() {
		return mndIdRub;
	}

	public void setMndIdRub(short mndIdRub) {
		this.mndIdRub = mndIdRub;
	}

	public String getCodigo() {
		return this.id.getRubId();
	}
	
	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new RubroPK();
		}
		this.id.setRubId(value);

	}

}