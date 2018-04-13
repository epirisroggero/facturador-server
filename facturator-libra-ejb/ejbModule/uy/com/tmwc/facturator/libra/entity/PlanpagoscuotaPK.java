package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PlanpagoscuotaPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Column(name="EmpId")
  private String empId;
  private String PPid;

  @Column(name="PPCuotaId")
  private short numero;

  public String getEmpId()
  {
    return this.empId;
  }
  public void setEmpId(String empId) {
    this.empId = empId;
  }
  public String getPPid() {
    return this.PPid;
  }
  public void setPPid(String PPid) {
    this.PPid = PPid;
  }
  public short getNumero() {
    return this.numero;
  }
  public void setNumero(short numero) {
    this.numero = numero;
  }
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof PlanpagoscuotaPK)) {
      return false;
    }
    PlanpagoscuotaPK castOther = (PlanpagoscuotaPK)other;

    return (this.empId.equals(castOther.empId)) && 
      (this.PPid.equals(castOther.PPid)) && 
      (this.numero == castOther.numero);
  }

  public int hashCode()
  {
    int prime = 31;
    int hash = 17;
    hash = hash * 31 + this.empId.hashCode();
    hash = hash * 31 + this.PPid.hashCode();
    hash = hash * 31 + this.numero;

    return hash;
  }
}