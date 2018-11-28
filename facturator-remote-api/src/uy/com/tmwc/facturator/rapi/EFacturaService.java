package uy.com.tmwc.facturator.rapi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import com.google.gwt.user.client.rpc.RemoteService;

import uy.com.tmwc.facturator.dto.EFacturaResult;
import uy.com.tmwc.facturator.entity.Documento;
import uy.com.tmwc.facturator.entity.ValidationException;

public interface EFacturaService extends RemoteService {
	
	EFacturaResult generarEfactura(Documento doc) throws PermisosException, IOException;
	
	Documento updateDocumento(Documento doc) throws PermisosException, ValidationException, IOException;
	
	ArrayList<String> obtenerDuplicados(Date desde, Date hasta); 
}