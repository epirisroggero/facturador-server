package uy.com.tmwc.facturator.session;

import java.math.BigDecimal;
import java.util.Collection;

import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.IAportaRenta;
import uy.com.tmwc.facturator.entity.LineaDocumento;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.ParticipacionVendedor;

public class RentaContado implements IAportaRenta {
	private Documento contado;

	public RentaContado(Documento contado) {
		this.contado = contado;
	}

	public Collection<LineaDocumento> getLineas() {
		return this.contado.getLineas().getLineas();
	}

	public Moneda getMoneda() {
		return this.contado.getMoneda();
	}

	public Collection<ParticipacionVendedor> getParticipaciones() {
		return this.contado.getComisiones().getParticipaciones();
	}

	public BigDecimal getRenta() {
		return this.contado.getRentaNetaComercial();
	}
}