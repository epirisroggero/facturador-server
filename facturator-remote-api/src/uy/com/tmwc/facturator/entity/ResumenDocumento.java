package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ResumenDocumento
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private BigDecimal total;
  private BigDecimal subTotal;
  private BigDecimal costo;

  public BigDecimal getTotal()
  {
    return this.total;
  }

  public void setTotal(BigDecimal total) {
    this.total = total;
  }

  public BigDecimal getSubTotal() {
    return this.subTotal;
  }

  public void setSubTotal(BigDecimal subTotal) {
    this.subTotal = subTotal;
  }

  public BigDecimal getCosto() {
    return this.costo;
  }

  public void setCosto(BigDecimal costo) {
    this.costo = costo;
  }
}