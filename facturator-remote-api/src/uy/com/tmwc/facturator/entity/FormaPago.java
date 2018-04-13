package uy.com.tmwc.facturator.entity;

public class FormaPago extends CodigoNombreEntity {
	private static final long serialVersionUID = 1L;
	
	public static final FormaPago EFECTIVO = new FormaPago("1", "Efectivo_INTERNAL_USE");
	
	private String formaPagoAbrevia;
	
	private String formaPagoTipo;

	public FormaPago() {
	}

	public FormaPago(String codigo, String nombre) {
		super(codigo, nombre);
	}

	public String getFormaPagoAbrevia() {
		return formaPagoAbrevia;
	}

	public void setFormaPagoAbrevia(String formaPagoAbrevia) {
		this.formaPagoAbrevia = formaPagoAbrevia;
	}

	public String getFormaPagoTipo() {
		return formaPagoTipo;
	}

	public void setFormaPagoTipo(String formaPagoTipo) {
		this.formaPagoTipo = formaPagoTipo;
	}
}