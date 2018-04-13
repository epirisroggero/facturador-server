package uy.com.tmwc.facturator.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.entity.VinculoDocumentos;
import uy.com.tmwc.facturator.utils.Maths;

public class ParticipacionEnCobranza implements Serializable {
	private static final long serialVersionUID = 1L;
	private ParticipacionVendedor participacionVendedor;
	private VinculoDocumentos vinculo;

	public ParticipacionEnCobranza() {
	}

	public ParticipacionEnCobranza(ParticipacionVendedor pv, VinculoDocumentos vinculo) {
		this.participacionVendedor = pv;
		this.vinculo = vinculo;
	}

	public ParticipacionVendedor getParticipacionVendedor() {
		return this.participacionVendedor;
	}

	public void setParticipacionVendedor(ParticipacionVendedor participacionVendedor) {
		this.participacionVendedor = participacionVendedor;
	}

	public VinculoDocumentos getVinculo() {
		return this.vinculo;
	}

	public void setVinculo(VinculoDocumentos vinculo) {
		this.vinculo = vinculo;
	}

	public Documento getFactura() {
		return this.vinculo.getFactura();
	}

	public Documento getRecibo() {
		return this.vinculo.getRecibo();
	}

	public BigDecimal getRentaACobrar() {
		return Maths.calcularMontoDescuento(getVinculo().getCuotaparteRentaComercial(), this.participacionVendedor.getPorcentaje());
	}
	
	public BigDecimal getCuotaparteOperativos() {
		return Maths.calcularMontoDescuento(getVinculo().getCuotaparteOperativos(), this.participacionVendedor.getPorcentaje());
	}
}