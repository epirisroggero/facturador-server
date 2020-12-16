package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import javax.persistence.*;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;


/**
 * The persistent class for the lfx_vendedores_usuario database table.
 * 
 */
@Entity
@Table(name="lfx_vendedores_usuario")
public class VendedoresUsuario extends PersistentEntity<VendedoresUsuarioPK>  implements Serializable, HasId<VendedoresUsuarioPK>  {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private VendedoresUsuarioPK id; 

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional=false)
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "vendedorId", referencedColumnName = "VenId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Vendedore vendedor;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne(optional=false)
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "usuarioId", referencedColumnName = "UsuId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Usuario usuario;

    public VendedoresUsuario() {
    }

	public VendedoresUsuarioPK getId() {
		return this.id;
	}

	public void setId(VendedoresUsuarioPK id) {
		this.id = id;
	}
	
	public Vendedore getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedore vendedor) {
		this.vendedor = vendedor;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	

	
}