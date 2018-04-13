package uy.com.tmwc.facturator.session;

import uy.com.tmwc.facturator.rapi.PermisosException;

public abstract interface FacturaPrinterExporter
{
  public abstract void execute() throws PermisosException;

  public abstract String getDocId();

  public abstract void setDocId(String paramString);
}