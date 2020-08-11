package uy.com.tmwc.facturator.libra.ejb;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.lang.StringUtils;
import org.dozer.DozerBeanMapper;
import org.hibernate.Hibernate;

import uy.com.tmwc.facturator.dto.DocumentoDTO;
import uy.com.tmwc.facturator.dto.DocumentoQuery;
import uy.com.tmwc.facturator.dto.ParticipacionEnCobranza;
import uy.com.tmwc.facturator.dto.StockActualDTO;
import uy.com.tmwc.facturator.entity.ArticuloCompraVentaCosto;
import uy.com.tmwc.facturator.entity.ArticuloPrecio;
import uy.com.tmwc.facturator.entity.ArticuloPrecioFabricaCosto;
import uy.com.tmwc.facturator.entity.Auditoria;
import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.entity.CotizacionesMonedas;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.LineaDocumento;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.ParticipacionAfilador;
import uy.com.tmwc.facturator.entity.PermisosDocumentoUsuario;
import uy.com.tmwc.facturator.entity.ResumenEntrega;
import uy.com.tmwc.facturator.entity.SerieNumero;
import uy.com.tmwc.facturator.entity.StockActual;
import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.entity.Vendedor;
import uy.com.tmwc.facturator.entity.VendedoresUsuario;
import uy.com.tmwc.facturator.entity.VinculoDocumentos;
import uy.com.tmwc.facturator.libra.entity.Articulo;
import uy.com.tmwc.facturator.libra.entity.ArticuloPK;
import uy.com.tmwc.facturator.libra.entity.ComprobantePK;
import uy.com.tmwc.facturator.libra.entity.Deposito;
import uy.com.tmwc.facturator.libra.entity.DocumentoPK;
import uy.com.tmwc.facturator.libra.entity.DummyDocumento;
import uy.com.tmwc.facturator.libra.entity.Fanfold;
import uy.com.tmwc.facturator.libra.entity.FanfoldPK;
import uy.com.tmwc.facturator.libra.entity.IdTablaGen;
import uy.com.tmwc.facturator.libra.entity.Linea;
import uy.com.tmwc.facturator.libra.entity.Numerador;
import uy.com.tmwc.facturator.libra.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.libra.entity.ProveedorPK;
import uy.com.tmwc.facturator.libra.entity.Stockactual;
import uy.com.tmwc.facturator.libra.entity.StockactualPK;
import uy.com.tmwc.facturator.libra.entity.Vinculosdoc;
import uy.com.tmwc.facturator.libra.util.DozerMappingsService;
import uy.com.tmwc.facturator.libra.util.JPAUtils;
import uy.com.tmwc.facturator.mapper.Mapper;
import uy.com.tmwc.facturator.rapi.ArticulosService;
import uy.com.tmwc.facturator.rapi.AuditoriaService;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.rapi.MonedasCotizacionesService;
import uy.com.tmwc.facturator.rapi.PermisosException;
import uy.com.tmwc.facturator.rapi.RUTINA_MODIFCOSTO_ENUM;
import uy.com.tmwc.facturator.rapi.UsuariosService;
import uy.com.tmwc.facturator.spi.DocumentoDAOService;
import uy.com.tmwc.facturator.utils.DateUtils;
import uy.com.tmwc.facturator.utils.MappingUtils;

@Stateless
@Local({ DocumentoDAOService.class })
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class DocumentoDAOServiceImpl extends ServiceBase implements DocumentoDAOService {

	@PersistenceContext
	EntityManager em;

	@EJB
	DozerMappingsService mapService;

	@EJB
	CatalogService catalogService;

	@EJB
	ArticulosService artService;

	@EJB
	UsuariosService usuariosService;

	@EJB
	AuditoriaService auditoriaService;

	@EJB
	MonedasCotizacionesService tipoCambioService;

	private static final int SCALE = 2;

	private static final String ULTIMOS_FACTURADOS_SUBQUERY_CONCEPTO = "FROM Documento d join d.moneda m join d.cliente c join d.comprobante cmp left join d.docruc r left join d.lineas l left join c.vendedor v left join v.vendedoresUsuario vu left join vu.usuario u "
			+ "WHERE d.comprobante.tipo IN (1,2,3,4,32) "
			+ "AND d.id.empId = :empId "
			+ "AND (c.id.cliId = :cliente OR :cliente IS NULL) "
			+ "AND (m.codigo = :moneda OR :moneda IS NULL) "
			+ "AND (d.fecha >= :fechaDesde OR :fechaDesde IS NULL) "
			+ "AND (d.fecha <= :fechaHasta OR :fechaHasta IS NULL) "
			+ "AND (d.numero = :numero OR :numero IS NULL) AND (d.serie = :serie OR :serie IS NULL) "
			+ "AND (d.pendiente = :pendiente OR d.pendiente = '' OR :pendiente is null OR d.comprobante.tipo != 32) "
			+ "AND (d.emitido = :emitido OR d.emitido is null OR d.emitido = '' OR :emitido is null OR d.comprobante.tipo = 32) "
			+ "AND (d.saldo > 0 OR :tieneSaldo is NULL) "
			+ "AND (cmp.id.cmpid = :tipoComprobante OR :tipoComprobante IS NULL) "
			+ "AND (d.estado is null or d.estado != 'A') " + "AND (:concepto IS NULL OR l.concepto LIKE :concepto)";

	private static final String ULTIMOS_FACTURADOS_SUBQUERY = "FROM Documento d join d.moneda m join d.cliente c join d.comprobante cmp left join d.docruc r left join c.vendedor v left join v.vendedoresUsuario vu left join vu.usuario u "
			+ "WHERE d.comprobante.tipo IN (1,2,3,4,32) "
			+ "AND d.id.empId = :empId "
			+ "AND (c.id.cliId = :cliente OR :cliente IS NULL) "
			+ "AND (m.codigo = :moneda OR :moneda IS NULL) "
			+ "AND (d.fecha >= :fechaDesde OR :fechaDesde IS NULL) "
			+ "AND (d.fecha <= :fechaHasta OR :fechaHasta IS NULL) "
			+ "AND (d.numero = :numero OR :numero IS NULL) AND (d.serie = :serie OR :serie IS NULL) "
			+ "AND (d.pendiente = :pendiente OR d.pendiente = '' OR :pendiente IS null OR d.comprobante.tipo != 32) "
			+ "AND (d.emitido = :emitido OR :emitido is null OR d.emitido is null OR d.emitido = '' OR d.comprobante.tipo = 32) "
			+ "AND (d.saldo > 0 OR :tieneSaldo is NULL) "
			+ "AND (cmp.id.cmpid = :tipoComprobante OR :tipoComprobante IS NULL) "
			+ "AND (d.estado is null or d.estado != 'A') ";

	private static final String ULTIMOS_FACTURADOS_SUBQUERY_ARTICULO = "FROM Documento d join d.moneda m join d.cliente c join d.comprobante cmp left join d.docruc r left join d.lineas l left join c.vendedor v left join v.vendedoresUsuario vu left join vu.usuario u "
			+ "WHERE d.comprobante.tipo IN (1,2,3,4,32) "
			+ "AND d.id.empId = :empId "
			+ "AND (c.id.cliId = :cliente OR :cliente IS NULL) "
			+ "AND (m.codigo = :moneda OR :moneda IS NULL) "
			+ "AND (d.fecha >= :fechaDesde OR :fechaDesde IS NULL) "
			+ "AND (d.fecha <= :fechaHasta OR :fechaHasta IS NULL) "
			+ "AND (d.numero = :numero OR :numero IS NULL) AND (d.serie = :serie OR :serie IS NULL) "
			+ "AND (d.pendiente = :pendiente OR :pendiente IS null OR d.comprobante.tipo != 32) "
			+ "AND (d.emitido = :emitido OR d.emitido is null OR d.emitido = '' OR :emitido is null OR d.comprobante.tipo = 32) "
			+ "AND (d.estado is null OR d.estado != 'A') "
			+ "AND (cmp.id.cmpid = :tipoComprobante OR :tipoComprobante IS NULL) "
			+ "AND (:concepto IS NULL OR :concepto LIKE l.concepto) "
			+ "AND (m.codigo = :moneda OR :moneda IS NULL) "
			+ "AND (d.saldo > 0 OR :tieneSaldo is NULL) " + "AND (:articulo IS NULL OR l.articuloId = :articulo) ";

	private static final String ULTIMAS_SOLICITUDES_SUBQUERY = "FROM "
			+ "Documento d join d.moneda m join d.proveedor p join d.comprobante cmp left join d.docruc r "
			+ "WHERE d.comprobante.tipo IN (21,22,23,31) "
			+ "AND (d.id.empId = :empId) AND (p.id.prvId = :proveedor OR :proveedor IS NULL) "
			+ "AND (cmp.id.cmpid NOT IN ('110','111','116')) "
			+ "AND (d.fecha >= :fechaDesde OR :fechaDesde IS NULL) AND (d.fecha <= :fechaHasta OR :fechaHasta IS NULL) "
			+ "AND (d.numero = :numero OR :numero IS NULL) AND (d.serie = :serie OR :serie IS NULL) "
			+ "AND (d.pendiente = :pendiente OR :pendiente is null) "
			+ "AND (cmp.id.cmpid = :tipoComprobante OR :tipoComprobante IS NULL) "
			+ "AND (d.estado is null or d.estado != 'A') ";

	private static final String ULTIMAS_SOLICITUDES_SUBQUERY_CONCEPTO = "FROM Documento d join d.moneda m join d.proveedor p join d.comprobante cmp left join d.docruc r left join d.lineas l "
			+ "WHERE d.comprobante.tipo IN (5,21,22,23,31) AND (d.id.empId = :empId) AND (p.id.prvId = :proveedor OR :proveedor IS NULL) "
			+ "AND (d.fecha >= :fechaDesde OR :fechaDesde IS NULL) "
			+ "AND (cmp.id.cmpid NOT IN ('110','111','116')) "
			+ "AND (d.fecha <= :fechaHasta OR :fechaHasta IS NULL) AND (d.numero = :numero OR :numero IS NULL) "
			+ "AND (d.serie = :serie OR :serie IS NULL) "
			+ "AND (d.pendiente = :pendiente OR :pendiente is null) "
			+ "AND (cmp.id.cmpid = :tipoComprobante OR :tipoComprobante IS NULL) "
			+ "AND (d.estado is null or d.estado != 'A') " + "AND (:concepto IS NULL OR l.concepto LIKE :concepto) ";

	private static final String ULTIMOS_GASTOS_SUBQUERY = "FROM Documento d join d.moneda m join d.proveedor p join d.comprobante cmp left join d.docruc r "
			+ "WHERE  d.comprobante.tipo IN (21,22,23,24,31) "
			+ "AND (d.id.empId = :empId) AND (p.id.prvId = :proveedor OR :proveedor IS NULL) "
			+ "AND (d.fecha >= :fechaDesde OR :fechaDesde IS NULL) "
			+ "AND (d.fecha <= :fechaHasta OR :fechaHasta IS NULL) "
			+ "AND (d.numero = :numero OR :numero IS NULL) "
			+ "AND (d.pendiente = :pendiente OR :pendiente is null) "
			+ "AND (d.serie = :serie OR :serie IS NULL) "
			+ "AND (d.estado is null or d.estado != 'A') ";

	private static final String ULTIMOS_GASTOS_SUBQUERY_CONCEPTO = "FROM Documento d join d.moneda m join d.proveedor p join d.comprobante cmp left join d.docruc r left join d.lineas l "
			+ "WHERE  d.comprobante.tipo IN (21,22,23,24,31) "
			+ "AND (d.id.empId = :empId) AND (p.id.prvId = :proveedor OR :proveedor IS NULL) "
			+ "AND (d.fecha >= :fechaDesde OR :fechaDesde IS NULL) "
			+ "AND (d.fecha <= :fechaHasta OR :fechaHasta IS NULL) "
			+ "AND (d.numero = :numero OR :numero IS NULL) "
			+ "AND (d.pendiente = :pendiente OR :pendiente is null) "
			+ "AND (d.serie = :serie OR :serie IS NULL) "
			+ "AND (d.estado is null or d.estado != 'A') " + "AND (:concepto IS NULL OR l.concepto LIKE :concepto) ";

	private static final String ULTIMOS_RECIBOS_SUBQUERY = "FROM "
			+ "Documento d join d.moneda m join d.cliente c join d.comprobante cmp left join d.docruc r "
			+ "WHERE d.comprobante.tipo = 5 "
			+ "AND (d.id.empId = :empId) "
			+ "AND (d.fecha >= :fechaDesde OR :fechaDesde IS NULL) "
			+ "AND (d.fecha <= :fechaHasta OR :fechaHasta IS NULL) "
			+ "AND (d.numero = :numero OR :numero IS NULL) "
			+ "AND (d.serie = :serie OR :serie IS NULL) "
			+ "AND (c.id.cliId = :cliente OR :cliente IS NULL) "
			+ "AND (d.emitido = :emitido OR :emitido IS NULL OR (d.emitido is null AND 'N' = :emitido) OR ('N' = :emitido AND d.emitido = '')) "
			+ "AND (d.saldo > 0 OR :tieneSaldo is NULL) "
			+ "AND (cmp.id.cmpid = :tipoComprobante OR :tipoComprobante IS NULL) "
			+ "AND (m.codigo = :moneda OR :moneda IS NULL) " + "AND (d.estado is null or d.estado != 'A') ";

	private static final String ULTIMOS_CHEQHES_SUBQUERY = "FROM "
			+ "Documento d join d.moneda m join d.cliente c join d.comprobante cmp left join d.docruc r " + "WHERE "
			+ "d.comprobante.tipo = 43 " + "AND (d.id.empId = :empId) "
			+ "AND (d.fecha >= :fechaDesde OR :fechaDesde IS NULL) "
			+ "AND (d.fecha <= :fechaHasta OR :fechaHasta IS NULL) " + "AND (d.numero = :numero OR :numero IS NULL) "
			+ "AND (d.serie = :serie OR :serie IS NULL) " + "AND (c.id.cliId = :cliente OR :cliente IS NULL) "
			+ "AND (m.codigo = :moneda OR :moneda IS NULL) ";

	public String persist(uy.com.tmwc.facturator.entity.Documento doc) throws PermisosException {
		int docId = generateDocId();
		uy.com.tmwc.facturator.libra.entity.Documento libraDoc = toLibraDocumento(doc, docId, null);

		verifyUniqueSerieNumero(libraDoc);

		controlModificacionDocumento(libraDoc);

		if (doc.getComprobante().isCompra()|| doc.getComprobante().getTipo() == Comprobante.MOVIMIENTO_DE_STOCK_DE_PROVEEDORES) {
			ajustarStockLineasCompra(libraDoc, 1);
		} else {
			int signoStock = doc.getComprobante().isDevolucion() ? -1 : 1;
			ajustarStockLineasVenta(libraDoc, signoStock);
		}
		ajustarLineas(libraDoc, doc.getComprobante().isGasto());

		this.em.persist(libraDoc);
		this.em.flush();

		return String.valueOf(docId);
	}

	public void merge(uy.com.tmwc.facturator.entity.Documento doc) throws PermisosException {
		Short cfeStatus = doc.getDocCFEstatus();

		if (cfeStatus == null) {
			cfeStatus = Short.valueOf("0");
		}

		Boolean isRecibo = doc.getComprobante() != null && doc.getComprobante().isRecibo();

		if (cfeStatus.equals(new Short("1")) || isRecibo) {
			merge(doc, Boolean.FALSE);
		} else {
			merge(doc, Boolean.TRUE);
		}

	}

	public void merge(uy.com.tmwc.facturator.entity.Documento doc, Boolean verificar) throws PermisosException {
		DocumentoPK pk = new DocumentoPK();
		pk.setDocId(Integer.parseInt(doc.getDocId()));
		pk.setEmpId(getEmpId());

		uy.com.tmwc.facturator.libra.entity.Documento current = (uy.com.tmwc.facturator.libra.entity.Documento) this.em
				.find(uy.com.tmwc.facturator.libra.entity.Documento.class, pk);
		uy.com.tmwc.facturator.libra.entity.Documento libraDoc = toLibraDocumento(doc, current);

		controlModificacionDocumento(current);

		if (current != null) {
			ArrayList<uy.com.tmwc.facturator.libra.entity.Documento> facturasModificadas = new ArrayList<uy.com.tmwc.facturator.libra.entity.Documento>();
			HashMap<String, Auditoria> audits = new HashMap<String, Auditoria>();

			libraDoc.setRecibosVinculados(new HashSet<Vinculosdoc>());
			libraDoc.getRecibosVinculados().addAll(current.getRecibosVinculados());

			boolean aster = doc.getComprobante().isAster();

			NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("ES"));

			if (doc.getComprobante().isRecibo()) {//
				if (libraDoc.getSaldo().doubleValue() < 0) {
					throw new RuntimeException("Saldo menor que cero (" + libraDoc.getSaldo().toString() + " < 0.00)");
				}
				for (Vinculosdoc vinDoc : current.getFacturasVinculadas()) {
					int docIdVinA = vinDoc.getId().getDocIdVin1(); //

					boolean found = false;
					for (VinculoDocumentos vinculoDocumentos : doc.getFacturasVinculadas()) {
						int docIdVinB = vinculoDocumentos.getDocIdVin1();
						if (docIdVinA == docIdVinB) {
							found = true;

							BigDecimal ajuste;

							BigDecimal montoViejo;
							BigDecimal montoNuevo;
							BigDecimal netoViejo;
							BigDecimal netoNuevo = BigDecimal.ZERO;

							if (aster) {
								montoViejo = vinDoc.getMonto().setScale(4, RoundingMode.HALF_DOWN);
								montoNuevo = vinculoDocumentos.getMonto() != null ? vinculoDocumentos.getMonto()
										.setScale(4, RoundingMode.HALF_DOWN) : BigDecimal.ZERO;
								ajuste = montoNuevo.subtract(montoViejo).setScale(4, RoundingMode.HALF_DOWN);
							} else {
								netoViejo = vinDoc.getNeto() != null ? vinDoc.getNeto().setScale(4,
										RoundingMode.HALF_DOWN) : vinDoc.getMonto().setScale(4, RoundingMode.HALF_DOWN);
								netoNuevo = vinculoDocumentos.getNeto() != null ? vinculoDocumentos.getNeto().setScale(
										4, RoundingMode.HALF_DOWN) : BigDecimal.ZERO;
								ajuste = netoNuevo.subtract(netoViejo).setScale(4, RoundingMode.HALF_DOWN);
							}

							DocumentoPK pk1 = new DocumentoPK();
							pk1.setDocId(Integer.parseInt(vinDoc.getFactura().getDocId()));
							pk1.setEmpId(getEmpId());

							uy.com.tmwc.facturator.libra.entity.Documento factura = (uy.com.tmwc.facturator.libra.entity.Documento) this.em
									.find(uy.com.tmwc.facturator.libra.entity.Documento.class, pk1);
							BigDecimal viejoSaldo = factura.getSaldo().setScale(4, RoundingMode.HALF_DOWN);

							BigDecimal nuevoSaldo = viejoSaldo.subtract(ajuste);
							String moneda = factura.getMoneda().getCodigo();

							nuevoSaldo = ajustarSaldo(moneda, nuevoSaldo);

							if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
								throw new RuntimeException("Monto incorrecto documento " + factura.getSerie()
										+ factura.getNumero().toString() + "(" + viejoSaldo + " < " + ajuste + ")");
							} else {
								factura.setSaldo(nuevoSaldo);
								facturasModificadas.add(factura);

								String simbolo = factura.getMoneda().getSimbolo();

								Auditoria audit = new Auditoria();
								audit.setAudFechaHora(new Date());
								audit.setDocId(factura.getDocId());
								audit.setNotas("Saldo modificado, monto factura modificado en recibo "
										+ current.getSerie() + current.getNumero() + ".\n");
								audit.setProblemas("Se modificó el monto cancelado en recibo " + current.getSerie()
										+ current.getNumero() + ".\n" + "Ajuste:        \t\t" + simbolo
										+ formatter.format(ajuste.doubleValue()) + "\n" + "Saldo anterior:\t" + simbolo
										+ formatter.format(viejoSaldo.doubleValue()) + "\n" + "Saldo actual:  \t"
										+ simbolo + formatter.format(nuevoSaldo.doubleValue()) + "\n");

								audits.put(factura.getDocId(), audit);

							}
							break;
						}
					}
					if (!found) { // Se borra el v�nculo.
						libraDoc.getFacturasVinculadas().remove(vinDoc);

						// Ajusto el saldo de la factura
						DocumentoPK pk1 = new DocumentoPK();
						pk1.setDocId(Integer.parseInt(vinDoc.getFactura().getDocId()));
						pk1.setEmpId(getEmpId());

						uy.com.tmwc.facturator.libra.entity.Documento factura = (uy.com.tmwc.facturator.libra.entity.Documento) this.em
								.find(uy.com.tmwc.facturator.libra.entity.Documento.class, pk1);

						BigDecimal viejoSaldo = factura.getSaldo().setScale(4, RoundingMode.HALF_DOWN);
						BigDecimal monto = vinDoc.getMonto().setScale(4, RoundingMode.HALF_DOWN);
						BigDecimal neto = vinDoc.getNeto() != null ? vinDoc.getNeto().setScale(4,
								RoundingMode.HALF_EVEN) : monto;

						BigDecimal nuevoSaldo;
						if (aster) {
							nuevoSaldo = viejoSaldo.add(monto);
						} else {
							nuevoSaldo = viejoSaldo.add(neto);
						}
						factura.setSaldo(nuevoSaldo);
						facturasModificadas.add(factura);

						String simbolo = factura.getMoneda().getSimbolo();

						Auditoria audit = new Auditoria();
						audit.setAudFechaHora(new Date());
						audit.setDocId(factura.getDocId());
						audit.setNotas("Saldo modificado, factura borrada en recibo " + current.getSerie()
								+ current.getNumero() + ".\n");
						audit.setProblemas("Borrado de recibo " + current.getSerie() + current.getNumero() + ".\n"
								+ "Monto cancelado:\t" + simbolo
								+ formatter.format(aster ? monto.doubleValue() : neto.doubleValue()) + "\n"
								+ "Saldo anterior: \t\t" + simbolo + formatter.format(viejoSaldo.doubleValue()) + "\n"
								+ "Saldo actual:   \t\t" + simbolo + formatter.format(nuevoSaldo.doubleValue()) + "\n");

						audits.put(factura.getDocId(), audit);
					}
				}

				for (VinculoDocumentos vinDoc : doc.getFacturasVinculadas()) {
					int docIdVinA = vinDoc.getDocIdVin1();

					boolean found = false;
					for (Vinculosdoc vinculoDocumentos : current.getFacturasVinculadas()) {
						int docIdVinB = vinculoDocumentos.getId().getDocIdVin1();
						if (docIdVinA == docIdVinB) {
							found = true;
						}
					}
					if (!found) { // Se agrega el vínculo
						DocumentoPK pk1 = new DocumentoPK();
						pk1.setDocId(Integer.parseInt(vinDoc.getFactura().getDocId()));
						pk1.setEmpId(getEmpId());

						uy.com.tmwc.facturator.libra.entity.Documento factura = (uy.com.tmwc.facturator.libra.entity.Documento) this.em
								.find(uy.com.tmwc.facturator.libra.entity.Documento.class, pk1);

						BigDecimal monto = vinDoc.getMonto().setScale(4, RoundingMode.HALF_EVEN);
						BigDecimal neto = vinDoc.getNeto().setScale(4, RoundingMode.HALF_EVEN);
						BigDecimal viejoSaldo = factura.getSaldo().setScale(4, RoundingMode.HALF_EVEN);
						String moneda = factura.getMoneda().getCodigo();

						BigDecimal nuevoSaldo;
						if (aster) {
							nuevoSaldo = viejoSaldo.subtract(monto).setScale(4, RoundingMode.HALF_EVEN);
						} else {
							nuevoSaldo = viejoSaldo.subtract(neto).setScale(4, RoundingMode.HALF_EVEN);
						}
						nuevoSaldo = ajustarSaldo(moneda, nuevoSaldo);

						if (nuevoSaldo.compareTo(BigDecimal.ZERO) < 0) {
							throw new RuntimeException("Monto incorrecto documento " + factura.getSerie()
									+ factura.getNumero().toString() + " saldo < 0 (" + nuevoSaldo + " < " + monto
									+ ")");
						} else {
							factura.setSaldo(nuevoSaldo);
							facturasModificadas.add(factura);

							String simbolo = factura.getMoneda().getSimbolo();

							Auditoria audit = new Auditoria();
							audit.setAudFechaHora(new Date());
							audit.setDocId(factura.getDocId());
							audit.setNotas("Saldo modificado, factura adicionada en recibo " + current.getSerie()
									+ current.getNumero() + ".\n");
							audit.setProblemas("Factura vinculada al recibo " + current.getSerie()
									+ current.getNumero() + ".\n" + "Monto cancelado:\t" + simbolo
									+ formatter.format(aster ? monto.doubleValue() : neto.doubleValue()) + "\n"
									+ "Saldo anterior:\t\t" + simbolo + formatter.format(viejoSaldo.doubleValue())
									+ "\n" + "Saldo actual:\t\t" + simbolo + formatter.format(nuevoSaldo.doubleValue())
									+ "\n");

							audits.put(factura.getDocId(), audit);

						}
					}
				}

				// LOGGER.info(buffer.toString());

				for (uy.com.tmwc.facturator.libra.entity.Documento documento : facturasModificadas) {
					this.em.merge(documento);
					this.em.flush();

					Auditoria audit = audits.get(documento.getDocId());
					auditoriaService.alta(audit);
				}

			}

			// Ajustar el stock. Pueden variar las lineas, y los depositos.
			// Nunca el comprobante.
			if (doc.getComprobante().isCompra()|| doc.getComprobante().getTipo() == Comprobante.MOVIMIENTO_DE_STOCK_DE_PROVEEDORES) {
				ajustarStockLineasCompra(current, -1);
				ajustarStockLineasCompra(libraDoc, 1);
			} else {
				int signoStock = doc.getComprobante().isDevolucion() ? -1 : 1;
				ajustarStockLineasVenta(current, -signoStock); // Esto es un borrar, los signos estan cambiados
				ajustarStockLineasVenta(libraDoc, signoStock);
			}

			ajustarLineas(libraDoc, doc.getComprobante().isGasto());
		}

		if (verificar) {
			verifyUniqueSerieNumero(libraDoc);
		}

		// TODO: Revisar este ajuste de saldo en el recibo.
		BigDecimal saldoRecibo = ajustarSaldo(libraDoc.getMoneda().getCodigo(), libraDoc.getSaldo());
		if (saldoRecibo.compareTo(BigDecimal.ZERO) == 0) {
			libraDoc.setRedondeo(libraDoc.getSaldo().setScale(2, RoundingMode.HALF_EVEN));
		}
		libraDoc.setSaldo(saldoRecibo);

		this.em.merge(libraDoc);
		this.em.flush();
	}

	private BigDecimal ajustarSaldo(String moneda, BigDecimal saldo) {
		double ss = Math.abs(saldo.doubleValue());
		if (moneda.equals(Moneda.CODIGO_MONEDA_DOLAR) || moneda.equals(Moneda.CODIGO_MONEDA_EUROS)
				|| moneda.equals(Moneda.CODIGO_MONEDA_DOLAR_ASTER) || moneda.equals(Moneda.CODIGO_MONEDA_EUROS_ASTER)) {
			if (ss < 0.1) {
				saldo = BigDecimal.ZERO;
			}
		} else if (moneda.equals(Moneda.CODIGO_MONEDA_PESOS) || moneda.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
			if (ss < 1) {
				saldo = BigDecimal.ZERO;
			}
		}
		return saldo;

	}

	public Boolean remove(uy.com.tmwc.facturator.entity.Documento doc) throws PermisosException {
		if (doc != null && doc.getDocId() != null) {
			uy.com.tmwc.facturator.libra.entity.Documento docEntity = toLibraDocumento(doc, null);
			docEntity = em.find(uy.com.tmwc.facturator.libra.entity.Documento.class, docEntity.getId());

			controlModificacionDocumento(docEntity);

			int signoStock = doc.getComprobante().isDevolucion() ? -1 : 1;
			ajustarStockLineasVenta(docEntity, -signoStock); // Esto es un borrar, los signos estan cambiados
			if (docEntity != null) {
				em.remove(docEntity);
				em.flush();
			}
		}
		return true;

	}

	public Boolean finalizarMovimientoStock(uy.com.tmwc.facturator.entity.Documento doc) throws PermisosException {
		DocumentoPK pk = new DocumentoPK();
		pk.setDocId(Integer.parseInt(doc.getDocId()));
		pk.setEmpId(getEmpId());
		uy.com.tmwc.facturator.libra.entity.Documento docEntity = this.em.find(
				uy.com.tmwc.facturator.libra.entity.Documento.class, pk);

		controlModificacionDocumento(docEntity);

		docEntity.setPendiente("N"); // lo marco como no pendiente, remuevo la reserva de articulos
		docEntity.setEmitido("N");
		docEntity.setNextDocId(doc.getNextDocId());
		docEntity.setProcessId(doc.getProcessId());

		verifyUniqueSerieNumero(docEntity);

		this.em.merge(docEntity);
		this.em.flush();

		return true;

	}

	private void ajustarLineas(uy.com.tmwc.facturator.libra.entity.Documento libraDoc, boolean esGasto) {
		List<Linea> lineas = libraDoc.getLineas();
		for (Linea linea : lineas) {
			linea.setDepositoOrigen(libraDoc.getDepositoOrigen());
			linea.setDepositoDestino(libraDoc.getDepositoDestino());

			if (!esGasto) {
				if (!libraDoc.comprobanteComputaIva()) {
					linea.setIvaId(Short.parseShort("0"));
				} else {
					linea.setIvaId(linea.getIvaLin() != null ? linea.getIvaLin().getId().getIvaId() : Short
							.parseShort("0"));
				}
			}
		}
	}

	private void ajustarStockLineasVenta(uy.com.tmwc.facturator.libra.entity.Documento libraDoc, int signoStock) {
		List<Linea> lineas = libraDoc.getLineas();
		for (Linea linea : lineas) {
			if (linea.getArticulo() != null && linea.getArticulo().getInventario()) {
				ajustarStock(linea.getArticulo(), libraDoc.getDepositoOrigen(), libraDoc.getDepositoDestino(), linea
						.getCantidad().multiply(new BigDecimal(signoStock)));
			}
		}
	}

	private void ajustarStockLineasCompra(uy.com.tmwc.facturator.libra.entity.Documento libraDoc, int signoStock) {
		List<Linea> lineas = libraDoc.getLineas();
		for (Linea linea : lineas) {
			if (linea.getArticulo() != null && linea.getArticulo().getInventario()) {
				ajustarStockCompra(linea.getArticulo(), libraDoc.getDepositoOrigen(), libraDoc.getDepositoDestino(), linea.getCantidad().multiply(new BigDecimal(signoStock)));
			}
		}
	}


	@SuppressWarnings("rawtypes")
	private void verifyUniqueSerieNumero(uy.com.tmwc.facturator.libra.entity.Documento doc) {
		String serie = doc.getSerie();
		BigInteger numero = doc.getNumero();

		if ((numero != null) && (!StringUtils.isEmpty(serie))) {
			String queryName = "Documento.unique";
			if (doc.getTipo() == Comprobante.MOVIMIENTO_DE_STOCK_DE_CLIENTE
					|| doc.getTipo() == Comprobante.MOVIMIENTO_DE_STOCK_DE_CLIENTE) {
				queryName = "Documento.unique.movimientoStock";
			} else if (doc.getTipo() == Comprobante.RECIBO_COBRO) {
				queryName = "Documento.unique.recibo";
			}
			List result = this.em.createNamedQuery(queryName).setParameter("empId", getEmpId())
					.setParameter("serie", serie).setParameter("numero", numero).getResultList();
			if ((result.size() > 0)
					&& ((doc.getId() == null) || (doc.getId().getDocId() != ((DummyDocumento) result.get(0)).getId()
							.getDocId())))
				throw new IllegalArgumentException("Serie y número en uso");
		}
	}

	private uy.com.tmwc.facturator.libra.entity.Documento toLibraDocumento(uy.com.tmwc.facturator.entity.Documento doc,
			uy.com.tmwc.facturator.libra.entity.Documento current) {
		return toLibraDocumento(doc, Integer.valueOf(doc.getDocId()).intValue(), current);
	}

	private uy.com.tmwc.facturator.libra.entity.Documento toLibraDocumento(uy.com.tmwc.facturator.entity.Documento doc,
			int docId, uy.com.tmwc.facturator.libra.entity.Documento current) {
		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
		uy.com.tmwc.facturator.libra.entity.Documento libraDoc = (uy.com.tmwc.facturator.libra.entity.Documento) mapper
				.map(doc, uy.com.tmwc.facturator.libra.entity.Documento.class);
		libraDoc.provideId(getEmpId(), docId);
		libraDoc.setLocalId(Short.parseShort("1"));

		libraDoc.setFecha2(doc.getFecha());

		if (doc.getComprobante().getTipo() == Comprobante.COMPRA_CREDITO) {
			if (current != null) {
				if (current.getTotal().equals(doc.getTotal())) {
					libraDoc.setSaldo(current.getSaldo());
				} else {
					BigDecimal diferencia = current.getTotal().subtract(doc.getTotal());
					libraDoc.setSaldo(current.getSaldo().subtract(diferencia));
				}
			} else {
				libraDoc.setSaldo(doc.getTotal());
			}
		} else {
			if (doc.getComprobante().isNotaCreditoFinanciera()) {
				libraDoc.setSaldo(doc.getSaldo());

			} else if (!doc.getComprobante().isRecibo()) {
				if (!doc.isEmitido()) {
					libraDoc.setSaldo((doc.getComprobante().isCredito() || (doc.getComprobante().getTipo() == Comprobante.NOTA_CREDITO)) ? doc
							.getTotal() : BigDecimal.ZERO);
				} else if (current != null) {
					libraDoc.setSaldo(doc.getSaldo());
				}
			}
		}

		libraDoc.generarRedundancia();

		return libraDoc;
	}

	public SerieNumero generarSerieNumero(String codigoComprobante) {
		uy.com.tmwc.facturator.libra.entity.Comprobante comprobante = (uy.com.tmwc.facturator.libra.entity.Comprobante) this.em
				.find(uy.com.tmwc.facturator.libra.entity.Comprobante.class,
						new ComprobantePK(getEmpId(), Long.parseLong(codigoComprobante)));
		if (comprobante == null) {
			throw new RuntimeException("Comprobante no encontrado: " + codigoComprobante);
		}
		Numerador numerador = comprobante.getNumerador();
		if (numerador != null) {
			this.em.lock(numerador, LockModeType.WRITE);
			this.em.flush();
			long numero = numerador.getNumero();
			return new SerieNumero(numerador.getSerie(), Long.valueOf(numero));
		}
		throw new RuntimeException("No se encontró numerador: " + codigoComprobante);
	}

	public uy.com.tmwc.facturator.entity.Fanfold generarFanfold(String numFoldId) {
		FanfoldPK pKey = new FanfoldPK();
		pKey.setEmpId(getEmpId());
		pKey.setNumFoldId(numFoldId);

		Fanfold fanfold = (Fanfold) this.em.find(Fanfold.class, pKey);

		if (fanfold == null) {
			throw new RuntimeException("Fanfold no encontrado: " + pKey);
		} else {
			Long numFoldNumero = fanfold.getNumFoldNumero();
			fanfold.setNumFoldNumero(numFoldNumero + 1);

			this.em.lock(fanfold, LockModeType.WRITE);
			this.em.flush();

			DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();
			uy.com.tmwc.facturator.entity.Fanfold libraFanfold = (uy.com.tmwc.facturator.entity.Fanfold) mapper.map(
					fanfold, uy.com.tmwc.facturator.entity.Fanfold.class);
			return libraFanfold;
		}
	}

	public BigDecimal getTipoCambio(String codigoMoneda, Date fecha) {
		Query query = this.em.createNamedQuery("TipoCambio.tipoCambioComercialFecha").setParameter("empId", getEmpId())
				.setParameter("moneda", Short.valueOf(Short.parseShort(codigoMoneda))).setParameter("fecha", fecha);
		BigDecimal result = (BigDecimal) JPAUtils.getAtMostOne(query);
		return result;
	}

	public BigDecimal getTipoCambioFiscal(String codigoMoneda, Date fecha) {
		Query query = this.em.createNamedQuery("TipoCambio.tipoCambioFiscalFecha").setParameter("empId", getEmpId())
				.setParameter("moneda", Short.valueOf(Short.parseShort(codigoMoneda))).setParameter("fecha", fecha);
		BigDecimal result = (BigDecimal) JPAUtils.getAtMostOne(query);
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<LineaDocumento> getLineasCompraCliente(String cliente, Date fromDate, Date toDate, int limit) {
		List list = this.em.createNamedQuery("Documento.lineasCompraCliente").setParameter("empId", getEmpId())
				.setParameter("cliente", cliente).setParameter("fechaDesde", fromDate)
				.setParameter("fechaHasta", toDate).setMaxResults(limit).getResultList();

		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();

		List<LineaDocumento> res = MappingUtils.map(LineaDocumento.class, list, mapper);
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<LineaDocumento> getLineasCotizadas(String cliente, String artId, String cmpId, Date fromDate,
			Date toDate, int limit) {
		List list = this.em.createNamedQuery("Documento.lineasCotizadas").setParameter("empId", getEmpId())
				.setParameter("cliente", cliente).setParameter("articuloId", artId)
				.setParameter("fechaDesde", fromDate).setParameter("fechaHasta", toDate).setMaxResults(limit)
				.getResultList();

		DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();

		return MappingUtils.map(LineaDocumento.class, list, mapper);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<LineaDocumento> getAntecedentes(String articulo, String cliente, int limit, boolean venta)
			throws PermisosException {
		List list = null;

		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		boolean esSupervisor = usuarioLogin.isSupervisor();

		if (esSupervisor || Usuario.USUARIO_ADMINISTRADOR.equals(permisoId)
				|| Usuario.USUARIO_VENDEDOR_SENIOR.equals(permisoId) || Usuario.USUARIO_FACTURACION.equals(permisoId)
				|| (Usuario.USUARIO_ALIADOS_COMERCIALES.equals(permisoId) && venta)
				|| (Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId) && venta)) {

			if (cliente != null) {
				if (Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId)) {
					List listAux;
					if (venta) {
						listAux = this.em.createNamedQuery("Documento.antecedentesArticuloClienteVenta")
								.setParameter("empId", getEmpId()).setParameter("cliente", cliente)
								.setParameter("articulo", articulo).setMaxResults(limit).getResultList();
					} else {
						listAux = this.em.createNamedQuery("Documento.antecedentesArticuloClienteCompra")
								.setParameter("empId", getEmpId()).setParameter("cliente", cliente)
								.setParameter("articulo", articulo).setMaxResults(limit).getResultList();

					}

					List<VendedoresUsuario> vendedores = usuarioLogin.getVendedoresUsuario();

					List<String> userVendedores = new ArrayList<String>();
					for (VendedoresUsuario vendedoresUsuario : vendedores) {
						userVendedores.add(vendedoresUsuario.getVendedorId());
					}

					list = new ArrayList();
					for (Object object : listAux) {
						if (object instanceof Linea) {
							Linea l = (Linea) object;

							String encargadoCuenta = l.getDocumento().getCliente().getEncargadoCuenta();
							String vendedorId = l.getDocumento().getCliente().getVenIdCli();

							if (userVendedores.contains(encargadoCuenta) || userVendedores.contains(vendedorId)) {
								list.add(object);
							}
						}
					}

				} else {
					if (venta) {
						list = this.em.createNamedQuery("Documento.antecedentesArticuloClienteVenta")
								.setParameter("empId", getEmpId()).setParameter("cliente", cliente)
								.setParameter("articulo", articulo).setMaxResults(limit).getResultList();
					} else {
						list = this.em.createNamedQuery("Documento.antecedentesArticuloClienteCompra")
								.setParameter("empId", getEmpId()).setParameter("cliente", cliente)
								.setParameter("articulo", articulo).setMaxResults(limit).getResultList();
					}
				}

			} else {
				if (venta) {
					if (Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId)) {
						List listAux = this.em.createNamedQuery("Documento.antecedentesArticuloVenta")
								.setParameter("empId", getEmpId()).setParameter("articulo", articulo)
								.setMaxResults(limit).getResultList();

						List<VendedoresUsuario> vendedores = usuarioLogin.getVendedoresUsuario();

						List<String> userVendedores = new ArrayList<String>();
						for (VendedoresUsuario vendedoresUsuario : vendedores) {
							userVendedores.add(vendedoresUsuario.getVendedorId());
						}
						list = new ArrayList();
						for (Object object : listAux) {
							if (object instanceof Linea) {
								Linea l = (Linea) object;

								String encargadoCuenta = l.getDocumento().getCliente().getEncargadoCuenta();
								String vendedorId = l.getDocumento().getCliente().getVenIdCli();

								if (userVendedores.contains(encargadoCuenta) || userVendedores.contains(vendedorId)) {
									list.add(object);
								}
							}
						}
					} else {
						list = this.em.createNamedQuery("Documento.antecedentesArticuloVenta")
								.setParameter("empId", getEmpId()).setParameter("articulo", articulo)
								.setMaxResults(limit).getResultList();
					}
				} else {
					list = this.em.createNamedQuery("Documento.antecedentesArticuloCompra")
							.setParameter("empId", getEmpId()).setParameter("articulo", articulo).setMaxResults(limit)
							.getResultList();
				}
			}

			DozerBeanMapper mapper = this.mapService.getDozerBeanMapper();

			return MappingUtils.map(LineaDocumento.class, list, mapper);
		} else {
			throw new PermisosException("El usuario no tiene permiso para ver los antecedentes del artículo");
		}

	}

	@SuppressWarnings("unchecked")
	public List<DocumentoDTO> queryDocumentos(DocumentoQuery query) {

		// Filtros de usuario, no aplica en los recibos
		String filtroUsuario = "";
		if (!query.getEsRecibo()) {
			filtroUsuario = getFiltroUsuario();
		}

		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		boolean esSupervisor = usuarioLogin.isSupervisor();

		final StringBuilder sb = new StringBuilder("");

		if (query.getEsCheque()) {
			sb.append("SELECT new uy.com.tmwc.facturator.dto.DocumentoDTO(d.id.docId, cmp.id.cmpid, cmp.cmpNom, d.serie, d.numero, d.bancoIdDoc, d.fecha, "
					+ "d.emitido, d.titular, m.id.mndId, m.nombre, d.total, d.clienteId, c.nombre, d.fecha2) ");

		} else if (query.getEsGasto()) {
			sb.append("SELECT distinct new uy.com.tmwc.facturator.dto.DocumentoDTO( " + "d.id.docId, " + "d.serie, "
					+ "d.numero, " + "d.fecha, " + "d.CAEnom, " + "p.id.prvId, " + "p.nombre, "
					+ "r.nombre, m.id.mndId, m.nombre, cmp.id.cmpid, cmp.cmpNom, ");

			if (esSupervisor) {
				sb.append("d.costo, ");
			}
			sb.append("d.subTotal, d.iva, d.total, d.saldo, d.emitido, d.pendiente, d.comprobante.tipo) ");

		} else {
			if (query.getEsSolicitud() == null || !query.getEsSolicitud()) {
				if (query.getArticulo() == null || query.getArticulo() == "") {
					sb.append("SELECT new uy.com.tmwc.facturator.dto.DocumentoDTO(d.id.docId, d.serie, d.numero, d.fecha, d.CAEnom, d.clienteId, c.nombre, "
							+ "r.nombre, m.id.mndId, m.nombre, cmp.id.cmpid, cmp.cmpNom, ");
				} else {
					sb.append("SELECT distinct new uy.com.tmwc.facturator.dto.DocumentoDTO(d.id.docId, d.serie, d.numero, d.fecha, d.CAEnom, d.clienteId, c.nombre, "
							+ "r.nombre, m.id.mndId, m.nombre, cmp.id.cmpid, cmp.cmpNom, ");
				}
			} else {
				sb.append("SELECT distinct new uy.com.tmwc.facturator.dto.DocumentoDTO( " + "d.id.docId, "
						+ "d.serie, " + "d.numero, " + "d.fecha, " + "d.CAEnom, " + "p.id.prvId, " + "p.nombre, "
						+ "r.nombre, m.id.mndId, m.nombre, cmp.id.cmpid, cmp.cmpNom, ");
			}

			if (esSupervisor) {
				sb.append("d.costo, ");
			}
			sb.append("d.subTotal, d.iva, d.total, d.saldo, d.emitido, d.pendiente, d.comprobante.tipo, d.processId) ");
		}

		if (query.getEsSolicitud()) {
			sb.append(query.getLineaConcepto() == null ? ULTIMAS_SOLICITUDES_SUBQUERY
					: ULTIMAS_SOLICITUDES_SUBQUERY_CONCEPTO);
		} else if (query.getEsRecibo()) {
			sb.append(ULTIMOS_RECIBOS_SUBQUERY);
		} else if (query.getEsGasto()) {
			sb.append(query.getLineaConcepto() == null ? ULTIMOS_GASTOS_SUBQUERY : ULTIMOS_GASTOS_SUBQUERY_CONCEPTO);
		} else if (query.getEsCheque()) {
			sb.append(ULTIMOS_CHEQHES_SUBQUERY);
		} else {
			if (query.getArticulo() == null || "".equals(query.getArticulo())) {
				sb.append(query.getLineaConcepto() == null ? ULTIMOS_FACTURADOS_SUBQUERY
						: ULTIMOS_FACTURADOS_SUBQUERY_CONCEPTO);
			} else {
				sb.append(ULTIMOS_FACTURADOS_SUBQUERY_ARTICULO);
			}
		}

		String comprobantes = query.getComprobantes();
		if (comprobantes != null && comprobantes.length() > 0) {
			sb.append(" AND cmp.id.cmpid IN (").append(comprobantes).append(") ");
		}
		if (!query.getEsSolicitud()) {
			sb.append(filtroUsuario);
		}

		String orden = query.getOrden() != null ? query.getOrden() : "DESC";

		sb.append("ORDER BY d.fecha ").append(orden).append(", d.registroHora ").append(orden)
				.append(", d.serie, d.numero");

		Query q = this.em.createQuery(sb.toString());
		setUltimosDocumentosSubqueryParameters(q, query);
		List<DocumentoDTO> list = q.setFirstResult(query.getStart()).setMaxResults(query.getLimit()).getResultList();

		return list;
	}

	private String getFiltroUsuario() {
		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		boolean esSupervisor = usuarioLogin.isSupervisor();

		if (esSupervisor || Usuario.USUARIO_ADMINISTRADOR.equals(permisoId)
				|| Usuario.USUARIO_FACTURACION.equals(permisoId)) {
			return "";
		} else {
			String codigoUsuario = usuarioLogin.getCodigo();
			return " AND (" + "(d.usuarioId = " + codigoUsuario + ") OR (u.codigo = " + codigoUsuario + ") OR ("
					+ codigoUsuario
					+ " IN (SELECT p.vendedor.vendedoresUsuario.usuario.codigo FROM d.participaciones p)) OR " + "(('"
					+ permisoId + "'='5') AND (cmp.id.cmpid IN (70,71,72,73,80,81,82,83,90,91,92,93,130,131,132,133)))"
					+ ") ";
		}
	}

	private void setUltimosDocumentosSubqueryParameters(Query q, DocumentoQuery query) {
		BigInteger numero = query.getNumero() != null ? query.getNumero() : null;
		String pendiente = query.getPendiente() ? "S" : null;
		String emitido = query.getPendiente() ? "N" : null;
		String concepto = query.getLineaConcepto() != null ? "%" + query.getLineaConcepto() + "%" : null;

		if (query.getEmitido()) {
			emitido = "S";
		}
		if (query.getEsRecibo()) {
			q.setParameter("empId", getEmpId()).setParameter("cliente", query.getCliente())
					.setParameter("moneda", query.getMoneda() != null ? new Short(query.getMoneda()) : null)
					.setParameter("fechaDesde", query.getFechaDesde())
					.setParameter("fechaHasta", query.getFechaHasta()).setParameter("emitido", query.getEstado())
					.setParameter("numero", numero).setParameter("serie", query.getSerie())
					.setParameter("tipoComprobante", query.getTipoComprobante())
					.setParameter("tieneSaldo", query.getTieneSaldo() ? "S" : null);

		} else if (query.getEsCheque()) {
			q.setParameter("empId", getEmpId()).setParameter("cliente", query.getCliente())
					.setParameter("moneda", query.getMoneda() != null ? new Short(query.getMoneda()) : null)
					.setParameter("fechaDesde", query.getFechaDesde())
					.setParameter("fechaHasta", query.getFechaHasta()).setParameter("numero", numero)
					.setParameter("serie", query.getSerie());

		} else if (query.getEsGasto()) {
			q.setParameter("empId", getEmpId()).setParameter("proveedor", query.getProveedor())
					.setParameter("fechaDesde", query.getFechaDesde())
					.setParameter("fechaHasta", query.getFechaHasta()).setParameter("pendiente", pendiente)
					.setParameter("numero", numero).setParameter("serie", query.getSerie());
			if (concepto != null) {
				q.setParameter("concepto", concepto);
			}

		} else if (query.getEsSolicitud() == null || !query.getEsSolicitud()) {
			if (query.getArticulo() == null || query.getArticulo() == "") {
				q.setParameter("empId", getEmpId()).setParameter("cliente", query.getCliente())
						.setParameter("moneda", query.getMoneda() != null ? new Short(query.getMoneda()) : null)
						.setParameter("fechaDesde", query.getFechaDesde())
						.setParameter("fechaHasta", query.getFechaHasta()).setParameter("pendiente", pendiente)
						.setParameter("emitido", emitido).setParameter("numero", numero)
						.setParameter("serie", query.getSerie())
						.setParameter("tipoComprobante", query.getTipoComprobante())
						.setParameter("tieneSaldo", query.getTieneSaldo() ? "S" : null);
				if (query.getLineaConcepto() != null) {
					q.setParameter("concepto", concepto);
				}

			} else {
				q.setParameter("empId", getEmpId()).setParameter("articulo", query.getArticulo())
						.setParameter("cliente", query.getCliente())
						.setParameter("moneda", query.getMoneda() != null ? new Short(query.getMoneda()) : null)
						.setParameter("fechaDesde", query.getFechaDesde())
						.setParameter("fechaHasta", query.getFechaHasta()).setParameter("pendiente", pendiente)
						.setParameter("emitido", emitido).setParameter("numero", numero)
						.setParameter("serie", query.getSerie()).setParameter("concepto", query.getLineaConcepto())
						.setParameter("tipoComprobante", query.getTipoComprobante())
						.setParameter("tieneSaldo", query.getTieneSaldo() ? "S" : null);
			}
		} else if (query.getEsSolicitud() != null && query.getEsSolicitud()) {
			q.setParameter("empId", getEmpId()).setParameter("proveedor", query.getProveedor())
					.setParameter("fechaDesde", query.getFechaDesde())
					.setParameter("fechaHasta", query.getFechaHasta()).setParameter("pendiente", pendiente)
					.setParameter("numero", numero).setParameter("serie", query.getSerie())
					.setParameter("tipoComprobante", query.getTipoComprobante());

			if (concepto != null) {
				q.setParameter("concepto", concepto);
			}

		} else {
			q.setParameter("empId", getEmpId()).setParameter("proveedor", query.getProveedor())
					.setParameter("fechaDesde", query.getFechaDesde())
					.setParameter("fechaHasta", query.getFechaHasta()).setParameter("pendiente", pendiente)
					.setParameter("emitido", emitido).setParameter("numero", numero)
					.setParameter("serie", query.getSerie())
					.setParameter("tipoComprobante", query.getTipoComprobante());

			if (query.getLineaConcepto() != null) {
				q.setParameter("concepto", concepto);
			}

		}
	}

	public long countDocumentos(DocumentoQuery query) {
		final StringBuilder sb = new StringBuilder("");

		if (query.getEsRecibo()) {
			sb.append("SELECT COUNT(d.id.docId) ");
			sb.append(ULTIMOS_RECIBOS_SUBQUERY);

		} else if (query.getEsGasto()) {
			sb.append("SELECT COUNT(d.id.docId) ");
			if (query.getLineaConcepto() != null) {
				sb.append(ULTIMOS_GASTOS_SUBQUERY_CONCEPTO);
			} else {
				sb.append(ULTIMOS_GASTOS_SUBQUERY);
			}

		} else if (query.getEsSolicitud() == null || !query.getEsSolicitud()) {
			if (query.getArticulo() == null || query.getArticulo() == "") {
				sb.append("SELECT COUNT(d.id.docId) ");
				if (query.getLineaConcepto() != null) {
					sb.append(ULTIMOS_FACTURADOS_SUBQUERY_CONCEPTO);
				} else {
					sb.append(ULTIMOS_FACTURADOS_SUBQUERY);
				}
			} else {
				sb.append("SELECT COUNT(distinct(d.id.docId)) ");
				sb.append(ULTIMOS_FACTURADOS_SUBQUERY_ARTICULO);
			}

		} else {
			sb.append("SELECT COUNT(d.id.docId) ");
			if (query.getLineaConcepto() != null) {
				sb.append(ULTIMAS_SOLICITUDES_SUBQUERY_CONCEPTO);
			} else {
				sb.append(ULTIMAS_SOLICITUDES_SUBQUERY);
			}
		}

		String comprobantes = query.getComprobantes();
		if (comprobantes != null && comprobantes.length() > 0) {
			sb.append(" AND cmp.id.cmpid IN (").append(comprobantes).append(") ");
		}
		if (!query.getEsSolicitud() && !query.getEsRecibo()) {
			sb.append(getFiltroUsuario());
		}
		Query q = this.em.createQuery(sb.toString());

		setUltimosDocumentosSubqueryParameters(q, query);
		Long count = (Long) q.getSingleResult();
		return count.longValue();
	}

	@SuppressWarnings("unchecked")
	public List<DocumentoDTO> pendientesCliente(String cliId) {
		List<DocumentoDTO> list = this.em.createNamedQuery("Documento.pendientesCliente")
				.setParameter("empId", getEmpId()).setParameter("cliId", cliId).getResultList();

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<DocumentoDTO> getTrazabilidad(String value) {
		Integer docId;
		try {
			docId = (value != null && value.length() > 0 && !value.equals("NaN")) ? new Integer(value) : -1;
		} catch (NumberFormatException ex) {
			docId = -1;
		}
		List<DocumentoDTO> list = this.em.createNamedQuery("Documento.trazabilidadDocumento")
				.setParameter("empId", getEmpId()).setParameter("docId", docId).setParameter("processId", value)
				.getResultList();

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<DocumentoDTO> getSolicitudImportacion(String value) {
		List<DocumentoDTO> list = this.em.createNamedQuery("Documento.solicitudImportacion")
				.setParameter("empId", getEmpId()).setParameter("processId", value).getResultList();

		return list;
	}

	public uy.com.tmwc.facturator.entity.Documento findDocumento(String docId) throws PermisosException {
		uy.com.tmwc.facturator.libra.entity.Documento doc = (uy.com.tmwc.facturator.libra.entity.Documento) this.em
				.find(uy.com.tmwc.facturator.libra.entity.Documento.class,
						new DocumentoPK(getEmpId(), Integer.parseInt(docId)));

		if (doc == null) {
			return null;
		}

		Documento mapped = (uy.com.tmwc.facturator.entity.Documento) this.mapService.getDozerBeanMapper().map(doc,
				uy.com.tmwc.facturator.entity.Documento.class);

		PermisosDocumentoUsuario pdu = new PermisosDocumentoUsuario();
		mapped.setPermisosDocumentoUsuario(pdu);
		try {
			controlModificacionDocumento(doc);
			pdu.setEdicion(true);
		} catch (PermisosException e) {
			pdu.setEdicion(false);
		}
		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		String usuarioPermisoId = usuarioLogin.getPermisoId();
		boolean esSupervisor = usuarioLogin.isSupervisor();

		pdu.setRentaReal(esSupervisor
				|| Arrays.asList(new String[] { Usuario.USUARIO_FACTURACION, Usuario.USUARIO_ADMINISTRADOR }).contains(
						usuarioPermisoId)
				|| (usuarioPermisoId.equals(Usuario.USUARIO_VENDEDOR_SENIOR) && doc.usuarioInvolucrado(usuarioLogin
						.getCodigo())));
		pdu.setRentaDistribuidor(esSupervisor
				|| Arrays.asList(new String[] { Usuario.USUARIO_FACTURACION, Usuario.USUARIO_ADMINISTRADOR }).contains(
						usuarioPermisoId)
				|| (usuarioPermisoId.equals(Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR) && doc
						.usuarioInvolucrado(usuarioLogin.getCodigo())));
		return mapped;
	}

	private void controlModificacionDocumento(uy.com.tmwc.facturator.libra.entity.Documento doc)
			throws PermisosException {
		Collection<Integer> codigos = usuariosService.getCodigosComprobantesPermitidosUsuario();
		if (!(codigos.size() == 1 && codigos.iterator().next() == Integer.MAX_VALUE)) {
			if (!codigos.contains((int) doc.getComprobante().getId().getCmpid())) {
				throw new PermisosException("El usuario no tiene permiso para modificar este tipo de comprobantes");
			}
		}

		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		if (usuarioLogin.getImpedirModificacionDocumentosAjenos()) {
			if (doc.getId() != null && doc.getId().getDocId() > 0) {
				if (!usuarioLogin.getCodigo().equals(String.valueOf(doc.getUsuarioId()))) {
					throw new PermisosException(
							"El usuario no tiene permiso para modificar documentos creados por otros usuarios");
				}
			}
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public List<ResumenEntrega> getResumenEntregas(Date fechaDesde, Date fechaHasta) {
		List<Object[]> list = this.em.createNamedQuery("Documento.resumenEntregas").setParameter("empId", getEmpId())
				.setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta).getResultList();

		Mapper mapper = new Mapper();

		ArrayList rree = new ArrayList();
		for (Object[] objects : list) {
			uy.com.tmwc.facturator.libra.entity.Entrega elibra = (uy.com.tmwc.facturator.libra.entity.Entrega) objects[0];
			uy.com.tmwc.facturator.entity.Entrega e = (uy.com.tmwc.facturator.entity.Entrega) mapper.map(elibra,
					uy.com.tmwc.facturator.entity.Entrega.class);
			rree.add(new ResumenEntrega(e, ((Long) objects[1]).longValue()));
		}

		return rree;
	}

	@SuppressWarnings("rawtypes")
	public void grabarCostosOperativos(Date fechaDesde, Date fechaHasta, Map<String, BigDecimal> costosPorEntrega) {
		updateCostoOperativoDocumentos(fechaDesde, fechaHasta, null, BigDecimal.ZERO);

		for (Map.Entry costoEntrega : costosPorEntrega.entrySet())
			updateCostoOperativoDocumentos(fechaDesde, fechaHasta, (String) costoEntrega.getKey(),
					(BigDecimal) costoEntrega.getValue());
	}

	private void updateCostoOperativoDocumentos(Date fechaDesde, Date fechaHasta, String codigoEntrega, BigDecimal costo) {
		String queryStr = " UPDATE  lfx_documentos xd, documentos d, comprobantes c SET costoOperativo = :costo  "
				+ "WHERE d.empid = xd.empid AND d.docid = xd.docid "
				+ "AND c.empid = :empId AND c.cmpid = d.cmpiddoc "
				+ "AND c.cmptipo IN (1,2,3,4) "
				+ "AND (:entrega IS null OR exists (SELECT * FROM lfx_entrega xe WHERE xe.codigo = xd.entrega_id AND xe.codigo = :entrega)) "
				+ "AND docfecha1 between :fechaDesde AND :fechaHasta AND d.empId = :empId";

		Query query = this.em.createNativeQuery(queryStr).setParameter("fechaDesde", fechaDesde)
				.setParameter("fechaHasta", fechaHasta).setParameter("empId", getEmpId())
				.setParameter("entrega", codigoEntrega).setParameter("costo", costo);
		query.executeUpdate();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<uy.com.tmwc.facturator.entity.ParticipacionVendedor> getParticipaciones(Date fechaDesde,
			Date fechaHasta, String[] compsIncluidos, String[] compsExcluidos) {

		List pv = this.em.createNamedQuery("Documento.participacionesIntervaloFechas")
				.setParameter("empId", getEmpId()).setParameter("fechaDesde", fechaDesde)
				.setParameter("fechaHasta", fechaHasta).setParameter("compsIncluidos", toLongList(compsIncluidos))
				.setParameter("compsExcluidos", toLongList(compsExcluidos))
				.setParameter("incluirTodos", compsIncluidos == null)
				.setParameter("noExcluirNinguno", compsExcluidos == null).getResultList();

		fetchEagerComprobantes();

		fetchComisionesVendedores(pv);

		List<uy.com.tmwc.facturator.entity.ParticipacionVendedor> result = new ArrayList();

		for (Object object : pv) {
			ParticipacionVendedor partVendOrigen = (ParticipacionVendedor) object;

			Documento mappedDocument = (uy.com.tmwc.facturator.entity.Documento) this.mapService.getDozerBeanMapper()
					.map(partVendOrigen.getDocumento(), uy.com.tmwc.facturator.entity.Documento.class);
			Vendedor mappedVendedor = (uy.com.tmwc.facturator.entity.Vendedor) this.mapService.getDozerBeanMapper()
					.map(partVendOrigen.getVendedor(), uy.com.tmwc.facturator.entity.Vendedor.class);

			uy.com.tmwc.facturator.entity.ParticipacionVendedor partVendDest = new uy.com.tmwc.facturator.entity.ParticipacionVendedor();
			partVendDest.setDocumento(mappedDocument);
			partVendDest.setVendedor(mappedVendedor);
			partVendDest.setPorcentaje(partVendOrigen.getPorcentaje());

			result.add(partVendDest);
		}

		return result;
	}

	private void fetchComisionesVendedores(List<ParticipacionVendedor> pvs) {
		for (ParticipacionVendedor pv : pvs) {
			Hibernate.initialize(pv.getVendedor().getComisiones());
		}
	}

	@SuppressWarnings("unused")
	private void fetchLineas(List<ParticipacionVendedor> pvs) {
		for (ParticipacionVendedor pv : pvs) {
			Hibernate.initialize(pv.getDocumento().getLineas());
		}
	}

	private List<Long> toLongList(String[] longStrArray) {
		if (longStrArray == null) {
			return null;
		}
		ArrayList<Long> longList = new ArrayList<Long>();
		for (String longStr : longStrArray) {
			Long parsed;
			try {
				parsed = Long.parseLong(longStr);
			} catch (NumberFormatException nfex) {
				nfex.printStackTrace(System.err);
				System.err.println(nfex);
				continue;
			}
			longList.add(parsed);
		}
		return longList;
	}

	@SuppressWarnings({ "unused", "rawtypes" })
	private void fetchEagerComprobantes() {
		List list = this.em.createNamedQuery("Comprobante.query").setParameter("empId", getEmpId())
				.setParameter("query", "").getResultList();
	}

	@SuppressWarnings({ "unchecked" })
	private List<Object[]> getCobranzasAux(Date fechaDesde, Date fechaHasta, Date fechaCorte) {
		List<Object[]> list = this.em.createNamedQuery("Documento.participacionesEnCobranzasIntervaloFechas")
				.setParameter("empId", getEmpId()).setParameter("fechaDesde", fechaDesde)
				.setParameter("fechaHasta", fechaHasta).setParameter("fechaCorte", fechaCorte).getResultList();

		return list;
	}

	public Map<String, ArrayList<ParticipacionAfilador>> getParticipacionesAfilados(Date fechaDesde, Date fechaHasta,
			BigDecimal value) {
		@SuppressWarnings("unchecked")
		List<Object[]> list = this.em.createNamedQuery("Documento.lineasAfiladoIntervaloFechas")
				.setParameter("empId", getEmpId()).setParameter("fechaDesde", fechaDesde)
				.setParameter("fechaHasta", fechaHasta).getResultList();

		Map<String, ArrayList<ParticipacionAfilador>> map = new HashMap<String, ArrayList<ParticipacionAfilador>>();

		for (Object[] objects : list) {
			ParticipacionAfilador p = new ParticipacionAfilador();

			Linea linea = (Linea) objects[0];
			uy.com.tmwc.facturator.libra.entity.Documento documento = (uy.com.tmwc.facturator.libra.entity.Documento) objects[1];
			Articulo articulo = (uy.com.tmwc.facturator.libra.entity.Articulo) objects[2];

			BigDecimal precio = linea.getPrecio();
			if (precio == null || precio.intValue() < 1) {
				precio = value;
			}

			String afilador = linea.getAfilador() != null ? linea.getAfilador() : "";

			p.setAfilador(afilador);
			p.setItem(articulo.getNombre());
			p.setFecha(documento.getFecha());
			p.setSerieNro(documento.getSerie() + "/" + documento.getNumero() + "-" + linea.getId().getLinId());
			p.setCliente(documento.getCliente().getNombre());
			p.setDientes(linea.getCantidad());
			p.setPrecio(precio);
			p.setImporte(precio.multiply(linea.getCantidad()));
			p.setPercentage(new BigDecimal(25));
			p.setMontoACobrar(p.getImporte().multiply(new BigDecimal(.25)));
			p.setMoneda(documento.getMoneda().getSimbolo());

			if (map.containsKey(afilador)) {
				ArrayList<ParticipacionAfilador> l = map.get(afilador);
				l.add(p);
			} else {
				ArrayList<ParticipacionAfilador> l = new ArrayList<ParticipacionAfilador>();
				l.add(p);
				map.put(afilador, l);
			}
		}

		return map;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ParticipacionEnCobranza> getParticipacionesEnCobranzas(Date fechaDesde, Date fechaHasta, Date fechaCorte) {
		List<Object[]> list = getCobranzasAux(fechaDesde, fechaHasta, fechaCorte);

		ArrayList objParticips = new ArrayList();
		for (Object[] objects : list) {
			uy.com.tmwc.facturator.libra.entity.Documento factura = (uy.com.tmwc.facturator.libra.entity.Documento) objects[1];
			List<uy.com.tmwc.facturator.libra.entity.ParticipacionVendedor> participaciones = factura
					.getParticipaciones();
			for (uy.com.tmwc.facturator.libra.entity.ParticipacionVendedor participacionVendedor : participaciones) {
				objParticips.add(new Object[] { participacionVendedor, (Vinculosdoc) objects[0] });
			}
		}
		list = null;
		List<ParticipacionEnCobranza> res = new Mapper().mapArrayQueryResult(objParticips,
				ParticipacionEnCobranza.class);
		Collections.sort(res, new Comparator<ParticipacionEnCobranza>() {
			private void isOk(ParticipacionEnCobranza p) {
				if (p == null)
					throw new RuntimeException("p == null");
				if (p.getParticipacionVendedor() == null)
					throw new RuntimeException("p.participacionVendedor == null");
				if (p.getParticipacionVendedor().getVendedor() == null)
					throw new RuntimeException("#p.participacionVendedor.vendedor == null, "
							+ p.getParticipacionVendedor().getDocumento());
			}

			public int compare(ParticipacionEnCobranza o1, ParticipacionEnCobranza o2) {
				isOk(o1);
				isOk(o2);

				int c = o1.getParticipacionVendedor().getVendedor().getCodigo()
						.compareTo(o2.getParticipacionVendedor().getVendedor().getCodigo());
				if (c != 0)
					return c;
				Documento r1 = o1.getVinculo().getRecibo();
				Documento r2 = o2.getVinculo().getRecibo();
				c = r1.getComprobante().getCodigo().compareTo(r2.getComprobante().getCodigo());
				if (c != 0)
					return c;
				c = nullSafeCompare(r1.getSerie(), r2.getSerie());
				if (c != 0)
					return c;
				c = nullSafeCompare(r1.getNumero(), r2.getNumero());
				if (c != 0)
					return c;
				Documento f1 = o1.getVinculo().getFactura();
				Documento f2 = o2.getVinculo().getFactura();
				c = f1.getSerie().compareTo(f2.getSerie());
				if (c != 0)
					return c;
				c = f1.getNumero().compareTo(f2.getNumero());
				return c;
			}

			private <T extends Comparable<T>> int nullSafeCompare(T c1, T c2) {
				if ((c1 == null) && (c2 == null))
					return 0;
				if (c1 == null) {
					return -1;
				}
				if (c2 == null) {
					return 1;
				}
				return c1.compareTo(c2);
			}
		});
		return res;
	}

	public Collection<VinculoDocumentos> getCobranzas(Date fechaDesde, Date fechaHasta, Date fechaCorte) {
		List<Object[]> list = getCobranzasAux(fechaDesde, fechaHasta, fechaCorte);

		ArrayList<Vinculosdoc> vinculosCol = new ArrayList<Vinculosdoc>();
		for (Object[] objects : list) {
			vinculosCol.add((Vinculosdoc) objects[0]);
		}
		Collection<VinculoDocumentos> res = new Mapper().mapCollection(vinculosCol, VinculoDocumentos.class);
		return res;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<uy.com.tmwc.facturator.entity.ParticipacionVendedor> getParticipacionesEnContados(Date fechaDesde,
			Date fechaHasta, Date fechaCorte) {
		@SuppressWarnings("unused")
		List dummy = this.em.createNamedQuery("Documento.participacionesEnContadosIntervaloFechas.aux")
				.setParameter("empId", getEmpId()).setParameter("fechaDesde", fechaDesde)
				.setParameter("fechaHasta", fechaHasta).setParameter("fechaCorte", fechaCorte).getResultList();

		List<uy.com.tmwc.facturator.libra.entity.Documento> docs = this.em
				.createNamedQuery("Documento.participacionesEnContadosIntervaloFechas")
				.setParameter("empId", getEmpId()).setParameter("fechaDesde", fechaDesde)
				.setParameter("fechaHasta", fechaHasta).setParameter("fechaCorte", fechaCorte).getResultList();

		ArrayList result2map = new ArrayList();
		for (uy.com.tmwc.facturator.libra.entity.Documento doc : docs) {
			result2map.addAll(doc.getParticipaciones());
		}
		Collections.sort(result2map, new Comparator<uy.com.tmwc.facturator.libra.entity.ParticipacionVendedor>() {
			public int compare(uy.com.tmwc.facturator.libra.entity.ParticipacionVendedor o1,
					uy.com.tmwc.facturator.libra.entity.ParticipacionVendedor o2) {
				int c = o1.getVendedor().getId().getVenId().compareTo(o2.getVendedor().getId().getVenId());
				if (c != 0)
					return c;
				c = new Long(o1.getDocumento().getComprobante().getId().getCmpid()).compareTo(Long.valueOf(o2
						.getDocumento().getComprobante().getId().getCmpid()));
				if (c != 0)
					return c;
				c = o1.getDocumento().getSerie().compareTo(o2.getDocumento().getSerie());
				if (c != 0)
					return c;
				c = o1.getDocumento().getNumero().compareTo(o2.getDocumento().getNumero());
				return c;
			}
		});
		return new ArrayList(new Mapper().mapCollection(result2map,
				uy.com.tmwc.facturator.entity.ParticipacionVendedor.class));
	}

	private int generateDocId() {
		Query query = this.em.createNamedQuery("IdTablaGen.curValue").setParameter("tabla", "Documentos")
				.setParameter("empId", getEmpId());
		IdTablaGen lastId = (IdTablaGen) JPAUtils.getAtMostOne(query);
		this.em.lock(lastId, LockModeType.WRITE);
		this.em.flush();
		return lastId.getIDultimo();
	}

	public List<Documento> getDocumentosDeudoresCliente(String clienteId) {

		// Se hace un left join fetch de las cuotas de los documentos, de otro
		// modo se generaria un problema de N+1. Para las otras entidades
		// que hibernate trae (relaciones xToOne), no esta claro cuanto se gana,
		// ya que depende de las frecuencias en que aparezca cada entidad.
		// si se necesita optimizar luego, se puede, por ejemplo, hacer un fetch
		// previo de Clientes, o forzar un join fetch en la propia consulta.

		String where = "d.comprobante.tipo in (1,2) and d.saldo <> 0 and d.id.empId=:empId and d.cliente.id.cliId=:clienteId ";

		String queryStrFacturas = "SELECT distinct d FROM Documento d left join fetch d.cuotas where " + where
				+ " order by d.fecha, d.cliente.id.cliId, d.comprobante.id.cmpid, d.serie, d.numero";
		Query queryFacturas = this.em.createQuery(queryStrFacturas);
		queryFacturas.setParameter("empId", getEmpId());
		queryFacturas.setParameter("clienteId", clienteId);

		@SuppressWarnings("unchecked")
		List<uy.com.tmwc.facturator.libra.entity.Documento> ormResultFacturas = queryFacturas.getResultList();

		// recibos:
		String queryStrRecibos = "SELECT distinct d FROM Documento d where d.comprobante.tipo = 5 and d.saldo <> 0 and d.id.empId=:empId and d.cliente.id.cliId=:clienteId";
		Query queryRecibos = em.createQuery(queryStrRecibos);
		queryRecibos.setParameter("empId", getEmpId());
		queryRecibos.setParameter("clienteId", clienteId);

		@SuppressWarnings("unchecked")
		List<uy.com.tmwc.facturator.libra.entity.Documento> ormResultRecibos = queryRecibos.getResultList();

		List<uy.com.tmwc.facturator.libra.entity.Documento> ormResult = new ArrayList<uy.com.tmwc.facturator.libra.entity.Documento>();
		ormResult.addAll(ormResultFacturas);
		ormResult.addAll(ormResultRecibos);

		ArrayList<Documento> result = new ArrayList<Documento>(new Mapper().mapCollection(ormResult, Documento.class));

		return result;
	}

	public List<Documento> getDocumentosDeudores() {
		// Se hace un left join fetch de las cuotas de los documentos, de otro
		// modo se generaria un problema de N+1. Para las otras entidades
		// que hibernate trae (relaciones xToOne), no esta claro cuanto se gana,
		// ya que depende de las frecuencias en que aparezca cada entidad.
		// si se necesita optimizar luego, se puede, por ejemplo, hacer un fetch
		// previo de Clientes, o forzar un join fetch en la propia consulta.

		String where = "d.comprobante.tipo in (1,2) and d.saldo <> 0 and d.id.empId=:empId ";

		String queryStrFacturas = "SELECT distinct d FROM Documento d left join fetch d.cuotas where " + where
				+ " order by d.fecha, d.cliente.id.cliId, d.comprobante.id.cmpid, d.serie, d.numero";
		Query queryFacturas = this.em.createQuery(queryStrFacturas);
		queryFacturas.setParameter("empId", getEmpId());
		@SuppressWarnings("unchecked")
		List<uy.com.tmwc.facturator.libra.entity.Documento> ormResultFacturas = queryFacturas.getResultList();

		// recibos:
		String queryStrRecibos = "SELECT distinct d FROM Documento d "
				+ "where d.comprobante.tipo = 5 and d.saldo <> 0 and d.id.empId=:empId";
		Query queryRecibos = em.createQuery(queryStrRecibos);
		queryRecibos.setParameter("empId", getEmpId());
		@SuppressWarnings("unchecked")
		List<uy.com.tmwc.facturator.libra.entity.Documento> ormResultRecibos = queryRecibos.getResultList();

		List<uy.com.tmwc.facturator.libra.entity.Documento> ormResult = new ArrayList<uy.com.tmwc.facturator.libra.entity.Documento>();
		ormResult.addAll(ormResultFacturas);
		ormResult.addAll(ormResultRecibos);

		ArrayList<Documento> result = new ArrayList<Documento>(new Mapper().mapCollection(ormResult, Documento.class));

		return result;
	}

	private void ajustarStock(Articulo articulo, Deposito depositoDesde, Deposito depositoHasta, BigDecimal cantidad) {
		if (depositoDesde != null && depositoHasta != null) {
			ajustarStock(articulo, depositoDesde, cantidad.negate());
			ajustarStock(articulo, depositoHasta, cantidad);
		} else {
			if (depositoDesde != null && depositoHasta == null) { // una venta, va a la nada
				ajustarStock(articulo, depositoDesde, cantidad.negate());
			} else if (depositoDesde == null && depositoHasta != null) { // una devolución, viene de la nada
				ajustarStock(articulo, depositoHasta, cantidad.negate());
			}
		}
	}

	private void ajustarStockCompra(Articulo articulo, Deposito depositoDesde, Deposito depositoHasta, BigDecimal cantidad) {
		if (depositoDesde != null) {
			ajustarStock(articulo, depositoDesde, cantidad.negate());
		}
		if (depositoHasta != null) {
			ajustarStock(articulo, depositoHasta, cantidad);
		}
	}

	private void ajustarStock(Articulo articulo, Deposito deposito, BigDecimal cantidad) {
		if (cantidad.compareTo(BigDecimal.ZERO) == 0)
			return;

		// Notar que las lecturas sobre la tabla de stock manejan bloqueo
		// optimista usando dynamicUpdate / optmisticLock a nivel de Tabla (ver
		// StockActual)

		Stockactual stockActual = getStockActual(articulo, deposito);
		// El registro de stock para el art/deposito puede no existir, las
		// líneas que quedarian en 0 son borrados por libra.
		if (stockActual == null) {
			stockActual = new Stockactual();
			StockactualPK pk = getStockActualPK();
			stockActual.setId(pk);
			stockActual.setArticulo(articulo);
			stockActual.setDeposito(deposito);
			stockActual.setSAcantidad(BigDecimal.ZERO);
			em.persist(stockActual);
		}

		stockActual.add(cantidad);

		// Libra remueve los registros de stock en 0
		if (stockActual.getSAcantidad().compareTo(BigDecimal.ZERO) == 0) {
			em.remove(stockActual);
		}
	}

	private Stockactual getStockActual(Articulo articulo, Deposito deposito) {
		StockactualPK pk = getStockActualPK();
		pk.setArtIdSA(articulo.getCodigo());
		pk.setDepIdSA(deposito.getId().getDepId());
		return em.find(Stockactual.class, pk);
	}

	private StockactualPK getStockActualPK() {
		StockactualPK pk = new StockactualPK();
		pk.setLoteIdSA("");
		pk.setLoteVenceSA(DateUtils.newDate(1000, 1, 1));
		pk.setEmpId(getEmpId());
		return pk;
	}

	public List<StockActual> getStockActual(String articuloId) {
		List<?> ormResult = this.em.createNamedQuery("Stockactual.depositos").setParameter("empId", getEmpId())
				.setParameter("articuloId", articuloId).getResultList();

		return new ArrayList<StockActual>(new Mapper().mapCollection(ormResult, StockActual.class));
	}

	public List<StockActualDTO> getStockActualEnDeposito(String depositoId) {
		List<?> ormResult = this.em.createNamedQuery("Stockactual.deposito").setParameter("empId", getEmpId())
				.setParameter("depositoId", new Short(depositoId)).getResultList();

		return new ArrayList<StockActualDTO>(new Mapper().mapCollection(ormResult, StockActualDTO.class));
	}

	public BigDecimal getStock(String articulo, short deposito) {
		StockactualPK pk = getStockActualPK();
		pk.setArtIdSA(articulo);
		pk.setDepIdSA(deposito);

		Stockactual stockActual = em.find(Stockactual.class, pk);
		return stockActual != null ? stockActual.getSAcantidad() : BigDecimal.ZERO;
	}

	public List<Documento> getDocumentos(Date fechaDesde, Date fechaHasta, String moneda) {
		List<?> ormResult = this.em.createNamedQuery("Documento.controlLineasVenta").setParameter("empId", getEmpId())
				.setParameter("fechaDesde", fechaDesde).setParameter("fechaHasta", fechaHasta)
				.setParameter("moneda", moneda).getResultList();
		return new ArrayList<Documento>(new Mapper().mapCollection(ormResult, Documento.class));
	}

	public int modificarCostos(String codart, Date dateDesde, Date dateHasta, RUTINA_MODIFCOSTO_ENUM costoAnterior,
			BigDecimal valorCostoAnterior, BigDecimal costoNuevo, String monedaNuevoCosto, BigDecimal tcd) {
		return new RutinaModificarCostos(codart, dateDesde, dateHasta, costoAnterior, valorCostoAnterior, costoNuevo,
				monedaNuevoCosto, tcd, em, this).ejecutar();
	}

	public void modificarArticuloPrecio(String codart, String minorista, String industria, String distribuidor)
			throws PermisosException {
		// new RutinaModificarPrecios(codart, "2", new BigDecimal(minorista),
		// new BigDecimal(industria), new BigDecimal(distribuidor), em,
		// this).ejecutar();
	}

	public Boolean finalizarCompra(Documento doc) throws PermisosException {
		DocumentoPK pk = new DocumentoPK();
		pk.setDocId(Integer.parseInt(doc.getDocId()));
		pk.setEmpId(getEmpId());
		uy.com.tmwc.facturator.libra.entity.Documento docEntity = this.em.find(
				uy.com.tmwc.facturator.libra.entity.Documento.class, pk);

		controlModificacionDocumento(docEntity);

		docEntity.setPendiente("N"); // lo marco como no pendiente, remuevo la reserva de articulos

		verifyUniqueSerieNumero(docEntity);

		this.em.merge(docEntity);
		this.em.flush();

		return true;
	}

	public Boolean finalizarGasto(Documento doc) throws PermisosException {
		DocumentoPK pk = new DocumentoPK();
		pk.setDocId(Integer.parseInt(doc.getDocId()));
		pk.setEmpId(getEmpId());
		uy.com.tmwc.facturator.libra.entity.Documento docEntity = this.em.find(
				uy.com.tmwc.facturator.libra.entity.Documento.class, pk);

		controlModificacionDocumento(docEntity);

		docEntity.setPendiente("N"); // lo marco como no pendiente, remuevo la reserva de articulos

		this.em.merge(docEntity);
		this.em.flush();

		return true;
	}

	private BigDecimal convertPrecio(BigDecimal precio, String monedaOrigen, String monedaDestino) {
		if (monedaOrigen == null || monedaDestino == null) {
			return precio;
		}
		if (monedaOrigen.equals(monedaDestino)) {
			return precio;
		}

		Calendar calendar = Calendar.getInstance();
		List<CotizacionesMonedas> tiposCambio = tipoCambioService.getCotizacionesMonedas(calendar.getTime());
		if (tiposCambio.size() < 1) {
			return precio;
		}
		CotizacionesMonedas tipoCambio = tiposCambio.get(0);

		BigDecimal dolarCompra = tipoCambio.getDolarCompra().setScale(SCALE, RoundingMode.HALF_UP);
		BigDecimal dolarVenta = tipoCambio.getDolarVenta().setScale(SCALE, RoundingMode.HALF_UP);
		BigDecimal euroCompra = tipoCambio.getEuroCompra().setScale(SCALE, RoundingMode.HALF_UP);
		BigDecimal euroVenta = tipoCambio.getEuroVenta().setScale(SCALE, RoundingMode.HALF_UP);

		if (monedaOrigen.equals(Moneda.CODIGO_MONEDA_PESOS) || monedaOrigen.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
			if (monedaDestino.equals(Moneda.CODIGO_MONEDA_DOLAR)) {
				return precio.setScale(SCALE, RoundingMode.HALF_UP).divide(dolarCompra, SCALE, RoundingMode.HALF_UP)
						.setScale(SCALE, RoundingMode.HALF_UP);
			} else if (monedaDestino.equals(Moneda.CODIGO_MONEDA_EUROS)) {
				return precio.setScale(SCALE, RoundingMode.HALF_UP).divide(euroCompra, SCALE, RoundingMode.HALF_UP)
						.setScale(SCALE, RoundingMode.HALF_UP);
			}
		} else if (monedaOrigen.equals(Moneda.CODIGO_MONEDA_DOLAR)
				|| monedaOrigen.equals(Moneda.CODIGO_MONEDA_DOLAR_ASTER)) {
			if (monedaDestino.equals(Moneda.CODIGO_MONEDA_PESOS)) {
				return precio.setScale(SCALE, RoundingMode.HALF_UP).multiply(dolarVenta)
						.setScale(SCALE, RoundingMode.HALF_UP);
			} else if (monedaDestino.equals(Moneda.CODIGO_MONEDA_EUROS)) {
				return precio.setScale(SCALE, RoundingMode.HALF_UP)
						.multiply(dolarCompra.divide(euroCompra, SCALE, RoundingMode.HALF_UP))
						.setScale(SCALE, RoundingMode.HALF_UP);
			}
		} else if (monedaOrigen.equals(Moneda.CODIGO_MONEDA_EUROS)
				|| monedaOrigen.equals(Moneda.CODIGO_MONEDA_EUROS_ASTER)) {
			if (monedaDestino.equals(Moneda.CODIGO_MONEDA_PESOS)) {
				return precio.setScale(SCALE, RoundingMode.HALF_UP).multiply(euroVenta)
						.setScale(SCALE, RoundingMode.HALF_UP);
			} else if (monedaDestino.equals(Moneda.CODIGO_MONEDA_DOLAR)) {
				return precio.setScale(SCALE, RoundingMode.HALF_UP).multiply(euroVenta)
						.divide(dolarVenta, SCALE, RoundingMode.HALF_UP).setScale(SCALE, RoundingMode.HALF_UP);
			}
		}

		return precio;
	}

	public void updateArticulosPrecios(List<ArticuloPrecioFabricaCosto> lista, Boolean updateCosto) {
		for (ArticuloPrecioFabricaCosto articuloPrecios : lista) {
			if (!updateCosto) {
				ArticuloPrecio precioFabrica = catalogService.getPrecioArticulo("7", articuloPrecios.getCodigo());
				if (precioFabrica == null) {
					precioFabrica = new ArticuloPrecio();
					precioFabrica.setArtId(articuloPrecios.getCodigo());
					precioFabrica.setMndIdPrecio(articuloPrecios.getNuevaMonedaId());
					precioFabrica.setPrecioBaseId("01");
					precioFabrica.setPrecioBaseConIVA(false);
					precioFabrica.setPrecio(articuloPrecios.getPrecioNuevo());
				} else {
					precioFabrica.setMndIdPrecio(articuloPrecios.getNuevaMonedaId());
					precioFabrica.setPrecio(articuloPrecios.getPrecioNuevo());
				}
				artService.updateArticuloPrecio(precioFabrica);

			} else if (updateCosto) {
				uy.com.tmwc.facturator.entity.Articulo articulo = catalogService.findCatalogEntity(
						Articulo.class.getSimpleName(), articuloPrecios.getCodigo());

				articulo.setCosto(articuloPrecios.getPrecioNuevo());
				articulo.setFechaCosto(new Date());
				articulo.setMndIdArtCosto(new Short(articuloPrecios.getNuevaMonedaId()));

				artService.merge(articulo);
			}
		}

	}

	public void updateArticulosCostos(List<ArticuloPrecioFabricaCosto> lista) {
		for (ArticuloPrecioFabricaCosto articuloCosto : lista) {
			uy.com.tmwc.facturator.entity.Articulo articulo = catalogService.findCatalogEntity(
					Articulo.class.getSimpleName(), articuloCosto.getCodigo());

			articulo.setCosto(articuloCosto.getPrecioNuevo());
			articulo.setFechaCosto(new Date());
			articulo.setMndIdArtCosto(new Short(articuloCosto.getNuevaMonedaId()));

			artService.merge(articulo);
		}

	}

	public void updateCostosArticuloDocumentos(List<ArticuloCompraVentaCosto> lista) throws PermisosException {
		for (ArticuloCompraVentaCosto articuloCosto : lista) {
			uy.com.tmwc.facturator.entity.Articulo articulo = catalogService.findCatalogEntity(
					Articulo.class.getSimpleName(), articuloCosto.getCodigo());

			articulo.setCosto(articuloCosto.getCostoCompra());
			articulo.setMndIdArtCosto(new Short(articuloCosto.getCompraMonedaId()));
			articulo.setFechaCosto(new Date());

			artService.merge(articulo);

			if (articuloCosto.getDocVentaId() == null || articuloCosto.getDocVentaId().length() == 0) {
				continue;
			}
			uy.com.tmwc.facturator.libra.entity.Documento libraDoc = (uy.com.tmwc.facturator.libra.entity.Documento) this.em
					.find(uy.com.tmwc.facturator.libra.entity.Documento.class,
							new DocumentoPK(getEmpId(), Integer.parseInt(articuloCosto.getDocVentaId())));

			if (libraDoc == null) {
				continue;
			}

			Documento documento = (uy.com.tmwc.facturator.entity.Documento) this.mapService.getDozerBeanMapper().map(
					libraDoc, uy.com.tmwc.facturator.entity.Documento.class);

			Boolean hayCambios = false;
			for (uy.com.tmwc.facturator.entity.LineaDocumento l : documento.getLineas().getLineas()) {
				String codArt = l.getArticulo() != null ? l.getArticulo().getCodigo() : null;
				if (codArt != null && codArt.equals(articulo.getCodigo())) {
					String moneda = Moneda.getCodigoMonedaNoAster(documento.getMoneda().getCodigo());
					if (!articuloCosto.getCompraMonedaId().equals(moneda)) {
						BigDecimal nuevoCosto = convertPrecio(articuloCosto.getCostoCompra(),
								articuloCosto.getCompraMonedaId(), moneda);
						if (l.getCosto() != null && l.getCosto().compareTo(nuevoCosto) != 0) {
							l.setCosto(nuevoCosto);
							hayCambios = true;
						}
					} else {
						if (l.getCosto() != null && l.getCosto().compareTo(articuloCosto.getCostoCompra()) != 0) {
							l.setCosto(articuloCosto.getCostoCompra());
							hayCambios = true;
						}
					}
					break;
				}
			}
			if (hayCambios) {
				merge(documento);
			}
		}

	}

	public List<ArticuloCompraVentaCosto> getComprasPlazaCostos(Date fechaDesde, Date fechaHasta, Boolean mostrarTodas) {
		List<ArticuloCompraVentaCosto> costos = new ArrayList<ArticuloCompraVentaCosto>();

		String[] compsIncluidos = { "102", "103", "202", "203" };

		@SuppressWarnings("unchecked")
		List<uy.com.tmwc.facturator.libra.entity.Documento> documentos = this.em
				.createNamedQuery("Documento.comprasMercPlaza").setParameter("empId", getEmpId())
				.setParameter("compsIncluidos", toLongList(compsIncluidos)).setParameter("fechaDesde", fechaDesde)
				.setParameter("fechaHasta", fechaHasta).getResultList();

		for (uy.com.tmwc.facturator.libra.entity.Documento doc : documentos) {

			BigDecimal descuentoProveedor = BigDecimal.ZERO;
			if (doc.getProveedor() != null && doc.getProveedor().getDescuentoRecibo() != null) {
				descuentoProveedor = new BigDecimal(doc.getProveedor().getDescuentoRecibo());
			}

			for (uy.com.tmwc.facturator.libra.entity.Linea linea : doc.getLineas()) {
				uy.com.tmwc.facturator.libra.entity.Articulo articulo = linea.getArticulo();

				BigDecimal descuentoLinea = linea.getDescuento().setScale(SCALE, RoundingMode.HALF_UP);
				BigDecimal descuentos = descuentoProveedor.add(descuentoLinea).setScale(SCALE, RoundingMode.HALF_UP);

				if (linea.getPrecio() == null) {
					linea.setPrecio(BigDecimal.ZERO);
				}

				BigDecimal costoNeto = linea.getPrecio().subtract(
						linea.getPrecio().multiply(descuentos.setScale(SCALE, RoundingMode.HALF_UP))
								.divide(new BigDecimal("100").setScale(SCALE, RoundingMode.HALF_UP)));

				ArticuloCompraVentaCosto cvcArticulo = new ArticuloCompraVentaCosto();
				cvcArticulo.setCodigo(articulo.getCodigo());
				cvcArticulo.setNombre(articulo.getNombre());
				cvcArticulo.setFichaMonedaId(articulo.getMonedaCosto() != null ? articulo.getMonedaCosto().getCodigo()
						: "2");
				cvcArticulo.setCosto(articulo.getMonedaCosto() != null ? articulo.getCosto() : BigDecimal.ZERO);

				cvcArticulo.setDocCompraId(doc.getDocId());
				cvcArticulo.setCompraMonedaId(doc.getMoneda().getCodigo());
				cvcArticulo.setCostoCompraDescuento(descuentos);
				cvcArticulo.setCostoCompraSinDescuentos(linea.getPrecio().setScale(SCALE, RoundingMode.HALF_UP));
				cvcArticulo.setCostoCompra(costoNeto);

				cvcArticulo.setComprobanteCompra((doc.getSerie() != null ? doc.getSerie() : "")
						+ (doc.getNumero() != null ? doc.getNumero().intValue() : ""));

				if (linea.getNotas() != null) {
					String notas = linea.getNotas();
					String[] keys = notas.split("\n");

					if (keys.length >= 3) {
						String clienteId = keys[0];
						BigInteger docNumero;

						try {
							docNumero = new BigInteger(keys[1] != null ? keys[1] : "0");
							@SuppressWarnings("unchecked")
							List<uy.com.tmwc.facturator.libra.entity.Documento> ormResult = this.em
									.createNamedQuery("Documento.obtenerDocumentoClienteNumero")
									.setParameter("empId", getEmpId()).setParameter("docNro", docNumero)
									.setParameter("clienteId", clienteId).getResultList();

							if (ormResult.size() > 0) {
								uy.com.tmwc.facturator.libra.entity.Documento docEntity = (uy.com.tmwc.facturator.libra.entity.Documento) ormResult
										.get(0);
								for (Linea l : docEntity.getLineas()) {
									String codArt = l.getArticulo() != null ? l.getArticulo().getCodigo() : null;
									if (codArt != null && codArt.equals(articulo.getCodigo())) {
										cvcArticulo.setDocVentaId(String.valueOf(docEntity.getId().getDocId()));
										cvcArticulo.setVentaMonedaId(doc.getMoneda().getCodigo());
										cvcArticulo.setComprobanteVenta((docEntity.getSerie() != null ? docEntity
												.getSerie() : "") + docEntity.getNumero().intValue());
										cvcArticulo.setCostoVenta(convertPrecio(
												l.getCosto().setScale(SCALE, RoundingMode.HALF_UP), docEntity
														.getMoneda().getCodigo(), doc.getMoneda().getCodigo()));

										if (!mostrarTodas) {
											costos.add(cvcArticulo);
										}
										break;
									}
								}
							}
						} catch (NumberFormatException e) {
							e.printStackTrace();
						}

					}
					if (mostrarTodas) {
						costos.add(cvcArticulo);
					}
				}
			}
		}

		return costos;

	}

	public List<ArticuloCompraVentaCosto> getCompraVentaCostos(uy.com.tmwc.facturator.entity.Documento doc) {
		List<ArticuloCompraVentaCosto> costos = new ArrayList<ArticuloCompraVentaCosto>();

		ProveedorPK pk = new ProveedorPK();
		pk.setPrvId(doc.getProveedor().getCodigo());
		pk.setEmpId(getEmpId());
		uy.com.tmwc.facturator.libra.entity.Proveedor proveedor = (uy.com.tmwc.facturator.libra.entity.Proveedor) this.em
				.find(uy.com.tmwc.facturator.libra.entity.Proveedor.class, pk);

		BigDecimal descuentoProveedor = BigDecimal.ZERO;
		try {
			if (proveedor != null && proveedor.getDescuentoRecibo() != null
					&& proveedor.getDescuentoRecibo().trim().length() > 0) {
				descuentoProveedor = new BigDecimal(proveedor.getDescuentoRecibo());
			}
		} catch (NumberFormatException ex) {
			ex.printStackTrace();

		}

		List<LineaDocumento> lineas = doc.getLineas().getLineas();
		for (LineaDocumento linea : lineas) {
			ArticuloPK ak = new ArticuloPK();
			ak.setArtId(linea.getArticulo().getCodigo());
			ak.setEmpId(getEmpId());

			uy.com.tmwc.facturator.libra.entity.Articulo articulo = (uy.com.tmwc.facturator.libra.entity.Articulo) this.em
					.find(uy.com.tmwc.facturator.libra.entity.Articulo.class, ak);

			BigDecimal descuentoLinea = linea.getDescuento().setScale(SCALE, RoundingMode.HALF_UP);
			BigDecimal descuentos = descuentoProveedor.add(descuentoLinea).setScale(SCALE, RoundingMode.HALF_UP);
			BigDecimal costoNeto = linea.getPrecio().subtract(
					linea.getPrecio().multiply(descuentos)
							.divide(new BigDecimal("100").setScale(SCALE, RoundingMode.HALF_UP)));

			ArticuloCompraVentaCosto cvcArticulo = new ArticuloCompraVentaCosto();
			cvcArticulo.setCodigo(articulo.getCodigo());
			cvcArticulo.setNombre(articulo.getNombre());
			cvcArticulo.setFichaMonedaId(articulo.getMonedaCosto().getCodigo());
			cvcArticulo.setCosto(articulo.getCosto());

			cvcArticulo.setDocCompraId(doc.getDocId());
			cvcArticulo.setCostoCompraDescuento(descuentos);
			cvcArticulo.setCostoCompraSinDescuentos(linea.getPrecio().setScale(SCALE, RoundingMode.HALF_UP));
			cvcArticulo.setCostoCompra(costoNeto);
			cvcArticulo.setCompraMonedaId(doc.getMoneda().getCodigo());

			cvcArticulo.setComprobanteCompra((doc.getSerie() != null ? doc.getSerie() : "")
					+ String.valueOf(doc.getNumero().intValue()));

			costos.add(cvcArticulo);

			if (linea.getNotas() != null) {
				String notas = linea.getNotas();
				String[] keys = notas.split("\n");
				try {
					if (keys.length >= 3) {
						String clienteId = keys[0];
						String docNumero = keys[1];

						@SuppressWarnings("unchecked")
						List<uy.com.tmwc.facturator.libra.entity.Documento> ormResult = this.em
								.createNamedQuery("Documento.obtenerDocumentoClienteNumero")
								.setParameter("empId", getEmpId()).setParameter("docNro", new BigInteger(docNumero))
								.setParameter("clienteId", clienteId).getResultList();

						if (ormResult.size() > 0) {
							uy.com.tmwc.facturator.libra.entity.Documento docEntity = (uy.com.tmwc.facturator.libra.entity.Documento) ormResult
									.get(0);
							for (Linea l : docEntity.getLineas()) {
								String codArt = l.getArticulo() != null ? l.getArticulo().getCodigo() : null;
								if (codArt != null && codArt.equals(articulo.getCodigo())) {
									cvcArticulo.setDocVentaId(String.valueOf(docEntity.getId().getDocId()));
									cvcArticulo.setComprobanteVenta((docEntity.getSerie() != null ? docEntity
											.getSerie() : "") + String.valueOf(docEntity.getNumero().intValue()));
									cvcArticulo.setVentaMonedaId(doc.getMoneda().getCodigo());
									cvcArticulo.setCostoVenta(convertPrecio(
											l.getCosto().setScale(SCALE, RoundingMode.HALF_UP), docEntity.getMoneda()
													.getCodigo(), doc.getMoneda().getCodigo()));
									break;
								}
							}
						}
					}

				} catch (NumberFormatException e) {
					e.printStackTrace();
				}
			}

		}

		return costos;
	}

	public List<ArticuloPrecioFabricaCosto> getCostoArticulos(Documento doc) {
		List<ArticuloPrecioFabricaCosto> precios = new ArrayList<ArticuloPrecioFabricaCosto>();

		String nuevaMonedaId = doc.getMoneda().getCodigo();

		List<LineaDocumento> lineas = doc.getLineas().getLineas();
		for (LineaDocumento linea : lineas) {
			ArticuloPK ak = new ArticuloPK();
			ak.setArtId(linea.getArticulo().getCodigo());
			ak.setEmpId(getEmpId());

			uy.com.tmwc.facturator.libra.entity.Articulo articulo = (uy.com.tmwc.facturator.libra.entity.Articulo) this.em
					.find(uy.com.tmwc.facturator.libra.entity.Articulo.class, ak);

			ArticuloPrecioFabricaCosto costoArticulo = new ArticuloPrecioFabricaCosto();
			costoArticulo.setCodigo(articulo.getCodigo());
			costoArticulo.setNombre(articulo.getNombre());
			costoArticulo.setViejaMonedaId(String.valueOf(articulo.getMonedaCosto().getId().getMndId()));
			costoArticulo.setNuevaMonedaId(nuevaMonedaId);

			BigDecimal viejoCosto = articulo.getCosto().setScale(SCALE, RoundingMode.HALF_UP);
			BigDecimal nuevoCosto = linea.getPrecioUnitario().setScale(SCALE, RoundingMode.HALF_UP);

			if (nuevaMonedaId.equals(costoArticulo.getViejaMonedaId())) {
				nuevoCosto = linea.getPrecioUnitario().setScale(SCALE, RoundingMode.HALF_UP);
			} else {
				nuevoCosto = convertPrecio(linea.getPrecioUnitario().setScale(SCALE, RoundingMode.HALF_UP),
						costoArticulo.getNuevaMonedaId(), costoArticulo.getViejaMonedaId());
			}
			costoArticulo.setPrecioViejo(viejoCosto);
			costoArticulo.setPrecioNuevo(nuevoCosto);

			Boolean changeCostoArticulo = viejoCosto.compareTo(nuevoCosto) != 0;
			if (changeCostoArticulo) {
				precios.add(costoArticulo);
			}

		}

		return precios;
	}

	public List<ArticuloPrecioFabricaCosto> getPreciosArticuloDocumento(uy.com.tmwc.facturator.entity.Documento doc) {
		DocumentoPK pk = new DocumentoPK();
		pk.setDocId(Integer.parseInt(doc.getDocId()));
		pk.setEmpId(getEmpId());

		List<DocumentoDTO> documentsImp = doc.getProcessId() != null ? getSolicitudImportacion(doc.getProcessId())
				: getSolicitudImportacion(doc.getDocId());

		uy.com.tmwc.facturator.libra.entity.Documento current = (uy.com.tmwc.facturator.libra.entity.Documento) this.em
				.find(uy.com.tmwc.facturator.libra.entity.Documento.class, pk);

		uy.com.tmwc.facturator.libra.entity.Documento libraDoc = toLibraDocumento(doc, current);

		List<ArticuloPrecioFabricaCosto> precios = new ArrayList<ArticuloPrecioFabricaCosto>();

		String nuevaMonedaId = libraDoc.getMoneda().getCodigo();

		List<Linea> lineas = libraDoc.getLineas();
		for (Linea linea : lineas) {
			ArticuloPrecio precioFabrica = catalogService.getPrecioArticulo("7", linea.getArticulo().getCodigo());

			if (linea.getArticulo() == null) {
				continue;
			}
			Articulo articulo = linea.getArticulo();

			ArticuloPrecio precioFabrica = catalogService.getPrecioArticulo("7", articulo.getCodigo());
			if (precioFabrica == null) {
				precioFabrica = new ArticuloPrecio(linea.getArticulo().getCodigo(), "01", BigDecimal.ZERO, Short.valueOf(nuevaMonedaId), "N");
			}

			// Si el precio cambia de moneda hay que ajustarlo
			if (precioFabrica.getMoneda().getCodigo().equals(nuevaMonedaId)) {
				BigDecimal nuevoPrecioFabrica = convertPrecio(precioFabrica.getPrecio(), precioFabrica.getMoneda().getCodigo(), nuevaMonedaId);
				precioFabrica.setPrecio(nuevoPrecioFabrica);
				precioFabrica.setMoneda(doc.getMoneda());
				precioFabrica = new ArticuloPrecio(articulo.getCodigo(), "01", BigDecimal.ZERO, Short.valueOf("2"), "N");
			}

			ArticuloPrecioFabricaCosto preciosArticulo = new ArticuloPrecioFabricaCosto();
			preciosArticulo.setCodigo(articulo.getCodigo());
			preciosArticulo.setNombre(articulo.getNombre());
			preciosArticulo.setNuevaMonedaId(nuevaMonedaId);

			if (doc.getComprobante().getCodigo().equals("121")) {
				BigDecimal vPrecioFabrica = precioFabrica.getPrecio() != null ? precioFabrica.getPrecio()
						: BigDecimal.ZERO;
				BigDecimal nPrecioFabrica = linea.getPrecio() != null ? linea.getPrecio() : BigDecimal.ZERO;

				BigDecimal viejoPrecioFab = vPrecioFabrica.setScale(SCALE, RoundingMode.HALF_UP);
				BigDecimal nuevoPrecioFab = nPrecioFabrica.setScale(SCALE, RoundingMode.HALF_UP);

				preciosArticulo.setViejaMonedaId(precioFabrica.getMndIdPrecio());
				preciosArticulo.setPrecioViejo(viejoPrecioFab);
				preciosArticulo.setPrecioNuevo(nuevoPrecioFab);

				BigDecimal pf1 = viejoPrecioFab;
				BigDecimal pf2 = nuevoPrecioFab;

				Boolean changePrecioArticulo = pf1.compareTo(pf2) != 0;
				if (changePrecioArticulo) {
					precios.add(preciosArticulo);
				}

			} else if (doc.getComprobante().getCodigo().equals("122")) {
				BigDecimal stock = getStock(linea.getArticuloId(), (short) 1).setScale(SCALE, RoundingMode.HALF_UP);
				if (documentsImp != null && documentsImp.size() > 0) {
					stock = stock.subtract(linea.getCantidad()).setScale(SCALE, RoundingMode.HALF_UP);
				}
				BigDecimal nuevoCoeficienteImp = linea.getCoeficienteImp() != null ? linea.getCoeficienteImp()
						.setScale(SCALE, RoundingMode.HALF_UP) : libraDoc.getCoeficienteImp().setScale(SCALE,
						RoundingMode.HALF_UP);

				uy.com.tmwc.facturator.entity.Articulo art = catalogService.findCatalogEntity(
						Articulo.class.getSimpleName(), linea.getArticulo().getCodigo());

				String viejaMonedaId = art.getMonedaCosto().getCodigo();
				preciosArticulo.setViejaMonedaId(viejaMonedaId);
				preciosArticulo.setPrecioViejo(art.getCosto().setScale(SCALE, RoundingMode.HALF_UP));

				BigDecimal precio = precioFabrica.getPrecio() != null ? precioFabrica.getPrecio() : BigDecimal.ZERO;

				BigDecimal nuevoCostoMontevideo;
				if (nuevaMonedaId.equals(precioFabrica.getMndIdPrecio())) {
					nuevoCostoMontevideo = precio.setScale(SCALE, RoundingMode.HALF_UP)
							.multiply(nuevoCoeficienteImp.setScale(SCALE, RoundingMode.HALF_UP))
							.setScale(SCALE, RoundingMode.HALF_UP);
				} else {
					BigDecimal ncm = convertPrecio(precio.setScale(SCALE, RoundingMode.HALF_UP),
							precioFabrica.getMndIdPrecio(), nuevaMonedaId);
					nuevoCostoMontevideo = ncm.setScale(SCALE, RoundingMode.HALF_UP).multiply(nuevoCoeficienteImp)
							.setScale(SCALE, RoundingMode.HALF_UP);
				}

				BigDecimal costo = linea.getCosto() != null ? linea.getCosto() : BigDecimal.ZERO;

				BigDecimal viejoCostoMontevideo = costo.setScale(SCALE, RoundingMode.HALF_UP);
				if (viejoCostoMontevideo.doubleValue() > 0 && stock.compareTo(BigDecimal.ZERO) == 1) {
					BigDecimal s1 = viejoCostoMontevideo
							.multiply(stock)
							.add(nuevoCostoMontevideo.multiply(linea.getCantidad()
									.setScale(SCALE, RoundingMode.HALF_UP))).setScale(SCALE, RoundingMode.HALF_UP);
					BigDecimal s2 = stock.add(linea.getCantidad().setScale(SCALE, RoundingMode.HALF_UP)).setScale(
							SCALE, RoundingMode.HALF_UP);

					BigDecimal pp = s1;
					if (s2.compareTo(BigDecimal.ZERO) == 1) {
						pp = s1.divide(s2, SCALE, RoundingMode.HALF_UP);
					}
					preciosArticulo.setPrecioNuevo(pp);

				} else {
					preciosArticulo.setPrecioNuevo(nuevoCostoMontevideo);
				}

				BigDecimal cto1 = preciosArticulo.getPrecioViejo() != null ? preciosArticulo.getPrecioViejo().setScale(
						SCALE, RoundingMode.HALF_UP) : BigDecimal.ZERO;
				BigDecimal cto2 = preciosArticulo.getPrecioNuevo() != null ? preciosArticulo.getPrecioNuevo().setScale(
						SCALE, RoundingMode.HALF_UP) : BigDecimal.ZERO;

				Boolean changeCostoArticulo = cto1.compareTo(cto2) != 0;
				if (changeCostoArticulo) {
					precios.add(preciosArticulo);
				}

			}

		}

		return precios;
	}

	public Map<String, Object[]> getParticipacionesCobranza(Date paramDate1, Date paramDate2, String[] compsIncluidos,
			String[] compsExcluidos) {
		return null;
	}

	public Boolean updateNotaCreditoFinancieraEnRecibo(Documento doc, String ncfId) throws PermisosException {
		DocumentoPK pk = new DocumentoPK();
		pk.setDocId(Integer.parseInt(doc.getDocId()));
		pk.setEmpId(getEmpId());
		uy.com.tmwc.facturator.libra.entity.Documento docEntity = this.em.find(
				uy.com.tmwc.facturator.libra.entity.Documento.class, pk);

		controlModificacionDocumento(docEntity);

		docEntity.setProcessId(ncfId);

		this.em.merge(docEntity);
		this.em.flush();

		return true;
	}

}