package uy.com.tmwc.facturator.entity;



public class Concepto extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;
	
	private String conceptoActivo;

	private String conceptoIdNom;

	private String conceptoRetencion;

	private String conceptoRubro;

	private String conceptoTipo;

	private String conceptoTotales;

	private String grupoCptId;

	private Short ivaIdConcepto;

	
	public Concepto() {
		super();
	}

	public Concepto(String codigo, String nombre) {
		super(codigo, nombre);
	}

	public int hashCode() {
		int result = super.hashCode();

		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj)) {
			return false;
		}
		return (obj instanceof Concepto);
	}

	public String getConceptoActivo() {
		return conceptoActivo;
	}

	public void setConceptoActivo(String conceptoActivo) {
		this.conceptoActivo = conceptoActivo;
	}

	public String getConceptoIdNom() {
		return conceptoIdNom;
	}

	public void setConceptoIdNom(String conceptoIdNom) {
		this.conceptoIdNom = conceptoIdNom;
	}

	public String getConceptoRetencion() {
		return conceptoRetencion;
	}

	public void setConceptoRetencion(String conceptoRetencion) {
		this.conceptoRetencion = conceptoRetencion;
	}

	public String getConceptoRubro() {
		return conceptoRubro;
	}

	public void setConceptoRubro(String conceptoRubro) {
		this.conceptoRubro = conceptoRubro;
	}

	public String getConceptoTipo() {
		return conceptoTipo;
	}

	public void setConceptoTipo(String conceptoTipo) {
		this.conceptoTipo = conceptoTipo;
	}

	public String getConceptoTotales() {
		return conceptoTotales;
	}

	public void setConceptoTotales(String conceptoTotales) {
		this.conceptoTotales = conceptoTotales;
	}

	public String getGrupoCptId() {
		return grupoCptId;
	}

	public void setGrupoCptId(String grupoCptId) {
		this.grupoCptId = grupoCptId;
	}

	public Short getIvaIdConcepto() {
		return ivaIdConcepto;
	}

	public void setIvaIdConcepto(Short ivaIdConcepto) {
		this.ivaIdConcepto = ivaIdConcepto;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
}
