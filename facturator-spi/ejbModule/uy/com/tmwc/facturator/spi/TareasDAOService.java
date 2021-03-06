package uy.com.tmwc.facturator.spi;

import java.util.HashMap;
import java.util.List;

import uy.com.tmwc.facturator.deudores.DocumentoDeudor;
import uy.com.tmwc.facturator.dto.AgendaTareaDTO;
import uy.com.tmwc.facturator.entity.AgendaTarea;
import uy.com.tmwc.facturator.expediciones.AgendaTareaQuery;

public abstract interface TareasDAOService {

	public abstract void persist(AgendaTarea e);
	
	public abstract void merge(AgendaTarea e);
	
	public abstract void reagendarTareasPendientes(AgendaTareaQuery query);
	
	public abstract List<AgendaTareaDTO> queryAgendaTareas(AgendaTareaQuery query, boolean checkPermision);

	public abstract List<AgendaTareaDTO> queryAgendaTareasSupervisadas(AgendaTareaQuery query);
	
	public abstract List<AgendaTareaDTO> getAgendaTareas(String usuario);
	
	public abstract AgendaTarea getAgendaTarea(String id);
	
	public abstract void notificacionPorFacturaVencida(HashMap<String, List<DocumentoDeudor>> vencimientos);
}
