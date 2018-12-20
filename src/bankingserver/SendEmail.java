/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bankingserver;

/**
 *
 * @author Kuldeep Raj Tiwari
 */
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;
import java.net.UnknownHostException;
public class SendEmail {
    public static void sendmail(String email,String msg,String sub){    
   	        final String username = "bankingprojectjnu@gmail.com";
		final String password = "bankingproject123@";
                try {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.port", "587");
		Session session = Session.getInstance(props,
		  new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		  });

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress("bankingprojectjnu@gmail.com"));
			message.setRecipients(Message.RecipientType.TO,
				InternetAddress.parse(email));
			message.setSubject(sub);
			message.setText("Dear Customer,\n"
				+ msg);

			Transport.send(message);

			System.out.println("Done");

		} catch (MessagingException e) {
                        System.out.println("Email Exception found due to: "+e+"\n Connect to Internet");
			throw new RuntimeException(e);
		}
    
        }
}
