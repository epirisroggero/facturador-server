package uy.com.tmwc.facturator.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import org.dozer.DozerBeanMapper;

public class MappingUtils
{
  public static <D, T> List<D> map(Class<D> clazzB, List<T> list, DozerBeanMapper mapper)
  {
    ArrayList nruter = new ArrayList();
    for (Object t : list) {
      Object d = mapper.map(t, clazzB);
      nruter.add(d);
    }

    return nruter;
  }

  public static Field getField(Class<?> clazz, String name) {
    Field field = null;
    try {
      field = clazz.getDeclaredField(name);
    } catch (SecurityException e) {
      throw new RuntimeException(e);
    }
    catch (NoSuchFieldException localNoSuchFieldException)
    {
    }
    Class superClazz;
    if ((field == null) && ((superClazz = clazz.getSuperclass()) != null)) {
      return getField(superClazz, name);
    }
    return field;
  }
}