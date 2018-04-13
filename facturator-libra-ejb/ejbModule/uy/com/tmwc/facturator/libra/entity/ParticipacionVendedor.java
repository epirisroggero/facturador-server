package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;

@Entity
@Table(name="lfx_participacionvendedor")
public class ParticipacionVendedor
  implements Serializable
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private ParticipacionVendedorLPK id = new ParticipacionVendedorLPK();

  @ForeignKey(name="lfx_participacionVendedor_documentos")
  @ManyToOne(optional=false)
  @JoinColumns({@javax.persistence.JoinColumn(name="DocId", referencedColumnName="DocId", insertable=false, updatable=false), @javax.persistence.JoinColumn(name="EmpId", referencedColumnName="EmpId", insertable=false, updatable=false)})
  private Documento documento;

  @ForeignKey(name="lfx_participacionVendedor_vendedores")
  @ManyToOne(optional=false)
  @JoinColumns({@javax.persistence.JoinColumn(name="VenId", referencedColumnName="VenId", insertable=false, updatable=false), @javax.persistence.JoinColumn(name="EmpId", referencedColumnName="EmpId", insertable=false, updatable=false)})
  private Vendedore vendedor;

  @Column(precision=10, scale=4)
  private BigDecimal porcentaje;

  public Documento getDocumento() { return this.documento; }

  public void setDocumento(Documento documento)
  {
    if ((documento != null) && 
      (this.vendedor != null) && (!this.vendedor.getId().getEmpId().equals(documento.getId().getEmpId()))) {
      throw new RuntimeException("No coincide la empresa");
    }

    this.documento = documento;
    this.id.setDocId(documento == null ? 0 : documento.getId().getDocId());
    this.id.setEmpId(documento == null ? null : documento.getId().getEmpId());
  }

  public Vendedore getVendedor() {
    return this.vendedor;
  }

  public void setVendedor(Vendedore vendedor) {
    if ((vendedor != null) && 
      (this.documento != null) && (!this.documento.getId().getEmpId().equals(vendedor.getId().getEmpId()))) {
      throw new RuntimeException("No coincide la empresa");
    }

    this.vendedor = vendedor;
    this.id.setVenId(vendedor == null ? null : vendedor.getId().getVenId());
    this.id.setEmpId(vendedor == null ? null : vendedor.getId().getEmpId());
  }

  public BigDecimal getPorcentaje() {
    return this.porcentaje;
  }

  public void setPorcentaje(BigDecimal porcentaje) {
    this.porcentaje = porcentaje;
  }

  public ParticipacionVendedorLPK getId() {
    return this.id;
  }

  public void setId(ParticipacionVendedorLPK id) {
    this.id = id;
  }
}