package uy.com.tmwc.facturator.server.util;

import java.util.HashMap;

public abstract class DTOFactory<K, V, E>
{
  private HashMap<K, V> memory = new HashMap<K, V>();

  public V get(E source) {
    K key = getKey(source);
    V remembered = this.memory.get(key);
    if (remembered == null) {
      remembered = build(source);
      this.memory.put(key, remembered);
    }
    return remembered;
  }

  protected abstract V build(E paramE);

  protected abstract K getKey(E paramE);
}