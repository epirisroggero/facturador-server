package uy.com.tmwc.facturator.rapi;

import java.io.IOException;

import com.google.gwt.user.client.rpc.RemoteService;

import uy.com.tmwc.facturator.dto.EFacturaResult;
import uy.com.tmwc.facturator.entity.Documento;

public interface EFacturaService extends RemoteService {
	
	EFacturaResult generarEfactura(Documento doc) throws PermisosException, IOException;
}