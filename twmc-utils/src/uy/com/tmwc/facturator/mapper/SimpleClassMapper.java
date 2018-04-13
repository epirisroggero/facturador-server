package uy.com.tmwc.facturator.mapper;

import java.util.HashMap;

public class SimpleClassMapper
  implements ClassMapper
{
  private HashMap<Class<?>, Class<?>> map = new HashMap();

  public void add(Class<?> src, Class<?> dest) {
    this.map.put(src, dest);
  }

  public Class<?> map(Class<?> clazz) {
    return (Class)this.map.get(clazz);
  }
}