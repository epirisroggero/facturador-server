package uy.com.tmwc.facturator.libra.ejb;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.libra.entity.Linea;
import uy.com.tmwc.facturator.libra.entity.Moneda;
import uy.com.tmwc.facturator.rapi.RUTINA_MODIFCOSTO_ENUM;
import uy.com.tmwc.facturator.spi.DocumentoDAOService;
import uy.com.tmwc.facturator.utils.LogicaCotizacion;

public class RutinaModificarCostos {
	private String codart;
	private Date dateDesde;
	private Date dateHasta;
	private RUTINA_MODIFCOSTO_ENUM costoAnterior;
	private BigDecimal valorCostoAnterior;
	private BigDecimal costoNuevo;
	private String monedaNuevoCosto;
	private BigDecimal tcd;
	private EntityManager em;
	private DocumentoDAOService documentoDAOService;

	public RutinaModificarCostos(String codart, Date dateDesde, Date dateHasta, RUTINA_MODIFCOSTO_ENUM costoAnterior,
			BigDecimal valorCostoAnterior, BigDecimal costoNuevo, String monedaNuevoCosto, BigDecimal tcd,
			EntityManager em, DocumentoDAOService documentoDAOService) {
		super();
		this.codart = codart;
		this.dateDesde = dateDesde;
		this.dateHasta = dateHasta;
		this.costoAnterior = costoAnterior;
		this.valorCostoAnterior = valorCostoAnterior;
		this.costoNuevo = costoNuevo;
		this.monedaNuevoCosto = monedaNuevoCosto;
		this.tcd = tcd;
		this.em = em;
		this.documentoDAOService = documentoDAOService;
	}

	public int ejecutar() {
		if (costoAnterior == RUTINA_MODIFCOSTO_ENUM.costoEspecifico ^ valorCostoAnterior != null) {
			throw new RuntimeException(
					"el costo específico debe establecerse si y solo si se especifica el valor numérico del costo");
		}
		Query query = crearQuery();

		@SuppressWarnings("unchecked")
		List<Linea> list = query.getResultList();
		return procesarLineas(list);
	}

	private Query crearQuery() {
		String queryStr = "SELECT l from Linea l WHERE l.documento.comprobante.tipo in ("
				+ Comprobante.DEVOLUCION_CONTADO + "," + Comprobante.NOTA_CREDITO + "," + Comprobante.VENTA_CONTADO
				+ "," + Comprobante.VENTA_CREDITO + ") AND l.articulo.id.artId = :articulo "
				+ " AND l.documento.fecha >= :fechaDesde "
				+ (dateHasta == null ? "" : " AND l.documento.fecha <= :fechaHasta ");
		Query query = em.createQuery(queryStr);
		query.setParameter("articulo", codart);
		query.setParameter("fechaDesde", dateDesde);
		if (dateHasta != null) {
			query.setParameter("fechaHasta", dateHasta);
		}
		return query;
	}

	private int procesarLineas(List<Linea> list) {
		int nruter = 0;
		for (Linea linea : list) {
			boolean updateRow = false;
			if (costoAnterior == RUTINA_MODIFCOSTO_ENUM.costoCualquiera) {
				updateRow = true;
			} else if (costoAnterior == RUTINA_MODIFCOSTO_ENUM.costoNoEstablecido) {
				updateRow = linea.getCosto() == null || linea.getCosto().compareTo(BigDecimal.ZERO) == 0;
			} else if (costoAnterior == RUTINA_MODIFCOSTO_ENUM.costoEspecifico) {
				updateRow = linea.getCosto() != null && valorCostoAnterior.compareTo(linea.getCosto()) == 0;
			}
			if (updateRow) {
				boolean procesada = procesarLinea(linea);
				if (procesada) {
					nruter++;
				}
			}
		}
		em.flush();
		return nruter;
	}

	private boolean procesarLinea(Linea linea) {
		Moneda moneda = linea.getMonedaDocumento();
		if (moneda != null) {
			BigDecimal convertido = null;
			if (moneda != null && !moneda.getCodigo().equals(monedaNuevoCosto)) {
				convertido = convertir(costoNuevo, monedaNuevoCosto, moneda.getCodigo(), tcd);
			} else {
				convertido = costoNuevo;
			}
			linea.setCosto(convertido);
			return true;
		} else {
			return false;
		}
	}

	private BigDecimal convertir(BigDecimal costoNuevo, String monedaOrigen, String monedaDestino, BigDecimal tcd) {
		return costoNuevo.multiply(getTipoCambio(monedaOrigen, monedaDestino, tcd));
	}

	private BigDecimal getTipoCambio(String morigen, String modestino, BigDecimal tcd) {
		morigen = uy.com.tmwc.facturator.entity.Moneda.getCodigoMonedaNoAster(morigen);
		modestino = uy.com.tmwc.facturator.entity.Moneda.getCodigoMonedaNoAster(modestino);
		return new LogicaCotizacion(Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_DOLAR).getTipoCambio(morigen,
				modestino, null, new LogicaCotizacion.GetTipoCambio() {
					public BigDecimal getTipoCambio(String monedaOrigen, Date fecha) {
						return documentoDAOService.getTipoCambio(monedaOrigen, fecha);
					}
				}, tcd);

	}
}
