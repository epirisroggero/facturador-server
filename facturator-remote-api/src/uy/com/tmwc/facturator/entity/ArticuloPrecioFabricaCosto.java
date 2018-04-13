package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ArticuloPrecioFabricaCosto extends CodigoNombreEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nuevaMonedaId = Moneda.CODIGO_MONEDA_DOLAR;
	
	private String viejaMonedaId = Moneda.CODIGO_MONEDA_DOLAR;
	
	private BigDecimal precioViejo = BigDecimal.ZERO;
	
	private BigDecimal precioNuevo = BigDecimal.ZERO;

	public String getNuevaMonedaId() {
		return nuevaMonedaId;
	}

	public void setNuevaMonedaId(String nuevaMonedaId) {
		this.nuevaMonedaId = nuevaMonedaId;
	}

	public String getViejaMonedaId() {
		return viejaMonedaId;
	}

	public void setViejaMonedaId(String viejaMonedaId) {
		this.viejaMonedaId = viejaMonedaId;
	}

	public BigDecimal getPrecioViejo() {
		return precioViejo;
	}

	public void setPrecioViejo(BigDecimal precioViejo) {
		this.precioViejo = precioViejo;
	}

	public BigDecimal getPrecioNuevo() {
		return precioNuevo;
	}

	public void setPrecioNuevo(BigDecimal precioNuevo) {
		this.precioNuevo = precioNuevo;
	}
	


}
