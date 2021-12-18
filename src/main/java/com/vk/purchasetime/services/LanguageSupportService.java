package com.vk.purchasetime.services;


import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

public class LanguageSupportService {

    private static SessionLocaleResolver sessionLocaleResolver = new SessionLocaleResolver();
    private static ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();

    public static LocaleResolver localeResolver(){
        sessionLocaleResolver.setDefaultLocale(Locale.US);
        return sessionLocaleResolver;
    }

    public static ResourceBundleMessageSource messageSource(){
        resourceBundleMessageSource.setBasename("lng");
        resourceBundleMessageSource.setUseCodeAsDefaultMessage(true);
        return resourceBundleMessageSource;
    }

    public static String get(String code,String lang){
        return messageSource().getMessage(code,null,new Locale(lang));
    }
}
