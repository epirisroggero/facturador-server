package uy.com.tmwc.facturator.deudores;

import java.util.IdentityHashMap;

import uy.com.tmwc.facturator.dto.CodigoNombre;
import uy.com.tmwc.facturator.entity.CodigoNombreEntity;

public class CodigoNombreFactory {

	private IdentityHashMap<CodigoNombreEntity, CodigoNombre> cache = new IdentityHashMap<CodigoNombreEntity, CodigoNombre>();

	public CodigoNombre getFor(CodigoNombreEntity entity) {
		if (entity == null)
			return null;
		CodigoNombre dto = cache.get(entity);
		if (dto == null) {
			dto = new CodigoNombre(entity);
			cache.put(entity, dto);
		}
		return dto;
	}
}