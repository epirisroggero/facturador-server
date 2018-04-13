package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="asientos1")
public class Asientos1
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private Asientos1PK id;

  @ManyToOne(optional=false)
  @JoinColumns({@javax.persistence.JoinColumn(name="AsId", referencedColumnName="AsId", insertable=false, updatable=false), @javax.persistence.JoinColumn(name="EjeId", referencedColumnName="EjeId", insertable=false, updatable=false), @javax.persistence.JoinColumn(name="EmpId", referencedColumnName="EmpId", insertable=false, updatable=false)})
  private Asiento asiento;

  @Column(name="CenIdAs", length=3)
  private String cenIdAs;

  @Column(name="DHconcepto", length=50)
  private String concepto;

  @Column(name="DHdebe", nullable=false, precision=10, scale=2)
  private BigDecimal debe;

  @Temporal(TemporalType.DATE)
  @Column(name="DHfecha")
  private Date fecha;

  @Column(name="DHhaber", nullable=false, precision=10, scale=2)
  private BigDecimal haber;

  @Column(name="DHNro")
  private int nro;

  @Column(name="DHreferencia", length=10)
  private String referencia;

  @Column(name="LocIdDH", nullable=false)
  private short locIdDH;

  @Column(name="RubIdDH", length=10)
  private String rubIdDH;

  @ManyToOne
  @JoinColumns({@javax.persistence.JoinColumn(name="RubIdDH", referencedColumnName="RubId", insertable=false, updatable=false), @javax.persistence.JoinColumn(name="EmpId", referencedColumnName="EmpId", insertable=false, updatable=false)})
  private Rubro rubro;

  public Asientos1PK getId()
  {
    return this.id;
  }

  public void setId(Asientos1PK id) {
    this.id = id;
  }

  public String getCenIdAs() {
    return this.cenIdAs;
  }

  public void setCenIdAs(String cenIdAs) {
    this.cenIdAs = cenIdAs;
  }

  public String getConcepto() {
    return this.concepto;
  }

  public void setConcepto(String concepto) {
    this.concepto = concepto;
  }

  public BigDecimal getDebe() {
    return this.debe;
  }

  public void setDebe(BigDecimal debe) {
    this.debe = debe;
  }

  public Date getFecha() {
    return this.fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public BigDecimal getHaber() {
    return this.haber;
  }

  public void setHaber(BigDecimal haber) {
    this.haber = haber;
  }

  public int getNro() {
    return this.nro;
  }

  public void setNro(int nro) {
    this.nro = nro;
  }

  public String getReferencia() {
    return this.referencia;
  }

  public void setReferencia(String referencia) {
    this.referencia = referencia;
  }

  public short getLocIdDH() {
    return this.locIdDH;
  }

  public void setLocIdDH(short locIdDH) {
    this.locIdDH = locIdDH;
  }

  public Rubro getRubro() {
    return this.rubro;
  }

  public Asiento getAsiento() {
    return this.asiento;
  }
}