package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;
import java.util.Date;

public class Articulo extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;
	
	private BigDecimal costo;
	private Moneda monedaCosto;
	private Date fechaCosto;
	
	private boolean inventario;
	private boolean listaPrecios;
	private boolean activo;
	private boolean lotes;
	private boolean artGXPortal;
	private boolean artDobleCantidad;
	
	private String familiaId; 
	private String marcaId;

	private Proveedor proveedor;
	private Iva iva;
	private FamiliaArticulos familia;
	private CategoriasArticulos categArt;
	private Marca marca;
	
	private byte[] blob;
	private String blobExt;

	private String notas;
	private String puntos;
	private String vence;
	private String web;
	private String categArtId;
	private Short ivaIdArt;
	
	private String prvIdArt;
	private String rubIdArtCompras;
	private String rubIdArtProd;
	private String rubIdArtVentas;
	private String textoIdArt;
	private String unidadId;
	private String unidadId2;
	private String artCodigoBarras;

	private String abrevia;
	private Date alta;
	private String codigoOrigen;
	private BigDecimal costoUtilidad;
	private String idAbrevia;
	private Integer partidaId;
	private BigDecimal ranking;	
	private String GTCIdArt;
	private Short mndIdArtCosto;

	private String videoYoutube;
	private String videoYoutube2;
	private String videoYoutube3;
	private String peso;
	private String notaInterna;
	
	private String artNotasInt;		
	private String conceptoIdArt;
	
	public Iva getIva() {
		return this.iva;
	}

	public void setIva(Iva iva) {
		this.iva = iva;
	}

	public BigDecimal getTasaIva() {
		return this.iva == null ? BigDecimal.ZERO : this.iva.getTasa();
	}

	public BigDecimal getCosto() {
		return this.costo;
	}

	public void setCosto(BigDecimal costo) {
		this.costo = costo;
	}

	public Moneda getMonedaCosto() {
		return this.monedaCosto;
	}

	public void setMonedaCosto(Moneda monedaCosto) {
		this.monedaCosto = monedaCosto;
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

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public FamiliaArticulos getFamilia() {
		return this.familia;
	}

	public void setFamilia(FamiliaArticulos familia) {
		this.familia = familia;
	}

	public boolean getInventario() {
		return inventario;
	}

	public void setInventario(boolean inventario) {
		this.inventario = inventario;
	}

	public String getFamiliaId() {
		return familiaId;
	}

	public void setFamiliaId(String familiaId) {
		this.familiaId = familiaId;
	}

	public boolean getActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	public String getNotas() {
		return notas;
	}

	public void setNotas(String notas) {
		this.notas = notas;
	}

	public String getPuntos() {
		return puntos;
	}

	public void setPuntos(String puntos) {
		this.puntos = puntos;
	}

	public String getVence() {
		return vence;
	}

	public void setVence(String vence) {
		this.vence = vence;
	}

	public String getWeb() {
		return web;
	}

	public void setWeb(String web) {
		this.web = web;
	}

	public String getCategArtId() {
		return categArtId;
	}

	public void setCategArtId(String categArtId) {
		this.categArtId = categArtId;
	}

	public Short getIvaIdArt() {
		return ivaIdArt;
	}

	public void setIvaIdArt(Short ivaIdArt) {
		this.ivaIdArt = ivaIdArt;
	}

	public String getMarcaId() {
		return marcaId;
	}

	public void setMarcaId(String marcaId) {
		this.marcaId = marcaId;
	}

	public String getPrvIdArt() {
		return prvIdArt;
	}

	public void setPrvIdArt(String prvIdArt) {
		this.prvIdArt = prvIdArt;
	}

	public String getRubIdArtCompras() {
		return rubIdArtCompras;
	}

	public void setRubIdArtCompras(String rubIdArtCompras) {
		this.rubIdArtCompras = rubIdArtCompras;
	}

	public String getRubIdArtProd() {
		return rubIdArtProd;
	}

	public void setRubIdArtProd(String rubIdArtProd) {
		this.rubIdArtProd = rubIdArtProd;
	}

	public String getRubIdArtVentas() {
		return rubIdArtVentas;
	}

	public void setRubIdArtVentas(String rubIdArtVentas) {
		this.rubIdArtVentas = rubIdArtVentas;
	}

	public String getTextoIdArt() {
		return textoIdArt;
	}

	public void setTextoIdArt(String textoIdArt) {
		this.textoIdArt = textoIdArt;
	}

	public String getUnidadId() {
		return unidadId;
	}

	public void setUnidadId(String unidadId) {
		this.unidadId = unidadId;
	}

	public byte[] getBlob() {
		return blob;
	}

	public void setBlob(byte[] blob) {
		this.blob = blob;
	}

	public String getBlobExt() {
		return blobExt;
	}

	public void setBlobExt(String blobExt) {
		this.blobExt = blobExt;
	}

	public String getAbrevia() {
		return abrevia;
	}

	public void setAbrevia(String abrevia) {
		this.abrevia = abrevia;
	}

	public Date getAlta() {
		return alta;
	}

	public void setAlta(Date alta) {
		this.alta = alta;
	}

	public String getCodigoOrigen() {
		return codigoOrigen;
	}

	public void setCodigoOrigen(String codigoOrigen) {
		this.codigoOrigen = codigoOrigen;
	}

	public BigDecimal getCostoUtilidad() {
		return costoUtilidad;
	}

	public void setCostoUtilidad(BigDecimal costoUtilidad) {
		this.costoUtilidad = costoUtilidad;
	}

	public String getIdAbrevia() {
		return idAbrevia;
	}

	public void setIdAbrevia(String idAbrevia) {
		this.idAbrevia = idAbrevia;
	}

	public boolean getLotes() {
		return lotes;
	}

	public void setLotes(boolean lotes) {
		this.lotes = lotes;
	}

	public Integer getPartidaId() {
		return partidaId;
	}

	public void setPartidaId(Integer partidaId) {
		this.partidaId = partidaId;
	}

	public BigDecimal getRanking() {
		return ranking;
	}

	public void setRanking(BigDecimal ranking) {
		this.ranking = ranking;
	}

	public String getGTCIdArt() {
		return GTCIdArt;
	}

	public void setGTCIdArt(String gTCIdArt) {
		GTCIdArt = gTCIdArt;
	}

	public Short getMndIdArtCosto() {
		return mndIdArtCosto;
	}

	public void setMndIdArtCosto(Short mndIdArtCosto) {
		this.mndIdArtCosto = mndIdArtCosto;
	}

	public boolean isArtGXPortal() {
		return artGXPortal;
	}

	public void setArtGXPortal(boolean artGXPortal) {
		this.artGXPortal = artGXPortal;
	}
	
	public boolean isListaPrecios() {
		return listaPrecios;
	}

	public void setListaPrecios(boolean listaPrecios) {
		this.listaPrecios = listaPrecios;
	}

	public String getVideoYoutube() {
		return videoYoutube;
	}

	public void setVideoYoutube(String videoYoutube) {
		this.videoYoutube = videoYoutube;
	}

	public String getPeso() {
		return peso;
	}

	public void setPeso(String peso) {
		this.peso = peso;
	}

	public String getVideoYoutube2() {
		return videoYoutube2;
	}

	public void setVideoYoutube2(String videoYoutube2) {
		this.videoYoutube2 = videoYoutube2;
	}

	public String getVideoYoutube3() {
		return videoYoutube3;
	}

	public void setVideoYoutube3(String videoYoutube3) {
		this.videoYoutube3 = videoYoutube3;
	}

	public String getNotaInterna() {
		return notaInterna;
	}

	public void setNotaInterna(String notaInterna) {
		this.notaInterna = notaInterna;
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

	public boolean isArtDobleCantidad() {
		return artDobleCantidad;
	}

	public void setArtDobleCantidad(boolean artDobleCantidad) {
		this.artDobleCantidad = artDobleCantidad;
	}

	public String getUnidadId2() {
		return unidadId2;
	}

	public void setUnidadId2(String unidadId2) {
		this.unidadId2 = unidadId2;
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

	public CategoriasArticulos getCategArt() {
		return categArt;
	}

	public void setCategArt(CategoriasArticulos categArt) {
		this.categArt = categArt;
	}



}