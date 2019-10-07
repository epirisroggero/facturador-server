package uy.com.tmwc.facturator.entity;

import java.math.BigDecimal;

public class Moneda extends CodigoNombreEntity {
	
	public static final String CODIGO_MONEDA_PESOS = "1";
	public static final String CODIGO_MONEDA_DOLAR = "2";
	public static final String CODIGO_MONEDA_EUROS = "3";
	public static final String CODIGO_MONEDA_PESOS_ASTER = "4";
	public static final String CODIGO_MONEDA_DOLAR_ASTER = "5";
	public static final String CODIGO_MONEDA_EUROS_ASTER = "6";
	
	private static final long serialVersionUID = 1L;
	private short redondeo;
	private String simbolo;
	private boolean aster;
	private String mndAbrevia;


	public Moneda() {
		super();
	}

	public String getSimbolo() {
		return simbolo;
	}
	
	public void setSimbolo(String simbolo) {
		this.simbolo = simbolo;
	}

	public Moneda(String codigo, String nombre) {
		super(codigo, nombre);
	}

	public BigDecimal redondear(BigDecimal monto) {
		return monto.setScale(this.redondeo, 4);
	}

	public short getRedondeo() {
		return this.redondeo;
	}

	public void setRedondeo(short redondeo) {
		this.redondeo = redondeo;
	}

	public boolean isAster() {
		return this.getNombre() != null && this.getNombre().contains("*");
	}

	public void setAster(boolean aster) {
		this.aster = aster;
	}
	
	public String getMndAbrevia() {
		return mndAbrevia;
	}

	public void setMndAbrevia(String mndAbrevia) {
		this.mndAbrevia = mndAbrevia;
	}


	public static String getCodigoMonedaNoAster(String codigoMoneda) {
		if (codigoMoneda.equals(CODIGO_MONEDA_DOLAR_ASTER)) {
			return CODIGO_MONEDA_DOLAR;
		} else if (codigoMoneda.equals(CODIGO_MONEDA_PESOS_ASTER)) {
			return CODIGO_MONEDA_PESOS;
		} else if (codigoMoneda.equals(CODIGO_MONEDA_EUROS_ASTER)) {
			return CODIGO_MONEDA_EUROS;
		} else {
			return codigoMoneda;
		}
	}
	
	public String toString() {
		return getNombre();  
	}

}