package uy.com.tmwc.facturator.server.util;

import java.lang.reflect.Field;
import java.util.ArrayList;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.dozer.DozerBeanMapper;
import org.dozer.DozerEventListener;
import org.dozer.event.DozerEvent;

import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.facturator.spi.CatalogDAOService;
import uy.com.tmwc.facturator.utils.MappingUtils;

@Stateless
public class DTO2ModelMappingServiceImpl implements DTO2ModelMappingService {
	private static final DozerBeanMapper mapper = new DozerBeanMapper();

	static {
		ArrayList<String> files = new ArrayList<String>();
		files.add("dozerMappings_client.xml");
		mapper.setMappingFiles(files);

		ArrayList<DozerEventListener> eventListeners = new ArrayList<DozerEventListener>();
		eventListeners.add(new CatalogEntityConverterListener());
		mapper.setEventListeners(eventListeners);
	}

	public DozerBeanMapper getDozerBeanMapper() {
		return mapper;
	}

	private static class CatalogEntityConverterListener implements DozerEventListener {
		private CodigoNombreEntity convert(CodigoNombreEntity source) {
			if (source == null) {
				return null;
			}
			CatalogDAOService service;
			try {
				service = (CatalogDAOService) new InitialContext().lookup("facturator-server-ear/CatalogDAOServiceImpl/local");
			} catch (NamingException e) {
				throw new RuntimeException(e);
			}
			CodigoNombreEntity result = service.findCatalogEntity(source.getClass(), source.getCodigo());
			if (result == null) {
				throw new IllegalArgumentException("catalog entity not found");
			}
			return result;
		}

		public void mappingFinished(DozerEvent event) {
		}

		public void mappingStarted(DozerEvent event) {
		}

		public void postWritingDestinationValue(DozerEvent event) {
			Object value = event.getDestinationValue();
			if (value == null) {
				return;
			}
			if (CodigoNombreEntity.class.isAssignableFrom(value.getClass())) {
				value = convert((CodigoNombreEntity) value);

				String fieldName = event.getFieldMap().getDestFieldName();
				setField(event.getDestinationObject(), fieldName, value);
			}
		}

		private void setField(Object object, String fieldName, Object value) {
			try {
				Field field = MappingUtils.getField(object.getClass(), fieldName);
				field.setAccessible(true);
				field.set(object, value);
			} catch (SecurityException e) {
				throw new RuntimeException(e);
			} catch (IllegalArgumentException e) {
				throw new RuntimeException(e);
			} catch (IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			// Field field;
		}

		public void preWritingDestinationValue(DozerEvent event) {
		}
	}
}