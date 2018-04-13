package uy.com.tmwc.facturator.figaro;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.apache.commons.io.FileUtils;

import uy.com.tmwc.facturator.entity.ListaPrecios;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.TipoCambio;
import uy.com.tmwc.facturator.spi.ReportesDAOService;
import uy.com.tmwc.facturator.utils.Dates;

/**
 * Session Bean implementation class ReportesService
 */
@Stateless
public class ReportesDAOServiceImpl implements ReportesDAOService {

	protected static final String COMPROBANTE_DEVOLUCION_CONTADO = "9";
	protected static final String COMPROBANTE_NOTA_CREDITO = "2";
	protected static final String COMPROBANTE_VENTA_CONTADO = "10";
	protected static final String COMPROBANTE_VENTA_CREDITO = "3";
	protected static final String FIGARO_MONEDA_PESOS = "0";
	
	protected static final String COMPROBANTE_COMPRA_CONTADO = "16";
	protected static final String COMPROBANTE_COMPRA_CREDITO = "1";
	protected static final String COMPROBANTE_COMPRA_DEVOLUCION_CONTADO = "15";
	protected static final String COMPROBANTE_COMPRA_NOTA_CREDITO = "4";
	
	protected static final String RECIBO_COBRANZA = "5";
	protected static final String RECIBO_PAGO = "11";

	protected static final String[] COMPROBANTES_VENTA = new String[] {COMPROBANTE_NOTA_CREDITO, COMPROBANTE_NOTA_CREDITO, COMPROBANTE_VENTA_CONTADO, COMPROBANTE_VENTA_CREDITO};
	protected static final String[] COMPROBANTES_COMPRA = new String[] {COMPROBANTE_COMPRA_CONTADO, COMPROBANTE_COMPRA_CREDITO, COMPROBANTE_COMPRA_DEVOLUCION_CONTADO, COMPROBANTE_COMPRA_NOTA_CREDITO};
	protected static final BigDecimal THRESHOLD = new BigDecimal("4000");

//	private static final String CONNECTION_STRING = "jdbc:odbc:FigaroDSN";
//	private static final String CONNECTION_STRING = ;
//	private static final String CONNECTION_SERVIDOR_STRING = "jdbc:odbc:FigaroServidorDSN";
	
	static {
		try {
			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		} 
	}
	
    /**
     * Default constructor. 
     */
    public ReportesDAOServiceImpl() {
    }
    
    public List<Object[]> stockPrecio(final String[] listas, String tipoFiltro, final String desde, final String hasta, boolean filtrarCeros, boolean filtrarNegativos) {
    	final StringBuilder sb = new StringBuilder(
    			"SELECT " +
    			"  a.ar_codigo as codigoArticulo, " +
    			"  a.ar_nombre as nombreArticulo, " +
    			"  a.pr_codigo as proveedor, " +
    			"  a.jq_codigo as familia, " +
    			"  sum(ls_cant*ls_signo) as stock");
    	
    	for (int i = 0; i < listas.length; i++) {
			sb.append(
					", pb" + i + ".pb_precio as precio" + i +
					", pb" + i + ".mo_codigo as mprecio" + i + 
					", pb" + i + ".pb_fecha as fprecio" + i);
		} 
    	sb.append(" ").append(
    			"FROM articulo a " +
    			"LEFT JOIN linstock l on a.ar_codigo = l.ar_codigo");

    	int i=0;
    	for (String codigoLista : listas) {
    		sb.append(" " +
    			"LEFT JOIN presbase pb" + i + " on a.ar_codigo = pb" + i + ".ar_codigo AND pb" + i + ".pc_codigo='" + padLeft(codigoLista, 2) + "'"
    		);
    		i++;
    	}
    	
    	sb.append(" WHERE a.activo = 'S' ");

    	String decision;
		if ("Familia".equals(tipoFiltro))
			decision = "jq_codigo";
		else if ("Proveedor".equals(tipoFiltro)) 
			decision = "pr_codigo";
		else //"Codigo"
			decision = "ar_codigo";
		if (desde != null) sb.append(" AND UPPER(a." + decision + ") >= UPPER(?)" );
		if (hasta != null) sb.append(" AND UPPER(a." + decision + ") <= UPPER(?)" );

		sb.append(" ").append(
				"GROUP BY" +
				"  a.ar_codigo, " +
    			"  a.ar_nombre, " +
    			"  a.pr_codigo, " +
    			"  a.jq_codigo ");
    	
    	for (i = 0; i < listas.length; i++) {
			sb.append(
					", pb" + i + ".pb_precio" +
					", pb" + i + ".mo_codigo" +
					", pb" + i + ".pb_fecha");
    	}
    	
    	sb.append(" ORDER BY a.ar_codigo");
    	
    	if (filtrarCeros || filtrarNegativos) {
    		sb.append(" HAVING stock ");
    		if (filtrarNegativos && filtrarCeros) {
    			sb.append(" > 0");
    		} else if (filtrarNegativos) {
    			sb.append(" >= 0"); 
    		} else { //filtrarZero
    			sb.append(" <> 0");
    		}
    	}
    	
    	System.out.println(sb.toString());
    	
    	return execute(getConnectionString(true), new Task<List<Object[]>>() {

			public List<Object[]> execute(Connection connection) throws SQLException {
				PreparedStatement statement = connection.prepareStatement(sb.toString());
				{ //set parameters
					int paramCount = 1; //el primer parametro es el del indice 1
					if (!isEmpty(desde)) statement.setString(paramCount++, desde);
					if (!isEmpty(hasta)) statement.setString(paramCount++, hasta);
				}
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				ArrayList<Object[]> data = new ArrayList<Object[]>();
				while (resultSet.next()) {
					int cIndex=0;
					Object[] entry = new Object[5 + 3*listas.length];
					entry[cIndex++] = resultSet.getString("codigoArticulo").trim();
					entry[cIndex++] = resultSet.getString("nombreArticulo").trim();
					entry[cIndex++] = resultSet.getString("proveedor");
					entry[cIndex++] = resultSet.getString("familia");
					entry[cIndex++] = valueOrDefault(resultSet.getInt("stock"));
					for (int j = 0; j < listas.length; j++) {
						entry[cIndex++] = resultSet.getBigDecimal("precio" + j);
						entry[cIndex++] = resultSet.getString("mprecio" + j); 
						entry[cIndex++] = resultSet.getDate("fprecio" + j);
					}
					data.add(entry);
				}
				
				System.out.println("La consulta de reporte stock y precios devuelve " + data.size() + " resultado(s).");
				
				return data;			
			}
    	});
    }

	public List<TipoCambio> getTipoCambios() {
		return execute(getConnectionString(false), new Task<List<TipoCambio>>() {
			public List<TipoCambio> execute(Connection connection) throws SQLException {
				PreparedStatement statement = connection.prepareStatement("select tc_fecha as dia, mo_codigo as codigoMoneda, tc_cotiz as comercial from tcambio");
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				ArrayList<TipoCambio> data = new ArrayList<TipoCambio>();
				while (resultSet.next()) {
					data.add(
						new TipoCambio(resultSet.getDate("dia"), new Moneda(resultSet.getString("codigoMoneda").trim(), null), resultSet.getBigDecimal("comercial"))
					);
				}
				return data;
			}
		});
    }


	public List<ListaPrecios> getListasPrecios() {
		return execute(getConnectionString(true), new Task<List<ListaPrecios>>() {
			public List<ListaPrecios> execute(Connection connection) throws SQLException {
				PreparedStatement statement = connection.prepareStatement("select pc_codigo as codigo, pc_nombre as nombre from defprecs");
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				ArrayList<ListaPrecios> data = new ArrayList<ListaPrecios>();
				while (resultSet.next()) {
					data.add(
						new ListaPrecios(resultSet.getString("codigo").trim(), resultSet.getString("nombre"))
					);
				}
				return data;
			}});
	}

	public List<Moneda> getMonedasReporte() {
		return execute(getConnectionString(true), new Task<List<Moneda>>() {
			public List<Moneda> execute(Connection connection) throws SQLException {
				PreparedStatement statement = connection.prepareStatement("select mo_codigo as codigo, mo_simbolo as nombre from monedas where mo_codigo in ('0', '1', '6') order by 1");
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				ArrayList<Moneda> data = new ArrayList<Moneda>();
				while (resultSet.next()) {
					data.add(
						new Moneda(resultSet.getString("codigo").trim(), resultSet.getString("nombre"))
					);
				}
				return data;
			}
		});
	}
	
	private <T> T execute(String connectionString, Task<T> task) {
		Connection connection = getConnection(connectionString);
    	try {
    		return task.execute(connection);
    	} catch (SQLException e) {
    		throw new RuntimeException(e);
		} finally {
    		try {
				connection.close();
			} catch (SQLException e) {
				System.err.println("exception catch intentando limpiar conexion a figaro " + e.getMessage());
				e.printStackTrace();
			}
    	}		
	}
	
	private interface Task<T> {
		T execute(Connection connection) throws SQLException;
	}

	private boolean isEmpty(String desde) {
		return desde == null || desde.equals(""); 
	}
    
    private Integer valueOrDefault(Integer value) {
    	return value != null ? value : 0;
	}

	private String padLeft(String codigoLista, int count) {
    	String result = codigoLista;
    	while (result.length() < count) {
    		result = " " + result;
    	}
    	return result;
	}

	private Connection getConnection(String connString) {
		Connection connection;
		try {
			connection = DriverManager.getConnection(connString);
//			connection.setReadOnly(true);
//			connection.setTransactionIsolation(Connection.TRANSACTION_NONE);
			return connection;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private String getConnectionString(boolean empresa) {
		String path = empresa ? System.getProperty("facturator.dsnEmpresa") : System.getProperty("facturator.dsnServidor");
		return getConnectionString(path);
	}
	
	private String getConnectionString(String path) {
		return "jdbc:odbc:Driver={Microsoft Visual FoxPro Driver};Exclusive=No;SourceDB=" + path + ";SourceType=DBF";
	}

	public void generarReporteDGI(final int mes, final int anio) throws IOException {
		final Date desde = Dates.createDate(anio, mes, 1);
		final Date hasta = Dates.getLastDayOfMonth(anio, mes);
		final HashMap<String, ArrayList<Object[]>> tcmodel = getTCModel(getTiposCambio(desde, hasta));
		
		final HashMap<Long, HashMap<Long, BigDecimal>> acum = new HashMap<Long, HashMap<Long,BigDecimal>>();

		final File tmpFile = File.createTempFile("output.dbf", null);
		FileUtils.copyInputStreamToFile(getClass().getResourceAsStream("proto.dbf"), tmpFile);

		final File reportLogOutputFile = File.createTempFile("output.txt", null);
		final OutputStream reportLogOutput = new FileOutputStream(reportLogOutputFile);
		
		try {
		process(desde, hasta, tcmodel, acum, getQueryReporteDGIDeudores(), true, reportLogOutput);
		process(desde, hasta, tcmodel, acum, getQueryReporteDGIAcreedores(), true, reportLogOutput);
		process(desde, hasta, tcmodel, acum, getQueryReporteDGIDeudoresDescuentosRecibos(), false, reportLogOutput);
		process(desde, hasta, tcmodel, acum, getQueryReporteDGIAcreedoresDescuentosRecibos(), false, reportLogOutput);

		
		String parentDir = tmpFile.getParent();
		execute(getConnectionString(parentDir), new Task<Void>() {
			public Void execute(Connection connection) throws SQLException {
				PreparedStatement stmt = connection.prepareStatement("insert into " + tmpFile.getName() + " values (?, ?, ?, ?)" );
				for (Map.Entry<Long, HashMap<Long, BigDecimal>> entry : acum.entrySet()) {
					int i = 1;
					stmt.setBigDecimal(i++, new BigDecimal(entry.getKey())); //ruc
					String aniomesfac = anio + String.format("%02d", mes);
					stmt.setBigDecimal(i++, new BigDecimal(aniomesfac));
					for (Map.Entry<Long, BigDecimal> entryLinea : entry.getValue().entrySet()) {
						int j = i;
						stmt.setBigDecimal(j++, new BigDecimal(entryLinea.getKey())); //codigolinea
						BigDecimal montoRed = entryLinea.getValue().setScale(0, RoundingMode.HALF_UP);
						if (montoRed.compareTo(THRESHOLD) > 0) {
							stmt.setBigDecimal(j++, montoRed);
							stmt.execute();
							addLogEntry(reportLogOutput, "agregando " + entry.getKey() + ", " + entryLinea.getKey() + ", " + montoRed);
						} else {
							addLogEntry(reportLogOutput, "ignorando " + entry.getKey() + ", " + entryLinea.getKey() + ", " + montoRed);
						}
					}
				}
				return null;
			}
		});
		} finally {
			reportLogOutput.close();
		}
		
		FileUtils.copyFile(tmpFile, new File(System.getProperty("jboss.home.url").substring(6) + "/server/default/deploy/facturator-server-ear.ear/facturator-server.war/output.dbf"));
		FileUtils.copyFile(reportLogOutputFile, new File(System.getProperty("jboss.home.url").substring(6) + "/server/default/deploy/facturator-server-ear.ear/facturator-server.war/output.txt"));
	}

	private void process(final Date desde, final Date hasta, final HashMap<String, ArrayList<Object[]>> tcmodel,
			final HashMap<Long, HashMap<Long, BigDecimal>> acum, final String query, final boolean queryHasRucFac, final OutputStream reportLogOutput) {
		execute(getConnectionString(true), new Task<Void>() {
			public Void execute(Connection connection) throws SQLException {
				PreparedStatement statement = connection.prepareStatement(query);
				int i = 1;
				statement.setDate(i++, new java.sql.Date(desde.getTime()));
				statement.setDate(i++, new java.sql.Date(hasta.getTime()));
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				while (resultSet.next()) {
					String idFac = resultSet.getString("co_nombre") + " " + resultSet.getString("ca_serie") + "/" + resultSet.getString("ca_numero") + " " + resultSet.getString("ca_fecha") ;
					String moCodigo = resultSet.getString("mo_codigo");
					String ivCodigo = resultSet.getString("iv_codigo");
					String coBasico = resultSet.getString("co_basico");
					BigDecimal fdMontIva = resultSet.getBigDecimal("fd_montiva");
					java.sql.Date caFecha = resultSet.getDate("ca_fecha");
					String rucFacStr = queryHasRucFac ? resultSet.getString("ruc_fac") : null;
					if (rucFacStr == null) {
						rucFacStr = resultSet.getString("ruc");
					}
					Long rucFac = processRut(rucFacStr);					 
					if (rucFac == null) {
						addLogEntry(reportLogOutput, "ignorando " + idFac + ", rut invalido: " + rucFacStr);
						continue;
					}
					BigDecimal montoPesos = cambiar(fdMontIva, moCodigo, caFecha, tcmodel);
					if (montoPesos == null) {
						addLogEntry(reportLogOutput, "ignorando " + idFac + " , no se puede convertir monto en moneda " + moCodigo
								+ " para fecha " + caFecha);
						continue;
					}
					Long codigoLinea = getCodigoLinea(coBasico, ivCodigo);
					if (codigoLinea == null) {
						addLogEntry(reportLogOutput, "ignorando " + idFac + ", linea indeterminada " + coBasico + ", " + ivCodigo);
						continue;
					}
					if (coBasico.equals(COMPROBANTE_NOTA_CREDITO) || coBasico.equals(COMPROBANTE_DEVOLUCION_CONTADO)
							|| coBasico.equals(COMPROBANTE_COMPRA_NOTA_CREDITO) || coBasico.equals(COMPROBANTE_COMPRA_DEVOLUCION_CONTADO)
							|| coBasico.equals(RECIBO_COBRANZA) || coBasico.equals(RECIBO_PAGO)) {
						montoPesos = montoPesos.negate();
					}
					addLogEntry(reportLogOutput, "agregando " + idFac + "|" + rucFac + "|" + caFecha + "|" + moCodigo + "|" + fdMontIva
							+ "|" + montoPesos);
					addMonto(acum, rucFac, codigoLinea, montoPesos);
				}
				return null;
			}
		});
	}

	protected void addLogEntry(OutputStream reportLogOutput, String string) {
		System.out.println(string);
		try {
			reportLogOutput.write((string + "\n").getBytes(Charset.defaultCharset()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getQueryReporteDGIDeudores() {
		final String query1 =
		"select " +
			"c.id_cab, " + 
			"c.ca_serie, " + 
			"c.ca_numero, " + 
			"cl.cl_ruc as ruc, " +
			"li_texto as ruc_fac, " +
			"mo_simbolo, " +
			"co_nombre, " +
			"c.mo_codigo, " +
			"fd_montiva, " +
			"iv_codigo, " +
			"c.co_signo, " +
			"c.co_basico, " +
			"c.ca_fecha " + 
		"from ((((" +
			"cabsdeud c inner join comprobs co on co.co_codigo = c.co_codigo) " +
			"inner join clientes cl on cl.cl_codigo = c.cl_codigo) " +
			"inner join monedas mo on mo.mo_codigo = c.mo_codigo) " +
			"inner join facredeu f on f.id_cab = c.id_cab) " +
			"left join literfac li on (li.id_cab = c.id_cab and li_texto = 'W') " +
		"where " +
			"c.co_basico in (2, 3, 9, 10) " +
			"and fd_montiva <> 0 " + //con esto ya filtramos remitos.
			"and c.ca_fecha between ? and ? ";
		return query1;
	}
	
	private String getQueryReporteDGIAcreedores() {
		final String query1 =
			"select " +
				"c.id_cab, " + 
				"c.ca_serie, " + 
				"c.ca_numero, " + 
				"pr_ruc as ruc, " +
				"li_texto as ruc_fac, " +
				"mo_simbolo, " +
				"co_nombre, " +
				"c.mo_codigo, " +
				"fd_montiva, " +
				"iv_codigo, " +
				"c.co_signo, " +
				"c.co_basico, " +
				"c.ca_fecha " + 
			"from ((((" +
				"cabsacre c inner join comprobs co on co.co_codigo = c.co_codigo) " +
				"inner join proveeds p on p.pr_codigo = c.pr_codigo) " +
				"inner join monedas mo on mo.mo_codigo = c.mo_codigo) " +
				"inner join facreacr f on f.id_cab = c.id_cab) " +
				"left join literfac li on (li.id_cab = c.id_cab and li_texto = 'W') " +
			"where " +
				"c.co_basico in (1, 4, 15, 16) " +
				"and fd_montiva <> 0 " + //con esto ya filtramos remitos.
				"and c.ca_fecha between ? and ? ";
		return query1;
	}
	
	private String getQueryReporteDGIDeudoresDescuentosRecibos() {
		final String q = 
			"select " +
				"c.id_cab, " + 
				"c.ca_serie, " + 
				"c.ca_numero, " +
				"cl.cl_ruc as ruc, " +
				"mo_simbolo, " +
				"co_nombre, " +
				"c.mo_codigo, " +
				"fd_montiva, " +
				"iv_codigo, " +
				"c.co_signo, " +
				"c.co_basico, " +
				"c.ca_fecha " + 
			"from ((((" +
				"cabsdeud c inner join comprobs co on co.co_codigo = c.co_codigo) " +
				"inner join clientes cl on cl.cl_codigo = c.cl_codigo) " +
				"inner join monedas mo on mo.mo_codigo = c.mo_codigo) " +
				"inner join facredeu f on f.id_cab = c.id_cab) " +
			"where " +
				"c.co_basico = 5 " +
				"and fd_montiva <> 0 " + 
				"and c.ca_fecha between ? and ? ";	
		return q;
	}

	private String getQueryReporteDGIAcreedoresDescuentosRecibos() {
		final String q = 
			"select " +
				"c.id_cab, " + 
				"c.ca_serie, " + 
				"c.ca_numero, " +
				"p.pr_ruc as ruc, " +
				"mo_simbolo, " +
				"co_nombre, " +
				"c.mo_codigo, " +
				"fd_montiva, " +
				"iv_codigo, " +
				"c.co_signo, " +
				"c.co_basico, " +
				"c.ca_fecha " + 
			"from ((((" +
				"cabsacre c inner join comprobs co on co.co_codigo = c.co_codigo) " +
				"inner join proveeds p on p.pr_codigo = c.pr_codigo) " +
				"inner join monedas mo on mo.mo_codigo = c.mo_codigo) " +
				"inner join facreacr f on f.id_cab = c.id_cab) " +
			"where " +
				"c.co_basico = 11 " +
				"and fd_montiva <> 0 " + 
				"and c.ca_fecha between ? and ? ";	
		return q;
	}
	
	protected Long processRut(String string) {
		if (string == null) {
			return null;
		} else {
			String tmp = string.replace(" ", "");
			try {
				return Long.parseLong(tmp);
			} catch (NumberFormatException e) {
				return null;
			}
		}
	}

	protected void addMonto(HashMap<Long, HashMap<Long, BigDecimal>> acum, long rucFac, long codigoLinea, BigDecimal montoPesos) {
		HashMap<Long, BigDecimal> entry1 = acum.get(rucFac);
		if (entry1 == null) {
			entry1 = new HashMap<Long, BigDecimal>();
			acum.put(rucFac, entry1);
		}
		BigDecimal entry2 = entry1.get(codigoLinea);
		if (entry2 == null) {
			entry2 = montoPesos;
		} else {
			entry2 = entry2.add(montoPesos);
		}
		entry1.put(codigoLinea, entry2);
	}

	protected Long getCodigoLinea(String coBasico, String ivCodigo) {
		if (Arrays.asList(COMPROBANTES_VENTA).contains(coBasico) || coBasico.equals(RECIBO_COBRANZA)) {
			return 502L;
		} else if (Arrays.asList(COMPROBANTES_COMPRA).contains(coBasico) || coBasico.equals(RECIBO_PAGO)) {
			return 505L;
		} else {
			return null;
		}
	}

	protected boolean validarRut(String rucFac) {
		return rucFac != null && rucFac.length() == 12; //TODO tiene sentido mejorarlo?
	}
	
	private static BigDecimal cambiar(BigDecimal monto, String moneda, Date fecha, HashMap<String, ArrayList<Object[]>> tcmodel) {
		if (moneda.equals(FIGARO_MONEDA_PESOS)) {
			return monto;
		}
		BigDecimal tc = getTipoCambio(moneda, fecha, tcmodel);
		if (tc == null) {
			return null;
		}
		return monto.multiply(tc);
	}
	
	private static BigDecimal getTipoCambio(String moneda, Date fecha, HashMap<String, ArrayList<Object[]>> tcmodel) {
//		if (moneda.equals(FIGARO_MONEDA_PESOS)) {
//			return 
//		}
		ArrayList<Object[]> tclist = tcmodel.get(moneda);
		if (tclist == null) {
			return null;
		}
		Object[] entry = null;
		for (Object[] item : tclist) {
			Date tcdate = (Date) item[1];
			if (tcdate.before(fecha)) {
				entry  = item;
			} else {
				break;
			}
		}
		if (entry != null) {
			return (BigDecimal) entry[2];
		} else {
			return null;
		}
	}
	
	private ArrayList<Object[]> getTiposCambio(Date fechaDesde, Date fechaHasta) {
		String queryFirst = "select top 1 * from tcambio where tc_fecha < ? order by tc_fecha desc";
		String queryMiddle = "select * from tcambio where tc_fecha >= ? and tc_fecha <= ? order by tc_fecha";
		String queryLast = "select top 1 * from tcambio where tc_fecha > ? order by tc_fecha asc";
		
		ArrayList<Object[]> allTC = new ArrayList<Object[]>();
		allTC.addAll(getTiposCambio(queryFirst, fechaDesde));
		allTC.addAll(getTiposCambio(queryMiddle, fechaDesde, fechaHasta));
		allTC.addAll(getTiposCambio(queryLast, fechaHasta));
		
		return allTC;
	}
	
	private static HashMap<String, ArrayList<Object[]>> getTCModel(ArrayList<Object[]> tclist) {
		HashMap<String, ArrayList<Object[]>> nruter = new HashMap<String, ArrayList<Object[]>>();
		for (Object[] tc : tclist) {
			String moneda = (String) tc[0];
			ArrayList<Object[]> entry = nruter.get(moneda);
			if (entry == null) {
				entry = new ArrayList<Object[]>();
				nruter.put(moneda, entry);
			}
			entry.add(tc);
		}
		return nruter;
	}
	
	private ArrayList<Object[]> getTiposCambio(final String query, final Date... dateParams) {
		return execute(getConnectionString(false), new Task<ArrayList<Object[]>>() {
			public ArrayList<Object[]> execute(Connection connection) throws SQLException {
				PreparedStatement statement = connection.prepareStatement(query);
				int i = 1;
				for (Date date : dateParams) {
					statement.setDate(i, new java.sql.Date(date.getTime()));
					i++;
				}
				statement.execute();
				ResultSet resultSet = statement.getResultSet();
				ArrayList<Object[]> data = new ArrayList<Object[]>();
				while (resultSet.next()) {
					data.add(
						new Object[] {resultSet.getString(1), resultSet.getDate(2), resultSet.getBigDecimal(3)}
					);
				}
				return data;
			}
		});		
	}
}
