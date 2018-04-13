package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class JefaturaPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;
  private long jefaturaId;

  @Column(name="EmpId", length=10, nullable=false)
  private String empId;

  public long getJefaturaId()
  {
    return this.jefaturaId;
  }

  public void setJefaturaId(long jefaturaId) {
    this.jefaturaId = jefaturaId;
  }

  public String getEmpId() {
    return this.empId;
  }

  public void setEmpId(String empId) {
    this.empId = empId;
  }
}