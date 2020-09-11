package uy.com.tmwc.facturator.utils;

public enum MessageKey {
	OK ("ok"),
	ERROR("error");
	
	private final String value;

	MessageKey(String v) {
        value = v;
    }
	
	public String value() {
        return value;
    }

    public static MessageKey fromValue(String v) {
        for (MessageKey c: MessageKey.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
