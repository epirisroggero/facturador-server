package uy.com.tmwc.facturator.liquidacion;

import java.io.Serializable;
import uy.com.tmwc.facturator.dto.CodigoNombre;

public class JefaturaInfo
  implements Serializable
{
  private CodigoNombre jefe;
  private TipoJefatura tipo;
  private String target;

  public CodigoNombre getJefe()
  {
    return this.jefe;
  }

  public void setJefe(CodigoNombre jefe) {
    this.jefe = jefe;
  }

  public TipoJefatura getTipo() {
    return this.tipo;
  }

  public void setTipo(TipoJefatura tipo) {
    this.tipo = tipo;
  }

  public String getTarget() {
    return this.target;
  }

  public void setTarget(String target) {
    this.target = target;
  }
}