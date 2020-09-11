package uy.com.tmwc.facturator.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

public class ClienteDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	private String nombre;
	private String razonSocial;

	private String direccion;
	private String telefono;
	private String celular;
	
	private String email;
	private String zona;
	private String localidad;
	private String depto;
	private String rut;
	
	private String encargadoCuenta;
	private String especialista1;
	private String especialista2;
	
	private String categCliId;
	private String venIdCli;
	
	private BigDecimal cliTopeCredito;
	
	private Boolean activo;
	
	
	public ClienteDTO() {
	}

	public ClienteDTO(Object cliCodigo, String cliNombre) {
		this.codigo = cliCodigo.toString();
		this.nombre = cliNombre;
	}

	public ClienteDTO(Object cliCodigo, String cliNombre, String ctoRazonSocial, String ctoDireccion, String ctoTelefono, String ctoCelular, String ctoEmail, 
			String ctoZona, String ctoLocalida, String ctoDepto) {
		this(cliCodigo, cliNombre, ctoRazonSocial, ctoDireccion, ctoTelefono, ctoCelular, ctoEmail, ctoZona, ctoLocalida, ctoDepto, null, null, null, false);
	}

	public ClienteDTO(Object cliCodigo, String cliNombre, String ctoRazonSocial, String ctoDireccion, String ctoTelefono, String ctoCelular, String ctoEmail, 
			String ctoZona, String ctoLocalidad, String ctoDepto, Map<String, String> adicionales, String activo) {
		this(cliCodigo, cliNombre, ctoRazonSocial, ctoDireccion, ctoTelefono, ctoCelular, ctoEmail, ctoZona, ctoLocalidad, ctoDepto, null, null, null, "S".equals(activo));
	}

	
	public ClienteDTO(Object cliCodigo, String cliNombre, String ctoRazonSocial, String ctoDireccion, String ctoTelefono, String ctoCelular, String ctoEmail, 
		String ctoZona, String ctoLocalida, String ctoDepto, String encargadoCuenta, String especialista1, String especialista2, Boolean activo) {
		
		this.codigo = cliCodigo.toString();
		this.nombre = cliNombre;
		
		this.razonSocial = ctoRazonSocial;
		this.direccion = ctoDireccion;
		this.telefono = ctoTelefono;
		this.celular = ctoCelular;
		
		this.email = ctoEmail;
		this.zona = ctoZona;
		this.localidad = ctoLocalida;
		this.depto = ctoDepto;
		
		this.encargadoCuenta = encargadoCuenta;
		this.especialista1 = especialista1;
		this.especialista2 = especialista2;
		
		this.activo = activo;
	}
			
	public ClienteDTO(
			Object codigo, 
			String nombre, 
			String ctoRSocial, 
			String ctoRut, 
			String ctoDireccion, 
			String ctoTelefono, 
			String ctoCelular, 
			BigDecimal cliTopeCredito, 
			String categCliId, 
			String venIdCli/*,
			String encargadoCuenta*/) {
		
		this.codigo = codigo.toString();
		this.nombre = nombre;
		this.razonSocial = ctoRSocial;
		this.rut = ctoRut;
		this.direccion = ctoDireccion;
		this.telefono = ctoTelefono;
		this.celular = ctoCelular;
		this.cliTopeCredito = cliTopeCredito;
		this.categCliId = categCliId;
		this.venIdCli = venIdCli;
		/*this.encargadoCuenta = encargadoCuenta;*/
		this.activo = true;
		
	}
	
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getDepto() {
		return depto;
	}

	public void setDepto(String depto) {
		this.depto = depto;
	}

	public String getZona() {
		return zona;
	}

	public void setZona(String zona) {
		this.zona = zona;
	}

	public String getRazonSocial() {
		return razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getEncargadoCuenta() {
		return encargadoCuenta;
	}

	public void setEncargadoCuenta(String encargadoCuenta) {
		this.encargadoCuenta = encargadoCuenta;
	}

	public String getEspecialista1() {
		return especialista1;
	}

	public void setEspecialista1(String especialista1) {
		this.especialista1 = especialista1;
	}

	public String getEspecialista2() {
		return especialista2;
	}

	public void setEspecialista2(String especialista2) {
		this.especialista2 = especialista2;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}
	
	public BigDecimal getCliTopeCredito() {
		return cliTopeCredito;
	}

	public void setCliTopeCredito(BigDecimal cliTopeCredito) {
		this.cliTopeCredito = cliTopeCredito;
	}

	public String getCategCliId() {
		return categCliId;
	}

	public void setCategCliId(String categCliId) {
		this.categCliId = categCliId;
	}

	public String getVenIdCli() {
		return venIdCli;
	}

	public void setVenIdCli(String venIdCli) {
		this.venIdCli = venIdCli;
	}




}