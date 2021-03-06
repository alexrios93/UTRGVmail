package utrgvmail;

import java.util.Properties;
import javafx.scene.control.Label;
import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.AddressException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
/**
 *
 * @author Alex
 */
public class EmailSender {
    // Constructors
    EmailSender() {        
    }
    EmailSender(String fromEmail, String username, String password, String toEmail, String subject, String textMessage, String attachment, Label output) {
    }
    
    public static void EmailSender(String fromEmail, String username, String password, String toEmail, String subject, String textMessage, String attachment, Label output) {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.office365.com");
            props.put("mail.smtp.port","587");
            props.put("mail.smtp.auth", "true");  
            props.put("mail.smtp.starttls.enable", "true");

            Session mailSession = Session.getInstance(props, new javax.mail.Authenticator() {                      
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });
            
            mailSession.setDebug(true);

            Message emailMessage = new MimeMessage(mailSession);

            emailMessage.setFrom(new InternetAddress(fromEmail));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Mail multiple recipients using a comma ( , ) as parse
            emailMessage.setSubject(subject);

            // Create body part for the text message and attatchment
            BodyPart messageBodyPart = new MimeBodyPart();
            BodyPart attatchmentBodyPart = new MimeBodyPart();            

            // set contnets for text message and attatchment bodyparts
            messageBodyPart.setText(textMessage); 
            
            DataSource source = new FileDataSource(attachment); 
            attatchmentBodyPart.setDataHandler(new DataHandler(source));
            attatchmentBodyPart.setFileName(attachment);

            // Add bodyparts
            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(messageBodyPart);
            multipart.addBodyPart(attatchmentBodyPart);

            emailMessage.setContent(multipart);

            // Helps Sends Message through smtp protocol
            Transport transport =  mailSession.getTransport("smtp");
            transport.connect("smtp.office365.com", username, password);            

            transport.sendMessage(emailMessage, emailMessage.getAllRecipients());

            transport.send(emailMessage);
            
            output.setText("EMAIL SENT");            
            
        } catch (AddressException e) {
                e.printStackTrace();
                
                output.setText("Username or Password Mispelled or Missing");
        } catch (MessagingException e) {
            
                output.setText("Email not sent");
                e.printStackTrace();
        }
    }
}
