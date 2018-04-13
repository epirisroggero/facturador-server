package uy.com.tmwc.facturator.session;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import uy.com.tmwc.facturator.entity.Moneda;

public class SubTotalPorMoneda<G>
{
  private G grupo;
  private HashMap<Moneda, BigDecimal> map;

  public SubTotalPorMoneda(G grupo, HashMap<Moneda, BigDecimal> map)
  {
    this.grupo = grupo;
    this.map = map;
  }

  public BigDecimal getSubtotal(Moneda moneda) {
    BigDecimal entry = (BigDecimal)this.map.get(moneda);
    return entry != null ? entry : BigDecimal.ZERO;
  }

  public G getGrupo() {
    return this.grupo;
  }

  public String getExcelFormula(List<Moneda> monedas, String sheet, String col, int startRow) {
    StringBuilder sb = new StringBuilder("SUM(");
    int i = 0;
    for (Moneda moneda : monedas) {
      if (i > 0) {
        sb.append(",");
      }
      BigDecimal value = (BigDecimal)this.map.get(moneda);
      if (value == null) {
        value = BigDecimal.ZERO;
      }
      sb.append("'").append(sheet).append("'");
      sb.append("!").append(col).append(startRow + i);
      sb.append("*").append(value.setScale(2, RoundingMode.HALF_UP).toString());
      i++;
    }
    sb.append(")");
    return sb.toString();
  }

  public static <G, T> SubTotalPorMoneda<G> subtotalizar(G grupo, Collection<T> detalles, IMontoExtractor<T> montoExtractor) {
    HashMap map = new HashMap();
    for (T t : detalles) {
      Moneda moneda = montoExtractor.getMoneda(t);
      BigDecimal monto = montoExtractor.getMonto(t);
      BigDecimal subt = (BigDecimal)map.get(moneda);
      subt = subt == null ? monto : subt.add(monto);
      map.put(moneda, subt);
    }
    SubTotalPorMoneda s = new SubTotalPorMoneda(grupo, map);
    return s;
  }

  public static abstract interface IMontoExtractor<T>
  {
    public abstract Moneda getMoneda(T paramT);

    public abstract BigDecimal getMonto(T paramT);
  }
}