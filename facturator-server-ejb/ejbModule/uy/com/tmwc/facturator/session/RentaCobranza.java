package uy.com.tmwc.facturator.session;

import java.math.BigDecimal;
import java.util.Collection;

import uy.com.tmwc.facturator.entity.IAportaRenta;
import uy.com.tmwc.facturator.entity.LineaDocumento;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.entity.VinculoDocumentos;

public class RentaCobranza implements IAportaRenta {
	private VinculoDocumentos vinculo;

	public RentaCobranza(VinculoDocumentos vinculo) {
		this.vinculo = vinculo;
	}

	public Collection<LineaDocumento> getLineas() {
		return this.vinculo.getFactura().getLineas().getLineas();
	}

	public Moneda getMoneda() {
		return this.vinculo.getFactura().getMoneda();
	}

	public Collection<ParticipacionVendedor> getParticipaciones() {
		return this.vinculo.getFactura().getComisiones().getParticipaciones();
	}

	public BigDecimal getRenta() {
		return this.vinculo.getCuotaparteRentaComercial();
	}
}