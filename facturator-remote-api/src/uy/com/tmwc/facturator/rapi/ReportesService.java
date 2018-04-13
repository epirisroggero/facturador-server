package uy.com.tmwc.facturator.rapi;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import uy.com.tmwc.facturator.dto.ReportParameters;
import uy.com.tmwc.facturator.dto.TableReportResult;
import uy.com.tmwc.facturator.entity.Moneda;

public interface ReportesService {

	TableReportResult getReporteStockPrecios(ReportParameters parameters);

	List<Moneda> getMonedasReporte();
	
	byte[] generarListadoControlLineasVenta(Date fechaDesde, Date fechaHasta, BigDecimal rentaMinima, BigDecimal rentaMaxima);

	byte[] generarLiquidacionVendedores(Date desde, Date hasta, String[] compsIncluidos, String[] compsExcluidos) throws IOException;
}
