package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.util.List;

public class Contados implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<CuotaparteContado> cuotaparteContados;

	public List<CuotaparteContado> getCuotaparteContados() {
		return this.cuotaparteContados;
	}

	public void setCuotaparteContados(List<CuotaparteContado> cuotaparteContados) {
		this.cuotaparteContados = cuotaparteContados;
	}
}