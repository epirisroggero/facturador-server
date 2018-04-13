package uy.com.tmwc.facturator.rapi;

import java.util.List;

import uy.com.tmwc.facturator.dto.AgendaTareaDTO;
import uy.com.tmwc.facturator.entity.AgendaTarea;
import uy.com.tmwc.facturator.expediciones.AgendaTareaQuery;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("remote/agendaTareaService")
public abstract interface AgendaTareaService extends RemoteService {

	 public abstract void persist(AgendaTarea e);
	 
	 public abstract void merge(AgendaTarea e);
	 
	 public abstract void reagendarTareasPendientes(AgendaTareaQuery paramAgendaTareaQuery);

	 public abstract List<AgendaTareaDTO> getAgendaTareas(String usuario);

	 public abstract List<AgendaTareaDTO> queryAgendaTareas(AgendaTareaQuery paramAgendaTareaQuery);
	 
	 public abstract List<AgendaTareaDTO> queryAgendaTareasSupervisadas(AgendaTareaQuery query);
	 
	 public abstract AgendaTarea getAgendaTarea(String id);

}
