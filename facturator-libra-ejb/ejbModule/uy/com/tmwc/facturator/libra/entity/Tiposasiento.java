package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tiposasientos")
public class Tiposasiento
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private TiposasientoPK id;

  @Column(name="TipAsCentro", nullable=false, length=1)
  private String centro;

  @Column(name="TipAsImp", nullable=false, length=1)
  private String imp;

  @Column(name="TipAsNom", nullable=false, length=40)
  private String nombre;

  @Column(name="TipAsResumir", nullable=false, length=1)
  private String resumir;

  public TiposasientoPK getId()
  {
    return this.id;
  }

  public void setId(TiposasientoPK id) {
    this.id = id;
  }

  public String getCentro() {
    return this.centro;
  }

  public void setCentro(String centro) {
    this.centro = centro;
  }

  public String getImp() {
    return this.imp;
  }

  public void setImp(String imp) {
    this.imp = imp;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public String getResumir() {
    return this.resumir;
  }

  public void setResumir(String resumir) {
    this.resumir = resumir;
  }
}