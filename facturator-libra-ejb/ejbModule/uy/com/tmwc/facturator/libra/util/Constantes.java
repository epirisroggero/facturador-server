package uy.com.tmwc.facturator.libra.util;

import java.util.Calendar;
import java.util.Date;

public class Constantes {
	
	public static final String NOMBRE_TABLA_GENERADOR_DOCID = "Documentos";
	public static final Date DEFAULT_DATE;

	public static String getEmpId() {
		String value = System.getProperty("facturator.empId");
		return value != null ? value : "FULLTIME";
	}
	
	static {
		Calendar cal = Calendar.getInstance();
		cal.set(1000, 0, 1);
		DEFAULT_DATE = cal.getTime();
	}
}