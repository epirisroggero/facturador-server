package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ResumenEntregas
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private HashMap<String, ResumenEntrega> map;
  private BigDecimal gasto;
  private BigDecimal valorUnitarioEntrega;
  private Collection<ResumenEntrega> resumenPorEntrega;

  public ResumenEntregas()
  {
  }

  public ResumenEntregas(Collection<ResumenEntrega> resumenEntrega, BigDecimal gasto)
  {
    this.gasto = gasto;
    this.map = new HashMap();
    BigDecimal sumaCantidadesConPeso = BigDecimal.ZERO;
    for (ResumenEntrega r : resumenEntrega) {
      this.map.put(r.getEntrega().getCodigo(), r);
      sumaCantidadesConPeso = sumaCantidadesConPeso.add(r.getPeso());
    }
    this.valorUnitarioEntrega = (sumaCantidadesConPeso.compareTo(BigDecimal.ZERO) == 0 ? null : gasto.divide(sumaCantidadesConPeso, 4, 4));
    this.resumenPorEntrega = resumenEntrega;
  }

  public BigDecimal getValorUnitarioEntrega() {
    return this.valorUnitarioEntrega;
  }

  public BigDecimal getCostoOperativo(String codigoEntrega) {
    ResumenEntrega r = (ResumenEntrega)this.map.get(codigoEntrega);
    if (r == null) {
      return null;
    }
    return r.getEntrega().getCostoOperativo(this.valorUnitarioEntrega);
  }

  public long getCantidad(String codigoEntrega) {
    ResumenEntrega r = (ResumenEntrega)this.map.get(codigoEntrega);
    if (r == null) {
      return 0L;
    }
    return r.getCantidad();
  }

  public BigDecimal getGasto() {
    return this.gasto;
  }

  public Map<String, BigDecimal> getAsMap() {
    HashMap costosMap = new HashMap();
    for (String codigoEntrega : this.map.keySet()) {
      costosMap.put(codigoEntrega, getCostoOperativo(codigoEntrega));
    }
    return costosMap;
  }

  public Collection<ResumenEntrega> getResumenPorEntrega() {
    return this.resumenPorEntrega;
  }
}