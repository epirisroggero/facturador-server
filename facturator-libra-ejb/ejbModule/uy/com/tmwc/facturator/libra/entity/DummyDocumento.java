package uy.com.tmwc.facturator.libra.entity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="documentos")
public class DummyDocumento
{

  @EmbeddedId
  private DocumentoPK docId;

  public DocumentoPK getId()
  {
    return this.docId;
  }

  public void setId(DocumentoPK docId) {
    this.docId = docId;
  }
}