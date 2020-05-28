package vijayakumar.vysakh.wishlistapp.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class WishlistMailerUtil {
	public static boolean sendMail(String recipient) {
//		System.out.println("Preparing to send mail...");
		Properties properties = new Properties();					// smtp property settings
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		properties.put("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		
		String myEmailAccount = "xxxxx@gmail.com";				// application mail id  Set Email
		String myPass = "xxxxxx";								// password             Set Password
		
		Session session = Session.getInstance(properties, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(myEmailAccount, myPass);
			}
		});
		
		Message message = prepareMessage(session, myEmailAccount, recipient);
		try {
			Transport.send(message);
			return true;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
			
	}
	
	private static Message prepareMessage(Session session, String email, String recepient) {
		try {
			Message message  = new MimeMessage(session);
			message.setFrom(new InternetAddress(email));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
			message.setSubject("Movie Wishlist App Report");							// Mail subject
															// create MimeBodyPart object and setting message text 
			MimeBodyPart messageBodyPart1 = new MimeBodyPart();
			messageBodyPart1.setText("Movie Wishlist App wishlist details in PDF");					// message body
//			message.setText();
																// new Mimepart object created for attacheent
			MimeBodyPart messageBodyPart2 = new MimeBodyPart(); 
			String filepath = "pdf/MovieWishlistsPdf.pdf";				// pdf file path for attachment
			DataSource source = new FileDataSource(filepath);    		// setting data source..
															// set DataHandler object to MimeBodyPart object
            messageBodyPart2.setDataHandler(new DataHandler(source));    
            messageBodyPart2.setFileName(filepath); 
            												// Multipart object and add MimeBodyPart objects to this object
            Multipart multipart = new MimeMultipart();    
            multipart.addBodyPart(messageBodyPart1);     
            multipart.addBodyPart(messageBodyPart2); 
            
            message.setContent(multipart );					// set the multiplart object to the message object 
			return message;
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
}















