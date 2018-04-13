package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.util.Date;

public class Auditoria implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	private String empId;

	private String audId;
	
	private String usuId;
	
	private String usuNom;
	
	private String docId;
	
	private String problemas;

	private Date fechaEnv;
	
	private Date audFechaHora;
	
	private String destinatarios;
	
	private String notas;


	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getAudId() {
		return audId;
	}

	public void setAudId(String audId) {
		this.audId = audId;
	}

	public String getUsuId() {
		return usuId;
	}

	public void setUsuId(String usuId) {
		this.usuId = usuId;
	}

	public String getUsuNom() {
		return usuNom;
	}

	public void setUsuNom(String usuNom) {
		this.usuNom = usuNom;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getProblemas() {
		return problemas;
	}

	public void setProblemas(String problemas) {
		this.problemas = problemas;
	}

	public Date getFechaEnv() {
		return fechaEnv;
	}

	public void setFechaEnv(Date fechaEnv) {
		this.fechaEnv = fechaEnv;
	}

	public Date getAudFechaHora() {
		return audFechaHora;
	}

	public void setAudFechaHora(Date audFechaHora) {
		this.audFechaHora = audFechaHora;
	}

	public String getDestinatarios() {
		return destinatarios;
	}

	public void setDestinatarios(String destinatarios) {
		this.destinatarios = destinatarios;
	}
	
	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}


}
