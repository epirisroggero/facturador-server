package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * The persistent class for the vinculosfp database table.
 * 
 */
@Entity
@Table(name="vinculosfp")
public class Vinculosfp implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VinculosfpPK id;

	@Column(name="VinIE")
	private String vinIE;		
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "docIdFP1", referencedColumnName = "DocId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Documento docFP1;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "docIdFP2", referencedColumnName = "DocId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Documento docFP2;

	
    public Vinculosfp() {
    }

	public VinculosfpPK getId() {
		return this.id;
	}

	public void setId(VinculosfpPK id) {
		this.id = id;
	}
	
	public String getVinIE() {
		return this.vinIE;
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