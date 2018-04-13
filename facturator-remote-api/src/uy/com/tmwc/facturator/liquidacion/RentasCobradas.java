package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import java.util.List;

public class RentasCobradas
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private List<RentasCobradasJefatura> resumenPorJefatura;
}