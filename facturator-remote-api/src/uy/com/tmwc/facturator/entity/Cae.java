package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

public class Cae implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private short CAEid;

	private int CAEdesde;

	private Date CAEemison;

	private int CAEhasta;

	private String CAEnom;

	private BigInteger CAEnro;

	private String CAEserie;

	private int CAEultimo;

	private Date CAEvencimiento;

	private short tipoCFEid;

	public int getCAEdesde() {
		return CAEdesde;
	}

	public void setCAEdesde(int cAEdesde) {
		CAEdesde = cAEdesde;
	}

	public Date getCAEemison() {
		return CAEemison;
	}

	public void setCAEemison(Date cAEemison) {
		CAEemison = cAEemison;
	}

	public int getCAEhasta() {
		return CAEhasta;
	}

	public void setCAEhasta(int cAEhasta) {
		CAEhasta = cAEhasta;
	}

	public String getCAEnom() {
		return CAEnom;
	}

	public void setCAEnom(String cAEnom) {
		CAEnom = cAEnom;
	}

	public BigInteger getCAEnro() {
		return CAEnro;
	}

	public void setCAEnro(BigInteger cAEnro) {
		CAEnro = cAEnro;
	}

	public String getCAEserie() {
		return CAEserie;
	}

	public void setCAEserie(String cAEserie) {
		CAEserie = cAEserie;
	}

	public int getCAEultimo() {
		return CAEultimo;
	}

	public void setCAEultimo(int cAEultimo) {
		CAEultimo = cAEultimo;
	}

	public Date getCAEvencimiento() {
		return CAEvencimiento;
	}

	public void setCAEvencimiento(Date cAEvencimiento) {
		CAEvencimiento = cAEvencimiento;
	}

	public short getTipoCFEid() {
		return tipoCFEid;
	}

	public void setTipoCFEid(short tipoCFEid) {
		this.tipoCFEid = tipoCFEid;
	}

	public short getCAEid() {
		return CAEid;
	}

	public void setCAEid(short cAEid) {
		CAEid = cAEid;
	}


}
