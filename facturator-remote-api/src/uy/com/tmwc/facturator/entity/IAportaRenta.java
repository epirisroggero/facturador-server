package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;
import java.util.Collection;

public abstract interface IAportaRenta
{
  public abstract Collection<LineaDocumento> getLineas();

  public abstract Collection<ParticipacionVendedor> getParticipaciones();

  public abstract BigDecimal getRenta();

  public abstract Moneda getMoneda();
}