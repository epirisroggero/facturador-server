package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.util.Date;

public class AgendaTarea implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;
	
	private String empId;
	
	private int ageId;

	private byte[] ageBlob;

	private String ageBlobExt;

	private String descripcion;
	
	private String notas;
	
	private String textoAdjunto;

	private String estado;

	private Date fechaHora;

	private Date fechaHoraFin;

	private Date fechaInicio;
	
	private String prioridad;

	private String tipo;

	private String vinculo;

	private Contacto contacto;

	private Tarea tarea;

	private Usuario usuarioSolicitante;

	private Usuario usuarioAsignado;
	
	private String nroOrden;
	
	private Long orden;
	
	private String supervisor1;

	private String supervisor2;
	
	private String supervisor3;
	
	private boolean notify;
	
	private String usuEstado;

	private String usuCliId;


	public Object clone() {
		AgendaTarea obj=null;
        try{
            obj=(AgendaTarea)super.clone();
    	    if (contacto != null) {
    			obj.contacto = (Contacto)contacto.clone();
    		}
    	    if (tarea != null) {
    			obj.tarea = (Tarea)tarea.clone();
    		}
    	    if (usuarioSolicitante != null) {
    			obj.usuarioSolicitante = (Usuario)usuarioSolicitante.clone();
    		}
    	    if (usuarioAsignado != null) {
    			obj.usuarioAsignado = (Usuario)usuarioAsignado.clone();
    		}
    	    if (fechaInicio != null) {
    			obj.fechaInicio = (Date)fechaInicio.clone();
    		}
    	    

        } catch(CloneNotSupportedException ex){
        	ex.printStackTrace();
        }
        
        
        return obj;
	}

	public byte[] getAgeBlob() {
		return ageBlob;
	}

	public void setAgeBlob(byte[] ageBlob) {
		this.ageBlob = ageBlob;
	}

	public String getAgeBlobExt() {
		return ageBlobExt;
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

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
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

	public Tarea getTarea() {
		return tarea;
	}

	public void setTarea(Tarea tarea) {
		this.tarea = tarea;
	}

	public Usuario getUsuarioSolicitante() {
		return usuarioSolicitante;
	}

	public void setUsuarioSolicitante(Usuario usuarioSolicitante) {
		this.usuarioSolicitante = usuarioSolicitante;
	}

	public Usuario getUsuarioAsignado() {
		return usuarioAsignado;
	}

	public void setUsuarioAsignado(Usuario usuarioAsignado) {
		this.usuarioAsignado = usuarioAsignado;
	}

	public int getAgeId() {
		return ageId;
	}

	public void setAgeId(int ageId) {
		this.ageId = ageId;
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}
	
	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getNroOrden() {
		return nroOrden;
	}

	public void setNroOrden(String nroOrden) {
		this.nroOrden = nroOrden;
	}

	public Long getOrden() {
		return orden;
	}

	public void setOrden(Long orden) {
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
		return notify;
	}

	public void setNotify(boolean notify) {
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
