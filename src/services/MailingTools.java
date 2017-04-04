package services;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailingTools {
	
	public static void sendMail(String exp, String sujet, String msgContent) 
			throws AddressException, MessagingException{
		
	// create some properties and get the default Session
	Properties props = new Properties();
	//serveur smtp sortant
	String host = "smtp.dsl.ovh.net";
	props.put("mail.smtp.host", host);
	
	Session session = Session.getInstance(props, null);
	
	
	    // create a message
	    MimeMessage msg = new MimeMessage(session);
	    msg.setFrom(new InternetAddress(exp));
	    InternetAddress[] address = {new InternetAddress("enatalizio@gmail.com")};
	    msg.setRecipients(Message.RecipientType.TO, address);
	    msg.setSubject(sujet);
	    msg.setSentDate(new Date());
	    // If the desired charset is known, you can use
	    // setText(text, charset)
	    msg.setText(msgContent);
	    
	    Transport.send(msg);
		
		
		
	}
}
