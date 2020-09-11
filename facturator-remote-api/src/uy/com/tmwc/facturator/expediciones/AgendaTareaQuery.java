package uy.com.tmwc.facturator.expediciones;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AgendaTareaQuery implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private int start;

	private int limit;

	private Date fecha;
	
	private Date fechaDesde;
	
	private Date fechaHasta;

	private String usuario;
	
	private String state;
	
	private String contacto;
	
	private String asignado;
	
	private String supervisor;
	
	private ArrayList<String> usuarios;
	
	private ArrayList<String> tareas;
	
	private String capituloId;

	
	public AgendaTareaQuery() {
	}

	public String getCapituloId() {
		return capituloId;
	}

	public void setCapituloId(String capituloId) {
		this.capituloId = capituloId;
	}

	public ArrayList<String> getUsuarios() {
		return usuarios;
	}
	
	public void setUsuarios(ArrayList<String> usuarios) {
		this.usuarios = usuarios;
	}

	public ArrayList<String> getTareas() {
		return tareas;
	}

	public void setTareas(ArrayList<String> tareas) {
		this.tareas = tareas;
	}

	public AgendaTareaQuery(int start, int limit) {
		this.start = start;
		this.limit = limit;
	}
	
	public String getSupervisor() {
		return supervisor;
	}

	public void setSupervisor(String supervisor) {
		this.supervisor = supervisor;
	}

	public int getStart() {
		return this.start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return this.limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);		
		calendar.set(Calendar.HOUR_OF_DAY, 0);		
		calendar.set(Calendar.MINUTE, 1);
		this.fechaDesde  = calendar.getTime();
		
		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(fecha);		
		calendar2.set(Calendar.HOUR_OF_DAY, 23);		
		calendar2.set(Calendar.MINUTE, 59);
		this.fechaHasta = calendar2.getTime();
		
		//System.out.println(String.format("# Obtener Tareas entre el %s hasta %s", fechaDesde.toString(), fechaHasta.toString()));
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario= usuario;
	}
	
	public void setFechaDesde(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(fecha);		
		calendar.set(Calendar.HOUR_OF_DAY, 0);		
		calendar.set(Calendar.MINUTE, 1);
		
		this.fechaDesde  = calendar.getTime();
	}
	
	public void setFechaHasta(Date fecha) {
		Calendar calendar = Calendar.getInstance();
		
		calendar.setTime(fecha);		
		calendar.set(Calendar.HOUR_OF_DAY, 23);		
		calendar.set(Calendar.MINUTE, 59);
		
		this.fechaHasta  = calendar.getTime();
	}
	
	public Date getFechaDesde() {
		return fechaDesde;
	}

	public Date getFechaHasta() {
		return fechaHasta;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getContacto() {
		return contacto;
	}

	public void setContacto(String contacto) {
		this.contacto = contacto;
	}

	public String getAsignado() {
		return asignado;
	}

	public void setAsignado(String asignado) {
		this.asignado = asignado;
	}


}