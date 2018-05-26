package uy.com.tmwc.facturator.entity;

public class Banco extends CodigoNombreEntity {
	
	private static final long serialVersionUID = 1L;
	
	private String bancoAbrevia;

	private String bancoNotas;

	private String bancoTipo;

	
	public Banco() {
		super();
	}

	public Banco(String codigo, String nombre) {
		super(codigo, nombre);
	}

	public String getBancoAbrevia() {
		return bancoAbrevia;
	}

	public void setBancoAbrevia(String bancoAbrevia) {
		this.bancoAbrevia = bancoAbrevia;
	}

	public String getBancoNotas() {
		return bancoNotas;
	}

	public void setBancoNotas(String bancoNotas) {
		this.bancoNotas = bancoNotas;
	}

	public String getBancoTipo() {
		return bancoTipo;
	}

	public void setBancoTipo(String bancoTipo) {
		this.bancoTipo = bancoTipo;
	}


}
