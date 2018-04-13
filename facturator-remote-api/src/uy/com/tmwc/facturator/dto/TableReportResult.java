package uy.com.tmwc.facturator.dto;

public class TableReportResult {
	
	private String[] columns;
	
	private Object[][] rowsWithData;

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public Object[][] getRowsWithData() {
		return rowsWithData;
	}

	public void setRowsWithData(Object[][] rowsWithData) {
		this.rowsWithData = rowsWithData;
	}
	
}
