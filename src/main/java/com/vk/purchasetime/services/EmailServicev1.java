package com.vk.purchasetime.services;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailServicev1 {

    private ClientProperties clientProperties=new ClientProperties();

    // Sender's components.email ID needs to be mentioned
    private final String from = clientProperties.getMailId();

    // Assuming you are sending components.email from through gmails smtp
    private final String host = "smtp.gmail.com";

    // Get system properties
    private Properties properties = System.getProperties();

    private Session session;
    private MimeMessage message;

    public EmailServicev1() {
        // Setup mail server
        properties.put("mail.smtp.host","smtp.gmail.com");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls.enable", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");

        // Get the Session object.// and pass username and password
        session =  Session.getDefaultInstance(properties,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(from,"login197");
                    }
                });
        // Used to debug SMTP issues
        session.setDebug(true);
        // Create a default MimeMessage object.
        message = new MimeMessage(session);
    }


    public void resetPassword(String email,String authToken) throws MessagingException{

        // Set From: header field of the header.
        message.setFrom(new InternetAddress(from));

        // Set To: header field of the header.
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));

        // Set Subject: header field
        message.setSubject("Reset your Password!");

        // Now set the actual message
        message.setText("Please use the below link to reset your password for PurchaseTime!\n"
                +clientProperties.getDomainName()+"/resetpassword?authtoken="+authToken);

        // Send message
        Transport.send(message);
    }

    public void sendInvoice(String ToMail, String msg, String attachmentPath){

            String to=ToMail;

            Properties props = System.getProperties();
            props.put("mail.smtp.host","smtp.gmail.com");
            props.put("mail.smtp.auth", true);
            props.put("mail.smtp.starttls.enable", true);
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
            props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
            props.put("mail.smtp.ssl.protocols", "TLSv1.2");

            Session session = Session.getDefaultInstance(props,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(from, clientProperties.getMailPassword());
                        }
                    });

            try{
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(from));
                message.addRecipient(MimeMessage.RecipientType.TO,new InternetAddress(to));
                message.setSubject("Email with attachment");


                BodyPart messageBodyPart1 = new MimeBodyPart();
                messageBodyPart1.setText(msg);


                MimeBodyPart messageBodyPart2 = new MimeBodyPart();

                String filename = attachmentPath;
                String fileName = FilenameUtils.getName(filename);
                DataSource source = new FileDataSource(filename);
                messageBodyPart2.setDataHandler(new DataHandler(source));
                messageBodyPart2.setFileName(fileName);


                Multipart multipart = new MimeMultipart();
                multipart.addBodyPart(messageBodyPart1);
                multipart.addBodyPart(messageBodyPart2);

                message.setContent(multipart );

                Transport.send(message);

                System.out.println("mail with attachment sent....");
            }catch (MessagingException ex) {ex.printStackTrace();}

    }


}