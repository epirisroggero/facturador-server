package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import uy.com.tmwc.facturator.entity.VinculoDocumentos;

public class ReferenciaFactura
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Recibo recibo;
  private Factura factura;
  private BigDecimal pago;
  private BigDecimal montoDescuentoEsperado;
  private BigDecimal montoDescuentoReal;
  private BigDecimal rentaFinanciera;
  private BigDecimal cuotaparteRentaComercial;

  public ReferenciaFactura()
  {
  }

  public ReferenciaFactura(Recibo recibo, Factura factura, VinculoDocumentos vinculo)
  {
    this.recibo = recibo;
    this.factura = factura;
    this.pago = vinculo.getMonto();
    this.montoDescuentoEsperado = vinculo.getMontoDescuentoEsperado();
    this.montoDescuentoReal = vinculo.getMontoDescuentoReal();
    this.rentaFinanciera = vinculo.getRentaFinanciera();
    this.cuotaparteRentaComercial = vinculo.getCuotaparteRentaComercial();
  }

  public BigDecimal getCuotaparteRentaComercial() {
    return this.cuotaparteRentaComercial;
  }

  public BigDecimal getMontoDescuentoEsperado()
  {
    return this.montoDescuentoEsperado;
  }

  public BigDecimal getMontoDescuentoReal() {
    return this.montoDescuentoReal;
  }

  public BigDecimal getRentaFinanciera() {
    return this.rentaFinanciera;
  }

  public Factura getFactura() {
    return this.factura;
  }

  public void setFactura(Factura factura) {
    this.factura = factura;
  }

  public Recibo getRecibo() {
    return this.recibo;
  }

  public void setRecibo(Recibo recibo) {
    this.recibo = recibo;
  }

  public BigDecimal getPago() {
    return this.pago;
  }

  public void setPago(BigDecimal pago) {
    this.pago = pago;
  }

  public void setMontoDescuentoEsperado(BigDecimal montoDescuentoEsperado) {
    this.montoDescuentoEsperado = montoDescuentoEsperado;
  }

  public void setMontoDescuentoReal(BigDecimal montoDescuentoReal) {
    this.montoDescuentoReal = montoDescuentoReal;
  }

  public void setRentaFinanciera(BigDecimal rentaFinanciera) {
    this.rentaFinanciera = rentaFinanciera;
  }

  public void setCuotaparteRentaComercial(BigDecimal cuotaparteRentaComercial) {
    this.cuotaparteRentaComercial = cuotaparteRentaComercial;
  }
}