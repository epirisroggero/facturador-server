package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "vendedores")
@CatalogEntity(prefix = "ven", abreviated = true)
public class Vendedore extends PersistentEntity<VendedorePK> implements Serializable, HasId<VendedorePK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VendedorePK id;

	@Column(name = "LocIdVen")
	private Short locIdVen;

	@Column(name = "VenCobrador")
	private String venCobrador;

	@Column(name = "VenNom")
	private String venNom;

	@Column(name = "VenPostventa")
	private String venPostventa;

	@Column(name = "VenVendedor")
	private String venVendedor;
	
	@OneToOne(mappedBy = "vendedor", fetch = FetchType.LAZY)
	private VendedoresUsuario vendedoresUsuario;
	
	@OneToMany(mappedBy = "vendedor")
	private Set<Vendedorescomisione> comisiones;

	public Short getUsuarioId() {
		VendedoresUsuario vu = getVendedoresUsuario();
		if (vu == null) {
			return null;
		}
		return vu.getId().getUsuarioId();
	}

	public VendedorePK getId() {
		return this.id;
	}

	public void setId(VendedorePK id) {
		this.id = id;
	}

	public Short getLocIdVen() {
		return this.locIdVen;
	}

	public void setLocIdVen(Short locIdVen) {
		this.locIdVen = locIdVen;
	}

	public String getVenCobrador() {
		return this.venCobrador;
	}

	public void setVenCobrador(String venCobrador) {
		this.venCobrador = venCobrador;
	}

	public String getNombre() {
		return this.venNom;
	}

	public void setNombre(String venNom) {
		this.venNom = venNom;
	}

	public String getVenPostventa() {
		return this.venPostventa;
	}

	public void setVenPostventa(String venPostventa) {
		this.venPostventa = venPostventa;
	}

	public String getVenVendedor() {
		return this.venVendedor;
	}

	public void setVenVendedor(String venVendedor) {
		this.venVendedor = venVendedor;
	}

	public String getCodigo() {
		return this.id.getVenId();
	}

	public VendedoresUsuario getVendedoresUsuario() {
		return vendedoresUsuario;
	}

	public void setVendedoresUsuario(VendedoresUsuario vendedoresUsuario) {
		this.vendedoresUsuario = vendedoresUsuario;
	}

	public Set<Vendedorescomisione> getComisiones() {
		return comisiones;
	}

	public void setComisiones(Set<Vendedorescomisione> comisiones) {
		this.comisiones = comisiones;
	}
	
}