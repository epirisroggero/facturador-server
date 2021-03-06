package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.PrimaryKeyJoinColumns;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import uy.com.tmwc.facturator.libra.util.Constantes;

@Entity
@Table(name = "lineas")
public class Linea extends PersistentEntity<LineaPK> implements Serializable, HasId<LineaPK> {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LineaPK id;

	@ManyToOne(optional = false)
	@JoinColumns({ @javax.persistence.JoinColumn(name = "DocId", referencedColumnName = "DocId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Documento documento;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @javax.persistence.JoinColumn(name = "ArtIdLin", referencedColumnName = "ArtId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Articulo articulo;

	@Column(name = "ArtIdLin", length = 20)
	private String articuloId;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @javax.persistence.JoinColumn(name = "DepIdLinDestino", referencedColumnName = "DepId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Deposito depositoDestino;

	@SuppressWarnings("unused")
	@Column(name = "DepIdLinDestino")
	private Short depositoDestinoId = 0;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @javax.persistence.JoinColumn(name = "DepIdLinOrigen", referencedColumnName = "DepId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Deposito depositoOrigen;

	@SuppressWarnings("unused")
	@Column(name = "DepIdLinOrigen")
	private Short depositoOrigenId = 0;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @javax.persistence.JoinColumn(name = "IvaIdLin", referencedColumnName = "IvaId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Iva ivaLin;

	@Column(name = "IvaIdLin")
	private Short ivaId;

	@SuppressWarnings("unused")
	@Column(name = "LinArmado")
	private String armado = "";

	@Column(name = "LinCantidad")
	private BigDecimal cantidad;

	@SuppressWarnings("unused")
	@Column(name = "LinCantidadSaldo")
	private BigDecimal cantidadSaldo = BigDecimal.ZERO;

	@Column(name = "LinConcepto")
	private String concepto;

	@Column(name = "LinCosto")
	private BigDecimal costo;

	@Column(name = "LinDto1")
	private BigDecimal dto1;

	@Column(name = "LinDto2")
	private BigDecimal dto2;

	@Column(name = "LinDto3")
	private BigDecimal dto3;

	@Column(name = "LinDtoImp1")
	private BigDecimal dtoImp1 = BigDecimal.ZERO;

	@Column(name = "LinDtoImp2")
	private BigDecimal dtoImp2 = BigDecimal.ZERO;

	@Column(name = "LinDtoImp3")
	private BigDecimal dtoImp3 = BigDecimal.ZERO;

	@Temporal(TemporalType.DATE)
	@Column(name = "LinFecha")
	private Date fecha;

	@Column(name = "LinItems")
	private Integer items = Integer.valueOf(0);

	@Column(name = "LinIVA")
	private BigDecimal linIva;

	@Lob
	@Column(name = "LinNotas")
	private String notas = "";

	@Column(name = "LinPrecio1")
	private BigDecimal precio;

	@SuppressWarnings("unused")
	@Column(name = "LinPrecio2")
	private BigDecimal precio2;

	@Column(name = "LinPrecio3")
	private BigDecimal precioUnitario;

	@Column(name = "LinSubTotal")
	private BigDecimal subTotal;

	@Column(name = "LinTotal")
	private BigDecimal total;

	@SuppressWarnings("unused")
	@Temporal(TemporalType.DATE)
	@Column(name = "LinVence")
	private Date vence;

	@SuppressWarnings("unused")
	@Column(name = "LinVinculada")
	private String vinculada = "";

	@SuppressWarnings("unused")
	@Column(name = "LocIdLin")
	private Short locIdLin;

	@SuppressWarnings("unused")
	@Column(name = "LoteIdLin")
	private String loteIdLin = "";

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({ @javax.persistence.JoinColumn(name = "MndIdLinCosto", referencedColumnName = "MndId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Moneda monedaCosto;

	@SuppressWarnings("unused")
	@Column(name = "MndIdLinCosto")
	private Short monedaCostoId;

	@NotFound(action = NotFoundAction.IGNORE)
	@OneToOne(cascade = { CascadeType.ALL }, optional = true, fetch = FetchType.EAGER)
	@PrimaryKeyJoinColumns({ @PrimaryKeyJoinColumn(name = "empId", referencedColumnName = "empId"), @PrimaryKeyJoinColumn(name = "docId", referencedColumnName = "docId"),
			@PrimaryKeyJoinColumn(name = "linId", referencedColumnName = "linId") })
	private LineaExts lineaExts;

	@SuppressWarnings("unused")
	@Column(name = "CenIdLin")
	private String cenIdLin;

	@SuppressWarnings("unused")
	@Column(name = "LinReferencia")
	private String linReferencia;

	@SuppressWarnings("unused")
	@Column(name = "LinCantidad1")
	private BigDecimal linCantidad1;

	@SuppressWarnings("unused")
	@Temporal(TemporalType.DATE)
	@Column(name = "LinEntregaFecha")
	private Date linEntregaFecha;

	@SuppressWarnings("unused")
	@Column(name = "LinEntregaReparto")
	private String linEntregaReparto = "";

	@Column(name = "ConceptoIdLin")
	private String conceptoIdLin = "";
	
	@Column(name = "RubIdlin")
	private String rubIdlin;

	public Linea() {
		this.vence = Constantes.DEFAULT_DATE;

		this.linCantidad1 = BigDecimal.ZERO;
		this.locIdLin = new Short("1");
		this.linEntregaFecha = Constantes.DEFAULT_DATE;
	}

	public LineaPK getId() {
		return this.id;
	}

	public void setId(LineaPK id) {
		this.id = id;
	}

	public void setArticulo(Articulo articulo) {
		this.articulo = articulo;
		this.articuloId = (articulo == null ? null : articulo.getId().getArtId().trim());
	}

	public void setDepositoDestino(Deposito depositoDestino) {
		this.depositoDestino = depositoDestino;
		this.depositoDestinoId = Short.valueOf(depositoDestino == null ? (short)0 : depositoDestino.getId().getDepId());
	}

	public void setDepositoOrigen(Deposito depositoOrigen) {
		this.depositoOrigen = depositoOrigen;
		this.depositoOrigenId = Short.valueOf(depositoOrigen == null ? (short)0 : depositoOrigen.getId().getDepId());
	}

	public void setIvaLin(Iva ivaLin) {
		this.ivaLin = ivaLin;
	}
	
	public Iva getIvaLin() {
		return this.ivaLin;
	}

	public void setMonedaCosto(Moneda monedaCosto) {
		this.monedaCosto = monedaCosto;
		this.monedaCostoId = (monedaCosto == null ? null : Short.valueOf(monedaCosto.getId().getMndId()));
	}

	public void setPrecioUnitario(BigDecimal precioUnitario) {
		this.precioUnitario = precioUnitario;
		this.precio2 = precioUnitario;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public BigDecimal getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(BigDecimal cantidad) {
		this.cantidad = cantidad;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public BigDecimal getDto1() {
		return this.dto1;
	}

	public void setDto1(BigDecimal dto1) {
		this.dto1 = dto1;
	}

	public BigDecimal getDto2() {
		return this.dto2;
	}

	public void setDto2(BigDecimal dto2) {
		this.dto2 = dto2;
	}

	public BigDecimal getDto3() {
		return this.dto3;
	}

	public void setDto3(BigDecimal dto3) {
		this.dto3 = dto3;
	}

	public BigDecimal getDtoImp1() {
		return this.dtoImp1;
	}

	public void setDtoImp1(BigDecimal dtoImp1) {
		this.dtoImp1 = (dtoImp1 == null ? BigDecimal.ZERO : dtoImp1);
	}

	public BigDecimal getDtoImp2() {
		return this.dtoImp2;
	}

	public void setDtoImp2(BigDecimal dtoImp2) {
		this.dtoImp2 = (dtoImp2 == null ? BigDecimal.ZERO : dtoImp2);
	}

	public BigDecimal getDtoImp3() {
		return this.dtoImp3;
	}

	public void setDtoImp3(BigDecimal dtoImp3) {
		this.dtoImp3 = (dtoImp3 == null ? BigDecimal.ZERO : dtoImp3);
	}

	public Date getFecha() {
		return this.fecha;
	}

	public Integer getItems() {
		return this.items;
	}

	public void setItems(Integer items) {
		this.items = items;
	}

	public BigDecimal getIva() {
		return this.linIva;
	}

	public void setIva(BigDecimal iva) {
		this.linIva = iva;
	}

	public BigDecimal getPrecio() {
		return this.precio;
	}

	public void setPrecio(BigDecimal precio) {
		this.precio = precio;
	}

	public BigDecimal getPrecioUnitario() {
		return this.precioUnitario;
	}

	public BigDecimal getSubTotal() {
		return this.subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getTotal() {
		return this.total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public void setLoteIdLin(String loteIdLin) {
		this.loteIdLin = loteIdLin;
	}

	public Articulo getArticulo() {
		return this.articulo;
	}

	public Deposito getDepositoDestino() {
		return this.depositoDestino;
	}

	public Deposito getDepositoOrigen() {
		return this.depositoOrigen;
	}

	public Moneda getMonedaCosto() {
		return this.monedaCosto;
	}

	public Moneda getMonedaDocumento() {
		return documento != null ? documento.getMoneda() : null;
	}

	public BigDecimal getDescuento() {
		return this.dto1 == null ? BigDecimal.ZERO : this.dto1;
	}

	public void setDescuento(BigDecimal value) {
		setDto1(value);
	}

	public void setPrecioDistribuidor(BigDecimal precioDistribuidor) {
		if (precioDistribuidor == null) {
			if (lineaExts != null) {
				lineaExts.setPrecioDistribuidor(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setPrecioDistribuidor(precioDistribuidor);
		}
	}

	public BigDecimal getPrecioDistribuidor() {
		return lineaExts != null ? lineaExts.getPrecioDistribuidor() : null;
	}
	
	public void setCoeficienteImp(BigDecimal coeficienteImp) {
		if (coeficienteImp == null) {
			if (lineaExts != null) {
				lineaExts.setCoeficienteImp(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setCoeficienteImp(coeficienteImp);
		}
	}

	public BigDecimal getCoeficienteImp() {
		return lineaExts != null ? lineaExts.getCoeficienteImp() : null;
	}

	public void setContactoId(String contactoId) {
		if (contactoId == null) {
			if (lineaExts != null) {
				lineaExts.setContactoId(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setContactoId(contactoId);
		}
	}
	
	public String getContactoId() {
		return lineaExts != null ? lineaExts.getContactoId() : null;
	}
	
	public void setDocRefId(Integer docRefId) {
		if (docRefId == null) {
			if (lineaExts != null) {
				lineaExts.setDocRefId(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setDocRefId(docRefId);
		}
	}
	
	public Integer getDocRefId() {
		return lineaExts != null ? lineaExts.getDocRefId() : null;
	}
	
	public void setAfilador(String afilador) {
		if (afilador == null) {
			if (lineaExts != null) {
				lineaExts.setAfilador(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setAfilador(afilador);
		}
	}
	
	public String getAfilador() {
		return lineaExts != null ? lineaExts.getAfilador() : null;
	}

	public String getMarca() {
		return lineaExts != null ? lineaExts.getMarca() : null;
	}
	
	public void setMarca(String marca) {
		if (marca == null) {
			if (lineaExts != null) {
				lineaExts.setMarca(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setMarca(marca);
		}
	}
	
	public BigDecimal getDiametro() {
		return lineaExts != null ? lineaExts.getDiametro() : null;
	}
	
	public void setDiametro(BigDecimal diametro) {
		if (diametro == null) {
			if (lineaExts != null) {
				lineaExts.setDiametro(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setDiametro(diametro);
		}
	}

	
	public BigDecimal getRotos() {
		return lineaExts != null ? lineaExts.getRotos() : null;
	}
	
	public void setRotos(BigDecimal rotos) {
		if (rotos == null) {
			if (lineaExts != null) {
				lineaExts.setRotos(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setRotos(rotos);
		}
	}

	public BigDecimal getCascados() {
		return lineaExts != null ? lineaExts.getCascados() : null;
	}
	
	public void setCascados(BigDecimal cascados) {
		if (cascados == null) {
			if (lineaExts != null) {
				lineaExts.setCascados(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setCascados(cascados);
		}
	}
	
	public BigDecimal getLinDto1() {
		return lineaExts != null ? lineaExts.getDcto1() : null;
	}
	
	public void setLinDto1(BigDecimal dto) {
		if (dto == null) {
			if (lineaExts != null) {
				lineaExts.setDcto1(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setDcto1(dto);
		}
	}

	public BigDecimal getLinDto2() {
		return lineaExts != null ? lineaExts.getDcto2() : null;
	}
	
	public void setLinDto2(BigDecimal dto) {
		if (dto == null) {
			if (lineaExts != null) {
				lineaExts.setDcto2(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setDcto2(dto);
		}
	}
	
	public BigDecimal getLinDto3() {
		return lineaExts != null ? lineaExts.getDcto3() : null;
	}
	
	public void setLinDto3(BigDecimal dto) {
		if (dto == null) {
			if (lineaExts != null) {
				lineaExts.setDcto3(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setDcto3(dto);
		}
	}
	
	public BigDecimal getLinDto4() {
		return lineaExts != null ? lineaExts.getDcto4() : null;
	}

	public void setLinDto4(BigDecimal dto) {
		if (dto == null) {
			if (lineaExts != null) {
				lineaExts.setDcto4(null);
			} 
		} else {
			if (lineaExts == null) {
				lineaExts = new LineaExts();
				lineaExts.setId(id);
			}
			lineaExts.setDcto4(dto);
		}
	}
	
	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getConceptoIdLin() {
		return conceptoIdLin;
	}

	public void setConceptoIdLin(String conceptoIdLin) {
		this.conceptoIdLin = conceptoIdLin;
	}

	public Short getIvaId() {
		return ivaId;
	}

	public void setIvaId(Short ivaId) {
		this.ivaId = ivaId;
	}

	public String getArticuloId() {
		return articuloId;
	}

	public String getRubIdlin() {
		return rubIdlin;
	}

	public void setRubIdlin(String rubIdLin) {
		this.rubIdlin = rubIdLin;
	}
	
	public LineaExts getLineaExts() {
		return lineaExts;
	}

	public void setLineaExts(LineaExts lineaExts) {
		this.lineaExts = lineaExts;
	}

	
	

}