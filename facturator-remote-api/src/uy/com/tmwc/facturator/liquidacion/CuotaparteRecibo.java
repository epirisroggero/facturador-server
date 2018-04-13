package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import uy.com.tmwc.facturator.utils.Maths;

public class CuotaparteRecibo
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private ReferenciaFactura referenciaFactura;
  private BigDecimal participacion;

  public BigDecimal getCuotaparteRCFactura()
  {
    return getCuotaparte(this.referenciaFactura.getFactura().getRentaNetaComercial());
  }

  public BigDecimal getCuotaparteRCRecibo()
  {
    return getCuotaparte(this.referenciaFactura.getCuotaparteRentaComercial());
  }

  public BigDecimal getCuotaparteRentaFinanciera() {
    return getCuotaparte(this.referenciaFactura.getRentaFinanciera());
  }

  public BigDecimal getCuotaparte(BigDecimal monto) {
    return Maths.calcularMontoDescuento(monto, this.participacion);
  }
}