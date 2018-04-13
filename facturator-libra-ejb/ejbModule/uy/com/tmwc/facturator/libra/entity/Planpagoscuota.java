package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name="planpagoscuotas")
public class Planpagoscuota extends PersistentEntity<PlanpagoscuotaPK>
  implements Serializable, HasId<PlanpagoscuotaPK>
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private PlanpagoscuotaPK id;

  @Column(name="PPCuotaDias")
  private short dias;

  @Column(name="PPCuotaMes")
  private short mes;

  @Column(name="PPCuotaPorc")
  private BigDecimal porcentaje;

  @NotFound(action=NotFoundAction.IGNORE)
  @ManyToOne(optional=false)
  @JoinColumns({@javax.persistence.JoinColumn(name="PPid", referencedColumnName="PPid", insertable=false, updatable=false), @javax.persistence.JoinColumn(name="EmpId", referencedColumnName="EmpId", insertable=false, updatable=false)})
  private Planpago planPagos;

  public PlanpagoscuotaPK getId()
  {
    return this.id;
  }

  public void setId(PlanpagoscuotaPK id) {
    this.id = id;
  }

  public short getDias() {
    return this.dias;
  }

  public void setDias(short dias) {
    this.dias = dias;
  }

  public short getMes() {
    return this.mes;
  }

  public void setMes(short mes) {
    this.mes = mes;
  }

  public BigDecimal getPorcentaje() {
    return this.porcentaje;
  }

  public void setPorcentaje(BigDecimal porcentaje) {
    this.porcentaje = porcentaje;
  }

  public Planpago getPlanPagos() {
    return this.planPagos;
  }

  public void setPlanPagos(Planpago planPagos) {
    this.planPagos = planPagos;
  }
}