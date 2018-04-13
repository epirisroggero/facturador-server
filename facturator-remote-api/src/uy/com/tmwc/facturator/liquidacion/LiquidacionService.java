package uy.com.tmwc.facturator.liquidacion;

import java.util.ArrayList;
import java.util.Date;
import java.util.TreeMap;

import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.entity.ResumenEntregas;

public interface LiquidacionService {

	/**
	 * La liquidacion depende del calculo de operativos, el cual debe hacerse
	 * para el periodo al cual pertenece la fecha hasta, mas todos los periodos
	 * anteriores.
	 * 
	 * Ejemplo, si liquidamos del 1/1/2010 al 31/1/2010, y los periodos son
	 * mensuales comenzando del dia 1 del mes, es necesario que los operativos
	 * esten calculados para Enero/2010, Diciembre/2010, Noviembre/2010, etc...
	 * 
	 * Usualmente se calculan los operativos del periodo correspondiente al
	 * liquidado, y se suponen calculados los anteriores (ya que se liquida una
	 * vez por periodo)
	 * 
	 * @param fechaDesde
	 * @param fechaHasta
	 * @return
	 */
	Liquidacion liquidar(Date fechaDesde, Date fechaHasta);

	TreeMap<CodigoNombre, ArrayList<ParticipacionVendedor>> reporteVentasPeriodo(Date fechaDesde, Date fechaHasta);

	ResumenEntregas getResumenEntregas(Date fechaDesde, Date fechaHasta);

	ResumenEntregas calcularCostosOperativos(Date fechaDesde, Date fechaHasta);

}
