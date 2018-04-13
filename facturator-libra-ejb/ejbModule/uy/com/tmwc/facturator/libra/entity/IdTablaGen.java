package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Table(name="id")
public class IdTablaGen
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private IdTablaGenPK id;

  @Version
  private int IDultimo;

  public IdTablaGenPK getId()
  {
    return this.id;
  }

  public void setId(IdTablaGenPK id) {
    this.id = id;
  }

  public int getIDultimo() {
    return this.IDultimo;
  }

  public void setIDultimo(int IDultimo) {
    this.IDultimo = IDultimo;
  }
}