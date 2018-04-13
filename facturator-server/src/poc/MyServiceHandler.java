package poc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import uy.com.tmwc.facturator.entity.CodigoNombreEntity;
import uy.com.tmwc.facturator.rapi.CatalogService;

public class MyServiceHandler {

	private static Class<?> catalogServiceProxyClass;
	private static Constructor<?> catalogServiceProxyClassConstructor;
	static {
		catalogServiceProxyClass = Proxy.getProxyClass(CatalogService.class.getClassLoader(), new Class[] { CatalogService.class });
		try {
			catalogServiceProxyClassConstructor = catalogServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}
	
	public <T extends CodigoNombreEntity> List<T> getCatalogoUsuarios() {
		return getCatalogService().getCatalog("Usuario");
	}
	
	public <T extends CodigoNombreEntity> List<T> getCatalogoCajas() {
		return getCatalogService().getCatalog("Caja");
	}
	
	public <T extends CodigoNombreEntity> List<T> getCatalogoLocalesComerciales() {
		return getCatalogService().getCatalog("Localescomerciale");
	}


	private CatalogService getCatalogService() {
		try {
			CatalogService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (CatalogService)ctx.lookup("facturator-server-ear/CatalogServiceImpl/local");
			
			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (CatalogService) catalogServiceProxyClassConstructor.newInstance(handler);
			
			return service;
		} catch (NamingException e) {
			throw new RuntimeException(e);
		} catch (IllegalArgumentException e) {
			throw new RuntimeException(e);
		} catch (InstantiationException e) {
			throw new RuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new RuntimeException(e);
		}
	}
	
	
	private static class MyInvocationHandler implements InvocationHandler {
		private Object proxiedService;
		
		public MyInvocationHandler(Object proxiedService) {
			this.proxiedService = proxiedService;
		}
		
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			String name = method.getName();
			if (name.equals("hashCode")) {
				return proxiedService.hashCode();
			} else if (name.equals("equals")) {
				return proxiedService.equals(args[0]);
			} else if (name.equals("toString")) {
				return proxiedService.toString();
			} else {
				try {
					return method.invoke(proxiedService, args);
				} catch (InvocationTargetException e) {
					Throwable cause = e.getCause();
					logThrowable(cause);
					throw cause;
				} catch (Throwable e) {
					logThrowable(e);
					throw e;
				}			
			}
		}
		
		private void logThrowable(Throwable throwable) {
			System.out.println(throwable);
			throwable.printStackTrace();
		}
	}


	
	
}
