/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utrgvmail;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author Alex
 */
public class EmailSenderTest {
    private final String fromEmail = "USERNAME@utrgv.edu";                              // To test, hardcode your email here
    private final String username = "USERNAME@utrgv.edu";                               // To test, hardcode your email here, too
    private String password = "USERNAME_PASSWORD";                                      // To test, hardcode your password here
    private final String toEmail = "RECIEPIENT01@gmail.com, RECIEPIENT02@gmail.com";    // To test, hardcode email recipients here, must be separated by a comma (,)
    private final String subject= "Test Subject";
    private final String textMessage = "Test Message";
    private final String attachment = "MYPDF.pdf";                                      // Here goes the name of the file to attach
  
    public EmailSenderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of EmailSender method, of class EmailSender.
     */
    @Test
    public void testEmailSender() {
        try {
            Properties props = new Properties();
            props.put("mail.smtp.host", "smtp.office365.com");
            props.put("mail.smtp.port","587");
            props.put("mail.smtp.auth", "true"); 
            props.put("mail.smtp.starttls.enable", "true");

            Session mailSession = Session.getInstance(props, null);
            //mailSession.setDebug(true);

            Message emailMessage = new MimeMessage(mailSession);

            emailMessage.setFrom(new InternetAddress(fromEmail));
            emailMessage.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail)); // Mail multiple recipients using a ' ,' as parse
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
        } catch (MessagingException ex) {
            Logger.getLogger(EmailSender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
