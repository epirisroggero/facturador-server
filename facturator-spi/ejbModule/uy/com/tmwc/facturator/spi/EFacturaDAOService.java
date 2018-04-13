package uy.com.tmwc.facturator.spi;

import uy.com.tmwc.facturator.entity.SerieNumero;

public interface EFacturaDAOService {
	
	SerieNumero generarSobreNumero(String codigoNumerador);
}
