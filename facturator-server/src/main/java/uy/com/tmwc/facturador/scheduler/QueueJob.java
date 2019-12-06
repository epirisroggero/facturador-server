package uy.com.tmwc.facturador.scheduler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Calendar;
import java.util.Date;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import uy.com.tmwc.facturator.expediciones.AgendaTareaQuery;
import uy.com.tmwc.facturator.rapi.AgendaTareaService;

public class QueueJob implements Job {
	
	public QueueJob() {
		// This is required for the Blaze DS to instantiate the class
	}

	
	private static Class<?> agendaTareaServiceProxyClass;
	private static Constructor<?> agendaTareaServiceProxyClassConstructor;
	static {
		agendaTareaServiceProxyClass = Proxy.getProxyClass(AgendaTareaService.class.getClassLoader(), new Class[] {AgendaTareaService.class});
		try {
			agendaTareaServiceProxyClassConstructor = agendaTareaServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}		
	}


	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date fecha = new Date();
		
		System.out.println("=================================================================");
		System.out.println(fecha.toString() + " :: Reagendando tareas");
		System.out.println("=================================================================");
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.setTime(fecha);    	
		calendar1.add(Calendar.DAY_OF_MONTH, -7);

   		Calendar calendar2 = Calendar.getInstance();
		calendar2.setTime(fecha);    	
		calendar2.add(Calendar.DAY_OF_MONTH, -1);

		Date fechaDesde = calendar1.getTime();
		Date fechaHasta = calendar2.getTime();
		
    	AgendaTareaQuery query  = new AgendaTareaQuery();
		query.setFechaDesde(fechaDesde);
		query.setFechaHasta(fechaHasta);
		query.setState("P");
		query.setUsuario(null); 
		query.setAsignado(null);
		query.setContacto(null);

		getAgendaTareaService().reagendarTareasPendientes(query);
	}
	
	private AgendaTareaService getAgendaTareaService() {
		try {
			AgendaTareaService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (AgendaTareaService)ctx.lookup("facturator-server-ear/AgendaTareaServiceImpl/local");
			
			MyInvocationHandler handler = new MyInvocationHandler(service);
			service = (AgendaTareaService) agendaTareaServiceProxyClassConstructor.newInstance(handler);
			
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
