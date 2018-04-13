package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class RentasCobradasJefatura
  implements Serializable
{
  private List<RentaJefatura> detalle;
  private JefaturaInfo jefatura;
  private BigDecimal total;
  private Map<Integer, BigDecimal> totalPorVendedor;
}