package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "asientos")
public class Asiento
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private AsientoPK id;

  @Column(name="AsAnio")
  private short anio;

  @Column(name="AsConcepto", nullable=false, length=50)
  private String concepto;

  @Column(name="AsDHlin")
  private int ultimoNumeroLinea;

  @Column(name="AsDia")
  private short dia;

  @Column(name="AsDocId")
  private int docId;

  @Temporal(TemporalType.DATE)
  @Column(name="AsFecha", nullable=false)
  private Date fecha;

  @Column(name="AsImportado", length=1)
  private String importado;

  @Column(name="AsMes")
  private short mes;

  @Lob
  @Column(name="AsNotas")
  private String notas;

  @Column(name="AsNro", nullable=false)
  private int nro;

  @Column(name="AsOrigen", length=10)
  private String origen;

  @Temporal(TemporalType.DATE)
  @Column(name="AsRegistroFecha")
  private Date registroFecha;

  @Column(name="AsRegistroHora", length=8)
  private String registroHora;

  @Column(name="AsTC", precision=10, scale=7)
  private BigDecimal tc;

  @Column(name="AsValidado", length=1)
  private String validado;

  @SuppressWarnings("unused")
  @Column(name="MndIdAs")
  private short mndIdAs;

  @ManyToOne
  @JoinColumns({@javax.persistence.JoinColumn(name="MndIdAs", referencedColumnName="MndId", insertable=false, updatable=false), @javax.persistence.JoinColumn(name="EmpId", referencedColumnName="EmpId", insertable=false, updatable=false)})
  private Moneda moneda;

  @SuppressWarnings("unused")
  @Column(name="TipAsIdAs", nullable=false, length=3)
  private String tipAsIdAs;

  @NotFound(action=NotFoundAction.IGNORE)
  @ManyToOne
  @JoinColumns({@javax.persistence.JoinColumn(name="TipAsIdAs", referencedColumnName="TipAsId", insertable=false, updatable=false), @javax.persistence.JoinColumn(name="EmpId", referencedColumnName="EmpId", insertable=false, updatable=false)})
  private Tiposasiento tipoAsiento;

  @Column(name="UsuIdAs")
  private short usuIdAs;

  public AsientoPK getId() {
    return this.id;
  }

  public void setId(AsientoPK id) {
    this.id = id;
  }

  public short getAnio() {
    return this.anio;
  }

  public void setAnio(short anio) {
    this.anio = anio;
  }

  public String getConcepto() {
    return this.concepto;
  }

  public void setConcepto(String concepto) {
    this.concepto = concepto;
  }

  public int getUltimoNumeroLinea() {
    return this.ultimoNumeroLinea;
  }

  public void setUltimoNumeroLinea(int ultimoNumeroLinea) {
    this.ultimoNumeroLinea = ultimoNumeroLinea;
  }

  public short getDia() {
    return this.dia;
  }

  public void setDia(short dia) {
    this.dia = dia;
  }

  public int getDocId() {
    return this.docId;
  }

  public void setDocId(int docId) {
    this.docId = docId;
  }

  public Date getFecha() {
    return this.fecha;
  }

  public void setFecha(Date fecha) {
    this.fecha = fecha;
  }

  public String getImportado() {
    return this.importado;
  }

  public void setImportado(String importado) {
    this.importado = importado;
  }

  public short getMes() {
    return this.mes;
  }

  public void setMes(short mes) {
    this.mes = mes;
  }

  public String getNotas() {
    return this.notas;
  }

  public void setNotas(String notas) {
    this.notas = notas;
  }

  public int getNro() {
    return this.nro;
  }

  public void setNro(int nro) {
    this.nro = nro;
  }

  public String getOrigen() {
    return this.origen;
  }

  public void setOrigen(String origen) {
    this.origen = origen;
  }

  public Date getRegistroFecha() {
    return this.registroFecha;
  }

  public void setRegistroFecha(Date registroFecha) {
    this.registroFecha = registroFecha;
  }

  public String getRegistroHora() {
    return this.registroHora;
  }

  public void setRegistroHora(String registroHora) {
    this.registroHora = registroHora;
  }

  public BigDecimal getTc() {
    return this.tc;
  }

  public void setTc(BigDecimal tc) {
    this.tc = tc;
  }

  public String getValidado() {
    return this.validado;
  }

  public void setValidado(String validado) {
    this.validado = validado;
  }

  public Moneda getMoneda() {
    return this.moneda;
  }

  public short getUsuIdAs() {
    return this.usuIdAs;
  }

  public void setUsuIdAs(short usuIdAs) {
    this.usuIdAs = usuIdAs;
  }

  public Tiposasiento getTipoAsiento() {
    return this.tipoAsiento;
  }
}