package uy.com.tmwc.facturator.spi;

import uy.com.tmwc.facturator.entity.Usuario;


public abstract interface UsuariosDAOService {

	public abstract void updateClaveSup(String usuOId, String clave);
	
	public abstract void updateEmail(String usuOId, String email);
	
	public void update(Usuario userId);


}
