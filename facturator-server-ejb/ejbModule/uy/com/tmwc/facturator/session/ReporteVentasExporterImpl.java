package uy.com.tmwc.facturator.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;
import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.entity.ResumenEntregas;
import uy.com.tmwc.facturator.liquidacion.ParticipacionVendedor;
import uy.com.tmwc.facturator.rapi.LiquidacionService;

@Stateful
@Local({ReporteVentasExporter.class})
@Name("reporteVentasExporter")
public class ReporteVentasExporterImpl
  implements ReporteVentasExporter
{
  private Date fechaDesde;
  private Date fechaHasta;

  @EJB
  LiquidacionService liquidacionService;

  @Out(required=false)
  TreeMap<CodigoNombre, ArrayList<ParticipacionVendedor>> detallesVendedores;

  @Out(required=false)
  ResumenEntregas resumenEntregas;

  public void ejecutar()
  {
    this.detallesVendedores = this.liquidacionService.reporteVentasPeriodo(this.fechaDesde, this.fechaHasta);
    this.resumenEntregas = this.liquidacionService.getResumenEntregas(this.fechaDesde, this.fechaHasta, new BigDecimal("1000"));
  }

  public Date getFechaDesde() {
    return this.fechaDesde;
  }

  public void setFechaDesde(Date fechaDesde) {
    this.fechaDesde = fechaDesde;
  }

  public Date getFechaHasta() {
    return this.fechaHasta;
  }

  public void setFechaHasta(Date fechaHasta) {
    this.fechaHasta = fechaHasta;
  }

  @Destroy
  @Remove
  public void remove()
  {
  }
}