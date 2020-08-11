package uy.com.tmwc.facturator.server.util;

import java.util.ArrayList;
import java.util.TreeMap;
import uy.com.tmwc.facturator.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.entity.Vendedor;

public class AgruparVendedores2 extends CorteControlTemplate<ParticipacionVendedor>
{
  private TreeMap<Vendedor, ArrayList<ParticipacionVendedor>> participacionesPorVendedor = new TreeMap();
  private ArrayList<ParticipacionVendedor> participacionesVendedorActual;

  protected boolean equal(ParticipacionVendedor value1, ParticipacionVendedor value2)
  {
    return value2 != null;
  }

  protected void first(ParticipacionVendedor item)
  {
    this.participacionesVendedorActual = new ArrayList<ParticipacionVendedor>();
    this.participacionesPorVendedor.put(item.getVendedor(), this.participacionesVendedorActual);
  }

  protected void process(ParticipacionVendedor item)
  {
    this.participacionesVendedorActual.add(item);
  }

  public TreeMap<Vendedor, ArrayList<ParticipacionVendedor>> getParticipacionesPorVendedor() {
    return this.participacionesPorVendedor;
  }
}