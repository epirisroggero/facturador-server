package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MonedaPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Column(name="EmpId")
  private String empId;

  @Column(name="MndId")
  private short mndId;

  public String getEmpId()
  {
    return this.empId;
  }
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  public short getMndId() {
    return this.mndId;
  }
  public void setMndId(short mndId) {
    this.mndId = mndId;
  }

  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof MonedaPK)) {
      return false;
    }
    MonedaPK castOther = (MonedaPK)other;

    return (this.empId.equals(castOther.empId)) && 
      (this.mndId == castOther.mndId);
  }

  public int hashCode()
  {
    int prime = 31;
    int hash = 17;
    hash = hash * 31 + this.empId.hashCode();
    hash = hash * 31 + this.mndId;

    return hash;
  }
}