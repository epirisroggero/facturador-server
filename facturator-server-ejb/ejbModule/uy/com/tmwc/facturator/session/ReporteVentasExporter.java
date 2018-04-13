package uy.com.tmwc.facturator.session;

import java.util.Date;

public abstract interface ReporteVentasExporter
{
  public abstract void ejecutar();

  public abstract void remove();

  public abstract void setFechaHasta(Date paramDate);

  public abstract Date getFechaHasta();

  public abstract void setFechaDesde(Date paramDate);

  public abstract Date getFechaDesde();
}