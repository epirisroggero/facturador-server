package uy.com.tmwc.facturator.libra.util;

import org.dozer.DozerConverter;

public class BooleanDozerConverter extends DozerConverter<String, Boolean> {
	public BooleanDozerConverter() {
		super(String.class, Boolean.class);
	}
	
	public static boolean toBooleanValue(String source) {
		Boolean value = toBoolean(source);
		return value == null ? false : value.booleanValue();
	}

	public static Boolean toBoolean(String source) {
		if ("S".equals(source))
			return Boolean.TRUE;
		if (("N".equals(source)) || ("".equals(source))) {
			return Boolean.FALSE;
		} else {
			return null;
		}
	}
	
	public static String toString(Boolean source) {
		if (Boolean.TRUE.equals(source))
			return "S";
		if (Boolean.FALSE.equals(source)) {
			return "N";
		} else {
			return null;
		}
	}

	public Boolean convertTo(String source, Boolean destination) {
		Boolean value = BooleanDozerConverter.toBoolean(source);
		if (value == null)
			throw new IllegalStateException("Unknown value!");
		return value;
	}

	public String convertFrom(Boolean source, String destination) {
		String value = BooleanDozerConverter.toString(source);
		if (value == null)
			throw new IllegalStateException("Unknown value!");
		return value;
	}
}