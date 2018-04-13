package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;


/**
 * The persistent class for the cae database table.
 * 
 */
@Entity
@Table(name="cae")
public class Cae implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private CaePK id;

	private int CAEdesde;

    @Temporal( TemporalType.DATE)
	private Date CAEemison;

	private int CAEhasta;

	private String CAEnom;

	private BigInteger CAEnro;

	private String CAEserie;

	private int CAEultimo;

    @Temporal( TemporalType.DATE)
	private Date CAEvencimiento;

	@Column(name="TipoCFEid")
	private short tipoCFEid;

    public Cae() {
    }

	public CaePK getId() {
		return this.id;
	}

	public void setId(CaePK id) {
		this.id = id;
	}
	
	public int getCAEdesde() {
		return this.CAEdesde;
	}

	public void setCAEdesde(int CAEdesde) {
		this.CAEdesde = CAEdesde;
	}

	public Date getCAEemison() {
		return this.CAEemison;
	}

	public void setCAEemison(Date CAEemison) {
		this.CAEemison = CAEemison;
	}

	public int getCAEhasta() {
		return this.CAEhasta;
	}

	public void setCAEhasta(int CAEhasta) {
		this.CAEhasta = CAEhasta;
	}

	public String getCAEnom() {
		return this.CAEnom;
	}

	public void setCAEnom(String CAEnom) {
		this.CAEnom = CAEnom;
	}

	public BigInteger getCAEnro() {
		return this.CAEnro;
	}

	public void setCAEnro(BigInteger CAEnro) {
		this.CAEnro = CAEnro;
	}

	public String getCAEserie() {
		return this.CAEserie;
	}

	public void setCAEserie(String CAEserie) {
		this.CAEserie = CAEserie;
	}

	public int getCAEultimo() {
		return this.CAEultimo;
	}

	public void setCAEultimo(int CAEultimo) {
		this.CAEultimo = CAEultimo;
	}

	public Date getCAEvencimiento() {
		return this.CAEvencimiento;
	}

	public void setCAEvencimiento(Date CAEvencimiento) {
		this.CAEvencimiento = CAEvencimiento;
	}

	public short getTipoCFEid() {
		return this.tipoCFEid;
	}

	public void setTipoCFEid(short tipoCFEid) {
		this.tipoCFEid = tipoCFEid;
	}

}