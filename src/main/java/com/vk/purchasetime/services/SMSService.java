package com.vk.purchasetime.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.util.Random;


public class SMSService {
    @Autowired
    private ClientProperties clientProperties;

    public SMSService(){
        clientProperties=new ClientProperties();
    }

    public String smsSender(String toNumber) {
        final String ACCOUNT_SID = clientProperties.getSmsApiSID();
        final String AUTH_TOKEN = clientProperties.getSmsApiToken();
        final String FROM_NUMBER = clientProperties.getSmsfromNumber();


        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Random r=new Random();
        String otp = String.format("%04d", r.nextInt(10000));
//        Message message = Message
//                .creator(new PhoneNumber(toNumber),
//                        new PhoneNumber(FROM_NUMBER),
//                        otp)
//                .create();
//        System.out.println("sms sent"+message.getSid());
        return otp;
    }
}
