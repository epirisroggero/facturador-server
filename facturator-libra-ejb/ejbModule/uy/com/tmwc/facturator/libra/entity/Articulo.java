package uy.com.tmwc.facturator.libra.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SecondaryTable;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import uy.com.tmwc.facturator.dto.ICodigoNombre;
import uy.com.tmwc.facturator.libra.util.BooleanDozerConverter;
import uy.com.tmwc.utils.orm.CatalogEntity;

@Entity
@Table(name = "articulos")
@CatalogEntity(useNamedQuery = true)
@SecondaryTable(name = "lfx_articulos")
public class Articulo extends PersistentEntity<ArticuloPK> implements
		Serializable, HasId<ArticuloPK>, ICodigoNombre {
	private static final long serialVersionUID = 1L;

	public Articulo() {
	}

	public Articulo(String codigo, String nombre, String familiaId,
			String marcaId, String codigoOrigen, String prvId, String activo, 
			Familia familia, Marca marca, Proveedor proveedor) {
		setCodigo(codigo);

		this.nombre = nombre;
		this.familiaId = familiaId;
		this.marcaId = marcaId;
		this.codigoOrigen = codigoOrigen;
		this.prvIdArt = prvId;
		this.activo = activo != null ? activo : "N";
		this.familia = familia;
		this.marca = marca;
		
		this.proveedor = proveedor;
	}

	@EmbeddedId
	private ArticuloPK id;

	@Column(table = "lfx_articulos", name = "ArtNotasInt", length = 4096)
	private String artNotasInt;
	
	@Column(table = "lfx_articulos", name = "ArtEsCuponera")
	private String artEsCuponera;

	@SuppressWarnings("unused")
	@Column(name = "ArtId", insertable = false, updatable = false)
	private String codigo;

	@Column(name = "ArtAbrevia")
	private String abrevia;

	@Column(name = "ArtActivo")
	private String activo;

	@Temporal(TemporalType.DATE)
	@Column(name = "ArtAlta")
	private Date alta;

	@Lob
	@Column(name = "ArtBlob")
	private byte[] blob;

	@Column(name = "ArtBlobExt")
	private String blobExt;

	@Column(name = "ArtCodigoOrigen")
	private String codigoOrigen;

	@Column(name = "ArtCosto")
	private BigDecimal costo;

	@Temporal(TemporalType.DATE)
	@Column(name = "ArtCostoFecha")
	private Date fechaCosto;

	@Column(name = "ArtCostoUtilidad")
	private BigDecimal costoUtilidad;

	@Column(name = "ArtIdAbrevia")
	private String idAbrevia;

	@Column(name = "ArtInventario")
	private String inventario;

	@Column(name = "ArtListaPrecios")
	private String listaPrecios;

	@Column(name = "ArtLotes")
	private String lotes;

	@Column(name = "ArtNombre")
	private String nombre;

	@Lob
	@Column(name = "ArtNotas")
	private String notas;

	@Column(name = "ArtPartidaId")
	private Integer partidaId;

	@Column(name = "ArtPuntos")
	private String puntos;

	@Column(name = "ArtRanking")
	private BigDecimal ranking;

	@Column(name = "ArtVence")
	private String vence;

	@Column(name = "ArtWeb")
	private String web;

	@Column(name = "MarcaId")
	private String marcaId;
	
	@Column(name = "CategArtId")
	private String categArtId;
	
	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "FamiliaId", referencedColumnName = "FamiliaId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Familia familia;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "MarcaId", referencedColumnName = "MarcaId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Marca marca;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "CategArtId", referencedColumnName = "CATEGARTID", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EMPID", insertable = false, updatable = false) })
	private Categoriasarticulo categArt;
	

	@Column(name = "FamiliaId")
	private String familiaId;

	@Column(name = "ArtEmpaque")
	private BigDecimal artEmpaque;

	@Column(name = "ArtDobleCantidad")
	private String artDobleCantidad;

	@Column(name = "UnidadId2")
	private String unidadId2;

	@Column(name = "ArtGXPortal")
	private String artGXPortal;

	@Column(name = "ArtCodigoBarras")
	private String artCodigoBarras;

	@Column(name = "ConceptoIdArt")
	private String conceptoIdArt;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "IvaIdArt", referencedColumnName = "IvaId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Iva iva;

	@Column(name = "IvaIdArt")
	private Short ivaIdArt;


	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "MndIdArtCosto", referencedColumnName = "MndId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Moneda monedaCosto;

	@Column(name = "MndIdArtCosto")
	private Short mndIdArtCosto;

	@NotFound(action = NotFoundAction.IGNORE)
	@ManyToOne
	@JoinColumns({
			@javax.persistence.JoinColumn(name = "PrvIdArt", referencedColumnName = "PrvId", insertable = false, updatable = false),
			@javax.persistence.JoinColumn(name = "EmpId", referencedColumnName = "EmpId", insertable = false, updatable = false) })
	private Proveedor proveedor;

	@Column(name = "PrvIdArt")
	private String prvIdArt;

	@Column(name = "RubIdArtCompras")
	private String rubIdArtCompras;

	@Column(name = "RubIdArtProd")
	private String rubIdArtProd;

	@Column(name = "RubIdArtVentas")
	private String rubIdArtVentas;

	@Column(name = "TextoIdArt")
	private String textoIdArt;

	@Column(name = "UnidadId")
	private String unidadId;

	@CollectionOfElements()
	@JoinTable(name = "articulos6", joinColumns = { @JoinColumn(name = "EmpId"), @JoinColumn(name = "ArtId") })
	@org.hibernate.annotations.MapKey(columns = @Column(name = "CampoIdArt"))
	@Column(name = "ArtCampoValor")
	private Map<String, String> adicionales;
	
	
	public void provideId(String empId, String artId) {
		this.id = new ArticuloPK(empId, artId);
	}

	public Iva getIva() {
		return this.iva;
	}

	public void setIva(Iva iva) {
		this.iva = iva;
		this.ivaIdArt = (iva == null ? null : Short.valueOf(iva.getId()
				.getIvaId()));
	}

	public ArticuloPK getId() {
		return this.id;
	}

	public void setId(ArticuloPK id) {
		this.id = id;
	}

	public String getAbrevia() {
		return this.abrevia;
	}

	public void setAbrevia(String abrevia) {
		this.abrevia = abrevia;
	}

	public Date getAlta() {
		return this.alta;
	}

	public void setAlta(Date alta) {
		this.alta = alta;
	}

	public byte[] getBlob() {
		return this.blob;
	}

	public void setBlob(byte[] blob) {
		this.blob = blob;
	}

	public String getBlobExt() {
		return this.blobExt;
	}

	public void setBlobExt(String blobExt) {
		this.blobExt = blobExt;
	}

	public String getCodigoOrigen() {
		return this.codigoOrigen;
	}

	public void setCodigoOrigen(String codigoOrigen) {
		this.codigoOrigen = codigoOrigen;
	}

	public BigDecimal getCostoUtilidad() {
		return this.costoUtilidad;
	}

	public void setCostoUtilidad(BigDecimal costoUtilidad) {
		this.costoUtilidad = costoUtilidad;
	}

	public String getIdAbrevia() {
		return this.idAbrevia;
	}

	public void setIdAbrevia(String idAbrevia) {
		this.idAbrevia = idAbrevia;
	}

	public boolean getInventario() {
		return BooleanDozerConverter.toBooleanValue(inventario);
	}

	public void setInventario(boolean inventario) {
		this.inventario = BooleanDozerConverter.toString(inventario);
	}

	public boolean getActivo() {
		return BooleanDozerConverter.toBooleanValue(activo);
	}

	public void setActivo(boolean activo) {
		this.activo = BooleanDozerConverter.toString(activo);
	}

	public boolean getListaPrecios() {
		return BooleanDozerConverter.toBooleanValue(listaPrecios);
	}

	public void setListaPrecios(boolean listaPrecios) {
		this.listaPrecios = BooleanDozerConverter.toString(listaPrecios);
	}

	public boolean getLotes() {
		return BooleanDozerConverter.toBooleanValue(lotes);
	}

	public void setLotes(boolean lotes) {
		this.lotes = BooleanDozerConverter.toString(lotes);
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNotas() {
		return this.notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getPuntos() {
		return this.puntos;
	}

	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

	public BigDecimal getRanking() {
		return this.ranking;
	}

	public void setRanking(BigDecimal ranking) {
		this.ranking = ranking;
	}

	public String getVence() {
		return this.vence;
	}

	public void setVence(String vence) {
		this.vence = vence;
	}

	public String getWeb() {
		return this.web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getCategArtId() {
		return this.categArtId;
	}

	public void setCategArtId(String categArtId) {
		this.categArtId = categArtId;
	}

	public String getMarcaId() {
		return this.marcaId;
	}

	public void setMarcaId(String marcaId) {
		this.marcaId = marcaId;
	}

	public String getRubIdArtCompras() {
		return this.rubIdArtCompras;
	}

	public void setRubIdArtCompras(String rubIdArtCompras) {
		this.rubIdArtCompras = rubIdArtCompras;
	}

	public String getRubIdArtProd() {
		return this.rubIdArtProd;
	}

	public void setRubIdArtProd(String rubIdArtProd) {
		this.rubIdArtProd = rubIdArtProd;
	}

	public String getRubIdArtVentas() {
		return this.rubIdArtVentas;
	}

	public void setRubIdArtVentas(String rubIdArtVentas) {
		this.rubIdArtVentas = rubIdArtVentas;
	}

	public String getTextoIdArt() {
		return this.textoIdArt;
	}

	public void setTextoIdArt(String textoIdArt) {
		this.textoIdArt = textoIdArt;
	}

	public String getUnidadId() {
		return this.unidadId;
	}

	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	public Integer getPartidaId() {
		return this.partidaId;
	}

	public void setPartidaId(Integer PartidaId) {
		this.partidaId = PartidaId;
	}

	public Moneda getMonedaCosto() {
		return this.monedaCosto;
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public Date getFechaCosto() {
		return this.fechaCosto;
	}

	public void setFechaCosto(Date fechaCosto) {
		this.fechaCosto = fechaCosto;
	}

	public Proveedor getProveedor() {
		return this.proveedor;
	}

	public Familia getFamilia() {
		return this.familia;
	}

	public void setCodigo(String value) {
		this.codigo = value;
		if (this.id == null) {
			this.id = new ArticuloPK();
		}
		this.id.setArtId(value);
	}

	public String getCodigo() {
		return this.id.getArtId();
	}

	public String getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
	}

	public Short getIvaIdArt() {
		return ivaIdArt;
	}

	public void setIvaIdArt(Short ivaIdArt) {
		this.ivaIdArt = ivaIdArt;
	}

	public Short getMndIdArtCosto() {
		return mndIdArtCosto;
	}

	public void setMndIdArtCosto(Short mndIdArtCosto) {
		this.mndIdArtCosto = mndIdArtCosto;
	}

	public String getPrvIdArt() {
		return prvIdArt;
	}

	public void setPrvIdArt(String prvIdArt) {
		this.prvIdArt = prvIdArt;
	}

	public BigDecimal getArtEmpaque() {
		return artEmpaque;
	}

	public void setArtEmpaque(BigDecimal artEmpaque) {
		this.artEmpaque = artEmpaque;
	}

	public boolean getArtDobleCantidad() {
		return BooleanDozerConverter.toBooleanValue(artDobleCantidad);
	}

	public void setArtDobleCantidad(boolean artDobleCantidad) {
		this.artDobleCantidad = BooleanDozerConverter
				.toString(artDobleCantidad);
	}

	public String getUnidadId2() {
		return unidadId2;
	}

	public void setUnidadId2(String unidadId2) {
		this.unidadId2 = unidadId2;
	}

	public boolean getArtGXPortal() {
		return BooleanDozerConverter.toBooleanValue(artGXPortal);
	}

	public void setArtGXPortal(boolean artGXPortal) {
		this.artGXPortal = BooleanDozerConverter.toString(artGXPortal);
	}
	
	public boolean getArtEsCuponera() {
		return BooleanDozerConverter.toBooleanValue(artEsCuponera);
	}

	public void setArtEsCuponera(boolean artEsCuponera) {
		this.artEsCuponera = BooleanDozerConverter.toString(artEsCuponera);
	}


	public String getArtCodigoBarras() {
		return artCodigoBarras;
	}

	public void setArtCodigoBarras(String artCodigoBarras) {
		this.artCodigoBarras = artCodigoBarras;
	}

	public String getConceptoIdArt() {
		return conceptoIdArt;
	}

	public void setConceptoIdArt(String conceptoIdArt) {
		this.conceptoIdArt = conceptoIdArt;
	}

	public Map<String, String> getAdicionales() {
		return adicionales;
	}

	public void setAdicionales(Map<String, String> adicionales) {
		this.adicionales = adicionales;
	}	
	
	private String getAdicional(String codigo) {
		if (adicionales != null) {
			return adicionales.get(codigo);
		} else {
			return null;
		}
	}

	private void setAdicional(String codigo, String valor) {
		if (adicionales != null) {
			if (adicionales.containsKey(codigo)) {
				adicionales.remove(codigo);
			}
			adicionales.put(codigo, valor);
		}
	}
	
	
	public String getNotaInterna() {
		return getAdicional("115");
	}

	public void setNotaInterna(String value) {
		setAdicional("115", value);
	}

	public String getVideoYoutube() {
		return getAdicional("103");
	}

	public void setVideoYoutube(String value) {
		setAdicional("103", value);
	}

	public String getVideoYoutube2() {
		return getAdicional("113");
	}

	public void setVideoYoutube2(String value) {
		setAdicional("113", value);
	}

	public String getVideoYoutube3() {
		return getAdicional("114");
	}

	public void setVideoYoutube3(String value) {
		setAdicional("114", value);
	}

	public String getPeso() {
		return getAdicional("105");
	}

	public void setPeso(String value) {
		setAdicional("105", value);
	}

	public String getArtNotasInt() {
		return artNotasInt;
	}

	public void setArtNotasInt(String artNotasInt) {
		this.artNotasInt = artNotasInt;
	}

	public Marca getMarca() {
		return marca;
	}

	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	
	public Categoriasarticulo getCategArt() {
		return categArt;
	}

	public void setCategArt(Categoriasarticulo categArt) {
		this.categArt = categArt;
	}


}