package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import java.math.BigDecimal;


/**
 * The persistent class for the vendedorescomisiones database table.
 * 
 */
@Entity
@Table(name="vendedorescomisiones")
public class Vendedorescomisione implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VendedorescomisionePK id;

	@Column(name="ArtIdDesdeCom")
	private String artIdDesdeCom;

	@Column(name="ArtIdHastaCom")
	private String artIdHastaCom;

	@Column(name="CategArtIdCom")
	private String categArtIdCom;

	@Column(name="CmpIdCom")
	private Short cmpIdCom; 

	@Column(name="ComisionPorc")
	private BigDecimal porcentajeComision;

	@Column(name="FamiliaIdDesdeCom")
	private String familiaIdDesdeCom;

	@Column(name="FamiliaIdHastaCom")
	private String familiaIdHastaCom;

	@Column(name="MarcaIdCom")
	private String marcaIdCom;

	@Column(name="MndIdCom")
	private Short mndIdCom;

	private String PPidCom; //plan pagos

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @JoinColumn(name = "PrecioVentaIdCom", referencedColumnName = "PrecioVentaId", insertable = false, updatable = false),
			@JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Preciosventa preciosVenta;
	
	@Column(name="PrecioVentaIdCom")
	private Short precioVentaIdCom;

	@Column(name="PrvIdCom")
	private String prvIdCom; //proveedor
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "VenId", referencedColumnName = "VenId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Vendedore vendedor;	

	public Vendedorescomisione() {
    }

	public VendedorescomisionePK getId() {
		return this.id;
	}

	public void setId(VendedorescomisionePK id) {
		this.id = id;
	}
	
	public String getArtIdDesdeCom() {
		return this.artIdDesdeCom;
	}

	public void setArtIdDesdeCom(String artIdDesdeCom) {
		this.artIdDesdeCom = artIdDesdeCom;
	}

	public String getArtIdHastaCom() {
		return this.artIdHastaCom;
	}

	public void setArtIdHastaCom(String artIdHastaCom) {
		this.artIdHastaCom = artIdHastaCom;
	}

	public String getCategArtIdCom() {
		return this.categArtIdCom;
	}

	public void setCategArtIdCom(String categArtIdCom) {
		this.categArtIdCom = categArtIdCom;
	}

	public Short getCmpIdCom() {
		return this.cmpIdCom;
	}

	public void setCmpIdCom(Short cmpIdCom) {
		this.cmpIdCom = cmpIdCom;
	}

	public String getFamiliaIdDesdeCom() {
		return this.familiaIdDesdeCom;
	}

	public void setFamiliaIdDesdeCom(String familiaIdDesdeCom) {
		this.familiaIdDesdeCom = familiaIdDesdeCom;
	}

	public String getFamiliaIdHastaCom() {
		return this.familiaIdHastaCom;
	}

	public void setFamiliaIdHastaCom(String familiaIdHastaCom) {
		this.familiaIdHastaCom = familiaIdHastaCom;
	}

	public String getMarcaIdCom() {
		return this.marcaIdCom;
	}

	public void setMarcaIdCom(String marcaIdCom) {
		this.marcaIdCom = marcaIdCom;
	}

	public Short getMndIdCom() {
		return this.mndIdCom;
	}

	public void setMndIdCom(Short mndIdCom) {
		this.mndIdCom = mndIdCom;
	}

	public String getPPidCom() {
		return this.PPidCom;
	}

	public void setPPidCom(String PPidCom) {
		this.PPidCom = PPidCom;
	}

	public Short getPrecioVentaIdCom() {
		return this.precioVentaIdCom;
	}

	public void setPrecioVentaIdCom(Short precioVentaIdCom) {
		this.precioVentaIdCom = precioVentaIdCom;
	}

	public String getPrvIdCom() {
		return this.prvIdCom;
	}

	public void setPrvIdCom(String prvIdCom) {
		this.prvIdCom = prvIdCom;
	}

	public Preciosventa getPreciosVenta() {
		return preciosVenta;
	}
 
	public void setPreciosVenta(Preciosventa preciosVenta) {
		this.preciosVenta = preciosVenta;
	}

	public BigDecimal getPorcentajeComision() {
		return porcentajeComision;
	}

	public void setPorcentajeComision(BigDecimal porcentajeComision) {
		this.porcentajeComision = porcentajeComision;
	}

	public Vendedore getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedore vendedor) {
		this.vendedor = vendedor;
	}
}