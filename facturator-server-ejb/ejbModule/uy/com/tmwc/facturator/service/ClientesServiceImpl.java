package uy.com.tmwc.facturator.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

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
import uy.com.tmwc.facturator.entity.ValidationException;
import uy.com.tmwc.facturator.rapi.ClientesService;
import uy.com.tmwc.facturator.spi.ClientesDAOService;

@Name("clientesService")
@Stateless
@Local(ClientesService.class)
@Remote(ClientesService.class)
public class ClientesServiceImpl implements ClientesService {

	@EJB ClientesDAOService clientesDAOService;
	
	public ClientesServiceImpl() {
	}

	public String persist(Cliente e, Boolean force) {
		return clientesDAOService.persist(e, force);
	}

	public void merge(Cliente e) {
		clientesDAOService.merge(e);
	}

	public Boolean delete(Cliente e) {
		return clientesDAOService.delete(e);
	}
	
	public String verifyContacto(Contacto e) {
		return clientesDAOService.verifyContacto(e);
	}
	
	public String getLastId() {
		return clientesDAOService.getLastId();
	}

	public List<ClienteDTO> queryClientes(ClienteQuery paramClienteQuery) throws ValidationException {
		return clientesDAOService.queryClientes(paramClienteQuery);
	}
	
	public List<ContactoDTO> queryContactos(ContactoQuery contactoQuery) {
		return clientesDAOService.queryContactos(contactoQuery);
	}
	
	public List<ProveedorDTO> queryProveedores(ProveedoresQuery paramProvQuery) {
		return clientesDAOService.queryProveedores(paramProvQuery);
	}
	
	public List<Persona> queryPersonas(String codigo) {
		return clientesDAOService.queryPersonas(codigo);
	}

	public String persist(Persona p) {
		return clientesDAOService.persist(p);
	}
	
	public void merge(Persona p) {
		clientesDAOService.merge(p);
	}

	public Boolean delete(Persona p) {
		return clientesDAOService.delete(p);
	}
	
	public String persist(Proveedor p) {
		return clientesDAOService.persist(p);
	}
	 
	public void merge(Proveedor p) {
		clientesDAOService.merge(p);
	}

	 
}
