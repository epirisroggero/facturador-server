package uy.com.tmwc.facturator.libra.util;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;

import org.dozer.DozerBeanMapper;

@Stateless
public class DozerMappingsServiceImpl
  implements DozerMappingsService
{
  private static final DozerBeanMapper mapper = new DozerBeanMapper();
  private static final DozerBeanMapper lightmapper;

  static
  {
    ArrayList files = new ArrayList();
    files.add("dozerMappings.xml");
    mapper.setMappingFiles(files);

    List list = new ArrayList();
    list.add(new EmpIdDozerListener());
    mapper.setEventListeners(list);

    lightmapper = new DozerBeanMapper();
    files = new ArrayList();
    files.add("dozerMappings_lighter.xml");
    lightmapper.setMappingFiles(files);
  }

  public DozerBeanMapper getDozerBeanMapper() {
    return mapper;
  }

  public DozerBeanMapper getLightmapper() {
    return lightmapper;
  }
}