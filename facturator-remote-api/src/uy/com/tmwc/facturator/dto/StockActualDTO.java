package uy.com.tmwc.facturator.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class StockActualDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String articuloId, cantidad; 
	
	private static final NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("ES"));

	public StockActualDTO(){
	}

	public StockActualDTO(String articuloId, BigDecimal cantidad) {
		this.articuloId = articuloId;
		
		formatter.setMinimumFractionDigits(2);
		formatter.setMaximumFractionDigits(2);
		
		this.cantidad = formatter.format(cantidad != null ? cantidad : 0);
	}

	
	public String getArticuloId() {
		return articuloId;
	}

	public void setArticuloId(String articuloId) {
		this.articuloId = articuloId;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}


}