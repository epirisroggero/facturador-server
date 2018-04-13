package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name="iva")
@CatalogEntity
public class Iva extends PersistentEntity<IvaPK>
  implements Serializable, HasId<IvaPK>
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private IvaPK id;

  @Column(name="IvaAbrevia")
  private String abrevia;

  @Column(name="IvaNom")
  private String nombre;

  @Column(name="IvaTasa")
  private BigDecimal tasa;

  @Column(name="IvaTipo")
  private String tipo;

  @Column(name="RubIdIvaD")
  private String rubIdIvaD;

  @Column(name="RubIdIvaH")
  private String rubIdIvaH;

  public IvaPK getId()
  {
    return this.id;
  }

  public void setId(IvaPK id) {
    this.id = id;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }
  public String getRubIdIvaD() {
    return this.rubIdIvaD;
  }

  public void setRubIdIvaD(String rubIdIvaD) {
    this.rubIdIvaD = rubIdIvaD;
  }

  public String getRubIdIvaH() {
    return this.rubIdIvaH;
  }

  public void setRubIdIvaH(String rubIdIvaH) {
    this.rubIdIvaH = rubIdIvaH;
  }

  public String getAbrevia() {
    return this.abrevia;
  }

  public void setAbrevia(String abrevia) {
    this.abrevia = abrevia;
  }

  public BigDecimal getTasa() {
    return this.tasa;
  }

  public void setTasa(BigDecimal tasa) {
    this.tasa = tasa;
  }

  public String getTipo() {
    return this.tipo;
  }

  public void setTipo(String tipo) {
    this.tipo = tipo;
  }
}