package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class DescuentoPrometidoComprobante implements Serializable {
	private static final long serialVersionUID = 1L;

	private CategoriasClientes categCliente;

	private Comprobante comprobante;
	
	private int dpcId;
	
	private int retraso;
	
	private BigDecimal descuento;
	
	private String categoriaCliente;

	private int cmpid;
	
	public String getCategoriaCliente() {
		return categoriaCliente;
	}

	public void setCategoriaCliente(String categoriaCliente) {
		this.categoriaCliente = categoriaCliente;
	}

	public int getCmpid() {
		return cmpid;
	}

	public void setCmpid(int cmpid) {
		this.cmpid = cmpid;
	}

	public int getDpcId() {
		return dpcId;
	}

	public void setDpcId(int dpcId) {
		this.dpcId = dpcId;
	}

	public CategoriasClientes getCategCliente() {
		return categCliente;
	}

	public void setCategCliente(CategoriasClientes categCliente) {
		this.categCliente = categCliente;
	}

	public Comprobante getComprobante() {
		return this.comprobante;
	}

	public void setComprobante(Comprobante comprobante) {
		this.comprobante = comprobante;
	}

	public int getRetraso() {
		return this.retraso;
	}

	public void setRetraso(int retraso) {
		this.retraso = retraso;
	}

	public BigDecimal getDescuento() {
		return this.descuento;
	}

	public void setDescuento(BigDecimal descuento) {
		this.descuento = descuento;
	}
	


}