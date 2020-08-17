package uy.com.tmwc.facturator.libra.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.dozer.DozerEventListener;
import org.dozer.event.DozerEvent;
import org.dozer.fieldmap.FieldMap;

public class EmpIdDozerListener implements DozerEventListener {

	public EmpIdDozerListener() {
	}

	public void mappingFinished(DozerEvent event) {
	}

	public void mappingStarted(DozerEvent arg0) {
	}

	public void postWritingDestinationValue(DozerEvent event) {
		FieldMap fmap = event.getFieldMap();
		if (fmap == null) {
			return;
		}
		String fname = fmap.getDestFieldName();
		if (fname == null) {
			return;
		}
		String[] parts = fname.split("\\.");
		if ((parts.length > 1) && (parts[0].equals("id"))) {
			Object destObj = event.getDestinationObject();
			if (destObj == null) {
				return;
			}
			Object compositeId = getCompositeId(destObj);
			if (compositeId == null) {
				return;
			}
			setEmpId(compositeId);
		}
	}

	private void setEmpId(Object compositeId) {
		try {
			Class cidClazz = compositeId.getClass();
			Method method = cidClazz.getMethod("setEmpId", new Class[] { String.class });
			method.invoke(compositeId, new Object[] { Constantes.getEmpId() });
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}

	private Object getCompositeId(Object destObj) {
		Class clazz = destObj.getClass();
		try {
			Method method = clazz.getMethod("getId", new Class[0]);
			Object compositeId = method.invoke(destObj, new Object[0]);
			return compositeId;
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}

	}

	public void preWritingDestinationValue(DozerEvent arg0) {
	}
}