package uy.com.tmwc.facturator.libra.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.hibernate.annotations.ForeignKey;

@Entity
@DiscriminatorValue("F")
@Table(uniqueConstraints={@javax.persistence.UniqueConstraint(columnNames={"EmpId, familia_id"})})
public class JefaturaFamiliaArticulos extends Jefatura
{
  private static final long serialVersionUID = 1L;

  @ForeignKey(name="lfx_jefaturas_familias")
  @OneToOne(optional=false)
  @JoinColumns({@javax.persistence.JoinColumn(name="familia_id", referencedColumnName="FamiliaId", insertable=false, updatable=false), @javax.persistence.JoinColumn(name="EmpId", referencedColumnName="EmpId", insertable=false, updatable=false)})
  private Familia familia;

  @Column(length=10, nullable=false)
  private String familia_id;

  public Familia getFamilia()
  {
    return this.familia;
  }
}