package uy.com.tmwc.facturator.rapi;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;

import uy.com.tmwc.facturator.deudores.DocumentoDeudor;
import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.entity.ResumenEntregas;
import uy.com.tmwc.facturator.liquidacion.ParticipacionVendedor;

//@RemoteServiceRelativePath("remote/liquidacionService")
public abstract interface LiquidacionService /* extends RemoteService */
{
	TreeMap<CodigoNombre, ArrayList<ParticipacionVendedor>> reporteVentasPeriodo(Date paramDate1, Date paramDate2);

	ResumenEntregas getResumenEntregas(Date paramDate1, Date paramDate2, BigDecimal gastosPeriodo);

	ResumenEntregas calcularCostosOperativos(Date paramDate1, Date paramDate2, BigDecimal gastosPeriodo);

	List<DocumentoDeudor> getDocumentosDeudores(Date fechaHoy);
	
	List<DocumentoDeudor> getDocumentosDeudoresCliente(Date fechaHoy, String clienteId);

	//Calcula los costos operativos del mes, calcula valores nuevos de entrega, liquida.
	byte[] generarLiquidacion(Date fechaDesde, Date fechaHasta, Date fechaCorte, BigDecimal gastosPeriodo);
}