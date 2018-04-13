package uy.com.tmwc.facturator.session;

import java.util.Collection;

public class ExcelBlock<G, D>
{
  private int index;
  private Collection<D> data;
  private int firstDatumIndex;
  private G group;

  public int getIndex()
  {
    return this.index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  public Collection<D> getData() {
    return this.data;
  }

  public void setData(Collection<D> data) {
    this.data = data;
  }

  public int getFirstDatumIndex() {
    return this.firstDatumIndex;
  }

  public void setFirstDatumIndex(int firstDatumIndex) {
    this.firstDatumIndex = firstDatumIndex;
  }

  public G getGroup() {
    return this.group;
  }

  public void setGroup(G group) {
    this.group = group;
  }
}