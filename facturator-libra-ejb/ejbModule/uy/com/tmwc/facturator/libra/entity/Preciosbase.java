package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="preciosbase")
public class Preciosbase extends PersistentEntity<PreciosbasePK>
  implements Serializable, HasId<PreciosbasePK>
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private PreciosbasePK id;

  @Column(name="PrecioBaseNom")
  private String precioBaseNom;

  public PreciosbasePK getId()
  {
    return this.id;
  }

  public void setId(PreciosbasePK id) {
    this.id = id;
  }

  public String getPrecioBaseNom() {
    return this.precioBaseNom;
  }

  public void setPrecioBaseNom(String precioBaseNom) {
    this.precioBaseNom = precioBaseNom;
  }
}