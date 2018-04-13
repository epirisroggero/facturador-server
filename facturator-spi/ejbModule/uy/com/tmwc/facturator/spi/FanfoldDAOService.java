package uy.com.tmwc.facturator.spi;

import uy.com.tmwc.facturator.entity.Fanfold;

public abstract interface FanfoldDAOService {

	public abstract String persist(Fanfold e);
	
	public abstract void merge(Fanfold e);
	
	public abstract Boolean remove(Fanfold paramDocumento);
}
