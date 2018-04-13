package uy.com.tmwc.facturator.entity;

import java.io.Serializable;

public class SerieNumero implements Serializable {
	private static final long serialVersionUID = 1L;
	private String serie;
	private Long numero;

	public SerieNumero() {
	}

	public SerieNumero(String serie, Long numero) {
		this.serie = serie;
		this.numero = numero;
	}

	public String getSerie() {
		return this.serie;
	}

	public void setSerie(String serie) {
		this.serie = serie;
	}

	public Long getNumero() {
		return this.numero;
	}

	public void setNumero(Long numero) {
		this.numero = numero;
	}
	
	public String toString() {
		if (serie != null)
			return serie + " " + numero;
		else if (numero != null)
			return numero.toString();
		else
			return "Sin serie/numero";
	}
}