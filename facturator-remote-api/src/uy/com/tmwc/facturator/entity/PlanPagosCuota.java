package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class PlanPagosCuota
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private PlanPagos planPagos;
  private short numero;
  private short dias;
  private short mes;
  private BigDecimal porcentaje;

  public PlanPagosCuota(PlanPagos planPagos, short numero, short dias, short mes, BigDecimal porcentaje)
  {
    this.planPagos = planPagos;
    this.numero = numero;
    this.dias = dias;
    this.mes = mes;
    this.porcentaje = porcentaje;
  }

  public PlanPagosCuota()
  {
  }

  public PlanPagos getPlanPagos() {
    return this.planPagos;
  }

  public void setPlanPagos(PlanPagos planPagos) {
    this.planPagos = planPagos;
  }

  public short getNumero() {
    return this.numero;
  }

  public void setNumero(short numero) {
    this.numero = numero;
  }

  public short getDias() {
    return this.dias;
  }

  public void setDias(short dias) {
    this.dias = dias;
  }

  public short getMes() {
    return this.mes;
  }

  public void setMes(short mes) {
    this.mes = mes;
  }

  public BigDecimal getPorcentaje() {
    return this.porcentaje;
  }

  public void setPorcentaje(BigDecimal porcentaje) {
    this.porcentaje = porcentaje;
  }
}