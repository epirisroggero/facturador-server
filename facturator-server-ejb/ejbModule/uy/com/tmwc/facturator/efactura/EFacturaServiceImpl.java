package uy.com.tmwc.facturator.efactura;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;

import org.apache.commons.io.output.FileWriterWithEncoding;
import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.dto.EFacturaResult;
import uy.com.tmwc.facturator.entity.Auditoria;
import uy.com.tmwc.facturator.entity.Cliente;
import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.entity.Contacto;
import uy.com.tmwc.facturator.entity.Departamento;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.LineaDocumento;
import uy.com.tmwc.facturator.entity.LineasDocumento;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.Pais;
import uy.com.tmwc.facturator.entity.SerieNumero;
import uy.com.tmwc.facturator.rapi.AuditoriaService;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.rapi.EFacturaService;
import uy.com.tmwc.facturator.rapi.PermisosException;
import uy.com.tmwc.facturator.spi.DocumentoDAOService;
import uy.com.tmwc.facturator.spi.EFacturaDAOService;

@Name("eFacturaService")
@Stateless
@Local({ EFacturaService.class })
@Remote({ EFacturaService.class })
public class EFacturaServiceImpl implements EFacturaService {

	private static Charset ISO_8859_1 = Charset.forName("ISO-8859-1");

	public static final String COD_DGI_SUCURSAL = "5"; // Número otorgado por la DGI

	public static final String RUT_EMISOR = "215002560012";

	public static final String RAZON_EMISOR = "SOFIVAL S.A.";

	public static final String DIRECCION_EMISOR = "Gral. LUNA 1388";

	public static final String CIUDAD_EMISOR = "Montevideo";

	public static final String DEPTO_EMISOR = "MONTEVIDEO";

	private static final SimpleDateFormat eFacturaDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	
	private Boolean modeDevelop = false;

	@EJB
	DocumentoDAOService documentoDAOService;

	@EJB
	EFacturaDAOService eFacturaDAOService;
	
	@EJB 
	AuditoriaService auditoriaService;

	@EJB
	CatalogService catalogService;

	public EFacturaResult generarEfactura(Documento doc) throws PermisosException, IOException {
		Documento current = this.documentoDAOService.findDocumento(doc.getDocId());
		
//		String mode_develop = System.getProperty("facturador.mode.develop");
//		if (mode_develop != null) {
//			modeDevelop = mode_develop.equals("true");
//		}

		Integer nroSobre = 0;
		if (current.getDocCFEId() != null && current.getDocCFEId() > 0) {
			nroSobre = current.getDocCFEId();
		} else {
			SerieNumero serieNumero = eFacturaDAOService.generarSobreNumero("EF");
			nroSobre = serieNumero.getNumero().intValue();
			current.setDocCFEId(nroSobre);
			
			// Guardar numero de sobre en el documento.
			documentoDAOService.merge(current, Boolean.FALSE);
		}
		
		String milliseconds = String.valueOf(new Date().getTime()); 

		String rootInput = "C:\\eFactura\\input\\";

		String rootOutput = "C:\\eFactura\\output\\";

		String fileName; 
		if (!modeDevelop) {
			fileName = "eFacturaInfo_" + nroSobre + "_" + milliseconds;
		} else {
			fileName = "eFacturaInfo_1716_1477104858486";
		}

		String pathInput = rootInput + fileName + ".txt";
		
		String pathOutput = rootOutput + fileName + ".pdf";

		BufferedWriter bw = new BufferedWriter(new FileWriterWithEncoding(pathInput, ISO_8859_1));
		try {
			String sobre = getSobre(current);
			if (sobre != null && sobre.length() > 0) {
				bw.write(getSobre(current));
				bw.newLine();
			}
			String encabezado = getEncabezado(current);
			if (encabezado != null && encabezado.length() > 0) {
				bw.write(encabezado);
				bw.newLine();
			}
			String emisor = getEmisor(current);
			if (emisor != null && emisor.length() > 0) {
				bw.write(emisor);
				bw.newLine();
			}
			String receptor = getReceptor(current);
			if (receptor != null && receptor.length() > 0) {
				bw.write(receptor);
				bw.newLine();
			}
			String totales = getTotales(current);
			if (totales != null && totales.length() > 0) {
				bw.write(totales);
				bw.newLine();
			}
			bw = writeLineas(bw, current);

			if (doc.getComprobante().getTipo() == Comprobante.NOTA_CREDITO || doc.getComprobante().getTipo() == Comprobante.DEVOLUCION_CONTADO) {
				String referencias = getReferencias(current);
				bw.write(referencias);
				bw.newLine();
			}
			String addenda = getAddenda(current);
			if (addenda != null && addenda.length() > 0) {
				bw.write(addenda);
				bw.newLine();
			}
		} finally {
			bw.close();
		}

		// Ejecutar .exe por líneas de comandos
		ProcessBuilder processBuilder = new ProcessBuilder("C:\\Program Files (x86)\\eFactShell\\eFact.exe", pathInput, pathOutput);
		if (!modeDevelop) {
			processBuilder.start();
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}

		final String resultPath = rootOutput + fileName + ".txt";
		File f = new File(resultPath);
		while(!f.exists() || !f.canRead()) { 
			try {
				Thread.sleep(500);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}

		String result = f.exists() ? readFile(resultPath) : null;
		String[] rows = result != null ? result.split(System.getProperty("line.separator")) : null;
		
		EFacturaResult resultEFactura = new EFacturaResult();
		resultEFactura.setFileName(fileName + "_cliente");
		resultEFactura.setFileResultData(result != null ? result : "Error");
		resultEFactura.setDocCFEId(nroSobre);
		
		BigDecimal tipoCambioF = documentoDAOService.getTipoCambioFiscal(doc.getMoneda().getCodigo(), new Date());

		byte[] codigoQR = null;
		if (rows != null && !rows[0].contains("1|BE|")) {
			if (rows[1].startsWith("2|")) {
				String[] fields = rows[1].split("\\|");
				String serie = fields[1];
				String nro = fields[2];
				String codSeguridadCFE = fields[3];
				
				current.setSerie(serie);
				current.setNumero(new Long(nro));
				current.setCodSeguridadCFE(codSeguridadCFE);
			}			
			if (rows[2].startsWith("3|")) {				
				String[] fields = rows[2].split("\\|"); 
				
				BigInteger cAEnro = new BigInteger(fields[1]);
				String cAEserie = fields[2];
				Integer cAEdesde = new Integer(fields[3]);
				Integer cAEhasta = new Integer(fields[4]);
				
				current.setCAEnro(cAEnro);
				current.setCAEserie(cAEserie);
				current.setCAEdesde(cAEdesde);
				current.setCAEhasta(cAEhasta);
				current.setCAEemision(new Date());
			}
			
			final String codigoQR_path = rootOutput + fileName + ".png";

			if (!current.getComprobante().isContingencia()) {
				File fileQR = new File(codigoQR_path);
				while(!fileQR.exists()) { 
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				codigoQR = loadFile(codigoQR_path);
			} 

			Short tipoCFEdoc = getTipoCFEDoc(current);
			
			if (tipoCambioF != null) {
				current.setDocTCF(tipoCambioF);
			}
			current.setFecha(new Date());
			current.setFecha2(new Date());

			current.setTipoCFEid(tipoCFEdoc);
			current.setCAEnom(getTipoCFENombre(tipoCFEdoc));
			
			current.setDocCFEstatus(Short.valueOf("1"));
			current.setCodigoQR(codigoQR);
			current.setDocCFEId(nroSobre);
			current.setEmitido(true);
			
			resultEFactura.setFilePDFData(codigoQR);
			resultEFactura.setEfacturaFail(Boolean.FALSE);

			if (current.getComprobante().isNotaCreditoFinanciera()) {
				// Obtener el recibo
				String reciboId = current.getPrevDocId();
				Documento recibo = reciboId != null && reciboId.length() > 0 ? documentoDAOService.findDocumento(reciboId) : null;
				if (recibo != null) {
					recibo.setPrevDocId(current.getDocId());
					recibo.setNotas(recibo.getNotas() + "\nSe emitió una N/C36 PC X DTOS FINANCIEROS " + current.getSerie() + current.getNumero() + ".");
					
					// guardar el recibo 
					documentoDAOService.merge(recibo, Boolean.FALSE);
											
					// Agregar linea de auditoría en el recibo
					Auditoria audit = new Auditoria();
					audit.setAudFechaHora(new Date());
					audit.setDocId(recibo.getDocId());
					audit.setNotas("Se emitió una N/C36 PC X DTOS FINANCIEROS " + current.getSerie() + current.getNumero() + ".");
					audit.setProblemas("Ninguno");
					
					// guardar el documento con las facturas vinculadas.
					auditoriaService.alta(audit);
				}
			} 
			documentoDAOService.merge(current, Boolean.FALSE);

		} else {
			resultEFactura.setFilePDFData(null);
			resultEFactura.setEfacturaFail(Boolean.TRUE);
			current.setDocCFEstatus(Short.valueOf("0"));
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		return resultEFactura;

	}

	public static byte[] loadFile(String sourcePath) throws IOException {
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(sourcePath);
			return readFully(inputStream);
		} finally {
			if (inputStream != null) {
				inputStream.close();
			}
		}
	}

	public static byte[] readFully(InputStream stream) throws IOException {
		byte[] buffer = new byte[8192];
		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		int bytesRead;
		while ((bytesRead = stream.read(buffer)) != -1) {
			baos.write(buffer, 0, bytesRead);
		}
		return baos.toByteArray();
	}

	private String readFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append(System.getProperty("line.separator"));
				line = br.readLine();
			}
			String everything = sb.toString();
			return everything;

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			br.close();
		}
		return null;

	}

	/**
	 * Sobre:
	 * 
	 * Esta línea es la que contiene los datos que identifican al envio.
	 * 
	 * Sintaxis: Tipo|RutEmisor|NroSobre|FechaSobre
	 * 
	 * Ejemplo: 1|216025790012|12|2014-02-13
	 * 
	 * @param doc
	 * @return información del sobre
	 */
	private String getSobre(Documento doc) {
		StringBuffer sobreData = new StringBuffer("1|");
		sobreData.append(RUT_EMISOR).append("|");
		sobreData.append(doc.getDocCFEId()).append("|");
		sobreData.append(eFacturaDateFormat.format(new Date()));

		return sobreData.toString();
	}

	/**
	 * Encabezado
	 * 
	 * Esta línea es la que contiene los datos básicos del comprobante.
	 * 
	 * Sintaxis: Tipo|TipoCFE|Serie|Nro|FechaEmision|IndicadorMBruto|FormaPago|
	 * FechaVencimiento
	 * 
	 * Ejemplo: 2|101|||2013-12-23|1|1|2013-12-23
	 * 
	 * @param doc
	 * @return
	 */
	private String getEncabezado(Documento doc) {
		String formaPagoCFE = !doc.getComprobante().isCredito() ? "1" : "2"; // Valores posibles: 1 = Contado / 2 = Crédito.
		
		String tipoCFE = String.valueOf(getTipoCFEDoc(doc));

		StringBuffer encabezadoData = new StringBuffer("2|");
		
		// Ver tabla Tipos CFE
		encabezadoData.append(tipoCFE).append("|"); 
		
		// Serie del comprobante, se debe asignar solo en los comprobantes de contingencia.
		if (doc.getSerie() != null) {
			encabezadoData.append(doc.getSerie()).append("|"); 
		} else {
			encabezadoData.append("|");
		}
		
		// Número del comprobante, se debe asignar solo en los comprobantes de contingencia.
		if (doc.getNumero() != null) {
			encabezadoData.append(doc.getNumero()).append("|"); 
		} else {
			encabezadoData.append("|");
		}
		
		Date fecha = new Date(); //La fecha en el cabezal siempre es la fecha de hoy.
		
		encabezadoData.append(eFacturaDateFormat.format(fecha)).append("|");
		
		// Valor = 1, de existir indica que las líneas de detalle del comprobante se expresan con IVA incluido (de lo contrario el valor debe ser nulo "||".
		encabezadoData.append("").append("|"); 
		encabezadoData.append(formaPagoCFE).append("|");

		return encabezadoData.toString();
	}

	/**
	 * Emisor
	 * 
	 * Esta línea es la que contiene los datos del emisor electrónico, y de la
	 * sucursal que envía el comprobante.
	 * 
	 * Sintaxis:
	 * Tipo|RutEmisor|RazonEmisor|CodDGISucursal|DireccionEmisor|CiudadEmisor
	 * |DptoEmisor
	 * 
	 * Ejemplo: 3|21758958 0016|NERVUS SRL|1|Euskalerría esq. Rosa de los
	 * Vientos - Villa Santa Rita|Punta delEste|Maldonado
	 * 
	 * @param doc
	 * @return
	 */
	private String getEmisor(Documento doc) {
		StringBuffer emisorData = new StringBuffer("3|");
		emisorData.append(RUT_EMISOR).append("|");
		emisorData.append(RAZON_EMISOR).append("|");
		emisorData.append(COD_DGI_SUCURSAL).append("|");
		emisorData.append(DIRECCION_EMISOR).append("|");
		emisorData.append(CIUDAD_EMISOR).append("|");
		emisorData.append(DEPTO_EMISOR);
		
		return emisorData.toString();
	}

	/**
	 * Receptor
	 * 
	 * Esta línea es la que contiene los datos correspondientes al cliente.
	 * 
	 * Sintaxis:
	 * Tipo|TipoDocReceptor|CodPaisReceptor|DocReceptor|RznSocReceptor|
	 * DirReceptor|CiudadReceptor|DptoReceptor
	 * 
	 * Ejemplo Básico (eTicket sin receptor): 4|3|UY|0||||
	 * 
	 * @param doc
	 * @return
	 */
	private String getReceptor(Documento doc) {
		Cliente cliente = doc.getCliente();
		Contacto contacto = cliente.getContacto();

		Pais pais = catalogService.findCatalogEntity(Pais.class.getSimpleName(), contacto.getPaisIdCto());
		Departamento depto = catalogService.findCatalogEntity(Departamento.class.getSimpleName(), contacto.getDeptoIdCto());

		String tipoDocReceptor = (doc.getTipoDoc() != null && doc.getTipoDoc().equals("R")) ? "2" : "3"; // Entero Ver tabla Tipos de Documento

		String docReceptor = doc.getRut() != null ? doc.getRut() : ""; 
		if (tipoDocReceptor.equals("3")) { // C.I.
			docReceptor = docReceptor.replaceAll("\\D+", "");
			if (docReceptor.length() != 8) {
				docReceptor = "0";
			}
		} else {
			if (docReceptor.length() < 1) {
				tipoDocReceptor = "3";
				docReceptor = "0";
			}
		}

		String codPaisReceptor = pais != null ? pais.getPaisISO() : "UY"; 
		String rznSocReceptor = (doc.getRazonSocial() != null && doc.getRazonSocial().length() > 0) ? doc.getRazonSocial() : contacto.getCtoRSocial(); 
		String dirReceptor = (doc.getDireccion() != null && doc.getDireccion().length() > 0) ? doc.getDireccion() : contacto.getCtoDireccion(); 
		String deptoReceptor = depto != null ? (depto.getNombre() != null ? depto.getNombre().toUpperCase() : "") : "";

		StringBuffer receptorData = new StringBuffer("4|");
		receptorData.append(tipoDocReceptor).append("|");
		receptorData.append(codPaisReceptor).append("|");
		receptorData.append(docReceptor).append("|");
		receptorData.append(rznSocReceptor).append("|");
		receptorData.append(dirReceptor).append("|");
		receptorData.append("").append("|");
		receptorData.append(deptoReceptor);

		return receptorData.toString();
	}

	/**
	 * Totales
	 * 
	 * Datos globales del comprobante, los montos totales, son calculados
	 * automáticamente, por lo que no es necesario declararlos (solo existen los
	 * campos por compatibilidad). Si el tipo de cambio es nulo, se toma la
	 * cotización del sistema para la moneda indicada.
	 * 
	 * Sintaxis:
	 * Tipo|Moneda|TpoCambio|MntNoGrav|MntNetoIvaTasaMin|MntNetoIVATasaBasica
	 * |ivaTasaMin
	 * |ivaTasaBasica|MntIVATasaMin|MntIVATasaBasica|MntTotal|CantLinDet
	 * |MontoNF|MntPagar
	 * 
	 * Ejemplo Básico: 5|UYU||||10|22||||||0
	 * 
	 * @param doc
	 * @return
	 */
	private String getTotales(Documento doc) {
		String docMoneda = doc.getMoneda().getCodigo();

		String tipoCambio = "";
		/*if (doc.getMoneda() != null && !doc.getMoneda().getCodigo().equals("1")) {
			tipoCambio = doc.getCotizacion().setScale(3, RoundingMode.HALF_UP).toString();
		}*/

		String moneda = getMonedaCEF(docMoneda); // Ver tabla monedas.
		String tpoCambio = tipoCambio; // ##.000 Usar solo en el caso de moneda extranjera.
		String mntNoGrav = "";  // Total Monto - No Gravado (Opcional, no se procesa, puede ser nulo)
		String mntNetoIvaTasaMin = ""; // Total Monto Neto - IVA Tasa mínima (Opcional, no se procesa, puede ser nulo)
		String mntNetoIVATasaBasica = ""; // Total Monto Neto - IVA Tasa básica (Opcional, no se procesa, puede ser nulo)
		String ivaTasaMin = "10"; // Por defecto 10, no se admite otro valor OPCIONAL
		String ivaTasaBasica = "22"; // Por defecto 22, no se admite otro valor OPCIONAL
		String mntIVATasaMin = ""; // Total IVA, tasa mínima (Opcional, no se procesa, puede ser nulo)
		String mntIVATasaBasica = ""; // Total IVA, tasa básica (Opcional, no se procesa, puede ser nulo)
		String mntTotal = ""; // Monto Total del comprobante (Opcional, no se procesa, puede ser nulo)
		String cantLinDet = ""; // Cantidad de líneas de detalle (Opcional, no se procesa, puede ser nulo)
		String montoNF = ""; // Monto no Facturable (Opcional, no se procesa, puede ser nulo)
		String mntPagar = ""; // Monto total a pagar (Opcional, no se procesa, puede ser nulo)

		StringBuffer totalesData = new StringBuffer("5|");
		totalesData.append(moneda).append("|");
		totalesData.append(tpoCambio).append("|");
		totalesData.append(mntNoGrav).append("|");
		totalesData.append(mntNetoIvaTasaMin).append("|");
		totalesData.append(mntNetoIVATasaBasica).append("|");
		totalesData.append(ivaTasaMin).append("|");
		totalesData.append(ivaTasaBasica).append("|");
		totalesData.append(mntIVATasaMin).append("|");
		totalesData.append(mntIVATasaBasica).append("|");
		totalesData.append(mntTotal).append("|");
		totalesData.append(cantLinDet).append("|");
		totalesData.append(montoNF).append("|");
		totalesData.append(mntPagar);

		return totalesData.toString();
	}

	/**
	 * Líneas
	 * 
	 * Cada línea representa un ítem del comprobante, se deberá generar una
	 * línea tipo 6 por cada una del comprobante original. En el caso de que
	 * deba aplicar redondeos al comprobante, debe hacerse a través de la
	 * generación de una línea tipo 6 con el redondeo. Los números siempre deben
	 * ser positivos, para redondeos positivos debe usarse indicador de
	 * facturación 6 (Producto o servicio no facturable) y para los negativos 7
	 * (Producto o servicio facturable negativo).
	 * 
	 * Sintaxis:
	 * Tipo|Nro|IndicadorFacturacion|DescArticulo|Cant|UniMed|PrecioUnitario
	 * |DescPorc|DescMonto|RecPorc|RecMonto|MontoItem
	 * 
	 * Para precio = 0, el indicador de facturación debe ser 5.
	 * 
	 * Ejemplo: 6|1|3|Producto 1|2.000|kg|55.00|||||110
	 * 
	 * 
	 * @param doc
	 * @return
	 */
	private BufferedWriter writeLineas(BufferedWriter bw, Documento doc) throws IOException {
		LineasDocumento lineas = doc.getLineas();
		for (LineaDocumento linea : lineas.getLineas()) {
			String concepto = linea.getConcepto();
			if (concepto != null && concepto.length() > 0) {
				concepto = concepto.replace("<", "(").replace(">", ")").replace(System.getProperty("line.separator"), " ").replace("|", " ");
			} else {
				concepto = "";
			}
			StringBuffer lineasData = new StringBuffer("6|");
			lineasData.append(linea.getNumeroLinea()).append("|");
			lineasData.append(getIndDeFacturacionCFE(linea.getIvaArticulo() != null ? linea.getIvaArticulo().getCodigo() : "", linea.getPrecio())).append("|");
			lineasData.append(concepto).append("|");
			lineasData.append(linea.getCantidad().setScale(3, RoundingMode.HALF_UP).toString()).append("|");
			lineasData.append(getUnidadMedida(linea.getArticulo().getUnidadId() != null ? linea.getArticulo().getUnidadId() : "")).append("|");
			lineasData.append(linea.getPrecio() != null ? linea.getPrecio().setScale(6, RoundingMode.HALF_UP).toString() : "").append("|");
			lineasData.append(linea.getDescuento() != null ? linea.getDescuento().setScale(3, RoundingMode.HALF_DOWN).toString() : "").append("|");
			lineasData.append(linea.getImporteDescuentoTotal().setScale(2, RoundingMode.HALF_DOWN).toString()).append("|");
			lineasData.append("").append("|");
			lineasData.append("").append("|");
			lineasData.append("");

			bw.write(lineasData.toString());
			bw.newLine();
		}

		return bw;

	}

	/**
	 * Descuentos y Recargos
	 * 
	 * Pueden ser de 0 hasta 20 líneas. Estos aumentan o disminuyen la base del
	 * impuesto. Estos descuentos o recargos tienen una glosa que especifica el
	 * concepto. Por ejemplo, un descuento global aplicado a un determinado tipo
	 * de producto o un descuento por pago contado que afecta a todos los ítems.
	 * En caso que se apliquen descuentos o recargos globales y haya ítems
	 * exentos, gravados a distintas tasas o no, deberá haber tantas líneas como
	 * conceptos diferentes existan. Se deberá generar una línea para cada
	 * indicador de facturación afectado.
	 * 
	 * Sintaxis: Tipo|NroLin|TipoDR|Glosa|Valor|IndFact
	 * 
	 * Ejemplo: 7|1|D|DESCUENTO COMERCIANTE 0.15% TE|0.00|0
	 * 
	 * @param doc
	 * @return
	 */
	@SuppressWarnings("unused")
	private String getDescuentosyRecargos(Documento doc) {
		return "7|1|D|DESCUENTO COMERCIANTE 0.15% TE|0.00|0";
	}

	/**
	 * Referencias
	 * 
	 * Cuando se emiten los llamados comprobantes de corrección (notas de
	 * crédito y débito) es obligatorio indicar mediante referencias la
	 * situación que se corrige. La referencia puede ser global (Ejemplo:
	 * “Descuentos mes de Junio”) o específica, esto es, listando los
	 * comprobantes que se corrigen.
	 * 
	 * Sintaxis: Tipo|NroLin|IndGlobal|TipoCFERef|Serie|NroCFERef|Razon|
	 * FechaCFEReferencia
	 * 
	 * Ejemplo de referencia: 8|1||101|A|2344||2014-02-13
	 * 
	 * 
	 * @param doc
	 */
	private String getReferencias(Documento doc) {
		String nroLinea = "1";
		String indGlobal = (doc.getIndGlobalCFERef() != null && doc.getIndGlobalCFERef().equals("G")) ? "1" : "" ; // Entero; Se utiliza solo cuando no se pueden identificar los CFE de referencia. Valor = 1, de lo contrario debe ser nulo.
		String tipoCFERef = doc.getTipoCFERef() != null ? doc.getTipoCFERef() : "";	// Entero; Ver tabla TipoCFE
		String serie = doc.getSerieCFERef() != null ? doc.getSerieCFERef() : ""; // Texto; Serie asignada al comprobante de referencia
		String nroCFERef = doc.getNumCFERef() != null ? String.valueOf(doc.getNumCFERef()) : ""; // Entero; Nro. asignado al comprobante de referencia
		String razon = indGlobal.equals("1") ? doc.getRazonCFERef() : ""; // Texto; Razón de la referencia
		String fechaCFEReferencia = ""; // Fecha; Fecha de emisión del CFE Referenciado
		if (doc.getFechaCFERef() != null) {
			fechaCFEReferencia = eFacturaDateFormat.format(doc.getFechaCFERef());
		} 

		StringBuffer lineasData = new StringBuffer("8|");
		lineasData.append(nroLinea).append("|");
		lineasData.append(indGlobal).append("|");
		lineasData.append(tipoCFERef).append("|");
		lineasData.append(serie).append("|");
		lineasData.append(nroCFERef).append("|");
		lineasData.append(razon).append("|");
		lineasData.append(fechaCFEReferencia);

		return lineasData.toString();
	}

	/**
	 * Addenda
	 * 
	 * Leyenda para mostrar en el comprobante
	 * 
	 * Sintaxis: Tipo|Addenda
	 * 
	 * Ejemplo: 10| Presentando este comprobante en los próximos 7 días
	 * descontaremos de su compra el 10% del valor
	 * 
	 * @param doc
	 * @return Línea e addenda
	 */
	private String getAddenda(Documento doc) {
		String tpoDoc = doc.getComprobante().getNombre().toUpperCase();

		StringBuffer addenda = new StringBuffer();
		addenda.append("Tipo Doc: ").append(tpoDoc).append(" \n");
		addenda.append(doc.getComprobante().getNombre().toUpperCase()).append(" \n");
		if (doc.getCliente() != null && doc.getCliente().getContacto().getCtoDocumento() != null) {
			addenda.append("CI: ").append(doc.getCliente().getContacto().getCtoDocumento()).append(" \n");
		}

		return "10|" + addenda.toString();
	}

	private String getMonedaCEF(String moneda) {
		if (moneda.equals(Moneda.CODIGO_MONEDA_PESOS)) {
			return "UYU";
		} else if (moneda.equals(Moneda.CODIGO_MONEDA_DOLAR)) {
			return "USD";
		} else if (moneda.equals(Moneda.CODIGO_MONEDA_EUROS)) {
			return "EUR";
		}
		return "";
	}

	private String getIndDeFacturacionCFE(String tipo, BigDecimal precio) {
		if (precio != null && precio.compareTo(BigDecimal.ZERO) == 0) {
			return "5";
		}
		if ("1".equals(tipo) || "3".equals(tipo)) {
			return "3"; // Tasa básica 22%
		} else if ("2".equals(tipo) || "4".equals(tipo)) {
			return "2"; // Tasa mínima 10%
		} else{
			return "1"; // Excento de IVA
		}
	}
	
	private short getTipoCFEDoc(Documento doc) {
		int tipoDocReceptor = (doc.getTipoDoc() != null && doc.getTipoDoc().equals("R")) ? 2 : 3; // Entero Ver tabla Tipos de Documento

		String docReceptor = doc.getRut() != null ? doc.getRut() : ""; 
		if (tipoDocReceptor == 2) { // Is RUT
			if (docReceptor.length() < 1) {
				tipoDocReceptor = 3;
			}
		}		
		return getTipoCFE(doc.getComprobante(), tipoDocReceptor);
	
	}

	private short getTipoCFE(Comprobante comprobante, int docReceptor) {
		if (comprobante.isContingencia()) {
			if (comprobante.getCodigo().equals("300") || comprobante.getCodigo().equals("302")) { // e-ticket contado, e-ticket crédito
				return 201;
			} else if (comprobante.getCodigo().equals("304") || comprobante.getCodigo().equals("306")) { // e-factura contado, e-factura crédito
				return 211;
			} else if (comprobante.getCodigo().equals("301") || comprobante.getCodigo().equals("303")) { // e-ticket nota de crédito
				return 202;
			} else if (comprobante.getCodigo().equals("305") || comprobante.getCodigo().equals("307")) { // e-factura nota de crédito
				return 212;
			} 
			return 0;
			
		} else {
			switch (comprobante.getTipo()) {
			case Comprobante.VENTA_CONTADO:
			case Comprobante.VENTA_CREDITO:
				if (docReceptor == 2) {
					return 111;
				} else {
					return 101;
				}
			case Comprobante.DEVOLUCION_CONTADO:
			case Comprobante.NOTA_CREDITO:
				if (docReceptor == 2) {
					return 112;
				} else {
					return 102;
				}
			default:
				return 0;
			}
		}
		
	}
	
	
	private String getTipoCFENombre(int tipo) {
		switch (tipo) {
		case 101:
			return "e-Ticket";
		case 102: 
			return "Nota de Crédito de e-Ticket";
		case 111: 
			return "e-Factura";
		case 112:
			return "Nota de Crédito de e-Factura";
		case 201:
			return "e-Ticket de Contingencia";
		case 211:
			return "e-Factura de Contingencia";
		case 202:
			return "e-Ticket N/C Contingencia";
		case 212:
			return "e-Factura N/C Contingencia";
		}
		return "";
	}
	


	private String getUnidadMedida(String unidadId) {
		if (unidadId.equals("A")) {
			return "CTOS";
		} else if (unidadId.equals("B")) {
			return "MLES";
		} else if (unidadId.equals("C")) {
			return "CJAS";
		} else if (unidadId.equals("D")) {
			return "KGS";
		} else {
			return "UNID";
		}

	}

}
