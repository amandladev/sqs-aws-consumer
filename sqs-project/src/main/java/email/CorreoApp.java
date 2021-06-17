package email;

import service.CorreoService;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class CorreoApp {


    private static CorreoService correoService;




    public static void main(String[] args) {


        // Recipient's email ID needs to be mentioned.
        String to = correoService.showCorreoName();

        // Sender's email ID needs to be mentioned
        String from = "sreyescurotto@gmail.com";

        // Assuming you are sending email from through gmails smtp
        String host = "smtp.gmail.com";

        // Get system properties
        Properties properties = System.getProperties();

        // Setup mail server
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.ssl.enable", "true");
        properties.put("mail.smtp.auth", "true");

        // Get the Session object.// and pass username and password
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {

            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("sreyescurotto@gmail.com", "74629686");

            }

        });

        // Used to debug SMTP issues
        session.setDebug(true);

        try {
            // Create a default MimeMessage object.
            MimeMessage message1 = new MimeMessage(session);

            // Set From: header field of the header.
            message1.setFrom(new InternetAddress(from));

            // Set To: header field of the header.
            message1.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            // Set Subject: header field
            message1.setSubject("Tienda App!");

            // Now set the actual message
            message1.setText("Gracias por registrarse en nuestra aplicación.");

            System.out.println("sending...");
            // Send message
            Transport.send(message1);
            System.out.println("Sent message successfully....");
        } catch (MessagingException mex) {
            mex.printStackTrace();
        }

    }

}

