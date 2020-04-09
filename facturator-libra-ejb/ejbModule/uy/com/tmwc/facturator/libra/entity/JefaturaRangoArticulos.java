package uy.com.tmwc.facturator.libra.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumns;
import javax.persistence.OneToOne;
import org.hibernate.annotations.ForeignKey;

/*@Entity
 @DiscriminatorValue("R")
 */public class JefaturaRangoArticulos extends Jefatura {
	private static final long serialVersionUID = 1L;

	@ForeignKey(name = "lfx_jefaturas_articulosDesde")
	@OneToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "articuloDesde_id", referencedColumnName = "ArtId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Articulo articuloDesde;

	@Column(length = 20, nullable = false)
	private String articuloDesde_id;

	@ForeignKey(name = "lfx_jefaturas_articulosHasta")
	@OneToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "articuloHasta_id", referencedColumnName = "ArtId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Articulo articuloHasta;

	@Column(length = 20, nullable = false)
	private String articuloHasta_id;

	public Articulo getArticuloDesde() {
		return this.articuloDesde;
	}

	public Articulo getArticuloHasta() {
		return this.articuloHasta;
	}
}