package uy.com.tmwc.facturator.libra.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;

@Entity
@DiscriminatorValue("A")
@Table(uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"EmpId, articulo_id"})})
public class JefaturaArticulo extends Jefatura
{
  private static final long serialVersionUID = 1L;

  @ForeignKey(name="lfx_jefaturas_articulos")
  @OneToOne(optional=false)
  @JoinColumns({@javax.persistence.JoinColumn(name="articulo_id", referencedColumnName="ArtId", insertable=false, updatable=false), @javax.persistence.JoinColumn(name="EmpId", referencedColumnName="EmpId", insertable=false, updatable=false)})
  private Articulo articulo;

  @Column(length=20, nullable=false)
  private String articulo_id;

  public Articulo getArticulo()
  {
    return this.articulo;
  }
}