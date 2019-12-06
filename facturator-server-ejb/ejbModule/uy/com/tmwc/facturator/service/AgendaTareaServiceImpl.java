package uy.com.tmwc.facturator.service;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.deudores.DocumentoDeudor;
import uy.com.tmwc.facturator.dto.AgendaTareaDTO;
import uy.com.tmwc.facturator.entity.AgendaTarea;
import uy.com.tmwc.facturator.expediciones.AgendaTareaQuery;
import uy.com.tmwc.facturator.rapi.AgendaTareaService;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.spi.TareasDAOService;

@Name("agendaTareaService")
@Local({ AgendaTareaService.class })
@Stateless
public class AgendaTareaServiceImpl implements AgendaTareaService {

	@EJB
	TareasDAOService tareasDAOService;

	@EJB
	CatalogService catalogService;


	public void persist(AgendaTarea e) {
		tareasDAOService.persist(e);
	}

	public void merge(AgendaTarea e) {
		tareasDAOService.merge(e);
	}

	public void reagendarTareasPendientes(AgendaTareaQuery paramAgendaTareaQuery) {
		tareasDAOService.reagendarTareasPendientes(paramAgendaTareaQuery);
	}

	public List<AgendaTareaDTO> getAgendaTareas(String usuario) {
		return tareasDAOService.getAgendaTareas(usuario);
	}

	public List<AgendaTareaDTO> queryAgendaTareas(AgendaTareaQuery query, boolean checkPermision) {
		return tareasDAOService.queryAgendaTareas(query, checkPermision);
	}

	public List<AgendaTareaDTO> queryAgendaTareasSupervisadas(AgendaTareaQuery query) {
		return tareasDAOService.queryAgendaTareasSupervisadas(query);
	}

	public AgendaTarea getAgendaTarea(String id) {
		return tareasDAOService.getAgendaTarea(id);
	}
	
	public void notificacionPorFacturaVencida(HashMap<String, List<DocumentoDeudor>> vencimientos) {
		tareasDAOService.notificacionPorFacturaVencida(vencimientos);
	}
	

}
