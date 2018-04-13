package uy.com.tmwc.facturator.libra.ejb;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;

import uy.com.tmwc.facturator.entity.SerieNumero;
import uy.com.tmwc.facturator.libra.entity.NumeradorPK;
import uy.com.tmwc.facturator.rapi.CatalogService;
import uy.com.tmwc.facturator.spi.EFacturaDAOService;

@Stateless
@Local({ EFacturaDAOService.class })
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
public class EFacturaDAOServiceImpl extends ServiceBase implements EFacturaDAOService {

	@PersistenceContext
	EntityManager em;
	
	@EJB
	CatalogService catalogService;

	public SerieNumero generarSobreNumero(String codigoNumerador) {
		
		uy.com.tmwc.facturator.libra.entity.Numerador numerador = (uy.com.tmwc.facturator.libra.entity.Numerador) this.em.find(
				uy.com.tmwc.facturator.libra.entity.Numerador.class, new NumeradorPK(getEmpId(), codigoNumerador));

		if (numerador != null) {
			this.em.lock(numerador, LockModeType.WRITE);
			this.em.flush();
			
			long numero = numerador.getNumero();
			return new SerieNumero(numerador.getSerie(), Long.valueOf(numero));
		}
		return null;
	}

}
