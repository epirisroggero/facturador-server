package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import uy.com.tmwc.facturator.libra.util.BooleanDozerConverter;

@Entity
@Table(name = "agendatareas")
@SecondaryTable(name = "lfx_agendatareas")
public class Agendatarea extends PersistentEntity<AgendatareaPK> implements Serializable, HasId<AgendatareaPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AgendatareaPK id;

	@Lob()
	@Column(name = "AgeBlob")
	private byte[] ageBlob;

	@Column(name = "AgeBlobExt")
	private String ageBlobExt;

	@Lob()
	@Column(name = "AgeDescripcion")
	private String descripcion;

	@Column(name = "AgeEstado")
	private String estado;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AgeFechaHora")
	private Date fechaHora;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "AgeFin")
	private Date fechaHoraFin;

	@Lob()
	@Column(name = "AgeNotas")
	private String notas;

	@Column(name = "AgePrioridad")
	private String prioridad;

	@Column(name = "AgeTipo")
	private String tipo;

	@Lob()
	@Column(name = "AgeVinculo")
	private String vinculo;

	@Column(name = "CtoIdAge")
	private String ctoIdAge;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "CtoIdAge", referencedColumnName = "CtoId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Contacto contacto;


	@Column(name = "LocIdAge")
	private short locIdAge;

	private Integer OVidAge;

	@Column(name = "PvIdAge")
	private Integer pvIdAge;


	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "TareaId", referencedColumnName = "TareaId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Tarea tarea;

	@Column(name = "TareaId")
	private String tareaId;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "UsuIdAge1", referencedColumnName = "UsuId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Usuario usuarioSolicitante;

	@Column(name = "UsuIdAge1")
	private short usuIdAge1;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "UsuIdAge2", referencedColumnName = "UsuId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Usuario usuarioAsignado;

	@Column(name = "UsuIdAge2")
	private short usuIdAge2;

	@Column(table = "lfx_agendatareas")
	private Date fechaInicio; 

	@Column(table = "lfx_agendatareas", length = 50)
	private String nroOrden;	

	@Column(table = "lfx_agendatareas")
	private Integer orden;
	
	@Column(table = "lfx_agendatareas")
	private String supervisor1;	

	@Column(table = "lfx_agendatareas")
	private String supervisor2;	

	@Column(table = "lfx_agendatareas")
	private String supervisor3;	

	@Lob()
	@Column(table = "lfx_agendatareas")
	private String textoAdjunto;
	
	@Column(table = "lfx_agendatareas")
	private String notify;

	@Column(table = "lfx_agendatareas")
	private String usuEstado;

	@Column(table = "lfx_agendatareas")
	private String usuCliId;

	public Agendatarea() {
	}

	public AgendatareaPK getId() {
		return this.id;
	}

	public void setId(AgendatareaPK id) {
		this.id = id;
	}

	public byte[] getAgeBlob() {
		return this.ageBlob;
	}

	public void setAgeBlob(byte[] ageBlob) {
		this.ageBlob = ageBlob;
	}

	public String getAgeBlobExt() {
		return this.ageBlobExt;
	}

	public void setAgeBlobExt(String ageBlobExt) {
		this.ageBlobExt = ageBlobExt;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getPrioridad() {
		return prioridad;
	}

	public void setPrioridad(String prioridad) {
		this.prioridad = prioridad;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getVinculo() {
		return vinculo;
	}

	public void setVinculo(String vinculo) {
		this.vinculo = vinculo;
	}

	public String getCtoIdAge() {
		return this.ctoIdAge;
	}

	public void setCtoIdAge(String ctoIdAge) {
		this.ctoIdAge = ctoIdAge;
	}

	public short getLocIdAge() {
		return this.locIdAge;
	}

	public void setLocIdAge(short locIdAge) {
		this.locIdAge = locIdAge;
	}

	public Integer getOVidAge() {
		return this.OVidAge;
	}

	public void setOVidAge(Integer OVidAge) {
		this.OVidAge = OVidAge;
	}

	public Integer getPvIdAge() {
		return this.pvIdAge;
	}

	public void setPvIdAge(Integer pvIdAge) {
		this.pvIdAge = pvIdAge;
	}

	public String getTareaId() {
		return this.tareaId;
	}

	public void setTareaId(String tareaId) {
		this.tareaId = tareaId;
	}

	public short getUsuIdAge1() {
		return this.usuIdAge1;
	}

	public void setUsuIdAge1(short usuIdAge1) {
		this.usuIdAge1 = usuIdAge1;
	}

	public short getUsuIdAge2() {
		return this.usuIdAge2;
	}

	public void setUsuIdAge2(short usuIdAge2) {
		this.usuIdAge2 = usuIdAge2;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
		this.ctoIdAge = contacto == null ? null : contacto.getId().getCtoId();
	}

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
		this.tareaId = tarea == null ? null : tarea.getCodigo();
	}

	public Usuario getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(Usuario usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
		usuIdAge1 = usuarioSolicitante == null ? null : usuarioSolicitante.getId().getUsuId();
	}

	public Usuario getUsuarioAsignado() {
		return usuarioAsignado;
	}

	public void setUsuarioAsignado(Usuario usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
		usuIdAge2 = usuarioAsignado == null ? null : usuarioAsignado.getId().getUsuId();
	}
	
	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public String getNroOrden() {
		return nroOrden;
	}

	public void setNroOrden(String nroOrden) {
		this.nroOrden = nroOrden;
	}

	public Integer getOrden() {
		return orden;
	}

	public void setOrden(Integer orden) {
		this.orden = orden;
	}
	
	public String getTextoAdjunto() {
		return textoAdjunto;
	}

	public void setTextoAdjunto(String textoAdjunto) {
		this.textoAdjunto = textoAdjunto;
	}
	
	public String getSupervisor1() {
		return supervisor1;
	}

	public void setSupervisor1(String supervisor1) {
		this.supervisor1 = supervisor1;
	}

	public String getSupervisor2() {
		return supervisor2;
	}

	public void setSupervisor2(String supervisor2) {
		this.supervisor2 = supervisor2;
	}

	public String getSupervisor3() {
		return supervisor3;
	}

	public void setSupervisor3(String supervisor3) {
		this.supervisor3 = supervisor3;
	}

	public boolean getNotify() {
		return BooleanDozerConverter.toBooleanValue(notify);
	}

	public void setNotify(boolean notify) {
		this.notify = BooleanDozerConverter.toString(notify);
	}


	public void setNotify(String notify) {
		this.notify = notify;
	}
	
	public String getUsuEstado() {
		return usuEstado;
	}

	public void setUsuEstado(String usuEstado) {
		this.usuEstado = usuEstado;
	}
	
	public String getUsuCliId() {
		return usuCliId;
	}

	public void setUsuCliId(String usuCliId) {
		this.usuCliId = usuCliId;
	}


}