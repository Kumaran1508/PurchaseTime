package com.vk.purchasetime.services;


import java.util.UUID;

public class TokenGenerator {
    public static String getToken(){
        String token = UUID.randomUUID().toString();
        return token;
    }
}
