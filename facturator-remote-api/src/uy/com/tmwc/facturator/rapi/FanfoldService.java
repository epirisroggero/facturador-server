package uy.com.tmwc.facturator.rapi;

import uy.com.tmwc.facturator.entity.Fanfold;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("remote/fanfoldService")
public abstract interface FanfoldService extends RemoteService {
	 public abstract String persist(Fanfold e);
	 
	 public abstract void merge(Fanfold e);
	 
	 public abstract Boolean remove(Fanfold e);

}
