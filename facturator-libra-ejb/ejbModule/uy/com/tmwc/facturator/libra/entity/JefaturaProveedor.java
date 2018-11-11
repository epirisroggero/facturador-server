package uy.com.tmwc.facturator.libra.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;

@Entity
@DiscriminatorValue("P")
@Table(uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"EmpId, proveedor_id"})})
public class JefaturaProveedor extends Jefatura
{
  private static final long serialVersionUID = 1L;

  @ForeignKey(name="lfx_jefaturas_proveedores")
  @OneToOne(optional=false)
  @JoinColumns({@javax.persistence.JoinColumn(name="proveedor_id", referencedColumnName="PrvId", insertable=false, updatable=false), 
	  @javax.persistence.JoinColumn(name="EmpId", referencedColumnName="EmpId", insertable=false, updatable=false)})
  private Proveedor proveedor;

  @Column(length=10, nullable=false)
  private String proveedor_id;

  public Proveedor getProveedor()
  {
    return this.proveedor;
  }
}