package uy.com.tmwc.facturador.scheduler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import uy.com.tmwc.facturator.deudores.DocumentoDeudor;
import uy.com.tmwc.facturator.rapi.AgendaTareaService;
import uy.com.tmwc.facturator.rapi.LiquidacionService;

public class DeudoresJob implements Job {
	
	public DeudoresJob() {
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
	
	private static Class<?> liquidacionServiceProxyClass;
	private static Constructor<?> liquidacionServiceProxyClassConstructor;
	static {
		liquidacionServiceProxyClass = Proxy.getProxyClass(LiquidacionService.class.getClassLoader(), new Class[] { LiquidacionService.class });
		try {
			liquidacionServiceProxyClassConstructor = liquidacionServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}


	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date fecha = new Date();

		System.out.println("=================================================================");
		System.out.println(fecha.toString() + " :: Notificar vencimientos".toUpperCase());
		System.out.println("=================================================================");

		HashMap<String, List<DocumentoDeudor>> hashMap = new HashMap<String, List<DocumentoDeudor>>();
 		
		List<DocumentoDeudor> documentosDeudores = getLiquidacionService().getDocumentosDeudoresSupervisor(fecha);

		for (DocumentoDeudor docDeudor : documentosDeudores) {
			List<DocumentoDeudor> list = hashMap.get(docDeudor.getDeudor().getCodigo());
			if (list != null) {
				list.add(docDeudor);
			} else {
				ArrayList<DocumentoDeudor> lst = new ArrayList<DocumentoDeudor>();
				lst.add(docDeudor);
				hashMap.put(docDeudor.getDeudor().getCodigo(), lst);
			}
		}
		
		HashMap<String, List<DocumentoDeudor>> deudoresConVencimiento = new HashMap<String, List<DocumentoDeudor>>();

		List<DocumentoDeudor> documentosVencidos = getLiquidacionService().getDocumentosVencidosSupervisor(fecha);
		for (DocumentoDeudor documentoDeudor : documentosVencidos) {
			System.out.println("=================================================================");
			
			List<DocumentoDeudor> dd = hashMap.get(documentoDeudor.getDeudor().getCodigo());
			if (dd != null) {
				System.out.println(String.format("Cliente con vencimiento :: %s", documentoDeudor.getDeudor().getNombre()));
				deudoresConVencimiento.put(documentoDeudor.getDeudor().getCodigo(), dd); 
				
				for (DocumentoDeudor documentoDeudor2 : dd) {
					System.out.println(String.format("Documento %s :: %s%s", documentoDeudor2.getComprobante().getNombre(), documentoDeudor2.getSerie(), documentoDeudor2.getNumero()));
				}
				
				hashMap.remove(documentoDeudor.getDeudor().getCodigo());
			}
			System.out.println("=================================================================");

		}
		
		getAgendaTareaService().notificacionPorFacturaVencida(deudoresConVencimiento);
		
	}
	
	private AgendaTareaService getAgendaTareaService() {
		try {
			AgendaTareaService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (AgendaTareaService)ctx.lookup("facturator-server-ear/AgendaTareaServiceImpl/local");
			
			DeudoresInvocationHandler handler = new DeudoresInvocationHandler(service);
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
	
	private LiquidacionService getLiquidacionService() {
		try {
			LiquidacionService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (LiquidacionService) ctx.lookup("facturator-server-ear/LiquidacionServiceImpl/local");

			DeudoresInvocationHandler handler = new DeudoresInvocationHandler(service);
			service = (LiquidacionService) liquidacionServiceProxyClassConstructor.newInstance(handler);

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

	
	private static class DeudoresInvocationHandler implements InvocationHandler {
		private Object proxiedService;
		
		public DeudoresInvocationHandler(Object proxiedService) {
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
