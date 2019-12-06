package uy.com.tmwc.facturator.libra.ejb;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.dozer.DozerBeanMapper;

import uy.com.tmwc.facturator.deudores.DocumentoDeudor;
import uy.com.tmwc.facturator.dto.AgendaTareaDTO;
import uy.com.tmwc.facturator.entity.AgendaTarea;
import uy.com.tmwc.facturator.entity.Cliente;
import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.expediciones.AgendaTareaQuery;
import uy.com.tmwc.facturator.libra.entity.Agendatarea;
import uy.com.tmwc.facturator.libra.entity.AgendatareaPK;
import uy.com.tmwc.facturator.libra.util.DozerMappingsService;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.rapi.UsuariosService;
import uy.com.tmwc.facturator.spi.TareasDAOService;

@Stateless
@Local({ TareasDAOService.class })
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class TareasDAOServiceImpl extends ServiceBase implements TareasDAOService {

	@EJB
	UsuariosService usuariosService;
	
	@EJB
	CatalogService catalogService;
	
	@EJB
	DozerMappingsService mapService;

	@PersistenceContext
	EntityManager em;

	
	private SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");
	private SimpleDateFormat dt2 = new SimpleDateFormat("dd/MM/yyyy");


	public void persist(AgendaTarea e) {
		Number nextAgeId = (Number) em.createNamedQuery("Agendatarea.nextAgeId").setParameter("empId", getEmpId()).getSingleResult();
		if (nextAgeId == null) {
			nextAgeId = 1;
		}

		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		Agendatarea libraTarea = mapper.map(e, Agendatarea.class);
		libraTarea.setTipo("S");
		libraTarea.setPvIdAge(1);
		
		if (libraTarea.getFechaInicio() == null) {
			libraTarea.setFechaInicio(e.getFechaHora());
		}

		AgendatareaPK agendatareaPK = new AgendatareaPK();
		agendatareaPK.setAgeId(nextAgeId.intValue());
		agendatareaPK.setEmpId(getEmpId());
		
		libraTarea.setId(agendatareaPK);

		this.em.persist(libraTarea);
		this.em.flush();
		
	}
	
	public void merge(AgendaTarea e) {
		DozerBeanMapper mapper = mapService.getDozerBeanMapper();
		Agendatarea libraTarea = mapper.map(e, Agendatarea.class);;
		
		this.em.merge(libraTarea);
		this.em.flush();
	}

	public List<AgendaTareaDTO> getAgendaTareas(String usuario) {
		@SuppressWarnings("unchecked")
		List<AgendaTareaDTO> tareasLibra = this.em.createNamedQuery("Agendatarea.tareasUsuario")
			.setParameter("usuario", usuario)
			.setParameter("empId", getEmpId()).getResultList();

		/*DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		List res = MappingUtils.map(AgendaTarea.class, tareasLibra, mapper);*/
		return tareasLibra;
	}

	private ArrayList<String> getContactos() {
		ArrayList<String> contactos = new ArrayList<String>();
		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();

		if (Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId)) {
			List<Cliente> clientes = catalogService.getCatalog("Cliente");
			for (Cliente c : clientes) {
				String venId = (c.getVendedor() != null) ? c.getVendedor().getCodigo() : null;
				if (usuarioLogin.getVenId().equals(venId)) {					
					contactos.add(c.getContacto().getCodigo());
				}
			}
		}
		return contactos;
		
	}

	public List<AgendaTareaDTO> queryAgendaTareas(AgendaTareaQuery query, boolean checkPermision) {
		String state = query.getState();
		
		String[] values = new String[6]; 
		if (query.getTareas() != null) {
			int i = 0;
			for (String r : query.getTareas()) {
				values[i] = r;
				i++;
			}
		}
		String[] usuarios = new String[6]; 
		if (query.getUsuarios() != null) {
			int j = 0;
			for (String u : query.getUsuarios()) {
				usuarios[j] = u;
				j++;
			}
		}		
		Short asignado = null;
		if (query.getAsignado() != null) {
			asignado = new Short(query.getAsignado());
		}
		
		ArrayList<String> empty = new ArrayList<String>();
		empty.add("-1");		
		ArrayList<String> contactos = query.getContacto() == null ? getContactos() : new ArrayList<String>();
		
		@SuppressWarnings("unchecked")
		List<AgendaTareaDTO> tareasLibra = this.em.createNamedQuery("Agendatarea.tareasFecha")
				.setParameter("empId", getEmpId())
				.setParameter("contactos", contactos.size() > 0 ? contactos : empty)
				.setParameter("tieneContactos", contactos.size() > 0 ? "S" : "N")
				.setParameter("fechaDesde", query.getFechaDesde())
				.setParameter("fechaHasta", query.getFechaHasta())
				.setParameter("estado", state)
				.setParameter("asignado", asignado)
				.setParameter("contacto", query.getContacto())
				.setParameter("usuario1", usuarios[0])
				.setParameter("usuario2", usuarios[1])
				.setParameter("usuario3", usuarios[2])
				.setParameter("usuario4", usuarios[3])
				.setParameter("usuario5", usuarios[4])
				.setParameter("usuario6", usuarios[5])
				.setParameter("tarea1", values[0])
				.setParameter("tarea2", values[1])
				.setParameter("tarea3", values[2])
				.setParameter("tarea4", values[3])
				.setParameter("tarea5", values[4])
				.setParameter("tarea6", values[5])
				.setParameter("capituloId", query.getCapituloId()).setMaxResults(500)
				.getResultList();
		
		if (checkPermision) {
			Usuario usuarioLogin = usuariosService.getUsuarioLogin();
			String permisoId = usuarioLogin.getPermisoId();
			
			if (permisoId.equals(Usuario.USUARIO_ALIADOS_COMERCIALES)) {
				List<AgendaTareaDTO> result =  new ArrayList<AgendaTareaDTO>();

				String vendedorId = usuarioLogin.getVenId();
				
				for (AgendaTareaDTO agendaTareaDTO : tareasLibra) {
					String idUsuarioAsignado = Short.valueOf(agendaTareaDTO.getIdUsuAsignado()).toString();
					if (idUsuarioAsignado.equals(usuarioLogin.getCodigo())) {
						result.add(agendaTareaDTO);
						continue;
					}
					String idUsuarioSolicitante = agendaTareaDTO.getUsuSolicitante();
					if (idUsuarioSolicitante != null && idUsuarioSolicitante.equals(usuarioLogin.getNombre())) {
						result.add(agendaTareaDTO);
						continue;
					}
					
					String contactoId = agendaTareaDTO.getCtoId();
					if (contactoId != null) {					
						Cliente cliente = catalogService.findCatalogEntity("Cliente", contactoId);
						if (cliente == null) {
							continue;
						}
						
						String encargadoCuenta = cliente.getEncargadoCuenta();
						if (encargadoCuenta != null && encargadoCuenta.equals(vendedorId)) {
							result.add(agendaTareaDTO);
							continue;
						}
						
						String vendedorCliente = cliente.getVendedor() != null ? cliente.getVendedor().getCodigo() : null;
						if (vendedorCliente != null && vendedorCliente.equals(vendedorId)) {
							result.add(agendaTareaDTO);
							continue;
						}
					}
				}

				return result;
			}
		}

		return tareasLibra;
	}
	
	public List<AgendaTareaDTO> queryAgendaTareasSupervisadas(AgendaTareaQuery query) {
		String[] tareas = new String[6]; 
		if (query.getTareas() != null) {
			int i = 0;
			for (String r : query.getTareas()) {
				tareas[i] = r;
				i++;
			}
		}
		String[] usuarios = new String[6]; 
		if (query.getUsuarios() != null) {
			int j = 0;
			for (String u : query.getUsuarios()) {
				usuarios[j] = u;
				j++;
			}
		}		

		@SuppressWarnings("unchecked")
		List<AgendaTareaDTO> tareasLibra = this.em.createNamedQuery("Agendatarea.tareasSupervisadas")
				.setParameter("empId", getEmpId())
				.setParameter("fechaDesde", query.getFechaDesde())
				.setParameter("fechaHasta", query.getFechaHasta())
				.setParameter("supervisor", query.getSupervisor())
				.setParameter("contacto", query.getContacto())
				.setParameter("usuario1", usuarios[0])
				.setParameter("usuario2", usuarios[1])
				.setParameter("usuario3", usuarios[2])
				.setParameter("usuario4", usuarios[3])
				.setParameter("usuario5", usuarios[4])
				.setParameter("usuario6", usuarios[5])
				.setParameter("tarea1", tareas[0])
				.setParameter("tarea2", tareas[1])
				.setParameter("tarea3", tareas[2])
				.setParameter("tarea4", tareas[3])
				.setParameter("tarea5", tareas[4])
				.setParameter("tarea6", tareas[5])
				.setParameter("capituloId", query.getCapituloId()).setMaxResults(500)
				.getResultList();

		return tareasLibra;
	}
	
	public void reagendarTareasPendientes(AgendaTareaQuery query) {
		String state = query.getState();
		
		String[] values = new String[6]; 
		if (query.getTareas() != null) {
			int i = 0;
			for (String r : query.getTareas()) {
				values[i] = r;
				i++;
			}
		}
		String[] usuarios = new String[6]; 
		if (query.getUsuarios() != null) {
			int j = 0;
			for (String u : query.getUsuarios()) {
				usuarios[j] = u;
				j++;
			}
		}
		
		Short asignado = null;
		if (query.getAsignado() != null) {
			asignado = new Short(query.getAsignado());
		}
		
		@SuppressWarnings("unchecked")
		List<Agendatarea> tareasLibra = this.em.createNamedQuery("Agendatarea.reagendarTareasFecha")
				.setParameter("empId", getEmpId())
				.setParameter("fechaDesde", query.getFechaDesde())
				.setParameter("fechaHasta", query.getFechaHasta())
				.setParameter("asignado", asignado)
				.setParameter("contacto", query.getContacto())
				.setParameter("estado", state)
				.setParameter("usuario1", usuarios[0])
				.setParameter("usuario2", usuarios[1])
				.setParameter("usuario3", usuarios[2])
				.setParameter("usuario4", usuarios[3])
				.setParameter("usuario5", usuarios[4])
				.setParameter("usuario6", usuarios[5])
				.setParameter("tarea1", values[0])
				.setParameter("tarea2", values[1])
				.setParameter("tarea3", values[2])
				.setParameter("tarea4", values[3])
				.setParameter("tarea5", values[4])
				.setParameter("tarea6", values[5])
				.setParameter("capituloId", query.getCapituloId())
				.getResultList();
		
		for (Agendatarea agendaTarea : tareasLibra) {
	   		Calendar calendar1 = Calendar.getInstance();
    		calendar1.setTime(agendaTarea.getFechaHora());
    		int hours = calendar1.get(Calendar.HOUR_OF_DAY);

	   		Calendar calendar3 = Calendar.getInstance();
    		calendar3.setTime(new Date());
    		calendar3.set(Calendar.HOUR_OF_DAY, hours < 12 ? 8 : 16);
    		calendar3.set(Calendar.MINUTE, 0);
    		calendar3.set(Calendar.SECOND, 0); 
    		
    		agendaTarea.setFechaHora(calendar3.getTime());
    		
    		this.em.merge(agendaTarea);
    		this.em.flush();
		}
	}
	
	public AgendaTarea getAgendaTarea(String tareaId) {
		int tId;
		try {
			tId = Integer.parseInt(tareaId);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			return null;
		}		
		uy.com.tmwc.facturator.libra.entity.Agendatarea tarea = (uy.com.tmwc.facturator.libra.entity.Agendatarea) this.em.find(
				uy.com.tmwc.facturator.libra.entity.Agendatarea.class, new AgendatareaPK(getEmpId(), tId));
	
		if (tarea == null) {
			return null;
		}
		DozerBeanMapper mapper = mapService.getDozerBeanMapper();		
		AgendaTarea mapped = mapper.map(tarea, AgendaTarea.class);
		return mapped;
	}

	
	public void notificacionPorFacturaVencida(HashMap<String, List<DocumentoDeudor>> vencimientos) {
		ArrayList<String> tareas = new ArrayList<String>();
		tareas.add("030");

		NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("es"));
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);

		Iterator<String> iter = vencimientos.keySet().iterator();
		while (iter.hasNext()) {
			String contactoCode = iter.next();

			AgendaTareaQuery query = new AgendaTareaQuery();
			query.setContacto(contactoCode);
			query.setTareas(tareas);
			query.setState("P");
			query.setLimit(10);

			List<AgendaTareaDTO> agendadas = queryAgendaTareas(query, false);

			// Si no se encontró entonces creo una nueva tarea.
			if (agendadas != null && agendadas.size() == 0) {
				Number nextAgeId = (Number) em.createNamedQuery("Agendatarea.nextAgeId").setParameter("empId", getEmpId()).getSingleResult();
				if (nextAgeId == null) {
					nextAgeId = 1;
				}
				Agendatarea agendaTarea = new Agendatarea();
				agendaTarea.setEstado("P");
				agendaTarea.setTareaId("030");
				agendaTarea.setPrioridad("M");
				agendaTarea.setUsuIdAge1((short)28);
				agendaTarea.setUsuIdAge2((short)28);
				agendaTarea.setLocIdAge((short)0);
				agendaTarea.setNotas(String.format("[%s] Autogenerada por vencimiento", dt2.format(new Date())));
				
		   		Calendar calendarDate = Calendar.getInstance();
				calendarDate.setTime(new Date());
		   		calendarDate.set(Calendar.HOUR, 8);
		   		calendarDate.set(Calendar.MINUTE, 0);
		   		calendarDate.set(Calendar.SECOND, 0);	    		

				agendaTarea.setFechaHora(calendarDate.getTime());
				agendaTarea.setCtoIdAge(contactoCode);
				
				AgendatareaPK agendatareaPK = new AgendatareaPK();
				agendatareaPK.setAgeId(nextAgeId.intValue());
				agendatareaPK.setEmpId(getEmpId());
				
				agendaTarea.setId(agendatareaPK);

				StringBuilder sb = new StringBuilder("Documentos con deuda:\n");

				List<DocumentoDeudor> documentoDeudor = vencimientos.get(contactoCode);
				for (DocumentoDeudor documentoDeudor2 : documentoDeudor) {
					String serie = documentoDeudor2.getSerie() != null ? documentoDeudor2.getSerie() : "";
					String numero = String.valueOf(documentoDeudor2.getNumero());
					String fecha = documentoDeudor2.getFecha();
					String fechaVencimiento = documentoDeudor2.getFechaVencimiento() != null ? dt1.format(documentoDeudor2.getFechaVencimiento()) : "1-1-1000";
					String moneda = documentoDeudor2.getMoneda() != null ? documentoDeudor2.getMoneda().getNombre() : "";
					String facturado = formatter.format(documentoDeudor2.getFacturado());
					String cancelado = formatter.format(documentoDeudor2.getCancelado());

					sb.append(String.format("El comprobante %s%s con fecha %s y vencimiento %s esta pendiente de pago.\n" + "Moneda %s, Facturado: %s, Cancelado: %s\n", serie, numero, fecha,
							fechaVencimiento, moneda.toUpperCase(), facturado, cancelado));

				}
				agendaTarea.setDescripcion(sb.toString());

				this.em.persist(agendaTarea);
				this.em.flush();
			}
		}
	}


}
