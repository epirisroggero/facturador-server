package uy.com.tmwc.facturator.javamail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import uy.com.tmwc.facturator.entity.Usuario;

public class EmailSenderService {

	private Session session;

	private String[] addresses;

	private String subjectText;

	private String htmlText;

	private String headerImg;

	private String footerImg;

	private boolean esCobranza;

	private boolean esFactura;

	private boolean esExpedicion;

	private boolean esListadoDeudores;

	private Usuario usuario;


	private byte[] attachmentData;
	
	private byte[] attachmentDataPdf;
	
	private String serieNro;
	
	private String  tipoDocumento;



	public void sendEmail() throws MessagingException {
		String host = "smtp.gmail.com";
		String from = "fulltimeuruguay@gmail.com";
		String pass = "F1RN0ND3";

		Properties properties = new Properties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.mail.sender", from);
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.auth", "true");

		session = Session.getDefaultInstance(properties, null);

		// Una MultiParte para agrupar texto e imagen.
		MimeMultipart multiParte = new MimeMultipart();

		// Obtener el data source de la imagen
		DataSource fds = new FileDataSource("C:/Fulltime/resources/templates/images/header-mail-1.jpg");

		// Se compone la parte del texto
		BodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(htmlText, "text/html; charset=UTF-8");
		multiParte.addBodyPart(messageBodyPart);

		messageBodyPart = new MimeBodyPart();
		messageBodyPart.setDataHandler(new DataHandler(fds));
		messageBodyPart.setHeader("Content-ID", "<header>");
		if (!isEsExpedicion()) {
			// add first image to the multipart
			multiParte.addBodyPart(messageBodyPart);
		}

		// Obtener el data source de la imagen
		DataSource fds2 = new FileDataSource("C:/Fulltime/resources/templates/images/footer-mail-2.jpg");
		messageBodyPart = new MimeBodyPart();
		messageBodyPart.setDataHandler(new DataHandler(fds2));
		if (!isEsExpedicion()) {
			messageBodyPart.setHeader("Content-ID", "<footer>");
		}

		// add second image to the multipart
		multiParte.addBodyPart(messageBodyPart);

		messageBodyPart = new MimeBodyPart();

		// Obtener el data source de la imagen
		DataSource fds3 = null;
		if (usuario != null && usuario.getUsuBlob() != null) {
			ByteArrayDataSource dataSource = new ByteArrayDataSource(usuario.getUsuBlob(), "application/octet-stream");
			messageBodyPart.setDataHandler(new DataHandler(dataSource));

		} else {
			fds3 = new FileDataSource("C:/Fulltime/resources/templates/images/Logo.jpg");
			messageBodyPart.setDataHandler(new DataHandler(fds3));
		}  
		messageBodyPart.setFileName("Foto.jpg");
		messageBodyPart.setHeader("Content-ID", "<photo>");
		if (!isEsExpedicion()) {
			// add second image to the multipart
			multiParte.addBodyPart(messageBodyPart);
		}

		if (esFactura || esListadoDeudores) {
			DataSource fds4 = new FileDataSource("C:/Fulltime/resources/templates/images/formas_de_pago.jpg");
			messageBodyPart = new MimeBodyPart();
			messageBodyPart.setDataHandler(new DataHandler(fds4));
			messageBodyPart.setHeader("Content-ID", "<formasPago>");

			// add second image to the multipart
			multiParte.addBodyPart(messageBodyPart);
		}

		// Se compone el adjunto con la imagen en caso de tenerlo.
		if (attachmentData != null) {
			messageBodyPart = new MimeBodyPart();
			DataHandler dh = new DataHandler(attachmentData, "image/png");
			messageBodyPart.setDataHandler(dh);

			if (esListadoDeudores) {
				messageBodyPart.setFileName("Pendientes.png");
				messageBodyPart.setHeader("Content-ID", "<pendientes>");
			} else {
				messageBodyPart.setFileName("Comprobante.png");
				messageBodyPart.setHeader("Content-ID", "<documento>");
			}
			
			multiParte.addBodyPart(messageBodyPart);
		}
		
		if (attachmentDataPdf != null) {
			MimeBodyPart attachment= new MimeBodyPart();
		    ByteArrayDataSource ds = new ByteArrayDataSource(attachmentDataPdf, "application/pdf"); 
		    attachment.setDataHandler(new DataHandler(ds));
		    if (esListadoDeudores) {
			    attachment.setFileName("Fulltime - Facturas pendientes.pdf");
		    } else { 
			    attachment.setFileName(String.format("Fulltime %s_%s.pdf", tipoDocumento != null ? tipoDocumento : ""
			    	, serieNro != null ? serieNro : ""));
		    }
		    multiParte.addBodyPart(attachment);
		}

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));

		InternetAddress[] address = new InternetAddress[addresses.length];
		for (int i = 0; i < addresses.length; i++) {
			address[i] = new InternetAddress(addresses[i]);
		}
		
		// For testing only
		//address[0] = new InternetAddress("epirisroggero@gmail.com");

		message.addRecipients(Message.RecipientType.TO, address);
		message.setSubject(subjectText);
		message.setContent(multiParte);

		Transport transport = session.getTransport("smtp");
		transport.connect(host, 587, from, pass);
		transport.sendMessage(message, message.getAllRecipients());

		transport.close();
	}
	
	public byte[] getAttachmentData() {
		return attachmentData;
	}

	public void setAttachmentData(byte[] attachmentData) {
		this.attachmentData = attachmentData;
	}

	public String[] getAddresses() {
		return addresses;
	}

	public void setAddresses(String[] addresses) {
		this.addresses = addresses;
	}

	public void setHtmlText(String htmlText) {
		this.htmlText = htmlText;
	}

	public String getSubjectText() {
		return subjectText;
	}

	public void setSubjectText(String subjectText) {
		this.subjectText = subjectText;
	}

	public String getHeaderImg() {
		return headerImg;
	}

	public void setHeaderImg(String headerImg) {
		this.headerImg = headerImg;
	}

	public String getFooterImg() {
		return footerImg;
	}

	public void setFooterImg(String footerImg) {
		this.footerImg = footerImg;
	}

	public boolean getEsCobranza() {
		return esCobranza;
	}

	public void setEsCobranza(boolean esCobranza) {
		this.esCobranza = esCobranza;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public boolean isEsFactura() {
		return esFactura;
	}

	public void setEsFactura(boolean esFactura) {
		this.esFactura = esFactura;
	}

	public boolean isEsListadoDeudores() {
		return esListadoDeudores;
	}

	public void setEsListadoDeudores(boolean esListadoDeudores) {
		this.esListadoDeudores = esListadoDeudores;
	}

	public boolean isEsExpedicion() {
		return esExpedicion;
	}

	public void setEsExpedicion(boolean esExpedicion) {
		this.esExpedicion = esExpedicion;
	}
	
	public byte[] getAttachmentDataPdf() {
		return attachmentDataPdf;
	}

	public void setAttachmentDataPdf(byte[] attachmentDataPdf) {
		this.attachmentDataPdf = attachmentDataPdf;
	}
	
	public String getSerieNro() {
		return serieNro;
	}

	public void setSerieNro(String serieNro) {
		this.serieNro = serieNro;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}


}