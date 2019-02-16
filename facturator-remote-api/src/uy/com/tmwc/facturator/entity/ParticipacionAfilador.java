package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ParticipacionAfilador implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String afilador;
	
	private Date fecha;
	
	private String serieNro;
	
	private String item;
	
	private String moneda;
	
	public String getMoneda() {
		return moneda;
	}

	public void setMoneda(String moneda) {
		this.moneda = moneda;
	}

	private String cliente;
	
	private BigDecimal dientes;
	
	private BigDecimal precio;
	
	private BigDecimal importe;
	
	private BigDecimal percentage;
	
	private BigDecimal montoACobrar;

	public String getAfilador() {
		return afilador;
	}

	public void setAfilador(String afilador) {
		this.afilador = afilador;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getSerieNro() {
		return serieNro;
	}

	public void setSerieNro(String serieNro) {
		this.serieNro = serieNro;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getCliente() {
		return cliente;
	}

	public void setCliente(String cliente) {
		this.cliente = cliente;
	}

	public BigDecimal getDientes() {
		return dientes;
	}

	public void setDientes(BigDecimal dientes) {
		this.dientes = dientes;
	}

	public BigDecimal getPrecio() {
		return precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getImporte() {
		return importe;
	}

	public void setImporte(BigDecimal importe) {
		this.importe = importe;
	}

	public BigDecimal getPercentage() {
		return percentage;
	}

	public void setPercentage(BigDecimal percentage) {
		this.percentage = percentage;
	}

	public BigDecimal getMontoACobrar() {
		return montoACobrar;
	}

	public void setMontoACobrar(BigDecimal montoACobrar) {
		this.montoACobrar = montoACobrar;
	}
	
	
	
	

	
}
