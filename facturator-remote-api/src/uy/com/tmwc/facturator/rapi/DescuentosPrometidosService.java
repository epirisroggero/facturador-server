package uy.com.tmwc.facturator.rapi;

import java.util.List;

import uy.com.tmwc.facturator.entity.DescuentoPrometidoComprobante;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("remote/descuentosPrometidosService")
public abstract interface DescuentosPrometidosService extends RemoteService {
	 public abstract String persist(DescuentoPrometidoComprobante e);
	 
	 public abstract void merge(DescuentoPrometidoComprobante e);
	 
	 public abstract Boolean remove(DescuentoPrometidoComprobante e);
	 
	 public List<DescuentoPrometidoComprobante> getDescuentosPrometidos(String cmpid, String categoriaCliente);
	 
}
