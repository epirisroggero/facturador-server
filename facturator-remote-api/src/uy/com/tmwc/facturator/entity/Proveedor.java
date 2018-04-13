package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;


public class Proveedor extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;
	
	private Contacto contacto;

	private String categPrvId;
	
	private String rubIdPrv;
	
	private String prvIdNom;
	
	private short locIdPrv;
	
	private String pPidPrv; 
	
	private String prvAplicaTopes;
	
	private BigDecimal prvDto1;
	
	private BigDecimal prvDto2;
	
	private BigDecimal prvDto3;
	
	private String prvIvaInc; 
	
	private BigDecimal prvRanking; 
		
	private Short retencionIdPrv; 
	
	private String textoIdPrv; 
	
	private String canalYoutube;
	
	private String googleMaps;
	
	private String descuentoRecibo;
	
	private String facturaElectronica;
	
	private PlanPagos planPagos;
	
	public Proveedor() {
		super();
	}

	public Proveedor(String codigo, String nombre) {
		super(codigo, nombre);
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
		return (obj instanceof Proveedor);
	}

	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public String getCategPrvId() {
		return categPrvId;
	}

	public void setCategPrvId(String categPrvId) {
		this.categPrvId = categPrvId;
	}

	public String getRubIdPrv() {
		return rubIdPrv;
	}

	public void setRubIdPrv(String rubIdPrv) {
		this.rubIdPrv = rubIdPrv;
	}

	public String getPrvIdNom() {
		return prvIdNom;
	}

	public void setPrvIdNom(String prvIdNom) {
		this.prvIdNom = prvIdNom;
	}

	public short getLocIdPrv() {
		return locIdPrv;
	}

	public void setLocIdPrv(short locIdPrv) {
		this.locIdPrv = locIdPrv;
	}

	public String getpPidPrv() {
		return pPidPrv;
	}

	public void setpPidPrv(String pPidPrv) {
		this.pPidPrv = pPidPrv;
	}

	public String getPrvAplicaTopes() {
		return prvAplicaTopes;
	}

	public void setPrvAplicaTopes(String prvAplicaTopes) {
		this.prvAplicaTopes = prvAplicaTopes;
	}

	public BigDecimal getPrvDto1() {
		return prvDto1;
	}

	public void setPrvDto1(BigDecimal prvDto1) {
		this.prvDto1 = prvDto1;
	}

	public BigDecimal getPrvDto2() {
		return prvDto2;
	}

	public void setPrvDto2(BigDecimal prvDto2) {
		this.prvDto2 = prvDto2;
	}

	public BigDecimal getPrvDto3() {
		return prvDto3;
	}

	public void setPrvDto3(BigDecimal prvDto3) {
		this.prvDto3 = prvDto3;
	}

	public String getPrvIvaInc() {
		return prvIvaInc;
	}

	public void setPrvIvaInc(String prvIvaInc) {
		this.prvIvaInc = prvIvaInc;
	}

	public BigDecimal getPrvRanking() {
		return prvRanking;
	}

	public void setPrvRanking(BigDecimal prvRanking) {
		this.prvRanking = prvRanking;
	}

	public Short getRetencionIdPrv() {
		return retencionIdPrv;
	}

	public void setRetencionIdPrv(Short retencionIdPrv) {
		this.retencionIdPrv = retencionIdPrv;
	}

	public String getTextoIdPrv() {
		return textoIdPrv;
	}

	public void setTextoIdPrv(String textoIdPrv) {
		this.textoIdPrv = textoIdPrv;
	}

	public PlanPagos getPlanPagos() {
		return planPagos;
	}

	public void setPlanPagos(PlanPagos planPagos) {
		this.planPagos = planPagos;
	}

	public String getCanalYoutube() {
		return canalYoutube;
	}

	public void setCanalYoutube(String canalYoutube) {
		this.canalYoutube = canalYoutube;
	}

	public String getDescuentoRecibo() {
		return descuentoRecibo;
	}

	public void setDescuentoRecibo(String value) {
		this.descuentoRecibo = value;
	}

	public String getGoogleMaps() {
		return googleMaps;
	}

	public void setGoogleMaps(String googleMaps) {
		this.googleMaps = googleMaps;
	}

	public String getFacturaElectronica() {
		return facturaElectronica;
	}

	public void setFacturaElectronica(String facturaElectronica) {
		this.facturaElectronica = facturaElectronica;
	}


}