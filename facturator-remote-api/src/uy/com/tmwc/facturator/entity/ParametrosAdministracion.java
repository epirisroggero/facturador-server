package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.util.Date;


public class ParametrosAdministracion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private String codigo;
	
	private Short locIdParAdm;

	private String cliIdParAdm;
	
	private String parAdmFechaHoy;
	
	private String parAdmCuotaLunes;
	
	private Date parAdmFechaTrabado;
	
	private Short mndIdTH;

	private Short mndIdAR;
	
	private Short precioVentaIdParAdm;
	
	private Short depIdParAdm;


	public ParametrosAdministracion() {
	}

	public ParametrosAdministracion(String codigo) {
		this.codigo = codigo;
	}

	public int hashCode() {
		int result = super.hashCode();

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj)) {
			return false;
		}
		return (obj instanceof ParametrosAdministracion);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Short getLocIdParAdm() {
		return locIdParAdm;
	}

	public void setLocIdParAdm(Short locIdParAdm) {
		this.locIdParAdm = locIdParAdm;
	}

	public String getCliIdParAdm() {
		return cliIdParAdm;
	}

	public void setCliIdParAdm(String cliIdParAdm) {
		this.cliIdParAdm = cliIdParAdm;
	}

	public String getParAdmFechaHoy() {
		return parAdmFechaHoy;
	}

	public void setParAdmFechaHoy(String parAdmFechaHoy) {
		this.parAdmFechaHoy = parAdmFechaHoy;
	}

	public String getParAdmCuotaLunes() {
		return parAdmCuotaLunes;
	}

	public void setParAdmCuotaLunes(String parAdmCuotaLunes) {
		this.parAdmCuotaLunes = parAdmCuotaLunes;
	}

	public Date getParAdmFechaTrabado() {
		return parAdmFechaTrabado;
	}

	public void setParAdmFechaTrabado(Date parAdmFechaTrabado) {
		this.parAdmFechaTrabado = parAdmFechaTrabado;
	}

	public Short getMndIdTH() {
		return mndIdTH;
	}

	public void setMndIdTH(Short mndIdTH) {
		this.mndIdTH = mndIdTH;
	}

	public Short getMndIdAR() {
		return mndIdAR;
	}

	public void setMndIdAR(Short mndIdAR) {
		this.mndIdAR = mndIdAR;
	}

	public Short getPrecioVentaIdParAdm() {
		return precioVentaIdParAdm;
	}

	public void setPrecioVentaIdParAdm(Short precioVentaIdParAdm) {
		this.precioVentaIdParAdm = precioVentaIdParAdm;
	}

	public Short getDepIdParAdm() {
		return depIdParAdm;
	}

	public void setDepIdParAdm(Short depIdParAdm) {
		this.depIdParAdm = depIdParAdm;
	}
}