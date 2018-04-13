package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name="listas")
@CatalogEntity(useNamedQuery=true)
public class Lista extends PersistentEntity<ListaPK>
  implements Serializable, HasId<ListaPK>
{
  private static final long serialVersionUID = 1L;

  @EmbeddedId
  private ListaPK id;

  @Column(name="ListaCatalogo")
  private String listaCatalogo;

  @Column(name="ListaIVA")
  private String listaIVA;

  @Column(name="ListaOrden")
  private String listaOrden;

  @Column(name="ListaPie")
  private String listaPie;

  @Column(name="ListaSubtitulo")
  private String listaSubtitulo;

  @Column(name="ListaTitulo")
  private String listaTitulo;

  public ListaPK getId()
  {
    return this.id;
  }

  public void setId(ListaPK id) {
    this.id = id;
  }

  public String getListaCatalogo() {
    return this.listaCatalogo;
  }

  public void setListaCatalogo(String listaCatalogo) {
    this.listaCatalogo = listaCatalogo;
  }

  public String getListaIVA() {
    return this.listaIVA;
  }

  public void setListaIVA(String listaIVA) {
    this.listaIVA = listaIVA;
  }

  public String getListaOrden() {
    return this.listaOrden;
  }

  public void setListaOrden(String listaOrden) {
    this.listaOrden = listaOrden;
  }

  public String getListaPie() {
    return this.listaPie;
  }

  public void setListaPie(String listaPie) {
    this.listaPie = listaPie;
  }

  public String getListaSubtitulo() {
    return this.listaSubtitulo;
  }

  public void setListaSubtitulo(String listaSubtitulo) {
    this.listaSubtitulo = listaSubtitulo;
  }

  public String getListaTitulo() {
    return this.listaTitulo;
  }

  public void setListaTitulo(String listaTitulo) {
    this.listaTitulo = listaTitulo;
  }
}