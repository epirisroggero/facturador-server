package uy.com.tmwc.facturator.server.util;

import org.dozer.DozerBeanMapper;

public abstract interface DTO2ModelMappingService
{
  public abstract DozerBeanMapper getDozerBeanMapper();
}