package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

public class VinculosFP implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int docIdFP1;

	private int docIdFP2;
	
	private short vinFPTipo;
	
	private String vinIE;

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
	
	

}
