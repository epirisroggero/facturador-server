package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

public class VinculosFP implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int docIdFP1;

	private int docIdFP2;
	
	private short vinFPTipo;
	
	private String vinIE;
	
	private Documento docFP1;
	
	private Documento docFP2;

	public int getDocIdFP1() {
		return docIdFP1;
	}

	public void setDocIdFP1(int docIdFP1) {
		this.docIdFP1 = docIdFP1;
	}

	public int getDocIdFP2() {
		return docIdFP2;
	}

	public void setDocIdFP2(int docIdFP2) {
		this.docIdFP2 = docIdFP2;
	}

	public short getVinFPTipo() {
		return vinFPTipo;
	}

	public void setVinFPTipo(short vinFPTipo) {
		this.vinFPTipo = vinFPTipo;
	}

	public String getVinIE() {
		return vinIE;
	}

	public void setVinIE(String vinIE) {
		this.vinIE = vinIE;
	}

	public Documento getDocFP1() {
		return docFP1;
	}

	public void setDocFP1(Documento docFP1) {
		this.docFP1 = docFP1;
	}

	public Documento getDocFP2() {
		return docFP2;
	}

	public void setDocFP2(Documento docFP2) {
		this.docFP2 = docFP2;
	}
	
	

}
