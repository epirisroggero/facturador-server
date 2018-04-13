package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class ComisionJefatura
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Jefatura jefatura;
  private Vendedor vendedor;
  private BigDecimal comision;

  public Jefatura getJefatura()
  {
    return this.jefatura;
  }

  public void setJefatura(Jefatura jefatura) {
    this.jefatura = jefatura;
  }

  public Vendedor getVendedor() {
    return this.vendedor;
  }

  public void setVendedor(Vendedor vendedor) {
    this.vendedor = vendedor;
  }

  public BigDecimal getComision() {
    return this.comision;
  }

  public void setComision(BigDecimal comision) {
    this.comision = comision;
  }
}