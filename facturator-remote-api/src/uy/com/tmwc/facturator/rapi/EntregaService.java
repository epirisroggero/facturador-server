package uy.com.tmwc.facturator.rapi;

import uy.com.tmwc.facturator.entity.Entrega;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("remote/entregaService")
public abstract interface EntregaService extends RemoteService {
	 public abstract String persist(Entrega e);
	 
	 public abstract void merge(Entrega e);
	 
	 public abstract Boolean remove(Entrega e);

}
