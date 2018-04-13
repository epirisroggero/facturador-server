package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;

public class Cliente extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;

	private String categCliId;
	
	private GrupoContacto grupo;
	private BigDecimal cliTopeCredito;
	private BigDecimal cliDto1;
	
	private String encargadoPagos;
	private String direccionCobranza;
	private String lugarEntrega;
	private String agencia;
	
	private String encargadoCuenta;
	private String especialista1;
	private String especialista2;
	
	private String energiaElectrica;
	private String permisoPrecios;
	private String permisoStock;

	private Moneda moneda;
	private PlanPagos planPagos;
	private PreciosVenta preciosVenta;
	private Vendedor vendedor;

	private Short precioVentaIdCli;
	private String pPidCli;
	
	private String diaHoraPagos;
	
	private Contacto contacto;

	private Short locIdCli;
	
	private String cliIdNom;
	
	private BigDecimal cliRanking;
	
	private String googleMaps;
	
	public Contacto getContacto() {
		return contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	
	public String getEspecialista1() {
		return especialista1;
	}

	public void setEspecialista1(String especialista1) {
		this.especialista1 = especialista1;
	}

	public String getEspecialista2() {
		return especialista2;
	}

	public void setEspecialista2(String especialista2) {
		this.especialista2 = especialista2;
	}

	public String getEncargadoCuenta() {
		return encargadoCuenta;
	}

	public void setEncargadoCuenta(String encargadoCuenta) {
		this.encargadoCuenta = encargadoCuenta;
	}

	public String getDepartamento() {
		if (contacto != null) {
			return contacto.getDeptoIdCto();
		}
		return null;
	}
	
	public String getZona() {
		if (contacto != null) {
			return contacto.getZonaIdCto();
		}
		return null;
	}

	public PlanPagos getPlanPagos() {
		return this.planPagos;
	}

	public void setPlanPagos(PlanPagos planPagos) {
		this.planPagos = planPagos;
	}

	public Moneda getMoneda() {
		return moneda;
	}

	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
	}

	public Vendedor getVendedor() {
		return this.vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public PreciosVenta getPreciosVenta() {
		return this.preciosVenta;
	}

	public void setPreciosVenta(PreciosVenta preciosVenta) {
		this.preciosVenta = preciosVenta;
	}

	public BigDecimal getCliTopeCredito() {
		return cliTopeCredito;
	}

	public void setCliTopeCredito(BigDecimal cliTopeCredito) {
		this.cliTopeCredito = cliTopeCredito;
	}

	public String getEncargadoPagos() {
		return encargadoPagos;
	}

	public void setEncargadoPagos(String encargadoPagos) {
		this.encargadoPagos = encargadoPagos;
	}

	public String getDireccionCobranza() {
		return direccionCobranza;
	}

	public void setDireccionCobranza(String direccionCobranza) {
		this.direccionCobranza = direccionCobranza;
	}

	public String getDiaHoraPagos() {
		return diaHoraPagos;
	}

	public void setDiaHoraPagos(String diaHoraPagos) {
		this.diaHoraPagos = diaHoraPagos;
	}
	
	public String getLugarEntrega() {
		return lugarEntrega;
	}

	public void setLugarEntrega(String lugarEntrega) {
		this.lugarEntrega = lugarEntrega;
	}

	public GrupoContacto getGrupo() {
		return grupo;
	}

	public void setGrupo(GrupoContacto grupo) {
		this.grupo = grupo;
	}
	
	public String getAgencia() {
		return agencia;
	}
	
	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public BigDecimal getCliDto1() {
		return cliDto1;
	}

	public void setCliDto1(BigDecimal cliDto1) {
		this.cliDto1 = cliDto1;
	}

	public Short getLocIdCli() {
		return locIdCli;
	}

	public void setLocIdCli(Short locIdCli) {
		this.locIdCli = locIdCli;
	}

	public String getCategCliId() {
		return categCliId;
	}

	public void setCategCliId(String categCliId) {
		this.categCliId = categCliId;
	}

	public Short getPrecioVentaIdCli() {
		return precioVentaIdCli;
	}

	public void setPrecioVentaIdCli(Short precioVentaIdCli) {
		this.precioVentaIdCli = precioVentaIdCli;
	}

	public String getpPidCli() {
		return pPidCli;
	}

	public void setpPidCli(String pPidCli) {
		this.pPidCli = pPidCli;
	}

	public String getCliIdNom() {
		return cliIdNom;
	}

	public void setCliIdNom(String cliIdNom) {
		this.cliIdNom = cliIdNom;
	}

	public BigDecimal getCliRanking() {
		return cliRanking;
	}

	public void setCliRanking(BigDecimal cliRanking) {
		this.cliRanking = cliRanking;
	}
	
	public String toString() {
		return getCodigo() + "-" + getNombre();  
	}

	public String getEnergiaElectrica() {
		return energiaElectrica;
	}

	public void setEnergiaElectrica(String energiaElectrica) {
		this.energiaElectrica = energiaElectrica;
	}

	public String getPermisoPrecios() {
		return permisoPrecios;
	}

	public void setPermisoPrecios(String value) {
		this.permisoPrecios = value;
	}

	public String getPermisoStock() {
		return permisoStock;
	}

	public void setPermisoStock(String value) {
		this.permisoStock = value;
	}

	public String getGoogleMaps() {
		return googleMaps;
	}

	public void setGoogleMaps(String googleMaps) {
		this.googleMaps = googleMaps;
	}


}