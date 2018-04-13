package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ParticipacionVendedorLPK
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @Column(name="EmpId", length=10)
  private String empId;

  @Column(name="DocId")
  private int docId;

  @Column(name="VenId", length=3)
  private String venId;

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

  public String getVenId() {
    return this.venId;
  }

  public void setVenId(String venId) {
    this.venId = venId;
  }
}