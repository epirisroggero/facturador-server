package uy.com.tmwc.facturator.dto;

public class EFacturaResult {

	private byte[] filePDFData;
	
	private String fileName;
	
	private String resultData;
	
	private Integer docCFEId;
	
	private String codigoSeguridad;
	
	private String nroCAE;
	
	private Boolean efacturaFail;
	

	public byte[] getFilePDFData() {
		return filePDFData;
	}

	public void setFilePDFData(byte[] filePDFData) {
		this.filePDFData = filePDFData;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileResultData() {
		return resultData;
	}

	public void setFileResultData(String fileResultData) {
		this.resultData = fileResultData;
	}

	public String getResultData() {
		return resultData;
	}

	public void setResultData(String resultData) {
		this.resultData = resultData;
	}

	public String getCodigoSeguridad() {
		return codigoSeguridad;
	}

	public void setCodigoSeguridad(String codigoSeguridad) {
		this.codigoSeguridad = codigoSeguridad;
	}

	public Integer getDocCFEId() {
		return docCFEId;
	}

	public void setDocCFEId(Integer docCFEId) {
		this.docCFEId = docCFEId;
	}

	public Boolean getEfacturaFail() {
		return efacturaFail;
	}

	public void setEfacturaFail(Boolean efacturaFail) {
		this.efacturaFail = efacturaFail;
	}

	public String getNroCAE() {
		return nroCAE;
	}

	public void setNroCAE(String nroCAE) {
		this.nroCAE = nroCAE;
	}

	
}
