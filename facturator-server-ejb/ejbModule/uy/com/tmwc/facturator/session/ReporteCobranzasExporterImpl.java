package uy.com.tmwc.facturator.session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remove;
import javax.ejb.Stateful;

import org.jboss.seam.annotations.Destroy;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;

import uy.com.tmwc.facturator.dto.ParticipacionEnCobranza;
import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.IAportaRenta;
import uy.com.tmwc.facturator.entity.Jefatura;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.entity.RedistribucionRentaLinea;
import uy.com.tmwc.facturator.entity.RedistribucionRentasJefaturas;
import uy.com.tmwc.facturator.entity.Vendedor;
import uy.com.tmwc.facturator.entity.VinculoDocumentos;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.rapi.LiquidacionService;
import uy.com.tmwc.facturator.server.util.AgruparVendedores2;
import uy.com.tmwc.facturator.server.util.AgruparVendedores3;
import uy.com.tmwc.facturator.spi.DocumentoDAOService;

@Stateful
@Local({ReporteCobranzasExporter.class})
@Name("reporteCobranzasExporter")
public class ReporteCobranzasExporterImpl
  implements ReporteCobranzasExporter
{
  private Date fechaDesde;
  private Date fechaHasta;

  @EJB
  LiquidacionService liquidacionService;

  @EJB
  CatalogService catalogService;

  @EJB
  DocumentoDAOService documentoDAOService;

  @Out(required=false)
  private TreeMap<Vendedor, ArrayList<ParticipacionVendedor>> contadosPorVendedor;

  @Out(required=false)
  private TreeMap<Vendedor, ArrayList<ParticipacionEnCobranza>> cobranzasPorVendedor;

  @Out(required=false)
  private RedistribucionRentasJefaturas redistJefaturas;

  @Out(required=false)
  private TreeMap<Vendedor, Collection<ExcelBlock<Vendedor, SubTotalPorMoneda<Jefatura>>>> bloquesPaga;

  @Out(required=false)
  private TreeMap<Vendedor, Collection<SubTotalPorMoneda<Jefatura>>> jefaturasCobra;
  private List<Moneda> monedas;
  private List<Vendedor> vendedoresRentaDirecta;
  private ArrayList<Vendedor> vendedores;
  private static SubTotalPorMoneda.IMontoExtractor<RedistribucionRentaLinea> montoCobraJefeExtractor = new SubTotalPorMoneda.IMontoExtractor<RedistribucionRentaLinea>()
  {
    public Moneda getMoneda(RedistribucionRentaLinea src) {
      return src.getAportadorRenta().getMoneda();
    }

    public BigDecimal getMonto(RedistribucionRentaLinea src) {
      return src.getCobraJefe();
    }
  };

  public void ejecutar()
  {
    List<ParticipacionVendedor> participContados = this.documentoDAOService.getParticipacionesEnContados(this.fechaDesde, this.fechaHasta, null);
    List<ParticipacionEnCobranza> participCobranzas = this.documentoDAOService.getParticipacionesEnCobranzas(this.fechaDesde, this.fechaHasta, null);

    AgruparVendedores2 agruparVendedores = new AgruparVendedores2();
    agruparVendedores.process(participContados);
    this.contadosPorVendedor = agruparVendedores.getParticipacionesPorVendedor();

    AgruparVendedores3 agruparVendedoresCobranzas = new AgruparVendedores3();
    agruparVendedoresCobranzas.process(participCobranzas);
    this.cobranzasPorVendedor = agruparVendedoresCobranzas.getParticipacionesPorVendedor();

    TreeSet<Vendedor> setVendedores = new TreeSet<Vendedor>();
    for (ParticipacionVendedor participacionVendedor : participContados) {
      setVendedores.add(participacionVendedor.getVendedor());
    }
    for (ParticipacionEnCobranza participacionEnCobranza : participCobranzas) {
      setVendedores.add(participacionEnCobranza.getParticipacionVendedor().getVendedor());
    }
    this.vendedoresRentaDirecta = new ArrayList(setVendedores);

    this.monedas = this.catalogService.getCatalog("Moneda");

    Collection jefaturas = this.catalogService.getJefaturas();
    Collection aportacionesRenta = juntar(participContados, participCobranzas);
    this.redistJefaturas = new RedistribucionRentasJefaturas(aportacionesRenta, jefaturas);

    this.vendedores = new ArrayList(this.vendedoresRentaDirecta);
    this.vendedores.addAll(this.redistJefaturas.getJefes());
    Collections.sort(this.vendedores);

    this.bloquesPaga = new TreeMap();
    for (Vendedor vendedor : this.vendedores) {
      int subTotalesCount = 0;
      int bloquesCount = 0;
      MontoPagoVendedorExtractor montoPagoVendedorExtractor = new MontoPagoVendedorExtractor(vendedor);
      SortedMap<Vendedor, SortedMap<Jefatura, Collection<RedistribucionRentaLinea>>> paga = this.redistJefaturas.getPaga(vendedor);
      if (paga != null) {
        ArrayList bloques = new ArrayList();
        this.bloquesPaga.put(vendedor, bloques);
        for (Map.Entry<Vendedor, SortedMap<Jefatura, Collection<RedistribucionRentaLinea>>> pagaAJefe : paga.entrySet()) {
          ExcelBlock bloque = new ExcelBlock();
          bloque.setGroup((Vendedor)pagaAJefe.getKey());
          Collection data = new ArrayList();
          Set<Entry<Jefatura, Collection<RedistribucionRentaLinea>>> detalle = pagaAJefe.getValue().entrySet();
          for (Map.Entry entry : detalle) {
            SubTotalPorMoneda subtotal = SubTotalPorMoneda.subtotalizar((Jefatura)entry.getKey(), (Collection)entry.getValue(), montoPagoVendedorExtractor);
            data.add(subtotal);
          }

          bloque.setData(data);
          bloque.setFirstDatumIndex(subTotalesCount);
          bloque.setIndex(bloquesCount);
          subTotalesCount += data.size();
          bloquesCount++;
          bloques.add(bloque);
        }
      }
    }

    this.jefaturasCobra = new TreeMap();
    for (Vendedor vendedor : this.vendedores) {
      SortedMap<Jefatura, Collection<RedistribucionRentaLinea>> cobra = this.redistJefaturas.getCobra(vendedor);
      if (cobra != null) {
        Collection data = new ArrayList();
        for (Map.Entry entry : cobra.entrySet()) {
          SubTotalPorMoneda subtotal = SubTotalPorMoneda.subtotalizar((Jefatura)entry.getKey(), (Collection)entry.getValue(), montoCobraJefeExtractor);
          data.add(subtotal);
        }
        this.jefaturasCobra.put(vendedor, data);
      }
    }
  }

  private Collection<IAportaRenta> juntar(List<ParticipacionVendedor> participContados, List<ParticipacionEnCobranza> participCobranzas)
  {
    IdentityHashMap aux = new IdentityHashMap();
    ArrayList full = new ArrayList();

    for (ParticipacionVendedor participacionVendedor : participContados) {
      Documento contado = participacionVendedor.getDocumento();
      if (!aux.containsKey(contado)) {
        aux.put(contado, contado);
        full.add(new RentaContado(contado));
      }
    }

    for (ParticipacionEnCobranza participacionEnCobranza : participCobranzas) {
      VinculoDocumentos vinculo = participacionEnCobranza.getVinculo();
      if (!aux.containsKey(vinculo)) {
        aux.put(vinculo, vinculo);
        full.add(new RentaCobranza(vinculo));
      }
    }
    return full;
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

  public TreeMap<Vendedor, ArrayList<ParticipacionVendedor>> getContadosPorVendedor() {
    return this.contadosPorVendedor;
  }

  public TreeMap<Vendedor, ArrayList<ParticipacionEnCobranza>> getCobranzasPorVendedor() {
    return this.cobranzasPorVendedor;
  }

  public List<Vendedor> getVendedoresRentaDirecta() {
    return this.vendedoresRentaDirecta;
  }

  public List<Vendedor> getVendedores() {
    return this.vendedores;
  }

  public List<Moneda> getMonedas() {
    return this.monedas;
  }

  public int getContadosCount(CodigoNombreEntity vendedor) {
    ArrayList cv = (ArrayList)this.contadosPorVendedor.get(vendedor);
    return cv == null ? 0 : cv.size();
  }

  public int getCobranzasCount(CodigoNombreEntity vendedor) {
    ArrayList cv = (ArrayList)this.cobranzasPorVendedor.get(vendedor);
    return cv == null ? 0 : cv.size();
  }

  public int getRentasCount(CodigoNombreEntity vendedor) {
    return getContadosCount(vendedor) + getCobranzasCount(vendedor);
  }

  public RedistribucionRentasJefaturas getRedistJefaturas() {
    return this.redistJefaturas;
  }

  public TreeMap<Vendedor, Collection<ExcelBlock<Vendedor, SubTotalPorMoneda<Jefatura>>>> getBloquesPaga() {
    return this.bloquesPaga;
  }

  @Destroy
  @Remove
  public void remove()
  {
  }

  private static class MontoPagoVendedorExtractor
    implements SubTotalPorMoneda.IMontoExtractor<RedistribucionRentaLinea>
  {
    private Vendedor vendedor;

    public MontoPagoVendedorExtractor(Vendedor vendedor)
    {
      this.vendedor = vendedor;
    }

    public Moneda getMoneda(RedistribucionRentaLinea src) {
      return src.getAportadorRenta().getMoneda();
    }

    public BigDecimal getMonto(RedistribucionRentaLinea src) {
      return src.getPagaVendedor(this.vendedor);
    }
  }
}