package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.utils.orm.CatalogEntity;

/**
 * The persistent class for the conceptos database table.
 * 
 */
@Entity
@Table(name = "conceptos")
@CatalogEntity
public class Concepto extends PersistentEntity<ConceptoPK> implements Serializable, HasId<ConceptoPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConceptoPK id;

	@Column(name = "ConceptoActivo")
	private String conceptoActivo;

	@Column(name = "ConceptoIdNom")
	private String conceptoIdNom;

	@Column(name = "ConceptoNom")
	private String nombre;

	@Column(name = "ConceptoRetencion")
	private String conceptoRetencion;

	@Column(name = "ConceptoRubro")
	private String conceptoRubro;

	@Column(name = "ConceptoTipo")
	private String conceptoTipo;

	@Column(name = "ConceptoTotales")
	private String conceptoTotales;

	@Column(name = "GrupoCptId")
	private String grupoCptId;

	@Column(name = "IvaIdConcepto")
	private Short ivaIdConcepto;


	public ConceptoPK getId() {
		return this.id;
	}

	public void setId(ConceptoPK id) {
		this.id = id;
	}

	public String getConceptoActivo() {
		return this.conceptoActivo;
	}

	public void setConceptoActivo(String conceptoActivo) {
		this.conceptoActivo = conceptoActivo;
	}

	public String getConceptoIdNom() {
		return this.conceptoIdNom;
	}

	public void setConceptoIdNom(String conceptoIdNom) {
		this.conceptoIdNom = conceptoIdNom;
	}

	public String getConceptoRetencion() {
		return this.conceptoRetencion;
	}

	public void setConceptoRetencion(String conceptoRetencion) {
		this.conceptoRetencion = conceptoRetencion;
	}

	public String getConceptoRubro() {
		return this.conceptoRubro;
	}

	public void setConceptoRubro(String conceptoRubro) {
		this.conceptoRubro = conceptoRubro;
	}

	public String getConceptoTipo() {
		return this.conceptoTipo;
	}

	public void setConceptoTipo(String conceptoTipo) {
		this.conceptoTipo = conceptoTipo;
	}

	public String getConceptoTotales() {
		return this.conceptoTotales;
	}

	public void setConceptoTotales(String conceptoTotales) {
		this.conceptoTotales = conceptoTotales;
	}

	public String getGrupoCptId() {
		return this.grupoCptId;
	}

	public void setGrupoCptId(String grupoCptId) {
		this.grupoCptId = grupoCptId;
	}

	public Short getIvaIdConcepto() {
		return this.ivaIdConcepto;
	}

	public void setIvaIdConcepto(Short ivaIdConcepto) {
		this.ivaIdConcepto = ivaIdConcepto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


}