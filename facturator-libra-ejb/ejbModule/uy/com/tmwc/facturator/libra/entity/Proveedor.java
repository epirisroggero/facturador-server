package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import uy.com.tmwc.facturator.dto.ICodigoNombre;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "proveedores")
@CatalogEntity(useNamedQuery = true)
public class Proveedor extends PersistentEntity<ProveedorPK> implements Serializable, HasId<ProveedorPK>, ICodigoNombre {
	private static final long serialVersionUID = 1L;
	
	public Proveedor() {
	}

	public Proveedor(String codigo, String nombre, String telefono, String direccion, String razonSocial, String paisIdCto, String ctoRUT) {
		setCodigo(codigo);
		setNombre(nombre);
		
		contacto = new Contacto();
		contacto.setPaisIdCto(paisIdCto);
		contacto.setCtoTelefono(telefono);
		contacto.setCtoDireccion(direccion);
		contacto.setCtoRSocial(razonSocial);
		contacto.setCtoRUT(ctoRUT);
	}

	@EmbeddedId
	private ProveedorPK id;
	
	@Column(name = "PrvNombre")
	private String nombre;
	
	@SuppressWarnings("unused")
	@Column(name = "PrvId", insertable=false, updatable=false)
	private String codigo;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "PrvId", referencedColumnName = "CtoId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Contacto contacto;

	@Column(name = "CategPrvId")
	private String categPrvId;

	@Column(name = "LocIdPrv")
	private short locIdPrv;

	@Column(name = "PPidPrv")
	private String pPidPrv;
	
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "PPidPrv", referencedColumnName = "PPid", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Planpago planPagos;


	@Column(name = "PrvAplicaTopes")
	private String prvAplicaTopes;
	
	@Column(name = "PrvDto1")
	private BigDecimal prvDto1;

	@Column(name = "PrvDto2")
	private BigDecimal prvDto2;

	@Column(name = "PrvDto3")
	private BigDecimal prvDto3;

	@Column(name = "PrvIdNom")
	private String prvIdNom;

	@Column(name = "PrvIvaInc")
	private String prvIvaInc;

	@Column(name = "PrvRanking")
	private BigDecimal prvRanking;

	@Column(name = "RetencionIdPrv")
	private Short retencionIdPrv;

	@Column(name = "RubIdPrv")
	private String rubIdPrv;

	@Column(name = "TextoIdPrv")
	private String textoIdPrv;

	public ProveedorPK getId() {
		return this.id;
	}

	public void setId(ProveedorPK id) {
		this.id = id;
	}

	public String getCategPrvId() {
		return this.categPrvId;
	}

	public void setCategPrvId(String categPrvId) {
		this.categPrvId = categPrvId;
	}

	public short getLocIdPrv() {
		return this.locIdPrv;
	}

	public void setLocIdPrv(short locIdPrv) {
		this.locIdPrv = locIdPrv;
	}

	public String getPrvAplicaTopes() {
		return this.prvAplicaTopes;
	}

	public void setPrvAplicaTopes(String prvAplicaTopes) {
		this.prvAplicaTopes = prvAplicaTopes;
	}

	public BigDecimal getPrvDto1() {
		return this.prvDto1;
	}

	public void setPrvDto1(BigDecimal prvDto1) {
		this.prvDto1 = prvDto1;
	}

	public BigDecimal getPrvDto2() {
		return this.prvDto2;
	}

	public void setPrvDto2(BigDecimal prvDto2) {
		this.prvDto2 = prvDto2;
	}

	public BigDecimal getPrvDto3() {
		return this.prvDto3;
	}

	public void setPrvDto3(BigDecimal prvDto3) {
		this.prvDto3 = prvDto3;
	}

	public String getPrvIdNom() {
		return this.prvIdNom;
	}

	public void setPrvIdNom(String prvIdNom) {
		this.prvIdNom = prvIdNom;
	}

	public String getPrvIvaInc() {
		return this.prvIvaInc;
	}

	public void setPrvIvaInc(String prvIvaInc) {
		this.prvIvaInc = prvIvaInc;
	}

	public BigDecimal getPrvRanking() {
		return this.prvRanking;
	}

	public void setPrvRanking(BigDecimal prvRanking) {
		this.prvRanking = prvRanking;
	}

	public Short getRetencionIdPrv() {
		return this.retencionIdPrv;
	}

	public void setRetencionIdPrv(Short retencionIdPrv) {
		this.retencionIdPrv = retencionIdPrv;
	}

	public String getRubIdPrv() {
		return this.rubIdPrv;
	}

	public void setRubIdPrv(String rubIdPrv) {
		this.rubIdPrv = rubIdPrv;
	}

	public String getTextoIdPrv() {
		return this.textoIdPrv;
	}

	public void setTextoIdPrv(String textoIdPrv) {
		this.textoIdPrv = textoIdPrv;
	}

	public Contacto getContacto() {
		return this.contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCodigo() {
		return this.id.getPrvId();
	}
	
	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new ProveedorPK();
		}
		this.id.setPrvId(value);

	}

	public String getpPidPrv() {
		return pPidPrv;
	}

	public void setpPidPrv(String pPidPrv) {
		this.pPidPrv = pPidPrv;
	}

	public Planpago getPlanPagos() {
		return planPagos;
	}

	public void setPlanPagos(Planpago planPagos) {
		this.planPagos = planPagos;
		this.pPidPrv = (planPagos == null ? null : planPagos.getId().getPPid());
	}
	
	public String getCanalYoutube() {
		return getAdicional("25");
	}	
	
	public void setCanalYoutube(String valor) {
		setAdicional("25", valor);
	}

	public String getGoogleMaps() {
		return getAdicional("GM");
	}	
	
	public void setGoogleMaps(String valor) {
		setAdicional("GM", valor);
	}
	
	public String getDescuentoRecibo() {
		return getAdicional("24");
	}	
	
	public void setDescuentoRecibo(String valor) {
		setAdicional("24", valor);
	}
	
	public String getFacturaElectronica() {
		return getAdicional("59");
	}	
	
	public void setFacturaElectronica(String valor) {
		setAdicional("59", valor);
	}

	private String getAdicional(String codigo) {
		Map<String, String> adicionales = contacto != null ? contacto.getAdicionales() : null;
		if (adicionales != null) {
			return adicionales.get(codigo);
		} else {
			return null;
		}
	}
	
	private void setAdicional(String codigo, String valor) {
		Map<String, String> adicionales = contacto != null ? contacto.getAdicionales() : null;
		if (adicionales != null) {
			if (adicionales.containsKey(codigo)) {
				adicionales.remove(codigo);
			} 
			adicionales.put(codigo, valor);
		}
		if (contacto != null) {
			contacto.setAdicionales(adicionales);
		}
	}
	

}