package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Asientos1PK
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Column(name="EmpId", unique=true, nullable=false, length=10)
  private String empId;

  @Column(name="EjeId", unique=true, nullable=false)
  private short ejeId;

  @Column(name="AsId", unique=true, nullable=false)
  private int asId;

  @Column(unique=true, nullable=false)
  private int DHlin;

  public String getEmpId()
  {
    return this.empId;
  }
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  public short getEjeId() {
    return this.ejeId;
  }
  public void setEjeId(short ejeId) {
    this.ejeId = ejeId;
  }
  public int getAsId() {
    return this.asId;
  }
  public void setAsId(int asId) {
    this.asId = asId;
  }
  public int getDHlin() {
    return this.DHlin;
  }
  public void setDHlin(int DHlin) {
    this.DHlin = DHlin;
  }

  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof Asientos1PK)) {
      return false;
    }
    Asientos1PK castOther = (Asientos1PK)other;

    return (this.empId.equals(castOther.empId)) && 
      (this.ejeId == castOther.ejeId) && 
      (this.asId == castOther.asId) && 
      (this.DHlin == castOther.DHlin);
  }

  public int hashCode()
  {
    int prime = 31;
    int hash = 17;
    hash = hash * 31 + this.empId.hashCode();
    hash = hash * 31 + this.ejeId;
    hash = hash * 31 + this.asId;
    hash = hash * 31 + this.DHlin;

    return hash;
  }
}