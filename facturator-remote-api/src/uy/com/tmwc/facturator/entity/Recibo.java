package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;
import java.util.Set;
import uy.com.tmwc.facturator.utils.Maths;

public class Recibo extends DocumentoBase {
	private static final long serialVersionUID = 1L;
	private BigDecimal cancela;
	private BigDecimal docDescuentos;
	private BigDecimal descuentosPorc = BigDecimal.ZERO;
	private Set<VinculoDocumentos> facturasVinculadas;

	public BigDecimal getPaga() {
		return Maths.descontar(this.cancela, this.docDescuentos);
	}

	public BigDecimal getCancela() {
		return this.cancela;
	}

	public void setCancela(BigDecimal cancela) {
		this.cancela = cancela;
	}

	public Set<VinculoDocumentos> getFacturasVinculadas() {
		return this.facturasVinculadas;
	}

	public void setFacturasVinculadas(Set<VinculoDocumentos> facturasVinculadas) {
		this.facturasVinculadas = facturasVinculadas;
	}

	public BigDecimal getDescuentosPorc() {
		return descuentosPorc;
	}

	public void setDescuentosPorc(BigDecimal descuentosPorc) {
		this.descuentosPorc = descuentosPorc;
	}

	@Override
	public BigDecimal getTotal() {
		return total;
	}

	@Override
	public void setTotal(BigDecimal total) {
		this.total = total;
		this.cancela = total;
	}
}