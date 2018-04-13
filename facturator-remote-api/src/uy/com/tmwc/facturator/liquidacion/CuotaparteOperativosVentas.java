package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.util.List;

public class CuotaparteOperativosVentas
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private List<ParticipacionVendedor> participacionesVendedor;

  public CuotaparteOperativosVentas()
  {
  }

  public CuotaparteOperativosVentas(List<ParticipacionVendedor> participacionesVendedor)
  {
    this.participacionesVendedor = participacionesVendedor;
  }

  public List<ParticipacionVendedor> getParticipacionesVendedor() {
    return this.participacionesVendedor;
  }

  public void setParticipacionesVendedor(List<ParticipacionVendedor> participacionesVendedor) {
    this.participacionesVendedor = participacionesVendedor;
  }
}