package com.vk.purchasetime.services;

import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Component
public class ClientProperties {
    private String mailId;
    private String mailPassword;
    private String adminUserName;
    private String adminPassword;
    private String smsApiSID;
    private String smsApiToken;
    private String paymentApiKey;
    private String domainName;
    private String smsfromNumber;

    public String getSmsfromNumber() {
        return smsfromNumber;
    }

    public String getMailId() {
        return mailId;
    }

    public String getMailPassword() {
        return mailPassword;
    }

    public String getAdminUserName() {
        return adminUserName;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public String getSmsApiSID() {
        return smsApiSID;
    }

    public String getSmsApiToken() {
        return smsApiToken;
    }

    public String getPaymentApiKey() {
        return paymentApiKey;
    }

    public String getDomainName() {
        return domainName;
    }

    public ClientProperties() {
        Properties properties =new Properties();
        try {
            properties.load(new FileInputStream(System.getProperty("user.dir").replace("\\","\\\\")+"\\src\\main\\resources\\client.properties"));
            setMailId(properties.getProperty("mailId"));
            setMailPassword(properties.getProperty("mailPassword"));
            setAdminUserName(properties.getProperty("adminUserName"));
            setAdminPassword(properties.getProperty("adminPassword"));
            setSmsApiSID(properties.getProperty("smsApiSID"));
            setSmsApiToken(properties.getProperty("smsApiToken"));
            setPaymentApiKey(properties.getProperty("paymentApiKey"));
            setDomainName(properties.getProperty("domainName"));
            this.smsfromNumber = properties.getProperty("smsfromNumber");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public String toString() {
        return "ClientProperties{" +
                "mailId='" + getMailId() + '\'' +
                ", mailPassword='" + getMailPassword() + '\'' +
                ", adminUserName='" + getAdminUserName() + '\'' +
                ", adminPassword='" + getAdminPassword() + '\'' +
                ", smsApiSID='" + getSmsApiSID() + '\'' +
                ", smsApiToken='" + getSmsApiToken() + '\'' +
                ", paymentApiKey='" + getPaymentApiKey() + '\'' +
                ", domainName='" + getDomainName() + '\'' +
                '}';
    }

    private void setMailId(String mailId) {
        this.mailId = mailId;
    }

    private void setMailPassword(String mailPassword) {
        this.mailPassword = mailPassword;
    }

    private void setAdminUserName(String adminUserName) {
        this.adminUserName = adminUserName;
    }

    private void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    private void setSmsApiSID(String smsApiSID) {
        this.smsApiSID = smsApiSID;
    }

    private void setSmsApiToken(String smsApiToken) {
        this.smsApiToken = smsApiToken;
    }

    private void setPaymentApiKey(String paymentApiKey) {
        this.paymentApiKey = paymentApiKey;
    }

    private void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
