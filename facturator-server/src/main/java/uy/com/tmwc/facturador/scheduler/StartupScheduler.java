package uy.com.tmwc.facturador.scheduler;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Servlet implementation class StartupScheduler
 */
public class StartupScheduler extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public StartupScheduler() {
	}

	public void init() throws ServletException {
		try {
			// Obtengo scheduler a partir de factory
			Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			
			createAgendaTareaJob(scheduler);
			
			createDeudoresJob(scheduler);

			// Comienzo el scheduler
			scheduler.start();

		} catch (SchedulerException e) {
			System.out.println("Error al inicializar scheduler");
			e.printStackTrace();
		}
	}

	private void createAgendaTareaJob(Scheduler scheduler) throws SchedulerException {
		// Creo el Job
		JobDetail job0 = new JobDetail("startJob", "start", QueueJob.class);

		// Creo JobDataMap, que sirve para el pasaje de params
		JobDataMap dataMap0 = new JobDataMap();
		dataMap0.put("param1", "valor de param1");
		job0.setJobDataMap(dataMap0);
		
		Calendar calendarF = Calendar.getInstance();
		calendarF.setTime(new Date());
		calendarF.set(Calendar.MINUTE, calendarF.get(Calendar.MINUTE) + 2);
		
		SimpleTrigger trigger0 = new SimpleTrigger("firstTime", null, calendarF.getTime());

		// Asocio el job y trigger al scheduler
		scheduler.scheduleJob(job0, trigger0);
		
		JobDetail job = new JobDetail("queueJob", "group1", QueueJob.class);

		// Creo JobDataMap, que sirve para el pasaje de params
		JobDataMap dataMap = new JobDataMap();
		dataMap.put("param1", "valor de param1");
		job.setJobDataMap(dataMap);

		// Creo el trigger
		// Recibe nombre, grupo, hora comienzo, hora fin, cantidad
		// repeticiones, intervalo
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		calendar.set(Calendar.HOUR_OF_DAY, 3);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		
		SimpleTrigger trigger = new SimpleTrigger("nomTrigger", null, calendar.getTime(), null, SimpleTrigger.REPEAT_INDEFINITELY, 24 * 60 * 60 * 1000L);

		// Asocio el job y trigger al scheduler
		scheduler.scheduleJob(job, trigger);
	}
		
	private void createDeudoresJob(Scheduler scheduler) throws SchedulerException {
		Calendar calendarF = Calendar.getInstance();
		calendarF.setTime(new Date());
		calendarF.set(Calendar.MINUTE, calendarF.get(Calendar.MINUTE) + 1);

		// Creo el Job
		JobDetail job1 = new JobDetail("deudoresJob", "start", DeudoresJob.class);

		// Creo JobDataMap, que sirve para el pasaje de params
		JobDataMap dataMap1 = new JobDataMap();
		dataMap1.put("param1", "valor de param1");
		job1.setJobDataMap(dataMap1);
		
		SimpleTrigger trigger1 = new SimpleTrigger("firstTime1", "start2", calendarF.getTime());
					
		// creo el Job inicial para deudores
		JobDetail job2 = new JobDetail("deudoresQueueJob", "group1", DeudoresJob.class);
		
		// Creo JobDataMap, que sirve para el pasaje de params
		JobDataMap dataMap2 = new JobDataMap();
		dataMap2.put("param1", "valor de param1");
		job2.setJobDataMap(dataMap2);

		// Creo el trigger
		// Recibe nombre, grupo, hora comienzo, hora fin, cantidad repeticiones, intervalo
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());			
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
		calendar.set(Calendar.HOUR_OF_DAY, 6);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		SimpleTrigger trigger2 = new SimpleTrigger("enviarNotificaciones", "group1", calendar.getTime(), null, SimpleTrigger.REPEAT_INDEFINITELY, 24 * 60 * 60 * 1000L);
		
		// Asocio el job y trigger al scheduler
		scheduler.scheduleJob(job1, trigger1);
		scheduler.scheduleJob(job2, trigger2);	
		
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
