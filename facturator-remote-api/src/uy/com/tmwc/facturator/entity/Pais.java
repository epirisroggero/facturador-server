package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

public class Pais extends CodigoNombreEntity implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String paisISO;

	public String getPaisISO() {
		return paisISO;
	}

	public void setPaisISO(String paisISO) {
		this.paisISO = paisISO;
	}
}