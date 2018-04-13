package uy.com.tmwc.facturator.spi;
import java.util.List;

import javax.ejb.Local;

import uy.com.tmwc.facturator.entity.ListaPrecios;
import uy.com.tmwc.facturator.entity.TipoCambio;

@Local
public interface ReportesDAO {
	
	List<TipoCambio> getTipoCambios();

	List<TipoCambio> getUltimosTipoCambios();
	
	List<Object[]> stockPrecio(String[] listas, String tipoFiltro, String proveedores, String familias, String desde, String hasta, boolean filtrarCeros,
			boolean filtrarNegativos);

	List<ListaPrecios> getListasPrecios();
	
}
