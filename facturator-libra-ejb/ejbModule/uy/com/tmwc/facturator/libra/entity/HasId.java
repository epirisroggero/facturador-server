package uy.com.tmwc.facturator.libra.entity;

public abstract interface HasId<T>
{
  public abstract T getId();

  public abstract void setId(T paramT);
}