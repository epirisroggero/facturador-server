package uy.com.tmwc.facturator.liquidacion;

import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.deudores.CodigoNombreFactory;
import uy.com.tmwc.facturator.deudores.DocumentoDeudor;
import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.dto.ParticipacionEnCobranza;
import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.entity.CotizacionesMonedas;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.IAportaRenta;
import uy.com.tmwc.facturator.entity.Jefatura;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.ParticipacionVendedor;
import uy.com.tmwc.facturator.entity.RedistribucionRentaLinea;
import uy.com.tmwc.facturator.entity.RedistribucionRentasJefaturas;
import uy.com.tmwc.facturator.entity.ResumenEntrega;
import uy.com.tmwc.facturator.entity.ResumenEntregas;
import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.entity.Vendedor;
import uy.com.tmwc.facturator.entity.VinculoDocumentos;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.rapi.LiquidacionService;
import uy.com.tmwc.facturator.rapi.MonedasCotizacionesService;
import uy.com.tmwc.facturator.rapi.PermisosException;
import uy.com.tmwc.facturator.rapi.UsuariosService;
import uy.com.tmwc.facturator.session.RentaCobranza;
import uy.com.tmwc.facturator.session.RentaContado;
import uy.com.tmwc.facturator.spi.CatalogDAOService;
import uy.com.tmwc.facturator.spi.DocumentoDAOService;
import uy.com.tmwc.facturator.utils.Maths;

import com.csvreader.CsvWriter;

@Name("liquidacionService")
@Stateless
@Local({ LiquidacionService.class })
@Remote({ LiquidacionService.class })
public class LiquidacionServiceImpl implements LiquidacionService {

	@EJB
	DocumentoDAOService documentoDAOService;

	@EJB
	CatalogDAOService catalogDAOService;

	@EJB
	CatalogService catalogService;

	@EJB
	UsuariosService usuariosService;

	@EJB
	MonedasCotizacionesService tipoCambioService;

	private static final int TEMP_DIR_ATTEMPTS = 10000;
	
	private SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");

	private void generarReporteResumenEntregas(Date fechaDesde, Date fechaHasta, BigDecimal gastosPeriodo, File dirBase) {
		ResumenEntregas resumen = calcularCostosOperativos(fechaDesde, fechaHasta, gastosPeriodo);
		try {
			File tmpFilePath = new File(dirBase, "resumen-entregas.csv");
			CsvWriter w = new CsvWriter(tmpFilePath.getPath());
			try {
				Map<String, BigDecimal> map = resumen.getAsMap();
				w.write("CODIGO ENTREGA");
				w.write("COSTO");
				w.endRecord();
				for (Map.Entry<String, BigDecimal> entry : map.entrySet()) {
					w.write(entry.getKey());
					w.write(entry.getValue().toString());
					w.endRecord();
				}
			} finally {
				w.close();
			}

		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public ResumenEntregas calcularCostosOperativos(Date fechaDesde, Date fechaHasta, BigDecimal gastosPeriodo) {
		ResumenEntregas resumenEntregas = getResumenEntregas(fechaDesde, fechaHasta, gastosPeriodo);
		this.documentoDAOService.grabarCostosOperativos(fechaDesde, fechaHasta, resumenEntregas.getAsMap());
		return resumenEntregas;
	}

	public ResumenEntregas getResumenEntregas(Date fechaDesde, Date fechaHasta, BigDecimal gastosPeriodo) {
		List<ResumenEntrega> resumenEntregaList = this.documentoDAOService.getResumenEntregas(fechaDesde, fechaHasta);
		ResumenEntregas resumenEntregas = new ResumenEntregas(resumenEntregaList, gastosPeriodo);
		return resumenEntregas;
	}

	public TreeMap<CodigoNombre, ArrayList<uy.com.tmwc.facturator.liquidacion.ParticipacionVendedor>> reporteVentasPeriodo(
			Date fechaDesde, Date fechaHasta) {
		return null;
	}

	/*private LiquidacionVendedor ensureLiquidacionVendedor(HashMap<String, LiquidacionVendedor> mapLiquidaciones,
			CodigoNombre vendedor) {
		String codigoVendedor = vendedor.getCodigo();
		LiquidacionVendedor lv = (LiquidacionVendedor) mapLiquidaciones.get(codigoVendedor);
		if (lv == null) {
			lv = new LiquidacionVendedor(vendedor);
			mapLiquidaciones.put(codigoVendedor, lv);
		}
		return lv;
	}

	private TreeMap<CodigoNombre, ArrayList<uy.com.tmwc.facturator.liquidacion.ParticipacionVendedor>> agruparVendedores(
			Collection<uy.com.tmwc.facturator.liquidacion.ParticipacionVendedor> mapped) {
		AgruparVendedores corteControl = new AgruparVendedores();
		corteControl.process(mapped);
		TreeMap grupos = corteControl.getParticipacionesPorVendedor();
		return grupos;
	}*/

	/**
	 * 
	 * No hecho: - filtro fecha (solo puede hacerse en backend) - notar que anula la utilidad de docSaldo - vinculos notas de credito: creo que no es necesario hacerle caso. Si se
	 * vincula, se decrementa el saldo, y ya no tiene sentido mirar vinculos. Si no se vincula, queda evidente por el saldo de la nota de credito, y el procedimiento de fulltime
	 * deberia ser vincular, y luego volver a pedir el listado. - participaciones: al parecer, interesa usar al vendedor encargado, no a los participantes - Por ahora ignoro el
	 * asunto de los cheques (forma de pago en los contados que los termina haciendo similares a los credito) Emitido!
	 * 
	 * 
	 */
	public List<DocumentoDeudor> getDocumentosDeudoresSupervisor(Date fechaHoy) {
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");

		CodigoNombreFactory dtoCache = new CodigoNombreFactory();
		List<Documento> documentos = documentoDAOService.getDocumentosDeudores();
		ArrayList<DocumentoDeudor> result = new ArrayList<DocumentoDeudor>();
		for (Documento documento : documentos) {
			if (documento.getComprobante().getTipo() == Comprobante.RECIBO_COBRO) {
				continue;
			}
			if (!documento.isEmitido()) {
				continue;
			}
			DocumentoDeudor pendiente = new DocumentoDeudor();
			pendiente.setDocId(documento.getDocId());
			pendiente.setDeudor(documento.getCliente());
			pendiente.setFecha(dt1.format(documento.getFecha()));

			Calendar calendarDate = Calendar.getInstance();
			calendarDate.setTime(documento.getFecha());
			calendarDate.set(Calendar.HOUR, 8);
			calendarDate.set(Calendar.MINUTE, 0);
			calendarDate.set(Calendar.SECOND, 0);

			pendiente.setDate(calendarDate.getTime());
			pendiente.setMoneda(dtoCache.getFor(documento.getMoneda()));
			pendiente.setComprobante(documento.getComprobante());
			pendiente.setSerie(documento.getSerie() != null ? documento.getSerie() : "");
			pendiente.setNumero(documento.getNumero() != null ? documento.getNumero() : 0);

			pendiente.setFacturado(documento.getTotal());
			pendiente.setCancelado(documento.getCancelado());
			pendiente.setAdeudado(documento.getDeuda());
			pendiente.setAdeudadoNeto(documento.calcularDeuda(fechaHoy));
			pendiente.setPlanPago(dtoCache.getFor(documento.getPlanPagos()));
			BigDecimal descuento;
			if (pendiente.getAdeudado().compareTo(BigDecimal.ZERO) == 0) {
				descuento = BigDecimal.ZERO;
			} else {
				descuento = (pendiente.getAdeudado().subtract(pendiente.getAdeudadoNeto())).multiply(Maths.ONE_HUNDRED)
						.divide(pendiente.getAdeudado(), 2, RoundingMode.HALF_UP);
			}
			pendiente.setDescuento(descuento);
			pendiente.setTieneCuotaVencida(documento.isTieneCuotaVencida(fechaHoy));

			if (pendiente.isTieneCuotaVencida()) {
				pendiente.setDiasRetraso(documento.getDiasRetraso(fechaHoy));

				Calendar calendarRetraso = Calendar.getInstance();
				calendarRetraso.setTime(fechaHoy);
				calendarRetraso.add(Calendar.DATE, pendiente.getDiasRetraso() * -1);

				pendiente.setFechaVencimiento(calendarRetraso.getTime());

			}
			result.add(pendiente);
		}

		return result;
	}

	public List<DocumentoDeudor> getDocumentosDeudores(Date fechaHoy) {
		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		Boolean esVendedorDist = Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId);
		Boolean esAliadoComercial = Usuario.USUARIO_ALIADOS_COMERCIALES.equals(permisoId);

		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");

		CodigoNombreFactory dtoCache = new CodigoNombreFactory();
		List<Documento> documentos = documentoDAOService.getDocumentosDeudores();
		ArrayList<DocumentoDeudor> result = new ArrayList<DocumentoDeudor>();
		for (Documento documento : documentos) {
			String vendedor = documento.getCliente().getVendedor() != null ? documento.getCliente().getVendedor()
					.getCodigo() : "";
			String encargadoCuenta = documento.getCliente().getEncargadoCuenta() != null ? documento.getCliente()
					.getEncargadoCuenta() : "";

			if (esVendedorDist && !vendedor.equals(usuarioLogin.getVenId())) {
				continue;
			} else if (esAliadoComercial && !vendedor.equals(usuarioLogin.getVenId())
					&& !encargadoCuenta.equals(usuarioLogin.getVenId())) {
				continue;
			} else {
				DocumentoDeudor pendiente = new DocumentoDeudor();
				pendiente.setDocId(documento.getDocId());
				pendiente.setDeudor(documento.getCliente());
				pendiente.setFecha(dt1.format(documento.getFecha()));
				pendiente.setMoneda(dtoCache.getFor(documento.getMoneda()));
				pendiente.setComprobante(documento.getComprobante());
				pendiente.setNumero(documento.getNumero() != null ? documento.getNumero() : 0);

				if (documento.getComprobante().getTipo() == Comprobante.RECIBO_COBRO) {
					pendiente.setFacturado(documento.getTotal().negate());
					pendiente.setAdeudado(documento.getDeuda().negate());
					pendiente.setDescuento(documento.getDescuentosPorc() == null ? BigDecimal.ZERO : documento
							.getDescuentosPorc());
					pendiente.setAdeudadoNeto(documento.getAdeudadoNetoRecibo().negate());
					pendiente.setCancelado(documento.getCancelado().negate());
				} else {
					pendiente.setFacturado(documento.getTotal());
					pendiente.setCancelado(documento.getCancelado());
					pendiente.setAdeudado(documento.getDeuda());
					pendiente.setAdeudadoNeto(documento.calcularDeuda(fechaHoy));
					pendiente.setPlanPago(dtoCache.getFor(documento.getPlanPagos()));
					BigDecimal descuento;
					if (pendiente.getAdeudado().compareTo(BigDecimal.ZERO) == 0) {
						descuento = BigDecimal.ZERO;
					} else {
						descuento = (pendiente.getAdeudado().subtract(pendiente.getAdeudadoNeto())).multiply(
								Maths.ONE_HUNDRED).divide(pendiente.getAdeudado(), 2, RoundingMode.HALF_UP);
					}
					pendiente.setDescuento(descuento);
					pendiente.setTieneCuotaVencida(documento.isTieneCuotaVencida(fechaHoy));
				}
				result.add(pendiente);
			}

		}
		return result;
	}

	public List<DocumentoDeudor> getDocumentosVencidosSupervisor(Date fechaHoy) {
		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");

		CodigoNombreFactory dtoCache = new CodigoNombreFactory();
		List<Documento> documentos = documentoDAOService.getDocumentosDeudores();
		ArrayList<DocumentoDeudor> result = new ArrayList<DocumentoDeudor>();
		for (Documento documento : documentos) {
			if (!documento.isEmitido()) {
				continue;
			}
			if (documento.getComprobante().getTipo() != Comprobante.RECIBO_COBRO
					&& documento.isTieneCuotaVencida(fechaHoy)) {
				DocumentoDeudor pendiente = new DocumentoDeudor();
				pendiente.setDocId(documento.getDocId());
				pendiente.setDeudor(documento.getCliente());
				pendiente.setFecha(dt1.format(documento.getFecha()));
				pendiente.setDate(documento.getFecha());
				pendiente.setMoneda(dtoCache.getFor(documento.getMoneda()));
				pendiente.setComprobante(documento.getComprobante());
				pendiente.setSerie(documento.getSerie() != null ? documento.getSerie() : "");
				pendiente.setNumero(documento.getNumero() != null ? documento.getNumero() : 0);

				int retraso = documento.getDiasRetraso(fechaHoy);

				pendiente.setDiasRetraso(retraso);

				Date fechaVencimiento = org.apache.commons.lang.time.DateUtils.addDays(new Date(), retraso * -1);

				pendiente.setFechaVencimiento(fechaVencimiento);
				pendiente.setFacturado(documento.getTotal());
				pendiente.setCancelado(documento.getCancelado());
				pendiente.setAdeudado(documento.getDeuda());
				pendiente.setAdeudadoNeto(documento.calcularDeuda(fechaHoy));
				pendiente.setPlanPago(dtoCache.getFor(documento.getPlanPagos()));

				BigDecimal descuento;
				if (pendiente.getAdeudado().compareTo(BigDecimal.ZERO) == 0) {
					descuento = BigDecimal.ZERO;
				} else {
					descuento = (pendiente.getAdeudado().subtract(pendiente.getAdeudadoNeto())).multiply(
							Maths.ONE_HUNDRED).divide(pendiente.getAdeudado(), 2, RoundingMode.HALF_UP);
				}
				pendiente.setDescuento(descuento);
				pendiente.setTieneCuotaVencida(true);
				pendiente.setDocumento(documento);

				result.add(pendiente);
			}
		}
		return result;
	}

	public List<DocumentoDeudor> getDocumentosVencidos(Date fechaHoy) {
		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		Boolean esVendedorDist = Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId);
		Boolean esAliadoComercial = Usuario.USUARIO_ALIADOS_COMERCIALES.equals(permisoId);

		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");

		CodigoNombreFactory dtoCache = new CodigoNombreFactory();
		List<Documento> documentos = documentoDAOService.getDocumentosDeudores();
		ArrayList<DocumentoDeudor> result = new ArrayList<DocumentoDeudor>();
		for (Documento documento : documentos) {
			if (!documento.isEmitido()) {
				continue;
			}
			String vendedor = documento.getCliente().getVendedor() != null ? documento.getCliente().getVendedor()
					.getCodigo() : "";
			String encargadoCuenta = documento.getCliente().getEncargadoCuenta() != null ? documento.getCliente()
					.getEncargadoCuenta() : "";

			if (esVendedorDist && !vendedor.equals(usuarioLogin.getVenId())) {
				continue;
			} else if (esAliadoComercial && !vendedor.equals(usuarioLogin.getVenId())
					&& !encargadoCuenta.equals(usuarioLogin.getVenId())) {
				continue;
			} else if (documento.getComprobante().getTipo() != Comprobante.RECIBO_COBRO
					&& documento.isTieneCuotaVencida(fechaHoy)) {
				DocumentoDeudor pendiente = new DocumentoDeudor();
				pendiente.setDocId(documento.getDocId());
				pendiente.setDeudor(documento.getCliente());
				pendiente.setFecha(dt1.format(documento.getFecha()));
				pendiente.setDate(documento.getFecha());
				pendiente.setMoneda(dtoCache.getFor(documento.getMoneda()));
				pendiente.setComprobante(documento.getComprobante());
				pendiente.setSerie(documento.getSerie() != null ? documento.getSerie() : "");
				pendiente.setNumero(documento.getNumero() != null ? documento.getNumero() : 0);

				int retraso = documento.getDiasRetraso(fechaHoy);

				pendiente.setDiasRetraso(retraso);

				Date fechaVencimiento = org.apache.commons.lang.time.DateUtils.addDays(new Date(), retraso * -1);

				pendiente.setFechaVencimiento(fechaVencimiento);
				pendiente.setFacturado(documento.getTotal());
				pendiente.setCancelado(documento.getCancelado());
				pendiente.setAdeudado(documento.getDeuda());
				pendiente.setAdeudadoNeto(documento.calcularDeuda(fechaHoy));
				pendiente.setPlanPago(dtoCache.getFor(documento.getPlanPagos()));

				BigDecimal descuento;
				if (pendiente.getAdeudado().compareTo(BigDecimal.ZERO) == 0) {
					descuento = BigDecimal.ZERO;
				} else {
					descuento = (pendiente.getAdeudado().subtract(pendiente.getAdeudadoNeto())).multiply(
							Maths.ONE_HUNDRED).divide(pendiente.getAdeudado(), 2, RoundingMode.HALF_UP);
				}
				pendiente.setDescuento(descuento);
				pendiente.setTieneCuotaVencida(true);
				pendiente.setDocumento(documento);

				result.add(pendiente);
			}
		}
		return result;
	}

	public List<DocumentoDeudor> getDocumentosDeudoresCliente(Date fechaHoy, String clienteId) {
		Usuario usuarioLogin = usuariosService.getUsuarioLogin();
		String permisoId = usuarioLogin.getPermisoId();
		Boolean esVendedorDist = Usuario.USUARIO_VENDEDOR_DISTRIBUIDOR.equals(permisoId);
		Boolean esAliadoComercial = Usuario.USUARIO_ALIADOS_COMERCIALES.equals(permisoId);

		SimpleDateFormat dt1 = new SimpleDateFormat("dd-MM-yyyy");

		CodigoNombreFactory dtoCache = new CodigoNombreFactory();
		List<Documento> documentos = documentoDAOService.getDocumentosDeudoresCliente(clienteId);
		ArrayList<DocumentoDeudor> result = new ArrayList<DocumentoDeudor>();
		for (Documento documento : documentos) {
			String vendedor = documento.getCliente().getVendedor() != null ? documento.getCliente().getVendedor()
					.getCodigo() : "";
			String encargadoCuenta = documento.getCliente().getEncargadoCuenta() != null ? documento.getCliente()
					.getEncargadoCuenta() : "";

			if (esVendedorDist && !vendedor.equals(usuarioLogin.getVenId())) {
				continue;
			} else if (esAliadoComercial && !vendedor.equals(usuarioLogin.getVenId())
					&& !encargadoCuenta.equals(usuarioLogin.getVenId())) {
				continue;
			} else {
				DocumentoDeudor pendiente = new DocumentoDeudor();
				pendiente.setDocId(documento.getDocId());
				pendiente.setDeudor(documento.getCliente());
				pendiente.setFecha(dt1.format(documento.getFecha()));
				pendiente.setMoneda(dtoCache.getFor(documento.getMoneda()));
				pendiente.setComprobante(documento.getComprobante());
				pendiente.setNumero(documento.getNumero() != null ? documento.getNumero() : 0);

				if (documento.getComprobante().getTipo() == Comprobante.RECIBO_COBRO) {
					pendiente.setFacturado(documento.getTotal().negate());
					pendiente.setAdeudado(documento.getDeuda().negate());
					pendiente.setDescuento(documento.getDescuentosPorc() == null ? BigDecimal.ZERO : documento
							.getDescuentosPorc());
					pendiente.setAdeudadoNeto(documento.getAdeudadoNetoRecibo().negate());
					pendiente.setCancelado(documento.getCancelado().negate());
				} else {
					pendiente.setFacturado(documento.getTotal());
					pendiente.setCancelado(documento.getCancelado());
					pendiente.setAdeudado(documento.getDeuda());
					pendiente.setAdeudadoNeto(documento.calcularDeuda(fechaHoy));
					pendiente.setPlanPago(dtoCache.getFor(documento.getPlanPagos()));

					BigDecimal descuento;
					if (pendiente.getAdeudado().compareTo(BigDecimal.ZERO) == 0) {
						descuento = BigDecimal.ZERO;
					} else {
						descuento = (pendiente.getAdeudado().subtract(pendiente.getAdeudadoNeto())).multiply(
								Maths.ONE_HUNDRED).divide(pendiente.getAdeudado(), 2, RoundingMode.HALF_UP);
					}
					pendiente.setDescuento(descuento);
					pendiente.setTieneCuotaVencida(documento.isTieneCuotaVencida(fechaHoy));
				}
				result.add(pendiente);
			}

		}
		return result;
	}

	public byte[] generarLiquidacion(Date fechaDesde, Date fechaHasta, Date fechaCorte, BigDecimal gastosPeriodo) {
		Locale.setDefault(new Locale("es"));

		File dirBase = createTempDir();
		try {
			generarReporteResumenEntregas(fechaDesde, fechaHasta, gastosPeriodo, dirBase); // asociar a cada venta en el periodo un costo operativo
			List<ParticipacionVendedor> participContados = this.documentoDAOService.getParticipacionesEnContados(
					fechaDesde, fechaHasta, fechaCorte);
			List<ParticipacionEnCobranza> participCobranzas = this.documentoDAOService.getParticipacionesEnCobranzas(
					fechaDesde, fechaHasta, fechaCorte);

			printCobranzas(fechaDesde, fechaHasta, dirBase, participCobranzas, participContados);
			exportGananciaFinanciera(fechaDesde, fechaHasta, fechaCorte, dirBase);
			exportarRedistribucionesPorJefaturas(fechaDesde, fechaHasta, dirBase, participContados, participCobranzas);

			File zipFile = File.createTempFile(dirBase.getName(), ".zip");
			zipDirectory(dirBase, zipFile);

			return getFileByteArray(zipFile);
		} catch (PermisosException e1) {
			throw new RuntimeException();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private byte[] getFileByteArray(File file) {
		try {
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			try {
				for (int readNum; (readNum = fis.read(buf)) != -1;) {
					bos.write(buf, 0, readNum); // no doubt here is 0
					// Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
					// System.out.println("read " + readNum + " bytes,");
				}
			} catch (IOException ex) {
			}
			return bos.toByteArray();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private void exportarRedistribucionesPorJefaturas(Date fechaDesde, Date fechaHasta, File dirBase,
			List<ParticipacionVendedor> participContados, List<ParticipacionEnCobranza> participCobranzas)
			throws IOException {
		String fileName = new File(dirBase, "redistribucion-jefaturas.csv").getPath();
		CsvWriter w = new CsvWriter(fileName);
		try {
			w.write("JEFATURA");
			w.write("FACTURA");
			w.write("ARTICULO");
			w.write("MONEDA");
			w.write("JEFE COBRA");
			w.write("MONTO");
			w.write("VENDEDOR PAGA");
			w.endRecord();

			Collection<IAportaRenta> aportacionesRenta = juntar(participContados, participCobranzas);
			Collection<Jefatura> jefaturas = catalogService.getJefaturas();
			RedistribucionRentasJefaturas redistJefaturas = new RedistribucionRentasJefaturas(aportacionesRenta,
					jefaturas);
			Collection<RedistribucionRentaLinea> redists = redistJefaturas.getRedistribuciones();
			for (RedistribucionRentaLinea rrl : redists) {
				Collection<ParticipacionVendedor> participaciones = rrl.getAportadorRenta().getParticipaciones();
				for (ParticipacionVendedor participacionVendedor : participaciones) {
					BigDecimal montoPaga = rrl.getPagaVendedor(participacionVendedor.getVendedor());
					if (montoPaga.compareTo(BigDecimal.ZERO) != 0) {
						w.write(rrl.getJefatura().getDescripcion());
						w.write(rrl.getLinea().getDocumento().getSerieNumero());
						w.write(rrl.getLinea().getArticulo().getCodigo());
						w.write(rrl.getLinea().getDocumento().getMoneda().toString());
						w.write(rrl.getJefatura().getJefe().getCodigo());
						w.write(montoPaga);
						w.write(participacionVendedor.getVendedor().getCodigo());
						w.endRecord();
					}
				}
			}
		} finally {
			w.close();
		}
	}

	private void exportGananciaFinanciera(Date fechaDesde, Date fechaHasta, Date fechaCorte, File dirBase)
			throws IOException, PermisosException {
		String fileName = new File(dirBase, "ganancia-financiera.csv").getPath();
		CsvWriter w = new CsvWriter(fileName);

		Calendar calendar = Calendar.getInstance();
		CotizacionesMonedas tipoCambio = tipoCambioService.getUltimaCotizacion(calendar.getTime());

		// TIPO DE DOCUMENTO FECHA CLIENTE IMPORTE IVA INCLUIDO DESCUENTO ESPERADO DTO OTORGADO RENTA FINANCIERA..
		try {
			w.write("");
			w.write("");
			w.write("Dolar");
			w.write("");
			w.write("");
			w.write("Euro");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.endRecord();

			w.write("");
			w.write("");
			w.write("Compra");
			w.write("Venta");
			w.write("");
			w.write("Compra");
			w.write("Venta");
			w.write("");
			w.write("");
			w.write("");
			w.endRecord();

			w.write("");
			w.write("");
			w.write(tipoCambio.getDolarCompra().setScale(2, RoundingMode.HALF_UP).toString());
			w.write(tipoCambio.getDolarVenta().setScale(2, RoundingMode.HALF_UP).toString());
			w.write("");
			w.write(tipoCambio.getEuroCompra().setScale(2, RoundingMode.HALF_UP).toString());
			w.write(tipoCambio.getEuroVenta().setScale(2, RoundingMode.HALF_UP).toString());
			w.write("");
			w.write("");
			w.write("");
			w.endRecord();

			w.write("SERIE RECIBO");
			w.write("NÚMERO RECIBO");
			w.write("FECHA RECIBO");
			w.write("SERIE FACTURA");
			w.write("NÚMERO FACTURA");
			w.write("CLIENTE");
			w.write("MONEDA");
			w.write("FACTURA TOTAL");
			w.write("VALOR PAGO");
			w.write("DTO OTORGADO");
			w.write("DTO ESPERADO");
			w.write("RENTA FINANCIERA");
			w.endRecord();

			NumberFormat formatter = NumberFormat.getNumberInstance(new Locale("es"));
			formatter.setMinimumFractionDigits(2);
			formatter.setMaximumFractionDigits(2);

			Collection<VinculoDocumentos> participCobranzas = documentoDAOService.getCobranzas(fechaDesde, fechaHasta,
					fechaCorte);
			BigDecimal totalRentaFinanciera = BigDecimal.ZERO;

			BigDecimal subTotalRentaFinancieraOficiales = BigDecimal.ZERO;
			BigDecimal subTotalRentaFinancieraAster = BigDecimal.ZERO;

			BigDecimal totalRentaFinancieraDolares = BigDecimal.ZERO;
			BigDecimal totalRentaFinancieraPesos = BigDecimal.ZERO;
			BigDecimal totalRentaFinancieraDolaresAster = BigDecimal.ZERO;
			BigDecimal totalRentaFinancieraPesosAster = BigDecimal.ZERO;

			for (VinculoDocumentos vinculoDocumentos : participCobranzas) {
				BigDecimal rentaFinanciera = null;
				if (vinculoDocumentos.getRecibo().getComprobante().isAster()) {
					rentaFinanciera = vinculoDocumentos.getRentaFinanciera() != null ? vinculoDocumentos
							.getRentaFinanciera() : BigDecimal.ZERO;
				} else {
					rentaFinanciera = vinculoDocumentos.getVinRtaFin() != null ? vinculoDocumentos.getVinRtaFin()
							: BigDecimal.ZERO;
				}
				if (vinculoDocumentos.getFactura().getMoneda().getCodigo().equals(Moneda.CODIGO_MONEDA_PESOS)
						|| vinculoDocumentos.getFactura().getMoneda().getCodigo()
								.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
					totalRentaFinanciera = totalRentaFinanciera.add(convertMoneda(tipoCambio, rentaFinanciera,
							vinculoDocumentos.getFactura().getMoneda().getCodigo(), Moneda.CODIGO_MONEDA_DOLAR));

					if (vinculoDocumentos.getFactura().getMoneda().getCodigo().equals(Moneda.CODIGO_MONEDA_PESOS)) {
						totalRentaFinancieraPesos = totalRentaFinancieraPesos.add(rentaFinanciera);
					} else if (vinculoDocumentos.getFactura().getMoneda().getCodigo()
							.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
						totalRentaFinancieraPesosAster = totalRentaFinancieraPesosAster.add(rentaFinanciera);
					}

				} else if (vinculoDocumentos.getFactura().getMoneda().getCodigo().equals(Moneda.CODIGO_MONEDA_EUROS)
						|| vinculoDocumentos.getFactura().getMoneda().getCodigo()
								.equals(Moneda.CODIGO_MONEDA_EUROS_ASTER)) {
					totalRentaFinanciera = totalRentaFinanciera.add(convertMoneda(tipoCambio, rentaFinanciera,
							vinculoDocumentos.getFactura().getMoneda().getCodigo(), Moneda.CODIGO_MONEDA_DOLAR));
				} else {
					totalRentaFinanciera = totalRentaFinanciera.add(rentaFinanciera);

					if (vinculoDocumentos.getFactura().getMoneda().getCodigo().equals(Moneda.CODIGO_MONEDA_DOLAR)) {
						totalRentaFinancieraDolares = totalRentaFinancieraDolares.add(rentaFinanciera);
					} else if (vinculoDocumentos.getFactura().getMoneda().getCodigo()
							.equals(Moneda.CODIGO_MONEDA_DOLAR_ASTER)) {
						totalRentaFinancieraDolaresAster = totalRentaFinancieraDolaresAster.add(rentaFinanciera);
					}
				}

				BigDecimal dtoPorc = null;
				if (vinculoDocumentos.getRecibo().getComprobante().isAster()) {
					dtoPorc = vinculoDocumentos.getRecibo().getDescuentosPorc();
				} else {
					dtoPorc = vinculoDocumentos.getDescuentoPorc();
				}

				w.write(vinculoDocumentos.getRecibo().getSerie());
				w.write(vinculoDocumentos.getRecibo().getNumero() != null ? vinculoDocumentos.getRecibo().getNumero()
						.toString() : "");
				w.write(vinculoDocumentos.getRecibo().getFecha());
				w.write(vinculoDocumentos.getFactura().getSerie());
				w.write(vinculoDocumentos.getFactura().getNumero() != null ? vinculoDocumentos.getFactura().getNumero()
						.toString() : "");
				w.write(vinculoDocumentos.getFactura().getCliente().toString());
				w.write(vinculoDocumentos.getFactura().getMoneda().toString());
				w.write(formatter.format(vinculoDocumentos.getFactura().getTotal().doubleValue()));
				w.write(formatter.format(vinculoDocumentos.getMonto() != null ? vinculoDocumentos.getMonto()
						.doubleValue() : 0.0));
				w.write(formatter.format(dtoPorc != null ? dtoPorc.doubleValue() : 0.0));
				w.write(formatter.format(vinculoDocumentos.getFactura().getComprobante().getDescuentoPrometido()
						.floatValue()));
				w.write(formatter.format(rentaFinanciera));
				w.endRecord();
			}

			subTotalRentaFinancieraAster = totalRentaFinancieraDolaresAster.add(convertMoneda(tipoCambio,
					totalRentaFinancieraPesosAster, Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_DOLAR));
			subTotalRentaFinancieraOficiales = totalRentaFinancieraDolares.add(convertMoneda(tipoCambio,
					totalRentaFinancieraPesos, Moneda.CODIGO_MONEDA_PESOS, Moneda.CODIGO_MONEDA_DOLAR));

			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("RF en $U");
			w.write(formatter.format(totalRentaFinancieraPesos));
			w.endRecord();

			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("RF en U$S");
			w.write(formatter.format(totalRentaFinancieraDolares));
			w.endRecord();

			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("RF en $U *");
			w.write(formatter.format(totalRentaFinancieraPesosAster));
			w.endRecord();

			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("RF en U$S *");
			w.write(formatter.format(totalRentaFinancieraDolaresAster));
			w.endRecord();

			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("SubTotal U$S*");
			w.write(formatter.format(subTotalRentaFinancieraAster));
			w.endRecord();

			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("SubTotal U$S");
			w.write(formatter.format(subTotalRentaFinancieraOficiales.divide(new BigDecimal(1.22), 2,
					RoundingMode.HALF_UP)));
			w.endRecord();

			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("Total U$S");
			w.write(formatter.format(subTotalRentaFinancieraOficiales.divide(new BigDecimal(1.22), 2,
					RoundingMode.HALF_UP).add(subTotalRentaFinancieraAster)));
			w.endRecord();

		} finally {
			w.close();
		}
	}

	private BigDecimal convertMoneda(CotizacionesMonedas tipoCambio, BigDecimal monto, String monedaOrigen,
			String monedaDestino) {

		BigDecimal dolarCompra = tipoCambio.getDolarCompra().setScale(4, RoundingMode.HALF_UP);
		BigDecimal dolarVenta = tipoCambio.getDolarVenta().setScale(4, RoundingMode.HALF_UP);
		BigDecimal euroCompra = tipoCambio.getEuroCompra().setScale(4, RoundingMode.HALF_UP);
		BigDecimal euroVenta = tipoCambio.getEuroVenta().setScale(4, RoundingMode.HALF_UP);

		if (monedaOrigen.equals(Moneda.CODIGO_MONEDA_PESOS) || monedaOrigen.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
			if (monedaDestino.equals(Moneda.CODIGO_MONEDA_DOLAR)) {
				return monto.setScale(4, RoundingMode.HALF_UP).divide(dolarCompra, 4, RoundingMode.HALF_UP)
						.setScale(4, RoundingMode.HALF_UP);
			} else if (monedaDestino.equals(Moneda.CODIGO_MONEDA_EUROS)) {
				return monto.setScale(4, RoundingMode.HALF_UP).divide(euroCompra, 4, RoundingMode.HALF_UP)
						.setScale(4, RoundingMode.HALF_UP);
			}
		} else if (monedaOrigen.equals(Moneda.CODIGO_MONEDA_DOLAR)
				|| monedaOrigen.equals(Moneda.CODIGO_MONEDA_DOLAR_ASTER)) {
			if (monedaDestino.equals(Moneda.CODIGO_MONEDA_PESOS)) {
				return monto.setScale(4, RoundingMode.HALF_UP).multiply(dolarVenta).setScale(4, RoundingMode.HALF_UP);
			} else if (monedaDestino.equals(Moneda.CODIGO_MONEDA_EUROS)) {
				return monto.setScale(4, RoundingMode.HALF_UP)
						.multiply(dolarCompra.divide(euroCompra, 4, RoundingMode.HALF_UP))
						.setScale(4, RoundingMode.HALF_UP);
			}
		} else if (monedaOrigen.equals(Moneda.CODIGO_MONEDA_EUROS)
				|| monedaOrigen.equals(Moneda.CODIGO_MONEDA_EUROS_ASTER)) {
			if (monedaDestino.equals(Moneda.CODIGO_MONEDA_PESOS)) {
				return monto.setScale(4, RoundingMode.HALF_UP).multiply(euroVenta).setScale(4, RoundingMode.HALF_UP);
			} else if (monedaDestino.equals(Moneda.CODIGO_MONEDA_DOLAR)) {
				return monto.setScale(4, RoundingMode.HALF_UP).multiply(euroVenta)
						.divide(dolarVenta, 4, RoundingMode.HALF_UP).setScale(4, RoundingMode.HALF_UP);
			}
		}

		return monto;
	}

	private void printCobranzas(Date fechaDesde, Date fechaHasta, File dirBase,
			List<ParticipacionEnCobranza> participCobranzas, List<ParticipacionVendedor> participContados)
			throws IOException {
		Map<String, ArrayList<ParticipacionEnCobranza>> partVendedores = new HashMap<String, ArrayList<ParticipacionEnCobranza>>();

		for (ParticipacionEnCobranza participacionEnCobranza : participCobranzas) {
			String vendedorId = participacionEnCobranza.getParticipacionVendedor().getVendedor().getCodigo();
			ArrayList<ParticipacionEnCobranza> participaciones;
			if (partVendedores.containsKey(vendedorId)) {
				participaciones = partVendedores.get(vendedorId);
			} else {
				participaciones = new ArrayList<ParticipacionEnCobranza>();
			}
			participaciones.add(participacionEnCobranza);
			partVendedores.put(vendedorId, participaciones);
		}

		for (ParticipacionVendedor partVendedor : participContados) {
			String vendedorId = partVendedor.getVendedor().getCodigo();
			ArrayList<ParticipacionEnCobranza> participaciones;
			if (partVendedores.containsKey(vendedorId)) {
				participaciones = partVendedores.get(vendedorId);
			} else {
				participaciones = new ArrayList<ParticipacionEnCobranza>();
			}

			ParticipacionEnCobranza participacionVendedor = new ParticipacionEnCobranza();
			participacionVendedor.setParticipacionVendedor(partVendedor);
			participaciones.add(participacionVendedor);

			partVendedores.put(vendedorId, participaciones);
		}

		List<Usuario> usuarios = catalogDAOService.getCatalog(Usuario.class);
		ArrayList<String> vendedoresDist = new ArrayList<String>();
		for (Usuario user : usuarios) {
			if (user.getVenId() != null && user.getVenId().trim().length() > 0 && user.getUsuarioModoDistribuidor()) {
				vendedoresDist.add(user.getVenId());
			}
		}

		HashMap<String, BigDecimal> cotizaciones = new HashMap<String, BigDecimal>();
		
		List<CotizacionesMonedas> tiposCambio =  tipoCambioService.getCotizacionesMonedas(fechaDesde);
		for (CotizacionesMonedas cotizacionesMonedas : tiposCambio) {
			cotizaciones.put(dt1.format(cotizacionesMonedas.getDia()), cotizacionesMonedas.getDolarVenta());
			//System.out.println(dt1.format(cotizacionesMonedas.getDia()) + " :: "  +  cotizacionesMonedas.getDolarVenta());
		}
		
		Iterator<String> iter = partVendedores.keySet().iterator();
		while (iter.hasNext()) {
			String vendId = iter.next();
			printCobranzaVendedor(dirBase, vendId, vendedoresDist.contains(vendId), partVendedores.get(vendId),
					fechaDesde, fechaHasta, cotizaciones);
		}

	}

	private void printCobranzaVendedor(File dirBase, String vendedorId, boolean vendedorDist,
			List<ParticipacionEnCobranza> participCobranzas, Date fechaDesde, Date fechaHasta, HashMap<String, BigDecimal> cotizaciones) throws IOException {
		String fileName = new File(dirBase, "cobranzas_" + vendedorId + ".csv").getPath();
		CsvWriter w = new CsvWriter(fileName);

		try {
			Vendedor vendedor = catalogService.findCatalogEntity("Vendedor", vendedorId);

			w.write(vendedorDist ? "DISTRIBUIDOR:" : "VENDEDOR:");
			w.write(vendedor.getNombre().toUpperCase());
			w.endRecord();
			w.write("PERIODO SOLICITADO:");
			w.write("");
			w.write("DESDE");
			w.write(dt1.format(fechaDesde));
			w.write("");
			w.write("HASTA");
			w.write(dt1.format(fechaHasta));
			w.endRecord();

			w.write("");
			w.endRecord();

			w.write("FECHA RECIBO");
			w.write("SERIE");
			w.write("NÚMERO");
			w.write("FECHA VENTA");
			w.write("CLIENTE");
			w.write("COMPROBANTE DE VENTA");
			w.write("S/N");
			w.write("MONEDA");
			w.write("IMP. FACTURA CON IVA");
			w.write("RENTA DE VENTA");
			if (vendedorDist) {
				w.write("RENTA DISTRIBUIDOR");
			}
			w.write("PARTICIPACIÓN");
			w.write(vendedorDist ? "RTA. AL DIST." : "RTA. AL VEND.");
			w.write("MONTO CANCELADO"); // TODO: Revisar
			w.write("% COBRADO");
			w.write("RTA. A COBRAR");
			w.write("DOLARIZADA");
			w.write("COTIZACIÓN");

			w.endRecord();

			NumberFormat formatter = NumberFormat.getNumberInstance(Locale.getDefault());
			formatter.setMinimumFractionDigits(2);
			formatter.setMaximumFractionDigits(2);

			BigDecimal rentaTotal = BigDecimal.ZERO;

			for (ParticipacionEnCobranza participacionEnCobranza : participCobranzas) {
				Boolean esContado = participacionEnCobranza.getVinculo() == null;

				uy.com.tmwc.facturator.entity.Documento recibo = !esContado ? participacionEnCobranza.getRecibo()
						: null;
				uy.com.tmwc.facturator.entity.Documento factura = esContado ? participacionEnCobranza
						.getParticipacionVendedor().getDocumento() : participacionEnCobranza.getFactura();

				ParticipacionVendedor participacionVendedor = participacionEnCobranza.getParticipacionVendedor();

				if (vendedorDist) {
					try {
						factura = documentoDAOService.findDocumento(factura.getDocId());

						// Actualizar la info en el vinculo del documento...
						if (participacionEnCobranza != null && participacionEnCobranza.getVinculo() != null) {
							participacionEnCobranza.getVinculo().setFactura(factura);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}

				BigDecimal participacion = participacionVendedor.getPorcentaje();
				BigDecimal rentaNetaComprobante = factura.getRentaNetaComercial();
				BigDecimal rentaDistComprobante = BigDecimal.ZERO;

				BigDecimal rentaVendedor;
				if (vendedorDist) {
					rentaDistComprobante = factura.getRentaNetaDistComercial();
					rentaVendedor = rentaDistComprobante.multiply(participacion).divide(new BigDecimal("100"), 2,
							RoundingMode.HALF_UP);
				} else {
					rentaVendedor = rentaNetaComprobante.multiply(participacion).divide(new BigDecimal("100"), 2,
							RoundingMode.HALF_UP);
				}

				w.write(esContado ? "" : dt1.format(recibo.getFecha())); // FECHA RECIBO
				w.write(!esContado && recibo.getSerieNumero() != null ? recibo.getSerieNumero().getSerie() : ""); // S/N RECIBO
				w.write(!esContado && recibo.getSerieNumero() != null ? String.valueOf(recibo.getSerieNumero()
						.getNumero()) : ""); // S/N RECIBO
				w.write(dt1.format(factura.getFecha())); // FECHA VENTA

				w.write(factura.getCliente().getCliIdNom()); // CLIENTE
				w.write(factura.getComprobante().getNombre()); // COMPROBANTE DE VENTA
				w.write(factura.getSerieNumero()); // S/N FACTURA
				w.write(factura.getMoneda().getSimbolo()); // MONEDA

				w.write(formatter.format(factura.getTotal())); // IMPORTE NETO VENDIDO
				w.write(formatter.format(rentaNetaComprobante)); // RENTA DE VENTA
				if (vendedorDist)
					w.write(formatter.format(rentaDistComprobante)); // RENTA DISTRIBUIDOR
				w.write(formatter.format(participacion) + "%"); // PARTICIPACION
				w.write(formatter.format(rentaVendedor)); // RENTA AL VENDEDOR

				BigDecimal montoCancelado = BigDecimal.ZERO;
				BigDecimal porcentajeCancelacion = BigDecimal.ZERO;
				if (esContado) {
					montoCancelado = factura.getVentaNeta();
					porcentajeCancelacion = Maths.ONE_HUNDRED;
				} else {
					BigDecimal total = factura.getTotal() != null ? factura.getTotal() : BigDecimal.ZERO;
					if (factura.getComprobante().isAster()) {
						porcentajeCancelacion = participacionEnCobranza.getVinculo().getPorcentajeCancelacion();
						montoCancelado = total.multiply(porcentajeCancelacion).divide(Maths.ONE_HUNDRED, 2,
								RoundingMode.HALF_UP);
					} else {
						BigDecimal monto = participacionEnCobranza.getVinculo().getNeto() != null ? participacionEnCobranza
								.getVinculo().getNeto() : BigDecimal.ZERO;
						BigDecimal descuentoPorc = participacionEnCobranza.getVinculo().getDescuentoPorc() != null ? participacionEnCobranza
								.getVinculo().getDescuentoPorc() : BigDecimal.ZERO;

						montoCancelado = Maths.calcularTotalCancelado(monto, descuentoPorc);
						porcentajeCancelacion = Maths.calcularPorcentaje(total, montoCancelado);
					}
				}

				w.write(formatter.format(montoCancelado)); // CANCELADOS
				w.write(formatter.format(porcentajeCancelacion) + "%"); // % COBRADO

				String codigoMoneda = factura.getMoneda().getCodigo();
				BigDecimal rentaDolarizada = BigDecimal.ZERO;
				BigDecimal cotizacionDolar  = BigDecimal.ONE;
					
				if (codigoMoneda.equals(Moneda.CODIGO_MONEDA_PESOS)
						|| codigoMoneda.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
					cotizacionDolar = cotizaciones.get(dt1.format(factura.getFecha())); // TODO: Revisar
					
					if (cotizacionDolar == null) {
						CotizacionesMonedas ultimaCotizacion = tipoCambioService.getUltimaCotizacion(factura.getFecha());
						cotizacionDolar = ultimaCotizacion.getDolarVenta();
						if (cotizacionDolar != null) {
							cotizaciones.put(dt1.format(factura.getFecha()), cotizacionDolar);	
						} else {
							cotizacionDolar = BigDecimal.ONE;
						}						
					}
				}

				Boolean esDevolucion = Boolean.FALSE;
				if (factura.getComprobante().getCodigo().equals("98")) {
					esDevolucion = Boolean.TRUE;
				}

				if (esContado) {
					BigDecimal cuotaparteRentaComercial = vendedorDist ? participacionVendedor
							.getCuotaparteRentaDistComercial() : participacionVendedor.getCuotaparteRentaComercial();
					String crc = formatter.format(cuotaparteRentaComercial != null ? cuotaparteRentaComercial
							.doubleValue() : 0.0);
					w.write(crc); // RENTA COBRADA AL VENDEDOR

					if (codigoMoneda.equals(Moneda.CODIGO_MONEDA_PESOS)
							|| codigoMoneda.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
						if (cotizacionDolar.compareTo(BigDecimal.ZERO) == 1) {
							rentaDolarizada = cuotaparteRentaComercial.divide(cotizacionDolar, 2);
						} else {
							rentaDolarizada = BigDecimal.ZERO;
						}
					} else {
						rentaDolarizada = cuotaparteRentaComercial;
					}

					if (esDevolucion) {
						w.write(formatter.format(rentaDolarizada.negate()));
					} else {
						w.write(formatter.format(rentaDolarizada));
					}

					if (esDevolucion) {
						rentaTotal = rentaTotal.subtract(rentaDolarizada.setScale(2, RoundingMode.HALF_DOWN));
					} else {
						rentaTotal = rentaTotal.add(rentaDolarizada.setScale(2, RoundingMode.HALF_DOWN));
					}

					if (codigoMoneda.equals(Moneda.CODIGO_MONEDA_PESOS)
							|| codigoMoneda.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
						w.write(formatter.format(cotizacionDolar));
					} else {
						w.write("");
					}

				} else {
					BigDecimal rentaACobrar;
					if (factura.getComprobante().isAster()) {
						rentaACobrar = vendedorDist ? participacionEnCobranza.getRentaACobrarDist()
								: participacionEnCobranza.getRentaACobrar();
					} else {
						rentaACobrar = Maths.calcularMontoDescuento(rentaVendedor, porcentajeCancelacion);
					}

					String rc = formatter.format(rentaACobrar != null ? rentaACobrar.doubleValue() : 0.0);
					w.write(rc); // RENTA COBRADA AL VENDEDOR

					if (codigoMoneda.equals(Moneda.CODIGO_MONEDA_PESOS)
							|| codigoMoneda.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
						if (cotizacionDolar.compareTo(BigDecimal.ZERO) == 1) {
							rentaDolarizada = rentaACobrar.divide(cotizacionDolar, 2);
						} else {
							rentaDolarizada = BigDecimal.ZERO;
						}
					} else {
						rentaDolarizada = rentaACobrar;
					}
					if (esDevolucion) {
						w.write(formatter.format(BigDecimal.ZERO.subtract(rentaDolarizada)));
					} else {
						w.write(formatter.format(rentaDolarizada));
					}
					if (esDevolucion) {
						rentaTotal = rentaTotal.subtract(rentaDolarizada.setScale(2, RoundingMode.HALF_DOWN));
					} else {
						rentaTotal = rentaTotal.add(rentaDolarizada.setScale(2, RoundingMode.HALF_DOWN));
					}
					if (codigoMoneda.equals(Moneda.CODIGO_MONEDA_PESOS)
							|| codigoMoneda.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
						w.write(formatter.format(cotizacionDolar));
					} else {
						w.write("");
					}
				}

				w.endRecord();
			}
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("");
			w.write("TOTAL U$S: ");
			w.write(formatter.format(rentaTotal));
			w.endRecord();

		} finally {
			w.close();
		}

	}

	public static File createTempDir() {
		File baseDir = new File(System.getProperty("java.io.tmpdir"));
		String baseName = System.currentTimeMillis() + "-";

		for (int counter = 0; counter < TEMP_DIR_ATTEMPTS; counter++) {
			File tempDir = new File(baseDir, baseName + counter);
			if (tempDir.mkdir()) {
				return tempDir;
			}
		}
		throw new IllegalStateException("Failed to create directory within " + TEMP_DIR_ATTEMPTS + " attempts (tried "
				+ baseName + "0 to " + baseName + (TEMP_DIR_ATTEMPTS - 1) + ')');
	}

	public static final void zipDirectory(File directory, File zip) throws IOException {
		ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zip));
		try {
			zip(directory, directory, zos);
		} finally {
			finallyClose(zos);
		}
	}

	private static void finallyClose(Closeable zos) {
		try {
			zos.close();
		} catch (Throwable t) {
			System.out.println("catched finally exception: " + t);
			t.printStackTrace();
		}
	}

	private static final void zip(File directory, File base, ZipOutputStream zos) throws IOException {
		File[] files = directory.listFiles();
		byte[] buffer = new byte[8192];
		int read = 0;
		for (int i = 0, n = files.length; i < n; i++) {
			if (files[i].isDirectory()) {
				zip(files[i], base, zos);
			} else {
				FileInputStream in = new FileInputStream(files[i]);
				try {
					ZipEntry entry = new ZipEntry(files[i].getPath().substring(base.getPath().length() + 1));
					zos.putNextEntry(entry);
					while (-1 != (read = in.read(buffer))) {
						zos.write(buffer, 0, read);
					}
				} finally {
					finallyClose(in);
				}
			}
		}
	}

	private Collection<IAportaRenta> juntar(List<ParticipacionVendedor> participContados,
			List<ParticipacionEnCobranza> participCobranzas) {
		IdentityHashMap aux = new IdentityHashMap();
		ArrayList<IAportaRenta> full = new ArrayList<IAportaRenta>();

		for (ParticipacionVendedor participacionVendedor : participContados) {
			Documento contado = participacionVendedor.getDocumento();
			if (!aux.containsKey(contado)) {
				aux.put(contado, contado);
				full.add(new RentaContado(contado));
			}
		}

		for (ParticipacionEnCobranza participacionEnCobranza : participCobranzas) {
			VinculoDocumentos vinculo = participacionEnCobranza.getVinculo();
			if (!aux.containsKey(vinculo)) {
				aux.put(vinculo, vinculo);
				full.add(new RentaCobranza(vinculo));
			}
		}
		return full;
	}
}