package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name="depositos")
@CatalogEntity(prefix="dep", abreviated=true)
public class Deposito extends PersistentEntity<DepositoPK>
  implements Serializable, HasId<DepositoPK>
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private DepositoPK id;

  @Column(name="DepAbrevia")
  private String depAbrevia;

  @Column(name="DepInventario")
  private String depInventario;

  @Column(name="DepNom")
  private String nombre;

  @Column(name="LocIdDep")
  private short locIdDep;

  public String getCodigo() {
	  return id != null ? String.valueOf(id.getDepId()) : null;
  }
  
  public DepositoPK getId()
  {
    return this.id;
  }

  public void setId(DepositoPK id) {
    this.id = id;
  }

  public String getDepAbrevia() {
    return this.depAbrevia;
  }

  public void setDepAbrevia(String depAbrevia) {
    this.depAbrevia = depAbrevia;
  }

  public String getDepInventario() {
    return this.depInventario;
  }

  public void setDepInventario(String depInventario) {
    this.depInventario = depInventario;
  }

  public String getNombre() {
    return this.nombre;
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public short getLocIdDep() {
    return this.locIdDep;
  }

  public void setLocIdDep(short locIdDep) {
    this.locIdDep = locIdDep;
  }
}