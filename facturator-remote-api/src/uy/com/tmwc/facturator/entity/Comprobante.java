package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import uy.com.tmwc.facturator.utils.Maths;

public class Comprobante extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;

	public static final String COTIZACION = "1";

	public static final int VENTA_CREDITO = 1;
	public static final int NOTA_CREDITO = 2;
	public static final int VENTA_CONTADO = 3;
	public static final int DEVOLUCION_CONTADO = 4;
	public static final int RECIBO_COBRO = 5;
	
	public static final int CHEQUE_RECIBIDO = 43;
	public static final int TARJETA_RECIBIDA = 44;
	public static final int CONFORMES_A_COBRAR = 45;
	public static final int CREDITO_BANCARIO = 51;	
	

	public static final int COMPRA_CREDITO = 21;
	public static final int NOTA_CREDITO_COMPRA = 22;
	public static final int COMPRA_CONTADO = 23;
	public static final int DEVOLUCION_COMPRA_CONTADO = 24;

	public static final int MOVIMIENTO_DE_STOCK_DE_PROVEEDORES = 31;
	public static final int MOVIMIENTO_DE_STOCK_DE_CLIENTE = 32;

	private static final int[] comprobantesEmitibles = { 1, 2, 3, 4 };

	private static final String[] comprobantesGasto = { "110", "111", "112", "113", "114", "115", "116", "212", "213",
			"214", "215" };

	private Deposito depositoOrigen;
	private Deposito depositoDestino;
	private int tipo;
	private String cmptiponom;
	private String cmpfifo;
	private String formatoidcmp;
	private String cmpgastos;
	private String numCmpId;
/*
	private String cmpContingencia;
	private String cmpExportacion;
	private String cmpNotaDebito;
	private String cmpEfactura;
	private String cmpActivo;
	private String cmpRemitoInterno;
	private String cmpNotas;
*/
	private String cmpiva;

	private List<DescuentoPrometidoComprobante> descuentosPrometidos; // ordenado por días de retraso.
	private boolean aster;
	private boolean exento;

	public String getCmpfifo() {
		return cmpfifo;
	}

	public void setCmpfifo(String cmpfifo) {
		this.cmpfifo = cmpfifo;
	}

	public String getCmptiponom() {
		return cmptiponom;
	}

	public void setCmptiponom(String cmptiponom) {
		this.cmptiponom = cmptiponom;
	}

	public boolean isNotaCreditoFinanciera() {
		return getCodigo().equals("28");
	}

	public boolean isCredito() {
		return this.tipo == VENTA_CREDITO || this.tipo == COMPRA_CREDITO;
	}

	public boolean isMueveCaja() {
		return (this.tipo == VENTA_CONTADO || this.tipo == COMPRA_CONTADO || this.tipo == DEVOLUCION_CONTADO || this.tipo == RECIBO_COBRO)
				&& !getCodigo().equals("1") && !getCodigo().equals("93") && !getCodigo().equals("94")
				&& !getCodigo().equals("99");
	}

	public boolean isVenta() {
		return (this.tipo == VENTA_CONTADO) || (this.tipo == VENTA_CREDITO);
	}

	public boolean isCompra() {
		return (this.tipo == COMPRA_CONTADO) || (this.tipo == COMPRA_CREDITO);
	}

	public boolean isGasto() {
		List<String> entityTypesList = Arrays.asList(comprobantesGasto);
		return entityTypesList.contains(getCodigo());
	}

	public boolean isRecibo() {
		return (this.tipo == RECIBO_COBRO);
	}

	public boolean isDevolucion() {
		return (this.tipo == DEVOLUCION_CONTADO) || (this.tipo == NOTA_CREDITO);
	}

	public boolean isMovimientoStock() {
		return (this.tipo == MOVIMIENTO_DE_STOCK_DE_CLIENTE || this.tipo == MOVIMIENTO_DE_STOCK_DE_PROVEEDORES);
	}

	
	public boolean isContingencia() {
		return getCodigo().equals("300") || getCodigo().equals("301") || getCodigo().equals("302")
				|| getCodigo().equals("303") || getCodigo().equals("304") || getCodigo().equals("305")
				|| getCodigo().equals("306") || getCodigo().equals("307");
	}
	
	public boolean isFormaPago() {
		return (this.tipo == CHEQUE_RECIBIDO || this.tipo == TARJETA_RECIBIDA || this.tipo == CREDITO_BANCARIO || this.tipo == CONFORMES_A_COBRAR);
	}

	public BigDecimal getDescuentoPrometido() {
		if (this.descuentosPrometidos == null) {
			return BigDecimal.ZERO;
		}

		BigDecimal maxDto = BigDecimal.ZERO;
		for (DescuentoPrometidoComprobante d : this.descuentosPrometidos) {
			if (d.getDescuento().compareTo(maxDto) > 0) {
				maxDto = d.getDescuento();
			}
		}

		return maxDto;
	}

	public BigDecimal calcularMontoDescuentoPrometido(BigDecimal monto) {
		return monto.multiply(getDescuentoPrometido()).divide(Maths.ONE_HUNDRED, 2, RoundingMode.HALF_UP);
	}

	public BigDecimal getDescuentoPrometido(int diasRetraso, String categoriaCliente) {
		for (DescuentoPrometidoComprobante descuentoPrometido : getDescuentosParaCategoria(categoriaCliente)) {
			if (diasRetraso <= descuentoPrometido.getRetraso()) {
				return descuentoPrometido.getDescuento();
			}
		}
		return BigDecimal.ZERO;
	}

	private List<DescuentoPrometidoComprobante> getDescuentosParaCategoria(String categoriaCliente) {
		ArrayList<DescuentoPrometidoComprobante> result = new ArrayList<DescuentoPrometidoComprobante>();
		for (DescuentoPrometidoComprobante e : descuentosPrometidos) {

			String categCliente = e.getCategCliente() != null ? e.getCategCliente().getCodigo() : null;

			if (StringUtils.isEmpty(categoriaCliente) && StringUtils.isEmpty(categoriaCliente)
					|| categoriaCliente != null && categoriaCliente.equals(categCliente)) {
				result.add(e);
			}
		}
		return result;
	}

	public BigDecimal aplicarDescuentoPrometido(BigDecimal importe, String categoriaCliente) {
		return aplicarDescuentoPrometido(importe, 0, categoriaCliente);
	}

	public BigDecimal aplicarDescuentoPrometido(BigDecimal importe, int diasRetraso, String categoriaCliente) {
		BigDecimal descuentoPrometido = getDescuentoPrometido(diasRetraso, categoriaCliente);
		if (descuentoPrometido.compareTo(BigDecimal.ZERO) == 0) {
			return importe;
		}
		return importe.multiply(Maths.ONE_HUNDRED.subtract(descuentoPrometido)).divide(Maths.ONE_HUNDRED);
	}

	public Deposito getDepositoOrigen() {
		return this.depositoOrigen;
	}

	public void setDepositoOrigen(Deposito depositoOrigen) {
		this.depositoOrigen = depositoOrigen;
	}

	public Deposito getDepositoDestino() {
		return this.depositoDestino;
	}

	public void setDepositoDestino(Deposito depositoDestino) {
		this.depositoDestino = depositoDestino;
	}

	public int getTipo() {
		return this.tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;

		setAster(isAster());
	}

	public boolean isAster() {
		boolean flag = false;
		String regex = System.getProperty("facturator.comprobantes.aster");
		if (regex != null) {
			String[] values = regex.split(",");
			for (int i = 0; i < values.length; i++) {
				if (values[i].trim().equals(getCodigo())) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public List<DescuentoPrometidoComprobante> getDescuentosPrometidos() {
		return this.descuentosPrometidos;
	}

	public void setDescuentosPrometidos(List<DescuentoPrometidoComprobante> descuentosPrometidos) {
		this.descuentosPrometidos = descuentosPrometidos;
	}

	public boolean isEmitible() {
		boolean result = Arrays.binarySearch(comprobantesEmitibles, this.tipo) >= 0;
		return result;
	}

	public boolean getAster() {
		return aster;
	}

	public void setAster(boolean value) {
		this.aster = value;
	}

	public boolean isExento() {
		return exento;
	}

	public void setExento(boolean exento) {
		this.exento = exento;
	}

	public String getFormatoidcmp() {
		return formatoidcmp;
	}

	public void setFormatoidcmp(String formatoidcmp) {
		this.formatoidcmp = formatoidcmp;
	}

	public String getCmpgastos() {
		return cmpgastos;
	}

	public void setCmpgastos(String cmpgastos) {
		this.cmpgastos = cmpgastos;
	}

	public String getNumCmpId() {
		return numCmpId;
	}

	public void setNumCmpId(String numCmpId) {
		this.numCmpId = numCmpId;
	}

	public String getCmpiva() {
		return cmpiva;
	}

	public void setCmpiva(String cmpiva) {
		this.cmpiva = cmpiva;
	}

}