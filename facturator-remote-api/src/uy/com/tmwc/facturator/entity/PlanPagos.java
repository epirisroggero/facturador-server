package uy.com.tmwc.facturator.entity;

import java.util.List;

public class PlanPagos extends CodigoNombreEntity
{
  private static final long serialVersionUID = 1L;
  private short cantidadCuotas;
  private boolean cuotasIguales;
  private short primerDia;
  private short primerMes;
  private short separacionDia;
  private short separacionMes;
  private boolean acumularDecimales;
  List<PlanPagosCuota> planPagosCuotas;

  public short getCantidadCuotas()
  {
    return this.cantidadCuotas;
  }

  public void setCantidadCuotas(short cantidadCuotas) {
    this.cantidadCuotas = cantidadCuotas;
  }

  public boolean isCuotasIguales() {
    return this.cuotasIguales;
  }

  public void setCuotasIguales(boolean cuotasIguales) {
    this.cuotasIguales = cuotasIguales;
  }

  public short getPrimerDia() {
    return this.primerDia;
  }

  public void setPrimerDia(short primerDia) {
    this.primerDia = primerDia;
  }

  public short getPrimerMes() {
    return this.primerMes;
  }

  public void setPrimerMes(short primerMes) {
    this.primerMes = primerMes;
  }

  public short getSeparacionDia() {
    return this.separacionDia;
  }

  public void setSeparacionDia(short separacionDia) {
    this.separacionDia = separacionDia;
  }

  public short getSeparacionMes() {
    return this.separacionMes;
  }

  public void setSeparacionMes(short separacionMes) {
    this.separacionMes = separacionMes;
  }

  public List<PlanPagosCuota> getPlanPagosCuotas() {
    return this.planPagosCuotas;
  }

  public void setPlanPagosCuotas(List<PlanPagosCuota> planPagosCuotas) {
    this.planPagosCuotas = planPagosCuotas;
  }

  public boolean isAcumularDecimales() {
    return this.acumularDecimales;
  }

  public void setAcumularDecimales(boolean acumularDecimales) {
    this.acumularDecimales = acumularDecimales;
  }
}