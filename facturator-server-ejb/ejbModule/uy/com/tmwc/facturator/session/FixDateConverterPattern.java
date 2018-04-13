package uy.com.tmwc.facturator.session;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Observer;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.faces.DateConverter;

@Name("DCPFixer")
@Scope(ScopeType.APPLICATION)
public class FixDateConverterPattern
{
  @Observer({"org.jboss.seam.postCreate.org.jboss.seam.faces.dateConverter"})
  public void fixDCP(Object instance)
  {
    // XXX se usaba para XML ? ((DateConverter)instance).setPattern("yyyy-MM-dd");
  }
}