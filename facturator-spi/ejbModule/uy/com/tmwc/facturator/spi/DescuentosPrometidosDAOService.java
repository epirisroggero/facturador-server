package uy.com.tmwc.facturator.spi;

import java.util.List;

import uy.com.tmwc.facturator.entity.DescuentoPrometidoComprobante;

public abstract interface DescuentosPrometidosDAOService {

	public abstract String persist(DescuentoPrometidoComprobante e);

	public abstract void merge(DescuentoPrometidoComprobante e);

	public abstract Boolean remove(DescuentoPrometidoComprobante param);

	public List<DescuentoPrometidoComprobante> getDescuentosPrometidos(String cmpid, String categoriaCliente);

}
