package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DepositoPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Column(name="EmpId")
  private String empId;

  @Column(name="DepId")
  private short depId;

  public String getEmpId()
  {
    return this.empId;
  }
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  public short getDepId() {
    return this.depId;
  }
  public void setDepId(short depId) {
    this.depId = depId;
  }

  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof DepositoPK)) {
      return false;
    }
    DepositoPK castOther = (DepositoPK)other;

    return (this.empId.equals(castOther.empId)) && 
      (this.depId == castOther.depId);
  }

  public int hashCode()
  {
    int prime = 31;
    int hash = 17;
    hash = hash * 31 + this.empId.hashCode();
    hash = hash * 31 + this.depId;

    return hash;
  }
}