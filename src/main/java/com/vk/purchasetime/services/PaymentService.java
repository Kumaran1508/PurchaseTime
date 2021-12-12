package com.vk.purchasetime.services;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.model.Price;
import com.stripe.model.Product;
import com.stripe.model.checkout.Session;
import com.stripe.param.PaymentIntentCreateParams;
import com.stripe.param.checkout.SessionCreateParams;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;


public class PaymentService {
    public void makePayment(HttpServletResponse response, String amount){


        // This is your test secret API key.
        Stripe.apiKey = "sk_test_51K434zSHbrVpMbSJ1SszOJ54uhwVjKwvQOlSDfiH6fHHbLBwkpa94xENsw7finOGXJgfmUVoQ3CZAtCBfwaL8lu400jEWEPgso";

        String YOUR_DOMAIN = "http://localhost:8080";
        SessionCreateParams params =
                SessionCreateParams.builder()
                        .setMode(SessionCreateParams.Mode.PAYMENT)
                        .setSuccessUrl(YOUR_DOMAIN+"/invoice")
                        .setCancelUrl(YOUR_DOMAIN+"/failed")
                        .addLineItem(
                                SessionCreateParams.LineItem.builder()
                                        .setQuantity(1L)
                                        .setPriceData(
                                                SessionCreateParams.LineItem.PriceData.builder()
                                                        .setCurrency("inr")
                                                        .setUnitAmount(Math.round(Double.valueOf(amount))*100)
                                                        .setProductData(
                                                                SessionCreateParams.LineItem.PriceData.ProductData.builder()
                                                                        .setName("Order")
                                                                        .build())
                                                        .build())
                                        .build())
                        .build();

        Session session = null;
        try {
            session = Session.create(params);

        } catch (StripeException e) {
            e.printStackTrace();
        }

        try {

            response.sendRedirect(session.getUrl());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
