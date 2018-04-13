package uy.com.tmwc.facturator.spi;

import uy.com.tmwc.facturator.entity.Entrega;

public abstract interface EntregaDAOService {

	public abstract String persist(Entrega e);
	
	public abstract void merge(Entrega e);
	
	public abstract Boolean remove(Entrega paramDocumento);
}
