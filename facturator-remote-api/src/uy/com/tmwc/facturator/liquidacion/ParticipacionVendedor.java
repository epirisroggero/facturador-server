package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import uy.com.tmwc.facturator.dto.CodigoNombre;

public class ParticipacionVendedor
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private Factura documento;
  private CodigoNombre vendedor;
  private BigDecimal porcentaje;
  private BigDecimal cuotaparteOperativos;
  private BigDecimal cuotaparteRentaComercial;

  public Factura getDocumento()
  {
    return this.documento;
  }

  public void setDocumento(Factura documento) {
    this.documento = documento;
  }

  public BigDecimal getPorcentaje() {
    return this.porcentaje;
  }

  public void setPorcentaje(BigDecimal porcentaje) {
    this.porcentaje = porcentaje;
  }

  public BigDecimal getCuotaparteOperativos() {
    return this.cuotaparteOperativos;
  }

  public void setCuotaparteOperativos(BigDecimal cuotaparteOperativos) {
    this.cuotaparteOperativos = cuotaparteOperativos;
  }

  public CodigoNombre getVendedor() {
    return this.vendedor;
  }

  public void setVendedor(CodigoNombre vendedor) {
    this.vendedor = vendedor;
  }

  public BigDecimal getCuotaparteRentaComercial() {
    return this.cuotaparteRentaComercial;
  }

  public void setCuotaparteRentaComercial(BigDecimal cuotaparteRentaComercial) {
    this.cuotaparteRentaComercial = cuotaparteRentaComercial;
  }
}