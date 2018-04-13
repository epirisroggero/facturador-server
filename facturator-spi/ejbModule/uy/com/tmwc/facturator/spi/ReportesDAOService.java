package uy.com.tmwc.facturator.spi;
import java.io.IOException;

import javax.ejb.Local;

@Local
public interface ReportesDAOService {
	
	void generarReporteDGI(int mes, int anio) throws IOException;
	
}
