package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import uy.com.tmwc.facturator.utils.Dates;
import uy.com.tmwc.facturator.utils.Maths;

public class CuotasDocumento implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<CuotaDocumento> cuotas;
	private Documento documento;

	public CuotasDocumento() {
	}

	public CuotasDocumento(Documento documento) {
		this.documento = documento;
		this.cuotas = new ArrayList<CuotaDocumento>();
	}

	public BigDecimal getSumaCuotas() {
		if (this.cuotas == null) {
			return BigDecimal.ZERO;
		}
		BigDecimal sum = BigDecimal.ZERO;
		for (CuotaDocumento cuota : this.cuotas) {
			sum = sum.add(cuota.getImporte());
		}
		return sum;
	}

	public void validate() throws ValidationException {
		if (getSumaCuotas().compareTo(this.documento.getTotal()) != 0)
			throw new ValidationException("El total de cuotas (" + getSumaCuotas() + ") es diferente al importe de la factura ("
					+ this.documento.getTotal() + ")");
	}

	public int getCuotasCubiertas(BigDecimal monto) {
		int cuotasCubiertas = 0;
		BigDecimal sumaCuotas = BigDecimal.ZERO;
		for (CuotaDocumento cuotaDocumento : this.cuotas) {
			BigDecimal sumaCuotasTemp = sumaCuotas.add(cuotaDocumento.getImporte());
			if (sumaCuotasTemp.compareTo(monto) > 0) {
				break;
			}
			sumaCuotas = sumaCuotasTemp;
			cuotasCubiertas++;
		}
		return cuotasCubiertas;
	}

	public List<CuotaDocumento> getCuotas() {
		return this.cuotas;
	}

	public void setCuotas(List<CuotaDocumento> cuotas) {
		this.cuotas = cuotas;
	}

	public void clear() {
		this.cuotas.clear();
	}

	public void inicializarCuotas() {
		PlanPagos plan = this.documento.getPlanPagos();
		if (plan == null) {
			this.cuotas.clear();
			return;
		}

		this.cuotas.clear();

		BigDecimal total = this.documento.getTotal();
		BigDecimal acumCuotas = BigDecimal.ZERO;
		BigDecimal redondeoMinValue;
		if (plan.isAcumularDecimales())
			redondeoMinValue = BigDecimal.ONE.scaleByPowerOfTen(-1 * this.documento.getMoneda().getRedondeo());
		else {
			redondeoMinValue = new BigDecimal("0.01");
		}

		if (plan.isCuotasIguales()) {
			Date vencimiento = (Date) this.documento.getFecha().clone();
			Dates.addMonthsToDate(vencimiento, plan.getPrimerMes());
			Dates.addDaysToDate(vencimiento, plan.getPrimerDia());
			BigDecimal importeCuota;
			if (plan.isAcumularDecimales())
				importeCuota = total.divide(new BigDecimal(plan.getCantidadCuotas()), this.documento.getMoneda().getRedondeo(), 4);
			else {
				importeCuota = total.divide(new BigDecimal(plan.getCantidadCuotas()), 2, 4);
			}

			for (int i = 0; i < plan.getCantidadCuotas(); i++) {
				BigDecimal importe = importeCuota;

				this.cuotas.add(new CuotaDocumento(this.documento, i + 1, vencimiento, importe));

				acumCuotas = acumCuotas.add(importe);
				vencimiento = (Date) vencimiento.clone();
				Dates.addMonthsToDate(vencimiento, plan.getSeparacionMes());
				Dates.addDaysToDate(vencimiento, plan.getSeparacionDia());
			}
		} else {
			Date vencimiento = (Date) this.documento.getFecha().clone();
			int numero = 1;
			int decimales = plan.isAcumularDecimales() ? this.documento.getMoneda().getRedondeo() : 2;
			for (PlanPagosCuota ppc : plan.getPlanPagosCuotas()) {
				Dates.addMonthsToDate(vencimiento, plan.getSeparacionMes());
				Dates.addDaysToDate(vencimiento, plan.getSeparacionDia());
				vencimiento = (Date) vencimiento.clone();

				BigDecimal importe = total.multiply(ppc.getPorcentaje()).divide(Maths.ONE_HUNDRED, decimales, 4);
				this.cuotas.add(new CuotaDocumento(this.documento, numero++, vencimiento, importe));
				acumCuotas = acumCuotas.add(importe);
			}
		}

		BigDecimal diferencia = total.subtract(acumCuotas);
		Iterator<CuotaDocumento> iterator;
		if (diferencia.signum() > 0) {
			iterator = this.cuotas.iterator();
		} else {
			final ListIterator<CuotaDocumento> listIterator = this.cuotas.listIterator(this.cuotas.size());

			iterator = new Iterator<CuotaDocumento>() {
				public boolean hasNext() {
					return listIterator.hasPrevious();
				}

				public CuotaDocumento next() {
					return listIterator.previous();
				}

				public void remove() {
					throw new UnsupportedOperationException();
				}
			};
		}
		BigDecimal valueToAdd = redondeoMinValue.multiply(new BigDecimal(diferencia.signum()));
		while ((diferencia.signum() != 0) && (iterator.hasNext())) {
			CuotaDocumento cuota = (CuotaDocumento) iterator.next();
			cuota.setImporte(cuota.getImporte().add(valueToAdd));
			diferencia = diferencia.subtract(valueToAdd);
		}
	}

	public boolean isEmpty() {
		return this.cuotas.size() == 0;
	}

	public BigDecimal sumarCuotas(int desde, int hasta) {
		BigDecimal suma = BigDecimal.ZERO;
		for (int i = desde; i <= hasta; i++) {
			suma = suma.add(((CuotaDocumento) this.cuotas.get(i)).getImporte());
		}
		return suma;
	}

	public BigDecimal calcularDeuda(Date today, BigDecimal cancelado, String categoriaCliente) {
		return calcularDeudaFull(today, cancelado, categoriaCliente).deuda;
	}

	private CalculoDeudaResult calcularDeudaFull(Date today, BigDecimal cancelado, String categoriaCliente) {
		int primerCuotaSinCancelar = getCuotasCubiertas(cancelado);

		if (primerCuotaSinCancelar > this.cuotas.size()) {
			CalculoDeudaResult result = new CalculoDeudaResult();
			result.tieneCuotaVencida = false;
			result.deuda = BigDecimal.ZERO;
			return result;
		}

		BigDecimal favorable = cancelado.subtract(sumarCuotas(0, primerCuotaSinCancelar - 1));

		BigDecimal deuda = BigDecimal.ZERO;

		CalculoDeudaResult result = new CalculoDeudaResult();
		for (int i = primerCuotaSinCancelar; i < this.cuotas.size(); i++) {
			CuotaDocumento cuota = (CuotaDocumento) this.cuotas.get(i);
			BigDecimal importe = cuota.getImporte().subtract(favorable);
			int retraso = cuota.getRetrasoDias(today);
			if (retraso > 0) {
				result.tieneCuotaVencida = true;
			}
			//this.documento.getComprobante().getDescuentoPrometido(retraso, categoriaCliente);
			BigDecimal importeConDto = this.documento.getComprobante().aplicarDescuentoPrometido(importe, retraso, categoriaCliente);
			deuda = deuda.add(importeConDto);

			favorable = BigDecimal.ZERO;
		}

		result.deuda = deuda;
		return result;
	}

	public boolean isTieneCuotaVencida(Date today, BigDecimal cancelado) {
		return calcularDeudaFull(today, cancelado, null /* don't care */).tieneCuotaVencida;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	private static class CalculoDeudaResult {
		public boolean tieneCuotaVencida;
		public BigDecimal deuda;
	}
}