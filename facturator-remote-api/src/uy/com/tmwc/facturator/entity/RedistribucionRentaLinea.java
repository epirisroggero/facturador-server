package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import uy.com.tmwc.facturator.utils.Maths;

public class RedistribucionRentaLinea {
	private LineaDocumento linea;
	private IAportaRenta aportadorRenta;
	private Jefatura jefatura;
	private HashMap<Vendedor, BigDecimal> pagaVendedorMap = new HashMap<Vendedor, BigDecimal>();
	private BigDecimal cobraJefe;

	public RedistribucionRentaLinea(LineaDocumento linea, IAportaRenta aportadorRenta, Jefatura jefatura) {
		this.linea = linea;
		this.aportadorRenta = aportadorRenta;
		this.jefatura = jefatura;

		init();
	}

	private void init() {
		BigDecimal peso = this.linea.getPesoDocumento();
		BigDecimal renta = this.aportadorRenta.getRenta();
		Collection<ParticipacionVendedor> participaciones = this.aportadorRenta.getParticipaciones();
		Vendedor vendedor;
		for (ParticipacionVendedor participacionVendedor : participaciones) {
			vendedor = participacionVendedor.getVendedor();
			BigDecimal comisionJefatura = this.jefatura.getComisionVendedor(vendedor);
			BigDecimal pagaVendedor = peso.multiply(renta).multiply(comisionJefatura).multiply(participacionVendedor.getPorcentaje())
					.divide(Maths.ONE_HUNDRED).divide(Maths.ONE_HUNDRED);
			this.pagaVendedorMap.put(vendedor, pagaVendedor);
		}

		BigDecimal cobraJefe = BigDecimal.ZERO;
		for (BigDecimal monto : this.pagaVendedorMap.values()) {
			cobraJefe = cobraJefe.add(monto);
		}
		this.cobraJefe = cobraJefe;
	}

	public BigDecimal getCobraJefe() {
		return this.cobraJefe;
	}

	public BigDecimal getPagaVendedor(Vendedor vendedor) {
		BigDecimal res = (BigDecimal) this.pagaVendedorMap.get(vendedor);
		return res == null ? BigDecimal.ZERO : res;
	}

	public IAportaRenta getAportadorRenta() {
		return this.aportadorRenta;
	}

	public Jefatura getJefatura() {
		return this.jefatura;
	}

	public LineaDocumento getLinea() {
		return this.linea;
	}
}