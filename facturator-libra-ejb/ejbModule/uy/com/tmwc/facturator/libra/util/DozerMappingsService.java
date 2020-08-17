package uy.com.tmwc.facturator.libra.util;

import org.dozer.DozerBeanMapper;

public abstract interface DozerMappingsService
{
  public abstract DozerBeanMapper getDozerBeanMapper();
}