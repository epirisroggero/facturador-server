package uy.com.tmwc.facturator.service;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jboss.seam.annotations.Name;

import uy.com.tmwc.facturator.entity.DescuentoPrometidoComprobante;
import uy.com.tmwc.facturator.rapi.DescuentosPrometidosService;
import uy.com.tmwc.facturator.spi.DescuentosPrometidosDAOService;

@Name("descuentosPrometidosService")
@Stateless
public class DescuentosPrometidosServiceImpl implements DescuentosPrometidosService {

	@EJB DescuentosPrometidosDAOService descuentosPrometidosDAOService;
	
	public String persist(DescuentoPrometidoComprobante e) {
		return descuentosPrometidosDAOService.persist(e);
	}

	public void merge(DescuentoPrometidoComprobante e) {
		descuentosPrometidosDAOService.merge(e);
	}
	
	public Boolean remove(DescuentoPrometidoComprobante entrega) {
		return descuentosPrometidosDAOService.remove(entrega);
	}
	
	 public List<DescuentoPrometidoComprobante> getDescuentosPrometidos(String cmpid, String categoriaCliente) {
		 return descuentosPrometidosDAOService.getDescuentosPrometidos(cmpid, categoriaCliente);
	 }



}
