package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class DoccuotaPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Column(name="EmpId")
  private String empId;

  @Column(name="DocId")
  private int docId;

  @Column(name="CuotaId")
  private short cuotaId;

  public String getEmpId()
  {
    return this.empId;
  }
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  public int getDocId() {
    return this.docId;
  }
  public void setDocId(int docId) {
    this.docId = docId;
  }
  public short getCuotaId() {
    return this.cuotaId;
  }
  public void setCuotaId(short cuotaId) {
    this.cuotaId = cuotaId;
  }

  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof DoccuotaPK)) {
      return false;
    }
    DoccuotaPK castOther = (DoccuotaPK)other;

    return (this.empId.equals(castOther.empId)) && 
      (this.docId == castOther.docId) && 
      (this.cuotaId == castOther.cuotaId);
  }

  public int hashCode()
  {
    int prime = 31;
    int hash = 17;
    hash = hash * 31 + this.empId.hashCode();
    hash = hash * 31 + this.docId;
    hash = hash * 31 + this.cuotaId;

    return hash;
  }
}