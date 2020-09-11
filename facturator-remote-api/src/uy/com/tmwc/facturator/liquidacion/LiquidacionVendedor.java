package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import uy.com.tmwc.facturator.dto.CodigoNombre;

public class LiquidacionVendedor implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private CodigoNombre vendedor;
	private Recibos recibos;
	private Contados contados;
	private CuotaparteOperativosVentas cuotaparteOperativosVentas;
	private RentasCobradas rentasCobradas;
	private RentasPagas rentasPagas;

	public LiquidacionVendedor() {
	}

	public LiquidacionVendedor(CodigoNombre vendedor) {
		this.vendedor = vendedor;
	}

	public Recibos getRecibos() {
		return this.recibos;
	}

	public void setRecibos(Recibos recibos) {
		this.recibos = recibos;
	}

	public Contados getContados() {
		return this.contados;
	}

	public void setContados(Contados contados) {
		this.contados = contados;
	}

	public RentasCobradas getRentasCobradas() {
		return this.rentasCobradas;
	}

	public void setRentasCobradas(RentasCobradas rentasCobradas) {
		this.rentasCobradas = rentasCobradas;
	}

	public RentasPagas getRentasPagas() {
		return this.rentasPagas;
	}

	public void setRentasPagas(RentasPagas rentasPagas) {
		this.rentasPagas = rentasPagas;
	}

	public CodigoNombre getVendedor() {
		return this.vendedor;
	}

	public void setVendedor(CodigoNombre vendedor) {
		this.vendedor = vendedor;
	}

	public CuotaparteOperativosVentas getCuotaparteOperativosVentas() {
		return this.cuotaparteOperativosVentas;
	}

	public void setCuotaparteOperativosVentas(CuotaparteOperativosVentas cuotaparteOperativosVentas) {
		this.cuotaparteOperativosVentas = cuotaparteOperativosVentas;
	}
}