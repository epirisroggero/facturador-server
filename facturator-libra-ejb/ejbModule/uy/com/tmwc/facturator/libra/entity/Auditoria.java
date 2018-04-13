package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import java.util.Date;

@Entity
@Table(name = "lfx_auditoria")
public class Auditoria extends PersistentEntity<AuditoriaPK> implements Serializable, HasId<AuditoriaPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AuditoriaPK id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AudFechaHora")
	private Date audFechaHora;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "FechaEnv")
	private Date fechaEnv;

	@Lob()
	@Column(name = "Destinatarios")
	private String destinatarios;
	
	@Column(name = "DocId")
	private String docId;

	@Column(name = "UsuId")
	private short usuId;

	@Column(name = "UsuNom")
	private String usuNom;

	@Lob()
	@Column(name = "Problemas")
	private String problemas;

	@Lob()
	@Column(name = "Notas")
	private String notas;

	public Auditoria() {
	}

	public AuditoriaPK getId() {
		return this.id;
	}

	public void setId(AuditoriaPK id) {
		this.id = id;
	}	

	public Date getAudFechaHora() {
		return audFechaHora;
	}

	public void setAudFechaHora(Date audFechaHora) {
		this.audFechaHora = audFechaHora;
	}

	public Date getFechaEnv() {
		return fechaEnv;
	}

	public void setFechaEnv(Date fechaEnv) {
		this.fechaEnv = fechaEnv;
	}

	public String getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public short getUsuId() {
		return usuId;
	}

	public void setUsuId(short usuId) {
		this.usuId = usuId;
	}

	public String getUsuNom() {
		return usuNom;
	}

	public void setUsuNom(String usuNom) {
		this.usuNom = usuNom;
	}

	public String getProblemas() {
		return problemas;
	}

	public void setProblemas(String problemas) {
		this.problemas = problemas;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}


}