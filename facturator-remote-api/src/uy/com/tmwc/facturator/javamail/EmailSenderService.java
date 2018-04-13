package uy.com.tmwc.facturator.javamail;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class EmailSenderService {

	private Session session;

	private String[] addresses;

	private String subjectText;

	private String bodyText;

	private byte[] attachmentData;

	public void sendEmail() throws MessagingException {		
		String host = "smtp.gmail.com";
        String from = "fulltimeuruguay@gmail.com";
        String pass = "F1RN0ND3";
		
        Properties properties = new Properties();
        
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.port", "587"); //
		properties.put("mail.smtp.mail.sender", from);
		properties.put("mail.smtp.user", from);
		properties.put("mail.smtp.auth", "true");

		session = Session.getDefaultInstance(properties, null);

		// Se compone la parte del texto
		BodyPart texto = new MimeBodyPart();
		texto.setText(bodyText);

		// Se compone el adjunto con la imagen en caso de tenerlo.
		BodyPart adjunto = new MimeBodyPart();

		if (attachmentData != null) {
			DataHandler dh = new DataHandler(attachmentData, "image/png");
			adjunto.setDataHandler(dh);
			adjunto.setFileName("Factura-Fulltime.png");
		}

		// Una MultiParte para agrupar texto e imagen.
		MimeMultipart multiParte = new MimeMultipart();
		multiParte.addBodyPart(texto);
		if (attachmentData != null) {
			multiParte.addBodyPart(adjunto);
		}

		MimeMessage message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));

		InternetAddress[] address = new InternetAddress[addresses.length];
		for (int i = 0; i < addresses.length; i++) {
			address[i] = new InternetAddress(addresses[i]);
		}
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

	public String getBodyText() {
		return bodyText;
	}

	public void setBodyText(String bodyText) {
		this.bodyText = bodyText;
	}

	public String getSubjectText() {
		return subjectText;
	}

	public void setSubjectText(String subjectText) {
		this.subjectText = subjectText;
	}


}