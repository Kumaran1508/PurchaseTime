package com.vk.purchasetime.services;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServicev1 {

    // Sender's components.email ID needs to be mentioned
    private final String from = "kumaran201907@gmail.com";

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
                +"http://localhost:8080/resetpassword?authtoken="+authToken);

        // Send message
        Transport.send(message);
    }


}