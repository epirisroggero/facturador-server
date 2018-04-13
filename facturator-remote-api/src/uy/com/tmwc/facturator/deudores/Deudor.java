package uy.com.tmwc.facturator.deudores;

import java.io.Serializable;
import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.entity.Cliente;

public class Deudor implements Serializable {
	private static final long serialVersionUID = 1L;
	private String codigo;
	private String nombre;
	private String razonSocial;
	private String direccion;
	private String rut;
	private String telefono;
	private String fax;
	private CodigoNombre vendedor;
	private boolean sinDerechoDescuentos;

	public Deudor() {
	}
	
	public Deudor(Cliente cliente) {
		codigo = cliente.getCodigo();
		nombre = cliente.getNombre();
		
	}

	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRazonSocial() {
		return this.razonSocial;
	}

	public void setRazonSocial(String razonSocial) {
		this.razonSocial = razonSocial;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getRut() {
		return this.rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public CodigoNombre getVendedor() {
		return this.vendedor;
	}

	public void setVendedor(CodigoNombre vendedor) {
		this.vendedor = vendedor;
	}

	public boolean isSinDerechoDescuentos() {
		return this.sinDerechoDescuentos;
	}

	public void setSinDerechoDescuentos(boolean sinDerechoDescuentos) {
		this.sinDerechoDescuentos = sinDerechoDescuentos;
	}
}