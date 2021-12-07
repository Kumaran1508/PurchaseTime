package com.vk.purchasetime.services;

import org.springframework.stereotype.Component;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Random;


public class SMSService {
    public String smsSender(String toNumber) {
        final String ACCOUNT_SID = "ACf1227fc1568295def4b4d6c700343fbf";
        final String AUTH_TOKEN = "c06617b2e11575b1c6c66af3df5f5885";


        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Random r=new Random();
        String otp = String.format("%04d", r.nextInt(10000));
        Message message = Message
                .creator(new PhoneNumber(toNumber),
                        new PhoneNumber("+12182198505"),
                        otp)
                .create();
        System.out.println("sms sent"+message.getSid());
        return otp;
    }
}
