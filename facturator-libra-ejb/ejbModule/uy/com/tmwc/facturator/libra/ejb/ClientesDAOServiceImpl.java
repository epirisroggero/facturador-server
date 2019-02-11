package uy.com.tmwc.facturator.libra.ejb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.dozer.DozerBeanMapper;

import uy.com.tmwc.facturator.dto.ClienteDTO;
import uy.com.tmwc.facturator.dto.ClienteQuery;
import uy.com.tmwc.facturator.dto.ContactoDTO;
import uy.com.tmwc.facturator.dto.ContactoQuery;
import uy.com.tmwc.facturator.dto.ProveedorDTO;
import uy.com.tmwc.facturator.dto.ProveedoresQuery;
import uy.com.tmwc.facturator.entity.Cliente;
import uy.com.tmwc.facturator.entity.Contacto;
import uy.com.tmwc.facturator.entity.Persona;
import uy.com.tmwc.facturator.entity.Proveedor;
import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.entity.ValidationException;
import uy.com.tmwc.facturator.entity.VendedoresUsuario;
import uy.com.tmwc.facturator.libra.entity.ClientePK;
import uy.com.tmwc.facturator.libra.entity.ContactoPK;
import uy.com.tmwc.facturator.libra.entity.PersonaPK;
import uy.com.tmwc.facturator.libra.entity.ProveedorPK;
import uy.com.tmwc.facturator.libra.util.DozerMappingsService;
import uy.com.tmwc.facturator.rapi.UsuariosService;
import uy.com.tmwc.facturator.spi.ClientesDAOService;

@Stateless
@Local({ ClientesDAOService.class })
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class ClientesDAOServiceImpl extends ServiceBase implements ClientesDAOService {

	@PersistenceContext
	EntityManager em;

	@EJB
	DozerMappingsService mapService;

	@EJB
	UsuariosService usuariosService;


	private static final String CLIENTES_SUBQUERY = "from Cliente c join c.contacto cto " +
			" where c.id.empId = :empId " 
			+ "and (c.contacto.ctoActivo = :activo or :activo is null) " 
			+ "and (c.contacto.ctoRSocial = :razonSocial or :razonSocial is null) "
			+ "and (c.categCliId = :categoria or :categoria is null) " 
			+ "and (c.vendedor.id.venId = :vendedor or :vendedor is null) " 
			+ "and (c.contacto.girIdCto = :giro or :giro is null) "
			+ "and (c.contacto.zonaIdCto = :zona or :zona is null) "
			+ "and (LOWER(c.contacto.ctoLocalidad) LIKE :localidad or :localidad is null) ";

	private static final String CONTACTOS_SUBQUERY = "from " + "Contacto cto left join cto.departamento d left join cto.zona z " + "where " + "cto.id.empId = :empId "
			+ "and (cto.ctoActivo = :activo or :activo is null) " + "and (cto.ctoRSocial = :razonSocial or :razonSocial is null) " + "and (cto.girIdCto = :giro or :giro is null) "
			+ "and (cto.zonaIdCto = :zona or :zona is null) ";

	private static final String EXISTE_CONTACTO = "from Contacto cto where " 
			+ " (cto.id.empId = :empId) AND (" 
			+ "	(cto.codigo = :codigo) or "
			+ "	(cto.nombre = :nombre) or "
			+ "	(cto.ctoRSocial = :razonSocial) or " 
			+ "	(cto.ctoDireccion = :direccion and :direccion is not null) or " 
			+ "	(cto.ctoRUT = :rut and :rut is not null) or "
			+ "	(cto.ctoTelefono = :telefono and :telefono is not null))";
	

	private static final String PROVEEDORES_SUBQUERY = "from  Proveedor p join p.contacto cto left join cto.departamento d  left join cto.zona z  where " 
			+ "p.id.empId = :empId "
			+ "and (cto.ctoActivo = :activo or :activo is null) " 
			+ "and (cto.ctoRSocial = :razonSocial or :razonSocial is null) " 
			+ "and (p.categPrvId = :categoria or :categoria is null) "
			+ "and (cto.girIdCto = :giro or :giro is null) " 
			+ "and (cto.zonaIdCto = :zona or :zona is null) ";

	public String getLastId() {
		Number lastId = (Number) em.createNamedQuery("Contactos.nextId").setParameter("empId", getEmpId()).getSingleResult();
		lastId = lastId.intValue() - 1;
		return lastId.toString();

	}

	public String persist(Cliente e, Boolean force) {
		String codigo = e.getCodigo();
		if (codigo == null) {
			throw new IllegalArgumentException("El código no es válido.");
		}

		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Cliente libraCliente = mapper.map(e, uy.com.tmwc.facturator.libra.entity.Cliente.class);

		ClientePK clientePK = new ClientePK();
		clientePK.setCliId(e.getCodigo());
		clientePK.setEmpId(getEmpId());

		libraCliente.setId(clientePK);
		libraCliente.setLocIdCli(Short.parseShort("1"));
		libraCliente.setCliIdNom(e.getCodigo() + " - " + e.getNombre());

		HashMap<String, String> adicionales = new HashMap<String, String>();
		if (e.getEncargadoPagos() != null) {
			adicionales.put("03", e.getEncargadoPagos());
		}
		if (e.getDiaHoraPagos() != null) {
			adicionales.put("04", e.getDiaHoraPagos());
		}
		if (e.getLugarEntrega() != null) {
			adicionales.put("05", e.getLugarEntrega());
		}
		if (e.getAgencia() != null) {
			adicionales.put("07", e.getAgencia());
		}
		if (e.getEspecialista1() != null) {
			adicionales.put("10", e.getEspecialista1());
		}
		if (e.getEspecialista2() != null) {
			adicionales.put("11", e.getEspecialista2());
		}
		if (e.getEncargadoCuenta() != null) {
			adicionales.put("14", e.getEncargadoCuenta());
		}
		if (e.getEnergiaElectrica() != null) {
			adicionales.put("18", e.getEnergiaElectrica());
		}
		if (e.getPermisoStock() != null) {
			adicionales.put("55", e.getPermisoStock());
		}
		if (e.getPermisoPrecios() != null) {
			adicionales.put("56", e.getPermisoPrecios());
		}
		if (e.getGoogleMaps() != null) {
			adicionales.put("GM", e.getGoogleMaps());
		}

		libraCliente.getContacto().setAdicionales(adicionales);

		String ppId = libraCliente.getPlanPagos() != null ? libraCliente.getPlanPagos().getCodigo() : null;
		Short pvId = libraCliente.getPreciosVenta() != null ? libraCliente.getPreciosVenta().getId().getPrecioVentaId() : null;

		libraCliente.setPlanPagos(null); // TODO: esto por algun motivo viene con un objeto, no deberia.
		libraCliente.setPreciosVenta(null); // TODO: esto por algun motivo viene con un objeto, no deberia.

		libraCliente.setpPidCli(ppId);
		libraCliente.setPrecioVentaIdCli(pvId);

		libraCliente.setCliRanking(BigDecimal.ZERO);
		libraCliente.setCliFichaLocal("N");
		libraCliente.setCliExentoIVA("N");

		ContactoPK contactoPK = new ContactoPK();
		contactoPK.setCtoId(e.getCodigo());
		contactoPK.setEmpId(getEmpId());

		uy.com.tmwc.facturator.libra.entity.Contacto libraContacto = (uy.com.tmwc.facturator.libra.entity.Contacto) this.em.find(uy.com.tmwc.facturator.libra.entity.Contacto.class, contactoPK);
		if (libraContacto != null) {
			libraCliente.setContacto(libraContacto);
		} else {
			libraContacto = libraCliente.getContacto();
			libraCliente.getContacto().setId(contactoPK);
			libraContacto.setCtoAlta(new Date()); // es necesaria la fecha de alta
			libraCliente.getContacto().setCtoProveedor("N"); // estamos insertando un cliente, no un proveedor
		}
		
		libraContacto.setCtoCliente("S"); // estamos insertando un cliente
		libraContacto.setCtoActivo("S"); // siempre insertamos activos
		
		if (libraContacto.getCtoRUT() != null && libraContacto.getCtoRUT().trim().length() > 0) {
			libraContacto.setCtoDocumentoTipo("R"); // TODO: el tipo de documento es requerido, lo dejo vacio que parece que funciona.
			libraContacto.setCtoDocumentoSigla("RUT"); 
		} else if (libraContacto.getCtoDocumento() != null && libraContacto.getCtoDocumento().trim().length() > 0) {
			libraContacto.setCtoDocumentoTipo("C"); 
			libraContacto.setCtoDocumentoSigla("C.I."); 
		} else {
			libraContacto.setCtoDocumentoTipo(""); 
			libraContacto.setCtoDocumentoSigla(""); 
		}
		
		libraContacto.setCtoNom(libraCliente.getNombre()); // redundancia, es requerido.
		libraContacto.setCtoNombreCompleto(codigo + " - " + libraCliente.getNombre()); // redundancia, es requerido.

		em.persist(libraCliente);
		this.em.flush();

		return codigo;
	}

	public void merge(Cliente e) {
		DozerBeanMapper mapper = mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Cliente libraCliente = mapper.map(e, uy.com.tmwc.facturator.libra.entity.Cliente.class);

		HashMap<String, String> adicionales = new HashMap<String, String>();
		if (e.getEncargadoPagos() != null) {
			adicionales.put("03", e.getEncargadoPagos());
		}
		if (e.getDiaHoraPagos() != null) {
			adicionales.put("04", e.getDiaHoraPagos());
		}
		if (e.getLugarEntrega() != null) {
			adicionales.put("05", e.getLugarEntrega());
		}
		if (e.getAgencia() != null) {
			adicionales.put("07", e.getAgencia());
		}
		if (e.getEspecialista1() != null) {
			adicionales.put("10", e.getEspecialista1());
		}
		if (e.getEspecialista2() != null) {
			adicionales.put("11", e.getEspecialista2());
		}
		if (e.getEncargadoCuenta() != null) {
			adicionales.put("14", e.getEncargadoCuenta());
		}
		if (e.getEnergiaElectrica() != null) {
			adicionales.put("18", e.getEnergiaElectrica());
		}
		if (e.getPermisoStock() != null) {
			adicionales.put("55", e.getPermisoStock());
		}
		if (e.getPermisoPrecios() != null) {
			adicionales.put("56", e.getPermisoPrecios());
		}
		if (e.getGoogleMaps() != null) {
			adicionales.put("GM", e.getGoogleMaps());
		}
		String codigo = e.getCodigo();
		
		libraCliente.getContacto().setCtoNom(libraCliente.getNombre()); // redundancia, es requerido.
		libraCliente.getContacto().setCtoNombreCompleto(codigo + " - " + libraCliente.getNombre()); // redundancia, es requerido.
		libraCliente.getContacto().setAdicionales(adicionales);
		libraCliente.getContacto().setCtoCliente("S");
		
		this.em.merge(libraCliente);
		this.em.flush();
	}

	public Boolean delete(Cliente e) {
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Cliente libraCliente = (uy.com.tmwc.facturator.libra.entity.Cliente) mapper.map(e, uy.com.tmwc.facturator.libra.entity.Cliente.class);

		libraCliente.provideId(getEmpId(), e.getCodigo());

		uy.com.tmwc.facturator.libra.entity.Cliente clienteEntity = null;
		clienteEntity = em.find(uy.com.tmwc.facturator.libra.entity.Cliente.class, libraCliente.getId());

		if (clienteEntity != null) {
			em.remove(clienteEntity);
			em.flush();
		}

		return true;
	}
	
	public String verifyContacto(Contacto e) {
		String tabbed = "    "; 
		StringBuffer result = new StringBuffer();
		result.append("<result>").append("\n");
		
		List<ContactoDTO> contactos = existContacto(e);
		result.append(tabbed).append("<errors>").append("\n");
		if (contactos.size() > 0) {
			for (ContactoDTO cto : contactos) {
				if (cto.getCodigo() != null && e.getCodigo() != null && cto.getCodigo().toUpperCase().equals(e.getCodigo().toUpperCase())) {
					result.append(tabbed).append(tabbed).append("<error>").append("[" + cto.getCodigo() + " " + cto.getNombre() + "]  tiene el mismo código").append("</error>").append("\n");
				}
				if (cto.getNombre() != null && e.getNombre() != null && cto.getNombre().toUpperCase().equals(e.getNombre().toUpperCase())) {
					result.append(tabbed).append(tabbed).append("<error>").append("[" + cto.getCodigo() + " " + cto.getNombre() + "]  tiene el mismo nombre").append("</error>").append("\n");
				}
			}
		}
		result.append(tabbed).append("</errors>").append("\n");
		
		result.append(tabbed).append("<warnings>").append("\n");
		if (contactos.size() > 0) {
			for (ContactoDTO cto : contactos) {
				if (cto.getRut() != null && cto.getRut().equals(e.getCtoRUT())) {
					result.append(tabbed).append(tabbed).append("<warning>").append("[" + cto.getCodigo() + " " + cto.getNombre() + "]  tiene el mismo 'RUT'").append("</warning>").append("\n");
				}
				if (cto.getRazonSocial() != null && e.getCtoRSocial() != null && cto.getRazonSocial().toUpperCase().equals(e.getCtoRSocial().toUpperCase())) {
					result.append(tabbed).append(tabbed).append("<warning>").append("[" + cto.getCodigo() + " " + cto.getNombre() + "]  tiene la misma 'Razón Social'").append("</warning>").append("\n");
				}
				if (cto.getDireccion() != null && e.getCtoDireccion() != null && cto.getDireccion().toUpperCase().equals(e.getCtoDireccion().toUpperCase())) {
					result.append(tabbed).append(tabbed).append("<warning>").append("[" + cto.getCodigo() + " " + cto.getNombre() + "]  tiene la misma 'Dirección'").append("</warning>").append("\n");
				}
				if (cto.getTelefono() != null && e.getCtoTelefono() != null && cto.getTelefono().toUpperCase().equals(e.getCtoTelefono().toUpperCase())) {
					result.append(tabbed).append(tabbed).append("<warning>").append("[" + cto.getCodigo() + " " + cto.getNombre() + "]  tiene el mismo 'Teléfono'").append("</warning>").append("\n");
				}
			}
		}
		result.append(tabbed).append("</warnings>").append("\n");
		result.append("</result>");
		
		return result.toString();
	}
	
	
	@SuppressWarnings("unchecked")
	public List<ContactoDTO> existContacto(Contacto c) {
		Query q = this.em.createQuery("select cto " + EXISTE_CONTACTO + "order by cto.codigo asc ");

		q.setParameter("empId", getEmpId())
				.setParameter("codigo", c.getCodigo())
				.setParameter("nombre", c.getNombre())
				.setParameter("razonSocial", c.getCtoRSocial())
				.setParameter("direccion", c.getCtoDireccion())
				.setParameter("rut", c.getCtoRUT())
				.setParameter("telefono", c.getCtoTelefono());

		List<ContactoDTO> result = new ArrayList<ContactoDTO>();
		
		List<uy.com.tmwc.facturator.libra.entity.Contacto> l = q.getResultList();
		for (uy.com.tmwc.facturator.libra.entity.Contacto contacto : l) {
			ContactoDTO contactoDTO = new ContactoDTO(contacto.getCodigo(), contacto.getNombre());
			contactoDTO.setRazonSocial(contacto.getCtoRSocial());
			contactoDTO.setDireccion(contacto.getCtoDireccion());
			contactoDTO.setRut(contacto.getCtoRUT());
			contactoDTO.setTelefono(contacto.getCtoTelefono());

			result.add(contactoDTO);
		}
		return result;
	}


	@SuppressWarnings("unchecked")
	public List<ClienteDTO> queryClientes(ClienteQuery query) throws ValidationException {
		Query q = this.em.createQuery("select c " +
			CLIENTES_SUBQUERY + " order by c.codigo asc ");

		q.setParameter("empId", getEmpId())
			.setParameter("vendedor", query.getVendedor())
			.setParameter("categoria", query.getCategoria())
			.setParameter("giro", query.getGiro())
			.setParameter("activo", query.getActivo() == Boolean.TRUE ? "S" : null)
			.setParameter("razonSocial", query.getRazonSocial())
			.setParameter("zona", query.getZona())
			.setParameter("localidad", query.getLocalidad() != null && query.getLocalidad().length() > 0 ? query.getLocalidad().toLowerCase() + "%" : null);

		List<ClienteDTO> result = new ArrayList<ClienteDTO>();

		Usuario user = usuariosService.getUsuarioLogin();		
		List<VendedoresUsuario> vendedores = user.getVendedoresUsuario();
		
		List<String> userVendedores = new ArrayList<String>(); 
		for (VendedoresUsuario vendedoresUsuario : vendedores) {
			userVendedores.add(vendedoresUsuario.getVendedorId());
		}

		List<uy.com.tmwc.facturator.libra.entity.Cliente> list = q.getResultList();
		for (uy.com.tmwc.facturator.libra.entity.Cliente cliente : list) {
			uy.com.tmwc.facturator.libra.entity.Contacto contacto = cliente.getContacto();
			
			ClienteDTO clienteDTO = new ClienteDTO(cliente.getCodigo(), cliente.getNombre(), contacto.getCtoRSocial(), contacto.getCtoDireccion(), 
			 		contacto.getCtoTelefono(), contacto.getCtoCelular(), 
			 		contacto.getCtoEmail1(), contacto.getZonaIdCto() != null ? contacto.getZonaIdCto() : "", 
			    	contacto.getCtoLocalidad(), contacto.getDeptoIdCto() != null ? contacto.getDeptoIdCto() : "", 
			    	cliente.getEncargadoCuenta(), cliente.getEspecialista1(), cliente.getEspecialista2(), contacto.getCtoActivo().equals("S"));

			if (user.getPermisoId().equals(Usuario.USUARIO_ALIADOS_COMERCIALES)) {
				String encargadoCuenta = cliente.getEncargadoCuenta();
				String vendedor = cliente.getVenIdCli();
				
				if (!userVendedores.contains(encargadoCuenta) && !userVendedores.contains(vendedor)) {
					continue;
				} 
			}
			
			if (query.getEncargadoCuenta() != null && query.getEspecialista() != null) {
				if (query.getEncargadoCuenta().equals(clienteDTO.getEncargadoCuenta())
						&& (query.getEspecialista().equals(clienteDTO.getEspecialista1()) || query.getEspecialista().equals(clienteDTO.getEspecialista2()))) {
					result.add(clienteDTO);
				}
			} else if (query.getEncargadoCuenta() != null) {
				if (query.getEncargadoCuenta().equals(clienteDTO.getEncargadoCuenta())) {
					result.add(clienteDTO);
				}
			} else if (query.getEspecialista() != null) {
				if (query.getEspecialista().equals(clienteDTO.getEspecialista1()) || query.getEspecialista().equals(clienteDTO.getEspecialista2())) {
					result.add(clienteDTO);
				}
			} else {
				result.add(clienteDTO);
			}
		}

		return result;
	}

	public List<ContactoDTO> queryContactos(ContactoQuery query) {
		Query q = this.em.createQuery("select cto " + CONTACTOS_SUBQUERY + "order by " + "cto.codigo asc ");

		q.setParameter("empId", getEmpId()).setParameter("giro", query.getGiro()).setParameter("activo", query.getActivo() == Boolean.TRUE ? "S" : null)
				.setParameter("razonSocial", query.getRazonSocial()).setParameter("zona", query.getZona());

		List<ContactoDTO> result = new ArrayList<ContactoDTO>();

		List<uy.com.tmwc.facturator.libra.entity.Contacto> l = q.getResultList();
		for (uy.com.tmwc.facturator.libra.entity.Contacto c : l) {
			ContactoDTO contactoDTO = new ContactoDTO(c.getCodigo(), c.getNombre(), c.getCtoRSocial(), c.getCtoDireccion(), c.getCtoTelefono(), c.getCtoCelular(), c.getCtoEmail1(),
					c.getZona() != null ? c.getZona().getNombre() : "", c.getCtoLocalidad(), c.getDepartamento() != null ? c.getDepartamento().getNombre() : "", c.getCtoActivo().equals("S"));

			result.add(contactoDTO);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	public List<ProveedorDTO> queryProveedores(ProveedoresQuery query) {
		Query q = this.em.createQuery("select p " + PROVEEDORES_SUBQUERY + "order by " + "p.codigo asc ");

		q.setParameter("empId", getEmpId()).setParameter("categoria", query.getCategoria()).setParameter("giro", query.getGiro())
				.setParameter("activo", query.getActivo() == Boolean.TRUE ? "S" : null).setParameter("razonSocial", query.getRazonSocial()).setParameter("zona", query.getZona());

		List<ProveedorDTO> result = new ArrayList<ProveedorDTO>();

		List<uy.com.tmwc.facturator.libra.entity.Proveedor> proveedores = q.getResultList();
		for (uy.com.tmwc.facturator.libra.entity.Proveedor p : proveedores) {
			ProveedorDTO proveedorDTO = new ProveedorDTO(p.getCodigo(), p.getNombre(), p.getContacto().getCtoRSocial(), p.getContacto().getCtoDireccion(), p.getContacto().getCtoTelefono(), p
					.getContacto().getCtoCelular(), p.getContacto().getCtoEmail1(), p.getContacto().getZona() != null ? p.getContacto().getZona().getNombre() : "", p.getContacto().getCtoLocalidad(),
					p.getContacto().getDepartamento() != null ? p.getContacto().getDepartamento().getNombre() : "", p.getContacto().getCtoActivo().equals("S"));

			result.add(proveedorDTO);
		}

		return result;
	}

	public List<Persona> queryPersonas(String codigo) {
		Query q = this.em.createQuery("select " + "new uy.com.tmwc.facturator.entity.Persona( " + "p.codigo, " + "p.nombre, " + "p.ctoPerCargo, " + "p.ctoPerEmail, " + "p.ctoPerTelefono, "
				+ "p.ctoPerCelular, " + "p.ctoPerNotas" + ") " + "from " + "Persona p " + "where " + "p.id.empId = :empId " + "and (p.id.ctoId  = :codigo) " +

				"order by " + "p.codigo asc ");

		q.setParameter("empId", getEmpId()).setParameter("codigo", codigo);

		@SuppressWarnings("unchecked")
		List<Persona> list = q.getResultList();
		return list;

	}

	public String persist(Persona p) {
		String codigo = p.getCodigo();
		String nombre = p.getNombre();
		String cargo = p.getCtoPerCargo();

		if (codigo == null) {
			throw new IllegalArgumentException("El código no es válido.");
		}
		if (nombre == null) {
			throw new IllegalArgumentException("El nombre no es válido.");
		}
		if (cargo == null) {
			throw new IllegalArgumentException("El cargo no es válido.");
		}

		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Persona libraPersona = mapper.map(p, uy.com.tmwc.facturator.libra.entity.Persona.class);

		PersonaPK personaPK = new PersonaPK();
		personaPK.setCtoId(codigo);
		personaPK.setEmpId(getEmpId());
		personaPK.setNombre(nombre);

		libraPersona.setId(personaPK);

		em.persist(libraPersona);
		this.em.flush();

		return codigo;
	}

	public void merge(Persona p) {
		String codigo = p.getCodigo();
		String nombre = p.getNombre();
		String cargo = p.getCtoPerCargo();

		if (codigo == null) {
			throw new IllegalArgumentException("El código no es válido.");
		}
		if (nombre == null) {
			throw new IllegalArgumentException("El nombre no es válido.");
		}
		if (cargo == null) {
			throw new IllegalArgumentException("El cargo no es válido.");
		}

		DozerBeanMapper mapper = mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Persona libraPersona = mapper.map(p, uy.com.tmwc.facturator.libra.entity.Persona.class);

		PersonaPK personaPK = new PersonaPK();
		personaPK.setCtoId(codigo);
		personaPK.setNombre(nombre);
		personaPK.setEmpId(getEmpId());

		libraPersona.setId(personaPK);

		this.em.merge(libraPersona);
		this.em.flush();

	}

	public Boolean delete(Persona p) {
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Persona libraPersona = (uy.com.tmwc.facturator.libra.entity.Persona) mapper.map(p, uy.com.tmwc.facturator.libra.entity.Persona.class);

		libraPersona.provideId(getEmpId(), p.getCodigo(), p.getNombre());

		uy.com.tmwc.facturator.libra.entity.Persona personaEntity = null;
		personaEntity = em.find(uy.com.tmwc.facturator.libra.entity.Persona.class, libraPersona.getId());

		if (personaEntity != null) {
			em.remove(personaEntity);
			em.flush();
		}

		return true;
	}

	public String persist(Proveedor p) {
		String codigo = p.getCodigo();
		if (codigo == null) {
			throw new IllegalArgumentException("El código no es válido.");
		}

		if (p.getContacto().getCtoRSocial() != null && p.getContacto().getCtoRSocial().length() > 0) {
			ProveedoresQuery proveedoresQuery = new ProveedoresQuery();
			proveedoresQuery.setRazonSocial(p.getContacto().getCtoRSocial());

			List<ProveedorDTO> proveedores = this.queryProveedores(proveedoresQuery);
			if (proveedores.size() > 0) {
				throw new IllegalArgumentException("Ya existe un proveedor con la misma razón social.");
			}
		}

		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Proveedor libraProveedor = mapper.map(p, uy.com.tmwc.facturator.libra.entity.Proveedor.class);

		HashMap<String, String> adicionales = new HashMap<String, String>();
		if (p.getCanalYoutube() != null) {
			adicionales.put("25", p.getCanalYoutube());
		}
		if (p.getDescuentoRecibo() != null) {
			adicionales.put("24", p.getDescuentoRecibo());
		}
		if (p.getGoogleMaps() != null) {
			adicionales.put("GM", p.getGoogleMaps());
		}
		if (p.getFacturaElectronica()  != null) {
			adicionales.put("59", p.getFacturaElectronica());
		}
		if (p.getNroCuentaCB() != null) {
			adicionales.put("61", p.getNroCuentaCB());
		}
		if (p.getDestinatarioCB()  != null) {
			adicionales.put("62", p.getDestinatarioCB());
		}
		if (p.getBancoCB()  != null) {
			adicionales.put("64", p.getBancoCB());
		}
		if (p.getSucursalCB()  != null) {
			adicionales.put("65", p.getSucursalCB());
		}
		if (p.getDireccionCB() != null) {
			adicionales.put("66", p.getDireccionCB());
		}
		if (p.getObservacionesCB() != null) {
			adicionales.put("67", p.getObservacionesCB());
		}


		libraProveedor.getContacto().setAdicionales(adicionales);
		
		ProveedorPK proveedorPK = new ProveedorPK();
		proveedorPK.setPrvId(p.getCodigo());
		proveedorPK.setEmpId(getEmpId());

		libraProveedor.setId(proveedorPK);
		libraProveedor.setLocIdPrv(Short.parseShort("1")); // por ahora pongo "Casa central"
		libraProveedor.setPrvIdNom(p.getCodigo() + " - " + p.getNombre());

		String ppId = null;
		libraProveedor.setpPidPrv(ppId);

		libraProveedor.setPrvRanking(BigDecimal.ZERO);

		ContactoPK contactoPK = new ContactoPK();
		contactoPK.setCtoId(p.getCodigo());
		contactoPK.setEmpId(getEmpId());
		
		uy.com.tmwc.facturator.libra.entity.Contacto libraContacto = (uy.com.tmwc.facturator.libra.entity.Contacto) this.em.find(uy.com.tmwc.facturator.libra.entity.Contacto.class, contactoPK);
		if (libraContacto != null) {
			libraProveedor.setContacto(libraContacto);
		} else {
			libraContacto = libraProveedor.getContacto();
			libraContacto.setId(contactoPK);
			libraContacto.setCtoAlta(new Date()); // es necesaria la fecha de alta
			libraContacto.setCtoCliente("N"); // estamos insertando un cliente, no un proveedor
			libraContacto.setCtoDocumentoTipo(""); // TODO: el tipo de documento es requerido, lo dejo vacio que parece que funciona.
			libraContacto.setCtoDocumentoSigla("C.I.");
			libraContacto.setCtoNombreCompleto(codigo + " - " + libraProveedor.getNombre()); // redundancia, es requerido.
			libraContacto.setCtoNom(libraProveedor.getNombre()); // redundancia, es requerido.
		}
		
		libraContacto.setCtoProveedor("S");
		libraContacto.setCtoActivo("S"); // siempre insertamos activos
		// libraProveedor.getContacto().setPaisIdCto("UY"); //TODO: el pais es
		// requerido, pongo algo para poder grabar, pero deberia venir del
		// cliente.

		em.persist(libraProveedor);
		this.em.flush();

		return codigo;
	}

	public void merge(Proveedor p) {
		DozerBeanMapper mapper = mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Proveedor libraProveedor = mapper.map(p, uy.com.tmwc.facturator.libra.entity.Proveedor.class);
		
		HashMap<String, String> adicionales = new HashMap<String, String>();
		if (p.getCanalYoutube() != null) {
			adicionales.put("25", p.getCanalYoutube());
		}
		if (p.getDescuentoRecibo() != null) {
			adicionales.put("24", p.getDescuentoRecibo());
		}
		if (p.getGoogleMaps() != null) {
			adicionales.put("GM", p.getGoogleMaps());
		}
		if (p.getFacturaElectronica() != null) {
			adicionales.put("59", p.getFacturaElectronica());
		}
		if (p.getNroCuentaCB() != null) {
			adicionales.put("61", p.getNroCuentaCB());
		}
		if (p.getDestinatarioCB()  != null) {
			adicionales.put("62", p.getDestinatarioCB());
		}
		if (p.getBancoCB()  != null) {
			adicionales.put("64", p.getBancoCB());
		}
		if (p.getSucursalCB()  != null) {
			adicionales.put("65", p.getSucursalCB());
		}
		if (p.getDireccionCB() != null) {
			adicionales.put("66", p.getDireccionCB());
		}
		if (p.getObservacionesCB() != null) {
			adicionales.put("67", p.getObservacionesCB());
		}
		
		String codigo = p.getCodigo();
		libraProveedor.getContacto().setCtoNom(libraProveedor.getNombre()); // redundancia, es requerido.
		libraProveedor.getContacto().setCtoNombreCompleto(codigo + " - " + libraProveedor.getNombre()); // redundancia, es requerido.
		libraProveedor.getContacto().setAdicionales(adicionales);
		libraProveedor.getContacto().setCtoProveedor("S");

		this.em.merge(libraProveedor);
		this.em.flush();
	}

}
