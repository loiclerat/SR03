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
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class MailingToolsJNDI {
	
	public static void sendMail(String exp, String sujet, String msgContent) 
			throws AddressException, MessagingException, NamingException{
		
		

		Context initCtx = new InitialContext();
		Context envCtx = (Context) initCtx.lookup("java:comp/env");
		Session session = (Session) envCtx.lookup("mail/Session");
	
	
	
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
