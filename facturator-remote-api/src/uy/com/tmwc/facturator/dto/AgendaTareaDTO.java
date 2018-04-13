package uy.com.tmwc.facturator.dto;

import java.io.Serializable;
import java.util.Date;

public class AgendaTareaDTO implements Serializable {
	
	public AgendaTareaDTO(){
		
	}
	
	public AgendaTareaDTO(int ageId, Date fechaHora, String estado, Date fechaInicio, Date fechaFin, String descripcion, String prioridad, String tipo, String ctoId, String ctoNombre, String ctoDireccion, String ctoTelefono, String tarea,
			String usuSolicitante, short idUsuAsignado, String nroOrden, Object orden) {
		super();
		this.ageId = ageId;
		this.fechaHora = fechaHora;
		this.fechaHoraFin = fechaFin;
		this.estado = estado;
		this.fechaInicio = fechaInicio;
		this.descripcion = descripcion;
		this.prioridad = prioridad;
		this.tipo = tipo;
		this.ctoId = ctoId;
		this.ctoNombre = ctoNombre;
		this.ctoDireccion = ctoDireccion;
		this.ctoTelefono = ctoTelefono;
		this.tarea = tarea;
		this.usuSolicitante = usuSolicitante;
		this.idUsuAsignado = idUsuAsignado;
		this.nroOrden = nroOrden;
		this.orden = orden;
		
	}

	private static final long serialVersionUID = 1L;
	
	private int ageId;

	private Date fechaHora;
	
	private Date fechaHoraFin;

	private String estado;

	private Date fechaInicio;

	private String descripcion;
		
	private String prioridad;

	private String tipo;

	private String ctoNombre;
	
	private String ctoDireccion;
	
	private String ctoTelefono;

	private String tarea;

	private String usuSolicitante;

	private short idUsuAsignado;
	
	private String nroOrden;
	
	private Object orden;
	
	private String ctoId;

	public int getAgeId() {
		return ageId;
	}

	public void setAgeId(int ageId) {
		this.ageId = ageId;
	}

	public Date getFechaHora() {
		return fechaHora;
	}

	public void setFechaHora(Date fechaHora) {
		this.fechaHora = fechaHora;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
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

	public String getCtoNombre() {
		return ctoNombre;
	}

	public void setCtoNombre(String ctoNombre) {
		this.ctoNombre = ctoNombre;
	}

	public String getCtoDireccion() {
		return ctoDireccion;
	}

	public void setCtoDireccion(String ctoDireccion) {
		this.ctoDireccion = ctoDireccion;
	}

	public String getTarea() {
		return tarea;
	}

	public void setTarea(String tarea) {
		this.tarea = tarea;
	}

	public short getIdUsuAsignado() {
		return idUsuAsignado;
	}

	public void setIdUsuAsignado(short idUsuAsignado) {
		this.idUsuAsignado = idUsuAsignado;
	}

	public String getNroOrden() {
		return nroOrden;
	}

	public void setNroOrden(String nroOrden) {
		this.nroOrden = nroOrden;
	}

	public Object getOrden() {
		return orden;
	}

	public void setOrden(Object orden) {
		this.orden = orden;
	}

	public String getUsuSolicitante() {
		return usuSolicitante;
	}

	public void setUsuSolicitante(String usuSolicitante) {
		this.usuSolicitante = usuSolicitante;
	}

	public Date getFechaHoraFin() {
		return fechaHoraFin;
	}

	public void setFechaHoraFin(Date fechaHoraFin) {
		this.fechaHoraFin = fechaHoraFin;
	}

	public String getCtoTelefono() {
		return ctoTelefono;
	}

	public void setCtoTelefono(String ctoTelefono) {
		this.ctoTelefono = ctoTelefono;
	}

	public String getCtoId() {
		return ctoId;
	}

	public void setCtoId(String ctoId) {
		this.ctoId = ctoId;
	}
	
}
