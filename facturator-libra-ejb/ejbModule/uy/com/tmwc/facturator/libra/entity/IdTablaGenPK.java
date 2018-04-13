package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IdTablaGenPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Column(name="EmpId")
  private String empId;

  @Column(name="IDtabla")
  private String tabla;

  public String getEmpId()
  {
    return this.empId;
  }
  public void setEmpId(String empId) {
    this.empId = empId;
  }

  public String getTabla() {
    return this.tabla;
  }
  public void setTabla(String tabla) {
    this.tabla = tabla;
  }
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (!(other instanceof IdTablaGenPK)) {
      return false;
    }
    IdTablaGenPK castOther = (IdTablaGenPK)other;

    return (this.empId.equals(castOther.empId)) && 
      (this.tabla.equals(castOther.tabla));
  }

  public int hashCode()
  {
    int prime = 31;
    int hash = 17;
    hash = hash * 31 + this.empId.hashCode();
    hash = hash * 31 + this.tabla.hashCode();

    return hash;
  }
}