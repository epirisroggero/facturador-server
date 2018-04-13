package uy.com.tmwc.facturator.dto;

import java.io.Serializable;
import java.util.Comparator;
import uy.com.tmwc.facturator.entity.CodigoNombreEntity;

public class CodigoNombreCodigoComparator
  implements Comparator<CodigoNombreEntity>, Serializable
{
  private static final long serialVersionUID = 1L;
  private static final CodigoNombreCodigoComparator _instance = new CodigoNombreCodigoComparator();

  public static CodigoNombreCodigoComparator instance() {
    return _instance;
  }

  public int compare(CodigoNombreEntity o1, CodigoNombreEntity o2) {
    return o1.getCodigo().compareTo(o2.getCodigo());
  }
}