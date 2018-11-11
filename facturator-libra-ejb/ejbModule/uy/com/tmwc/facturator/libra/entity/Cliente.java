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
@Table(name = "clientes")
@CatalogEntity(useNamedQuery = true)
public class Cliente extends PersistentEntity<ClientePK> implements Serializable, ICodigoNombre, HasId<ClientePK> {
	private static final long serialVersionUID = 1L;

	public Cliente() {
	}
	
	public Cliente(Cliente e, Contacto cto) {
		this(e.getCodigo(), e.getNombre(), cto.getCtoRUT(), cto.getCtoTelefono(), cto.getCtoDireccion(), cto.getCtoRSocial(), e.getCliTopeCredito(), cto.getCtoCliente(), cto.getCtoProveedor(), e.getCategCliId(), e.getVenIdCli(), cto.getNombre(), e.getEncargadoCuenta());
	}

	public Cliente(String codigo, String nombre, String rut, String telefono, String direccion, String razonSocial, BigDecimal topeCredito, String ctoCliente, String ctoProveedor, String categCliId) {
		this(codigo, nombre, rut, telefono, direccion, razonSocial, topeCredito, ctoCliente, ctoProveedor, categCliId, null, null, null);
	}

	public Cliente(String codigo, String nombre, String rut, String telefono, String direccion, String razonSocial, BigDecimal topeCredito, String ctoCliente, String ctoProveedor, String categCliId, String venId, String ctoNombre, String encCuenta) {
		setCodigo(codigo);
		setNombre(nombre);
		setCliTopeCredito(topeCredito);
		setCategCliId(categCliId);
		setVenIdCli(venId);
		setEncargadoCuenta(encCuenta);
		
		contacto = new Contacto();
		contacto.setCodigo(codigo);
		contacto.setNombre(ctoNombre != null ? ctoNombre : nombre);
		contacto.setCtoRUT(rut);
		contacto.setCtoTelefono(telefono);
		contacto.setCtoDireccion(direccion);
		contacto.setCtoRSocial(razonSocial);
		contacto.setCtoCliente(ctoCliente);
		contacto.setCtoProveedor(ctoProveedor);
		
		if (venId != null) {
			VendedorePK key = new VendedorePK();
			key.setVenId(venId);
			vendedor = new Vendedore();
			vendedor.setId(key);			
		}
	}

	@EmbeddedId
	private ClientePK id;

	@SuppressWarnings("unused")
	@Column(name = "CliId", insertable = false, updatable = false)
	private String codigo;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false, cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	@JoinColumns({ @javax.persistence.JoinColumn(name = "CliId", referencedColumnName = "CtoId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Contacto contacto;

	@Column(name = "CategCliId")
	private String categCliId;

	@Column(name = "CliDto1")
	private BigDecimal cliDto1;

	@Column(name = "CliDto2")
	private BigDecimal cliDto2;

	@Column(name = "CliDto3")
	private BigDecimal cliDto3;

	@Column(name = "CliExentoIVA")
	private String cliExentoIVA;

	@Column(name = "CliFichaLocal")
	private String cliFichaLocal;

	@Column(name = "CliIdNom")
	private String cliIdNom;

	@Column(name = "CliNombre")
	private String nombre;

	@Column(name = "CliPuntos")
	private String cliPuntos;

	@Column(name = "CliRanking")
	private BigDecimal cliRanking;

	@Column(name = "CliTopeCredito")
	private BigDecimal cliTopeCredito;

	@Column(name = "CliTopeDias")
	private Short cliTopeDias;

	@Column(name = "LocIdCli")
	private Short locIdCli;

	@SuppressWarnings("unused")
	@Column(name = "MndIdCli")
	private short mndIdCli;
	
	@SuppressWarnings("unused")
	@Column(name = "PPidCli")
	private String pPidCli;
	
	@SuppressWarnings("unused")
	@Column(name = "VenIdCli")
	private String venIdCli;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "PPidCli", referencedColumnName = "PPid", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Planpago planPagos;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "PrecioVentaIdCli", referencedColumnName = "PrecioVentaId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Preciosventa preciosVenta;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "MndIdCli", referencedColumnName = "MndId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Moneda moneda;

	@SuppressWarnings("unused")
	@Column(name = "PrecioVentaIdCli")
	private Short precioVentaIdCli;

	@Column(name = "RubIdCli")
	private String rubIdCli;

	@Column(name = "TextoIdCli")
	private String textoIdCli;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "VenIdCli", referencedColumnName = "VenId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Vendedore vendedor;
	
	
	@Column(name = "CliEfactura")
	private String cliEfactura;
	
	@Column(name = "CliEmail")
    private String cliEmail;

	public String getCategCliId() {
		return this.categCliId;
	}

	public void setCategCliId(String categCliId) {
		this.categCliId = categCliId;
	}

	public BigDecimal getCliDto1() {
		return this.cliDto1;
	}

	public void setCliDto1(BigDecimal cliDto1) {
		this.cliDto1 = cliDto1;
	}

	public BigDecimal getCliDto2() {
		return this.cliDto2;
	}

	public void setCliDto2(BigDecimal cliDto2) {
		this.cliDto2 = cliDto2;
	}

	public BigDecimal getCliDto3() {
		return this.cliDto3;
	}

	public void setCliDto3(BigDecimal cliDto3) {
		this.cliDto3 = cliDto3;
	}

	public String getCliExentoIVA() {
		return this.cliExentoIVA;
	}

	public void setCliExentoIVA(String cliExentoIVA) {
		this.cliExentoIVA = cliExentoIVA;
	}

	public String getCliFichaLocal() {
		return this.cliFichaLocal;
	}

	public void setCliFichaLocal(String cliFichaLocal) {
		this.cliFichaLocal = cliFichaLocal;
	}

	public String getCliIdNom() {
		return this.cliIdNom;
	}

	public void setCliIdNom(String cliIdNom) {
		this.cliIdNom = cliIdNom;
	}

	public String getCliPuntos() {
		return this.cliPuntos;
	}

	public void setCliPuntos(String cliPuntos) {
		this.cliPuntos = cliPuntos;
	}

	public BigDecimal getCliRanking() {
		return this.cliRanking;
	}

	public void setCliRanking(BigDecimal cliRanking) {
		this.cliRanking = cliRanking;
	}

	public BigDecimal getCliTopeCredito() {
		return this.cliTopeCredito;
	}

	public void setCliTopeCredito(BigDecimal cliTopeCredito) {
		this.cliTopeCredito = cliTopeCredito;
	}

	public Planpago getPlanPagos() {
		return this.planPagos;
	}

	public String getRubIdCli() {
		return this.rubIdCli;
	}

	public void setRubIdCli(String rubIdCli) {
		this.rubIdCli = rubIdCli;
	}

	public String getTextoIdCli() {
		return this.textoIdCli;
	}

	public void setTextoIdCli(String textoIdCli) {
		this.textoIdCli = textoIdCli;
	}

	public Vendedore getVendedor() {
		return this.vendedor;
	}

	public void setVendedor(Vendedore vendedor) {
		this.vendedor = vendedor;
		this.venIdCli = (vendedor == null ? null : vendedor.getId().getVenId());
	}

	public Short getCliTopeDias() {
		return this.cliTopeDias;
	}

	public void setCliTopeDias(Short cliTopeDias) {
		this.cliTopeDias = cliTopeDias;
	}

	public Short getLocIdCli() {
		return this.locIdCli;
	}

	public void setLocIdCli(Short locIdCli) {
		this.locIdCli = locIdCli;
	}

	public ClientePK getId() {
		return this.id;
	}

	public void setId(ClientePK id) {
		this.id = id;
	}

	public Contacto getContacto() {
		return this.contacto;
	}

	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}

	public Preciosventa getPreciosVenta() {
		return this.preciosVenta;
	}

	public String getCodigo() {
		return this.id.getCliId();
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getRut() {
		return this.contacto != null ? this.contacto.getCtoRUT() : null;
	}

	public String getDireccion() {
		return this.contacto != null ? this.contacto.getCtoDireccion() : null;
	}

	public String getRazonSocial() {
		return this.contacto != null ? this.contacto.getCtoRSocial() : null;
	}

	public String getTelefono() {
		return this.contacto != null ? this.contacto.getCtoTelefono() : null;
	}

	public String getLocalidad() {
		return this.contacto != null ? this.contacto.getCtoLocalidad() : null;
	}

	public String getEmail1() {
		return this.contacto != null ? this.contacto.getCtoEmail1() : null;
	}

	public String getEmail2() {
		return this.contacto != null ? this.contacto.getCtoEmail2() : null;
	}
	

	public Moneda getMoneda() {
		return moneda;
	}
	
	public void setMoneda(Moneda moneda) {
		this.moneda = moneda;
		this.mndIdCli = (moneda == null ? null : Short.valueOf(moneda.getId().getMndId()));
	}
	
	public void setPreciosVenta(Preciosventa preciosVenta) {
		this.preciosVenta = preciosVenta;
		this.precioVentaIdCli = (preciosVenta == null ? null : Short.valueOf(preciosVenta.getId().getPrecioVentaId()));
	}
	
	public void setPlanPagos(Planpago planPago) {
		this.planPagos = planPago;
		this.pPidCli = (planPagos == null ? null : planPagos.getId().getPPid());
	}		

	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new ClientePK();
		}
		this.id.setCliId(value);
	}

	public String getCategoria() {
		return this.categCliId;
	}

	public void setCategoria(String value) {
		this.categCliId = value;
	}

	public void setZona(String value) {
		if (contacto != null) {
			contacto.setZonaIdCto(value);
		}
	}
	
	public void setDepartamento(String deptoIdCto) {
		if (contacto != null) {
			contacto.setDeptoIdCto(deptoIdCto);
		}
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

	
	public void provideId(String empId, String cliId) {
		this.id = new ClientePK(empId, cliId);
	}

	public Gruposcontacto getGrupo() {
		return contacto != null ? contacto.getGrupo() : null;
	}

	public String getEncargadoPagos() {
		return getAdicional("03");
	}

	public void setEncargadoPagos(String value) {
		setAdicional("03", value);
	}
	
	public String getDiaHoraPagos() {
		return getAdicional("04");
	}
	
	public void setDiaHoraPagos(String value) {
		setAdicional("04", value);
	}
	
	public String getLugarEntrega() {
		return getAdicional("05");
	}

	public void setLugarEntrega(String value) {
		setAdicional("05", value);
	}

	public String getDireccionCobranza() {
		return getAdicional("06");
	}

	public void setDireccionCobranza(String value) {
		setAdicional("06", value);
	}

	public String getAgencia() {
		return getAdicional("07");
	}
	
	public void setAgencia(String agencia) {
		setAdicional("07", agencia);
	}

	public String getEspecialista1() {
		return getAdicional("10");
	}

	public void setEspecialista1(String especialista) {
		setAdicional("10", especialista);
	}

	public String getEspecialista2() {
		return getAdicional("11");
	}

	public void setEspecialista2(String especialista) {
		setAdicional("11", especialista);
	}

	public String getEncargadoCuenta() {
		String encargadoCuenta = getAdicional("14");
		return encargadoCuenta;
	}	
	
	public void setEncargadoCuenta(String encargadoCuenta) {
		setAdicional("14", encargadoCuenta);
	}
	
	public String getEnergiaElectrica() {
		return getAdicional("18");
	}	
	
	public void setEnergiaElectrica(String energiaElectrica) {
		setAdicional("18", energiaElectrica);
	}
	
	public String getGoogleMaps() {
		return getAdicional("GM");
	}	
	
	public void setGoogleMaps(String valor) {
		setAdicional("GM", valor);
	}

	public String getPermisoStock() {
		return getAdicional("55");
	}

	public void setPermisoStock(String value) {
		setAdicional("55", value);
	}

	public String getPermisoPrecios() {
		return getAdicional("56");
	}

	public void setPermisoPrecios(String value) {
		setAdicional("56", value);
	}

	public String getpPidCli() {
		return pPidCli;
	}

	public void setpPidCli(String pPidCli) {
		this.pPidCli = pPidCli;
	}

	public Short getPrecioVentaIdCli() {
		return precioVentaIdCli;
	}

	public void setPrecioVentaIdCli(Short precioVentaIdCli) {
		this.precioVentaIdCli = precioVentaIdCli;
	}

	public String getCliEfactura() {
		return cliEfactura;
	}

	public void setCliEfactura(String cliEfactura) {
		this.cliEfactura = cliEfactura;
	}

	public String getCliEmail() {
		return cliEmail;
	}

	public void setCliEmail(String cliEmail) {
		this.cliEmail = cliEmail;
	}

	public String getVenIdCli() {
		return venIdCli;
	}

	public void setVenIdCli(String venIdCli) {
		this.venIdCli = venIdCli;
	}


}