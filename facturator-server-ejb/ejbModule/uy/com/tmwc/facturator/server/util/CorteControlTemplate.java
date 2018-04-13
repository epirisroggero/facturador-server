package uy.com.tmwc.facturator.server.util;

import java.util.Collection;

public abstract class CorteControlTemplate<V>
{
  public final void process(Collection<V> items)
  {
    V previous = null;
    for (V item : items) {
      if (!equal(item, previous)) {
        first(item);
      }
      previous = item;
      process(item);
    }
  }

  protected abstract boolean equal(V paramV1, V paramV2);

  protected abstract void first(V paramV);

  protected abstract void process(V paramV);
}