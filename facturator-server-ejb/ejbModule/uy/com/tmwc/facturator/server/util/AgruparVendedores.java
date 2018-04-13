package uy.com.tmwc.facturator.server.util;

import java.util.ArrayList;
import java.util.TreeMap;
import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.dto.CodigoNombreCodigoComparator;
import uy.com.tmwc.facturator.liquidacion.ParticipacionVendedor;

public class AgruparVendedores extends CorteControlTemplate<ParticipacionVendedor>
{
  private TreeMap<CodigoNombre, ArrayList<ParticipacionVendedor>> participacionesPorVendedor = new TreeMap(new CodigoNombreCodigoComparator());
  private ArrayList<ParticipacionVendedor> participacionesVendedorActual;

  protected boolean equal(ParticipacionVendedor value1, ParticipacionVendedor value2)
  {
    return value2 != null;
  }

  protected void first(ParticipacionVendedor item)
  {
    this.participacionesVendedorActual = new ArrayList();
    this.participacionesPorVendedor.put(item.getVendedor(), this.participacionesVendedorActual);
  }

  protected void process(ParticipacionVendedor item)
  {
    this.participacionesVendedorActual.add(item);
  }

  public TreeMap<CodigoNombre, ArrayList<ParticipacionVendedor>> getParticipacionesPorVendedor() {
    return this.participacionesPorVendedor;
  }
}