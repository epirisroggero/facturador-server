package uy.com.tmwc.facturator.session;

import java.math.BigDecimal;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Out;

import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.rapi.PermisosException;
import uy.com.tmwc.facturator.spi.DocumentoDAOService;

@Name("facturaPrinterExporter")
@Stateless
@Local({FacturaPrinterExporter.class})
public class FacturaPrinterExporterImpl
  implements FacturaPrinterExporter
{
  private String docId;

  @EJB
  DocumentoDAOService documentoDAOService;

  @Out(required=false)
  Documento documento;

  @Out(required=false)
  RentaDisguiser rentaDisguiser = new RentaDisguiser();

  public void execute() throws PermisosException {
    this.documento = this.documentoDAOService.findDocumento(this.docId);
  }

  public String getDocId() {
    return this.docId;
  }

  public void setDocId(String docId) {
    this.docId = docId;
  }

  public static final class RentaDisguiser
  {
    public String disguise(BigDecimal value) {
      String str = String.valueOf(value.intValue());
      StringBuilder b = new StringBuilder();
      for (int i = 0; i < str.length(); i++) {
        b.append(str.charAt(str.length() - i - 1));
      }
      return b.toString();
    }
  }
}