package uy.com.tmwc.facturator.rapi;

import java.util.List;

import uy.com.tmwc.facturator.entity.Auditoria;

public interface AuditoriaService {
	
	void alta(Auditoria e);
	
	List<Auditoria> getAuditoria(String docId);

}
