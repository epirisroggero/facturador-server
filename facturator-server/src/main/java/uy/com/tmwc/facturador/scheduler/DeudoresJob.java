package uy.com.tmwc.facturador.scheduler;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;


import javax.mail.MessagingException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import uy.com.tmwc.facturador.mail.FreemarkerConfig;
import uy.com.tmwc.facturator.deudores.DocumentoDeudor;
import uy.com.tmwc.facturator.entity.Comprobante;
import uy.com.tmwc.facturator.entity.CotizacionesMonedas;
import uy.com.tmwc.facturator.entity.Moneda;
import uy.com.tmwc.facturator.entity.Usuario;
import uy.com.tmwc.facturator.javamail.EmailSenderService;
import uy.com.tmwc.facturator.rapi.AgendaTareaService;
import uy.com.tmwc.facturator.rapi.LiquidacionService;
import uy.com.tmwc.facturator.rapi.MonedasCotizacionesService;

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
		
	private static Class<?> monedasCotizacionesServiceProxyClass;
	private static Constructor<?> monedasCotizacionesServiceProxyClassConstructor;
	static {
		monedasCotizacionesServiceProxyClass = Proxy.getProxyClass(MonedasCotizacionesService.class.getClassLoader(), new Class[] { MonedasCotizacionesService.class });
		try {
			monedasCotizacionesServiceProxyClassConstructor = monedasCotizacionesServiceProxyClass.getConstructor(new Class[] { InvocationHandler.class });
		} catch (SecurityException e) {
			throw new RuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new RuntimeException(e);
		}
	}



	public void execute(JobExecutionContext context) throws JobExecutionException {
		Date fecha = new Date();
		
		SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy HH:mm");

		try {
			System.out.println("=================================================================");
			System.out.println(df1.format(fecha) + " :: Notificar vencimientos".toUpperCase());
			System.out.println("=================================================================");
		} catch (Exception e) {
			e.printStackTrace();	
		}

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

		String[] correos = {"fernando@fulltime.uy"};

		String regex = System.getProperty("deudores.vencimientos.correo");
		String diasRetraso = System.getProperty("deudores.vencimientos.diasRetraso");
		
		if (regex != null) {
			correos = regex.split(",");
		}
		int dias = diasRetraso != null ? Integer.valueOf(diasRetraso).intValue() : 1;
		
		List<DocumentoDeudor> documentosVencidos = getLiquidacionService().getDocumentosVencidosSupervisor(fecha);
		for (DocumentoDeudor documentoDeudor : documentosVencidos) {
			List<DocumentoDeudor> dd = hashMap.get(documentoDeudor.getDeudor().getCodigo());
			if (dd != null) {
				deudoresConVencimiento.put(documentoDeudor.getDeudor().getCodigo(), dd); 
				
				if (documentoDeudor.getDiasRetraso() == dias) {
					//System.out.println(String.format("Cliente con vencimiento :: %s", documentoDeudor.getDeudor().getNombre()));
					sendEmail(correos, "FULLTIME - Notificación por vencimiento".toUpperCase(), documentoDeudor, dd);
				}
				hashMap.remove(documentoDeudor.getDeudor().getCodigo());
			}			
		}
		getAgendaTareaService().notificacionPorFacturaVencida(deudoresConVencimiento);
		
	}
	
	public void sendEmail(String[] addresses, String subject, DocumentoDeudor documentoVencido, List<DocumentoDeudor> pendientes) {
		NumberFormat numberFormat = NumberFormat.getInstance(new Locale("es", "ES"));
		numberFormat.setMinimumFractionDigits(2);
		numberFormat.setMaximumFractionDigits(2);

		SimpleDateFormat df1 = new SimpleDateFormat("dd-MM-yyyy");
		
		HashMap<String, Object> root = new HashMap<String, Object>();
		
		String codigoMoneda = documentoVencido.getMoneda().getCodigo();
		
		root.put("cliente", documentoVencido.getDeudor().getNombre());
		root.put("documento", documentoVencido.getSerie() + String.valueOf(documentoVencido.getNumero()));
		root.put("vencimiento", df1.format(documentoVencido.getFechaVencimiento()));

		root.put("montoTotal", numberFormat.format(documentoVencido.getAdeudado()) + " " + documentoVencido.getMoneda().getNombre());
		
		root.put("totalFacturado", getTotalFacturado(codigoMoneda, pendientes).doubleValue());
		root.put("totalCancelado", getTotalCancelado(codigoMoneda, pendientes).doubleValue());
		root.put("totalAdeudado",  getTotalAdeudado(codigoMoneda, pendientes).doubleValue());
		root.put("totalAdeudadoNeto", getTotalAdeudadoNeto(codigoMoneda, pendientes).doubleValue());

		root.put("pendientes", pendientes);		

		Usuario usuario = new Usuario();
		usuario.setCodigo("20");
		usuario.setUsuCelular("598 99 681 586");
		usuario.setUsuCargo("COBRANZA");
		usuario.setUsuEmail("vguevara@fulltimeuy.com");
		
		
		root.put("usuario", usuario);		
		
		EmailSenderService eMailSender = new EmailSenderService();
		eMailSender.setSubjectText(subject);
		eMailSender.setAddresses(addresses);
		eMailSender.setUsuario(usuario);

		eMailSender.setEsFactura(true);
		
		String htmlText = FreemarkerConfig.loadTemplate("templates/", "email-template-vencimientos.ftl", root);
		try {
			eMailSender.setHtmlText(htmlText);
			eMailSender.sendEmail();
			
		} catch (MessagingException me) {
			me.printStackTrace();
		}
	}
	
	public BigDecimal getTotalFacturado(String moneda, List<DocumentoDeudor> pendientes) {
		BigDecimal _totalFacturado = BigDecimal.ZERO;
		for (DocumentoDeudor documentoDeudor : pendientes) {
			String codigoMoneda = documentoDeudor.getMoneda().getCodigo();
			
			if (moneda != codigoMoneda) {
				_totalFacturado = _totalFacturado.add(convertir(aplicarSigno(documentoDeudor.getComprobante(), documentoDeudor.getFacturado()), codigoMoneda));
			} else {
				_totalFacturado = _totalFacturado.add(aplicarSigno(documentoDeudor.getComprobante(), documentoDeudor.getFacturado()));
			}			
		} 
		return _totalFacturado.setScale(2);
	}
	
	public BigDecimal getTotalCancelado(String moneda, List<DocumentoDeudor> pendientes) {
		BigDecimal _totalCancelado = BigDecimal.ZERO;
		for (DocumentoDeudor documentoDeudor : pendientes) {
			String codigoMoneda = documentoDeudor.getMoneda().getCodigo();
			if (moneda != codigoMoneda) {
				_totalCancelado = _totalCancelado.add(convertir(documentoDeudor.getCancelado(), codigoMoneda));
			} else {
				_totalCancelado = _totalCancelado.add(documentoDeudor.getCancelado());
			}
		}
		return _totalCancelado.setScale(2);
	}

	public BigDecimal getTotalAdeudado(String moneda, List<DocumentoDeudor> pendientes){
		BigDecimal _totalAdeudado = BigDecimal.ZERO;
		for (DocumentoDeudor documentoDeudor : pendientes) {
			String codigoMoneda = documentoDeudor.getMoneda().getCodigo();
			if (moneda != codigoMoneda) {
				_totalAdeudado = _totalAdeudado.add(convertir(documentoDeudor.getAdeudado(), codigoMoneda));
			} else {
				_totalAdeudado = _totalAdeudado.add(documentoDeudor.getAdeudado());
			}				
		}
		return _totalAdeudado.setScale(2);
	}

	public BigDecimal getTotalAdeudadoNeto(String moneda, List<DocumentoDeudor> pendientes) {
		BigDecimal _totalAdeudadoNeto = BigDecimal.ZERO;
		for (DocumentoDeudor documentoDeudor : pendientes) {
			String codigoMoneda = documentoDeudor.getMoneda().getCodigo();
			if (moneda != codigoMoneda) {
				_totalAdeudadoNeto = _totalAdeudadoNeto.add(convertir(documentoDeudor.getAdeudadoNeto(), codigoMoneda));
			} else {
				_totalAdeudadoNeto = _totalAdeudadoNeto.add(documentoDeudor.getAdeudadoNeto());
			}
		}
		return _totalAdeudadoNeto.setScale(2);
	}
	
	public BigDecimal convertir(BigDecimal valor, String moneda) {
		CotizacionesMonedas cotizacionesMonedas = getMonedasCotizacionesService().getUltimaCotizacion(new Date());
		BigDecimal result = valor.setScale(2, RoundingMode.HALF_UP);
		BigDecimal dolarC = cotizacionesMonedas.getDolarCompra();
		
		if (moneda.equals(Moneda.CODIGO_MONEDA_PESOS) || moneda.equals(Moneda.CODIGO_MONEDA_PESOS_ASTER)) {
			if (dolarC.doubleValue() > 0) {
				result = result.divide(dolarC, 2, RoundingMode.HALF_UP);
			} else {
				result = BigDecimal.ZERO;
			}
		}
		return result;
	}
	
	private BigDecimal aplicarSigno(Comprobante comprobante, BigDecimal abs) {
		if (comprobante.getTipo() == Comprobante.NOTA_CREDITO) {
			return abs.negate();
		} else {
			return abs;
		}
	}

	private MonedasCotizacionesService getMonedasCotizacionesService() {
		try {
			MonedasCotizacionesService service;
			InitialContext ctx;
			ctx = new InitialContext();
			service = (MonedasCotizacionesService) ctx.lookup("facturator-server-ear/MonedasCotizacionesServiceImpl/local");

			DeudoresInvocationHandler handler = new DeudoresInvocationHandler(service);
			service = (MonedasCotizacionesService) monedasCotizacionesServiceProxyClassConstructor.newInstance(handler);

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
