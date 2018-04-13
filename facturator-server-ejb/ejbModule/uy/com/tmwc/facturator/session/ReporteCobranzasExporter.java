package uy.com.tmwc.facturator.session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import uy.com.tmwc.facturator.dto.ParticipacionEnCobranza;
import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.entity.RedistribucionRentasJefaturas;
import uy.com.tmwc.facturator.entity.Vendedor;

public abstract interface ReporteCobranzasExporter
{
  public abstract void remove();

  public abstract void setFechaHasta(Date paramDate);

  public abstract Date getFechaHasta();

  public abstract void setFechaDesde(Date paramDate);

  public abstract Date getFechaDesde();

  public abstract void ejecutar();

  public abstract TreeMap<Vendedor, ArrayList<ParticipacionVendedor>> getContadosPorVendedor();

  public abstract TreeMap<Vendedor, ArrayList<ParticipacionEnCobranza>> getCobranzasPorVendedor();

  public abstract List<Vendedor> getVendedoresRentaDirecta();

  public abstract List<Moneda> getMonedas();

  public abstract int getContadosCount(CodigoNombreEntity paramCodigoNombreEntity);

  public abstract int getCobranzasCount(CodigoNombreEntity paramCodigoNombreEntity);

  public abstract int getRentasCount(CodigoNombreEntity paramCodigoNombreEntity);

  public abstract RedistribucionRentasJefaturas getRedistJefaturas();

  public abstract List<Vendedor> getVendedores();
}