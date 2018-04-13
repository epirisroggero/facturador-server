package uy.com.tmwc.facturator.libra.ejb;

import uy.com.tmwc.facturator.libra.entity.Documento;
import uy.com.tmwc.facturator.libra.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.libra.entity.Vinculosdoc;

public class ParticipacionEnCobranzaLibra
{
  private ParticipacionVendedor participacionVendedor;
  private Vinculosdoc vinculo;

  public ParticipacionEnCobranzaLibra(ParticipacionVendedor pv, Vinculosdoc vinculo, Documento factura, Documento recibo)
  {
    this.participacionVendedor = pv;
    this.vinculo = vinculo;
  }

  public ParticipacionVendedor getParticipacionVendedor()
  {
    return this.participacionVendedor;
  }

  public void setParticipacionVendedor(ParticipacionVendedor participacionVendedor) {
    this.participacionVendedor = participacionVendedor;
  }

  public Vinculosdoc getVinculo() {
    return this.vinculo;
  }

  public void setVinculo(Vinculosdoc vinculo) {
    this.vinculo = vinculo;
  }
}