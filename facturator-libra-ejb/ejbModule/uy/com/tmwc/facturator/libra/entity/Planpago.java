package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name="planpagos")
@CatalogEntity(abreviated=true, prefix="pP")
public class Planpago extends PersistentEntity<PlanpagoPK>
  implements Serializable, HasId<PlanpagoPK>
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private PlanpagoPK id;

  @Column(name="PPCuotas")
  private short cantidadCuotas;

  @Column(name="PPCuotasIguales")
  private String cuotasIguales;

  @Column(name="PPdecimales")
  private String acumularDecimales;

  @Column(name="PPDesde")
  private String desde;

  @Column(name="PPNom")
  private String nombre;

  @Column(name="PPPrimerDia")
  private short primerDia;

  @Column(name="PPPrimerMes")
  private short primerMes;

  @Column(name="PPrecargo")
  private BigDecimal recargo;

  @Column(name="PPSeparacionDia")
  private short separacionDia;

  @Column(name="PPSeparacionMes")
  private short separacionMes;

  @OneToMany(cascade={javax.persistence.CascadeType.ALL}, mappedBy="planPagos")
  @Cascade({org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
  private Set<Planpagoscuota> planPagosCuotas;

  public PlanpagoPK getId()
  {
    return this.id;
  }

  public void setId(PlanpagoPK id) {
    this.id = id;
  }
  
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getCodigo() {
		return this.id.getPPid();
	}

public short getCantidadCuotas() {
    return this.cantidadCuotas;
  }

  public void setCantidadCuotas(short cantidadCuotas) {
    this.cantidadCuotas = cantidadCuotas;
  }

  public Set<Planpagoscuota> getPlanPagosCuotas() {
    return this.planPagosCuotas;
  }

  public void setPlanPagosCuotas(Set<Planpagoscuota> planPagosCuotas) {
    this.planPagosCuotas = planPagosCuotas;
  }

  public String getCuotasIguales() {
    return this.cuotasIguales;
  }

  public void setCuotasIguales(String cuotasIguales) {
    this.cuotasIguales = cuotasIguales;
  }

  public String getAcumularDecimales() {
    return this.acumularDecimales;
  }

  public void setAcumularDecimales(String acumularDecimales) {
    this.acumularDecimales = acumularDecimales;
  }

  public String getDesde() {
    return this.desde;
  }

  public void setDesde(String desde) {
    this.desde = desde;
  }

  public short getPrimerDia() {
    return this.primerDia;
  }

  public void setPrimerDia(short primerDia) {
    this.primerDia = primerDia;
  }

  public short getPrimerMes() {
    return this.primerMes;
  }

  public void setPrimerMes(short primerMes) {
    this.primerMes = primerMes;
  }

  public BigDecimal getRecargo() {
    return this.recargo;
  }

  public void setRecargo(BigDecimal recargo) {
    this.recargo = recargo;
  }

  public short getSeparacionDia() {
    return this.separacionDia;
  }

  public void setSeparacionDia(short separacionDia) {
    this.separacionDia = separacionDia;
  }

  public short getSeparacionMes() {
    return this.separacionMes;
  }

  public void setSeparacionMes(short separacionMes) {
    this.separacionMes = separacionMes;
  }
}