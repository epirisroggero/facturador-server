package uy.com.tmwc.facturator.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class LineasDocumento implements Serializable {
	private static final long serialVersionUID = 1L;
	private List<LineaDocumento> lineas = new ArrayList<LineaDocumento>();
	private Documento documento;

	public LineasDocumento() {
	}

	public LineasDocumento(Documento documento) {
		this.documento = documento;
	}

	public void add(LineaDocumento linea) {
		linea.setNumeroLinea(this.lineas.size() + 1);
		this.lineas.add(linea);
		linea.setDocumento(this.documento);
	}

	public void remove(int numero) {
		this.lineas.remove(numero - 1);
		int numeroLinea = 1;
		for (LineaDocumento linea : this.lineas) {
			linea.setNumeroLinea(numeroLinea);
			numeroLinea++;
		}
	}

	public List<LineaDocumento> getLineas() {
		if (lineas == null) {
			lineas = new ArrayList<LineaDocumento>();
		}
		return this.lineas;
	}

	public void setLineas(List<LineaDocumento> lineas) {
		this.lineas = lineas;

	}

	public void setDocumento(Documento doc) {
		this.documento = doc;
	}

	public Documento getDocumento() {
		return this.documento;
	}

	public void fixNumerosLineas() {
		if (lineas != null) {
			int i = 1;
			for (LineaDocumento linea : lineas) {
				linea.setNumeroLinea(i);
				i++;
			}
		}
	}
		
	public void actualizarPrecioLineas(BigDecimal coeficiente) {
		if (lineas != null) {
			for (LineaDocumento linea : lineas) {
				if (linea.getCoeficienteImp() != null && linea.getCoeficienteImp().compareTo(BigDecimal.ZERO) == 1) {
					linea.setPrecio(linea.getPrecio().multiply(linea.getCoeficienteImp()));
				} else {
					linea.setPrecio(linea.getPrecio().multiply(coeficiente));
				}
			}
		}
	
	}
}