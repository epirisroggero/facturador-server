package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import uy.com.tmwc.utils.orm.CatalogEntity;


/**
 * The persistent class for the fanfold database table.
 * 
 */
@Entity
@Table(name="fanfold")
@CatalogEntity
public class Fanfold extends PersistentEntity<FanfoldPK> implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private FanfoldPK id;

	@Column(name="LocIdFold")
	private short locIdFold;

	@Column(name="NumFoldIncrementar")
	private short numFoldIncrementar;

	@Column(name="NumFoldNom")
	private String nombre;

	@Column(name="NumFoldNumero")
	private Long numFoldNumero;

	@Column(name="NumFoldSerie")
	private String numFoldSerie;

    public Fanfold() {
    }

	public FanfoldPK getId() {
		return this.id;
	}

	public void setId(FanfoldPK id) {
		this.id = id;
	}
	
	public short getLocIdFold() {
		return this.locIdFold;
	}

	public void setLocIdFold(short locIdFold) {
		this.locIdFold = locIdFold;
	}

	public short getNumFoldIncrementar() {
		return this.numFoldIncrementar;
	}

	public void setNumFoldIncrementar(short numFoldIncrementar) {
		this.numFoldIncrementar = numFoldIncrementar;
	}


	public Long getNumFoldNumero() {
		return this.numFoldNumero;
	}

	public void setNumFoldNumero(Long numFoldNumero) {
		this.numFoldNumero = numFoldNumero;
	}

	public String getNumFoldSerie() {
		return this.numFoldSerie;
	}

	public void setNumFoldSerie(String numFoldSerie) {
		this.numFoldSerie = numFoldSerie;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}