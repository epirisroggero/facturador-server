package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.util.List;

public class Recibos implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<CuotaparteRecibo> cuotapartesRecibos;

	public List<CuotaparteRecibo> getCuotapartesRecibos() {
		return this.cuotapartesRecibos;
	}

	public void setCuotapartesRecibos(List<CuotaparteRecibo> cuotapartesRecibos) {
		this.cuotapartesRecibos = cuotapartesRecibos;
	}
}