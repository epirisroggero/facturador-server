package uy.com.tmwc.facturador.mail;

import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateExceptionHandler;


public class FreemarkerConfig {
	
	public static Configuration cfg;
	
	public FreemarkerConfig() {
	}
	
	public static String loadTemplate(String basePath, String filename, Map<String, Object> root) {
		try {
			if (cfg == null) {
				// Create your Configuration instance, and specify if up to what FreeMarker
				// version (here 2.3.27) do you want to apply the fixes that are not 100%
				// backward-compatible. See the Configuration JavaDoc for details.
				cfg = new Configuration(Configuration.VERSION_2_3_27);
				
				// Specify the source where the template files come from. Here I set a
				// plain directory for it, but non-file-system sources are possible too:
				cfg.setClassForTemplateLoading(FreemarkerConfig.class, "/");
								
				// Set the preferred charset template files are stored in. UTF-8 is a good choice in most applications:
				cfg.setDefaultEncoding("UTF-8");
				
				// Sets how errors will appear.
				// During web page *development* TemplateExceptionHandler.HTML_DEBUG_HANDLER is better.
				cfg.setTemplateExceptionHandler(TemplateExceptionHandler.HTML_DEBUG_HANDLER);
				
				// Don't log exceptions inside FreeMarker that it will thrown at you anyway:
				cfg.setLogTemplateExceptions(false);
				
				// Wrap unchecked exceptions thrown during template processing into TemplateException-s.
				cfg.setWrapUncheckedExceptions(true);		
			}
			
			Template temp = cfg.getTemplate(basePath + filename);
			StringWriter writer = new StringWriter();
			temp.process(root, writer);
			String html = writer.toString();
			
			return html;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "none";
	}
}