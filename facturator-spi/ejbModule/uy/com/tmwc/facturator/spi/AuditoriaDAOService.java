package uy.com.tmwc.facturator.spi;

import java.util.List;

import uy.com.tmwc.facturator.entity.Auditoria;

public abstract interface AuditoriaDAOService {
	public abstract void persist(Auditoria e);

	public abstract void merge(Auditoria e);

	public List<Auditoria> getAuditoria(String docId);
}
