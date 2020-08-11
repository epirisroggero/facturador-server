package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import uy.com.tmwc.facturator.utils.Maths;

public class ComisionesDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	private Documento documento;
	private List<ParticipacionVendedor> participaciones;

	public ComisionesDocumento() {
		this.participaciones = new ArrayList<ParticipacionVendedor>();
	}

	public ComisionesDocumento(Documento documento) {
		this();
		this.documento = documento;
	}

	public void validate() throws ValidationException {
		if (this.participaciones.size() == 0) {
			return;
		}
		BigDecimal sum = BigDecimal.ZERO;
		for (ParticipacionVendedor pv : this.participaciones) {
			if ((pv.getPorcentaje() == null) || (pv.getVendedor() == null)) {
				throw new ValidationException("Los campos son obligatorios");
			}
			sum = sum.add(pv.getPorcentaje());
		}

		if (sum.compareTo(Maths.ONE_HUNDRED) != 0)
			throw new ValidationException("El porcentaje debe sumar 100%");
	}

	public List<ParticipacionVendedor> getParticipaciones() {
		return this.participaciones;
	}

	public void setParticipaciones(List<ParticipacionVendedor> participaciones) {
		this.participaciones = participaciones;
	}

	public void add(ParticipacionVendedor element) {
		this.participaciones.add(element);
	}

	public void remove(int index) {
		this.participaciones.remove(index);
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
}