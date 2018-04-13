package uy.com.tmwc.facturator.rapi;

import java.util.List;

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

public abstract interface ClientesService {
	 
	public abstract String persist(Cliente e, Boolean force);
	 
	public abstract void merge(Cliente e);
	 
	public abstract Boolean delete(Cliente e);
	
	public abstract String verifyContacto(Contacto e);
	
	public abstract String getLastId();
	
	List<ClienteDTO> queryClientes(ClienteQuery paramClienteQuery) throws ValidationException;
	
	List<ContactoDTO> queryContactos(ContactoQuery contactoQuery);
	
	List<ProveedorDTO> queryProveedores(ProveedoresQuery paramProvQuery);

	public abstract List<Persona> queryPersonas(String codigo);

	public abstract String persist(Persona p);
	
	public abstract void merge(Persona p);
	
	public abstract Boolean delete(Persona p);
	
	public abstract String persist(Proveedor e);
	 
	public abstract void merge(Proveedor e);
	 
}
