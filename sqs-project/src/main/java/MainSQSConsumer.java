import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;
import com.google.gson.Gson;
import model.Correo;



import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Properties;


public class MainSQSConsumer {

    private static final String QUEUE_URL = "https://sqs.us-east-1.amazonaws.com/022747143274/UserQueue";


    public static void main(String[] args) {


        AWSCredentials credentials = new BasicAWSCredentials("AKIAQKS6V3RVDYFFTNLZ",
                "hYDk+VNIeqrb83VnIlyHD46l01lcpfaEVexzgvIO");

        AmazonSQS sqsClient = AmazonSQSClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.US_EAST_1)
                .build();

        ReceiveMessageRequest message = new ReceiveMessageRequest()
                .withQueueUrl(QUEUE_URL)
                .withMaxNumberOfMessages(1)
                .withWaitTimeSeconds(3);

        while (true){

            ReceiveMessageResult receiveMessageResult = sqsClient.receiveMessage(message);

            if (receiveMessageResult.getSdkHttpMetadata().getHttpStatusCode() != 200){
                System.out.println("Ocurrió un error en la cola "+QUEUE_URL+" -> "+receiveMessageResult.getSdkHttpMetadata());
                return;
            }

            System.out.println("Obteniendo "+receiveMessageResult.getMessages().size()+" msj desde la cola "+QUEUE_URL);



            List<Message> messages = receiveMessageResult.getMessages();
            for (Message msg : messages){
                String body = msg.getBody();
                System.out.println("body = " + body);

                final Gson gson = new Gson();
                final Correo correo = gson.fromJson(body, Correo.class);
                String aaea= correo.getEmail();
                System.out.println(correo);
                sqsClient.deleteMessage(QUEUE_URL, msg.getReceiptHandle());
                // Recipient's email ID needs to be mentioned.
                String to = aaea;

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
                    message1.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress(to));

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

        }


}

