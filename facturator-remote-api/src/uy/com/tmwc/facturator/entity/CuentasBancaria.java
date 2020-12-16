package uy.com.tmwc.facturator.entity;

public class CuentasBancaria extends CodigoNombreEntity  {

	private static final long serialVersionUID = 1L;
	
	private String bancoIdCue;

	private String cuentaActiva;

	private String cuentaCheques;

	private String nombre;

	private String cuentaNotas;

	private String cuentaNro;

	private String cuentaTitular;

	private short mndIdCue;

	private String rubIdCue;

	
	public CuentasBancaria() {
		super();
	}


	public String getBancoIdCue() {
		return bancoIdCue;
	}


	public void setBancoIdCue(String bancoIdCue) {
		this.bancoIdCue = bancoIdCue;
	}


	public String getCuentaActiva() {
		return cuentaActiva;
	}


	public void setCuentaActiva(String cuentaActiva) {
		this.cuentaActiva = cuentaActiva;
	}


	public String getCuentaCheques() {
		return cuentaCheques;
	}


	public void setCuentaCheques(String cuentaCheques) {
		this.cuentaCheques = cuentaCheques;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getCuentaNotas() {
		return cuentaNotas;
	}


	public void setCuentaNotas(String cuentaNotas) {
		this.cuentaNotas = cuentaNotas;
	}


	public String getCuentaNro() {
		return cuentaNro;
	}


	public void setCuentaNro(String cuentaNro) {
		this.cuentaNro = cuentaNro;
	}


	public String getCuentaTitular() {
		return cuentaTitular;
	}


	public void setCuentaTitular(String cuentaTitular) {
		this.cuentaTitular = cuentaTitular;
	}


	public short getMndIdCue() {
		return mndIdCue;
	}


	public void setMndIdCue(short mndIdCue) {
		this.mndIdCue = mndIdCue;
	}


	public String getRubIdCue() {
		return rubIdCue;
	}


	public void setRubIdCue(String rubIdCue) {
		this.rubIdCue = rubIdCue;
	}


}
