package uy.com.tmwc.facturator.server.util;

import java.util.ArrayList;
import java.util.TreeMap;
import uy.com.tmwc.facturator.dto.ParticipacionEnCobranza;
import uy.com.tmwc.facturator.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.entity.Vendedor;

public class AgruparVendedores3 extends CorteControlTemplate<ParticipacionEnCobranza>
{
  private TreeMap<Vendedor, ArrayList<ParticipacionEnCobranza>> participacionesPorVendedor = new TreeMap();
  private ArrayList<ParticipacionEnCobranza> participacionesVendedorActual;

  protected boolean equal(ParticipacionEnCobranza value1, ParticipacionEnCobranza value2)
  {
    return value2 != null;
  }

  protected void first(ParticipacionEnCobranza item)
  {
    this.participacionesVendedorActual = new ArrayList();
    this.participacionesPorVendedor.put(item.getParticipacionVendedor().getVendedor(), this.participacionesVendedorActual);
  }

  protected void process(ParticipacionEnCobranza item)
  {
    this.participacionesVendedorActual.add(item);
  }

  public TreeMap<Vendedor, ArrayList<ParticipacionEnCobranza>> getParticipacionesPorVendedor() {
    return this.participacionesPorVendedor;
  }
}