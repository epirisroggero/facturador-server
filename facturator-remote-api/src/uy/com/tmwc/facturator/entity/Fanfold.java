package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

public class Fanfold extends CodigoNombreEntity implements Serializable {

	private static final long serialVersionUID = 1L;

	public Fanfold() {
	}	

	private short numFoldIncrementar;

	private Long numFoldNumero;

	private String numFoldSerie;

	private short locIdFold;

	public short getLocIdFold() {
		return locIdFold;
	}

	public void setLocIdFold(short locIdFold) {
		this.locIdFold = locIdFold;
	}

	public short getNumFoldIncrementar() {
		return numFoldIncrementar;
	}

	public void setNumFoldIncrementar(short numFoldIncrementar) {
		this.numFoldIncrementar = numFoldIncrementar;
	}

	public Long getNumFoldNumero() {
		return numFoldNumero;
	}

	public void setNumFoldNumero(Long numFoldNumero) {
		this.numFoldNumero = numFoldNumero;
	}

	public String getNumFoldSerie() {
		return numFoldSerie;
	}

	public void setNumFoldSerie(String numFoldSerie) {
		this.numFoldSerie = numFoldSerie;
	}
	
}
