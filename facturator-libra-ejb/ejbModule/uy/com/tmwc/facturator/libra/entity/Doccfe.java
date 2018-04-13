package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the doccfe database table.
 * 
 */
@Entity
@Table(name="doccfe")
public class Doccfe implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DoccfePK id;

	@Column(name="DocCFECodigoSeguridad")
	private String docCFECodigoSeguridad;

	@Column(name="DocCFEpdf")
	private String docCFEpdf;

    @Lob()
	@Column(name="DocCFEQR")
	private String docCFEQR;

    public Doccfe() {
    }

	public DoccfePK getId() {
		return this.id;
	}

	public void setId(DoccfePK id) {
		this.id = id;
	}
	
	public String getDocCFECodigoSeguridad() {
		return this.docCFECodigoSeguridad;
	}

	public void setDocCFECodigoSeguridad(String docCFECodigoSeguridad) {
		this.docCFECodigoSeguridad = docCFECodigoSeguridad;
	}

	public String getDocCFEpdf() {
		return this.docCFEpdf;
	}

	public void setDocCFEpdf(String docCFEpdf) {
		this.docCFEpdf = docCFEpdf;
	}

	public String getDocCFEQR() {
		return this.docCFEQR;
	}

	public void setDocCFEQR(String docCFEQR) {
		this.docCFEQR = docCFEQR;
	}

}