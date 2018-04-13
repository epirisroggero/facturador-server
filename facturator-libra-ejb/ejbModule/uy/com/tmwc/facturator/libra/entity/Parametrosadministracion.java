package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "parametrosadministracion")
public class Parametrosadministracion extends PersistentEntity<ParametrosadministracionPK> implements Serializable, HasId<ParametrosadministracionPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ParametrosadministracionPK id;
	
	@Column(name = "LocIdParAdm")
	private Short locIdParAdm;

	@Column(name = "CliIdParAdm")
	private String cliIdParAdm;
	
	@Column(name = "ParAdmFechaHoy")
	private String parAdmFechaHoy;
	
	@Column(name = "ParAdmCuotaLunes")
	private String parAdmCuotaLunes;
	
	@Column(name = "ParAdmFechaTrabado")
	private Date parAdmFechaTrabado;
	
	@Column(name = "MndIdTH")
	private Short mndIdTH;

	@Column(name = "MndIdAR")
	private Short mndIdAR;

	@Column(name = "PrecioVentaIdParAdm")
	private Short precioVentaIdParAdm;
	
	@Column(name = "DepIdParAdm")
	private Short depIdParAdm;

	
	public ParametrosadministracionPK getId() {
		return this.id;
	}

	public void setId(ParametrosadministracionPK id) {
		this.id = id;
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
	