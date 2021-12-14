package com.vk.purchasetime.services;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Table;
import com.spire.doc.fields.Field;
import com.vk.purchasetime.models.InvoicePrimary;
import com.vk.purchasetime.models.Product;
import com.vk.purchasetime.models.Profile;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.HashMap;

@Component
public class InvoiceGenerator {



    public void createPdf(InvoicePrimary invoice, Profile profile, HashMap<Product,Integer> cart){
        //create a document instance
        Document doc = new Document();
        String pattern = "MM-dd-yyyy hh:mm a";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        //load the template file
        doc.loadFromFile("D:\\Data\\Java\\PurchaseTime\\PurchaseTimeInvoice.docx");

        //replace text in the document
        doc.replace("#InvoiceNum", String.valueOf(invoice.getInvoiceId()), true, true);
        doc.replace("#InvoiceDate", simpleDateFormat.format(invoice.getInvoiceDate()), true, true);
        doc.replace("#DeliveryType", profile.getProfileType().toString(), true, true);
        doc.replace("#UserName", invoice.getUser().getUsername(), true, true);
        doc.replace("#UserAddress", profile.getAddress(), true, true);
        doc.replace("#CityState", profile.getState(), true, true);
        doc.replace("#Pincode", profile.getPincode(), true, true);
        doc.replace("#Phone", invoice.getUser().getPhoneNumber(), true, true);
        doc.replace("#ProfileName", profile.getProfileName(), true, true);
        doc.replace("#ShippingAddress", profile.getAddress(), true, true);
        doc.replace("#Phone2", invoice.getUser().getPhoneNumber(), true, true);
        doc.replace("#total", String.format("%.2f",invoice.getAmount()), true, true);

        //write the purchase data to the document
        writeDataToDocument(doc, cart);

        //update fields
        doc.isUpdateFields(true);

        //save file in pdf format
        doc.saveToFile("Invoice"+invoice.getInvoiceId()+".pdf", FileFormat.PDF);

        EmailServicev1 emailServicev1 =new EmailServicev1();
        emailServicev1.sendInvoice(invoice.getUser().getEmail(),"Purchase Time - Invoice"+invoice.getInvoiceId(),"Invoice"+invoice.getInvoiceId()+".pdf");

    }

    private static void addRows(Table table, int rowNum) {
        for (int i = 0; i < rowNum; i++) {
            //insert specific number of rows by cloning the second row
            if (i%2==0)
                table.getRows().insert(3 +i, table.getRows().get(1).deepClone());
            else
                table.getRows().insert(3 + i, table.getRows().get(2).deepClone());
        }
    }

    private static void fillTableWithData(Table table, HashMap<Product, Integer> data) {
        int index=0;
        for (Product product : data.keySet()){
            double cost = product.getCost()*(100-product.getDiscount())*0.01;
            table.getRows().get(index + 1).getCells().get(0).getParagraphs().get(0).setText(product.getProductName());
            table.getRows().get(index + 1).getCells().get(1).getParagraphs().get(0).setText(String.valueOf(data.get(product)));
            table.getRows().get(index + 1).getCells().get(2).getParagraphs().get(0).setText(String.format("%.2f",cost));
            table.getRows().get(index + 1).getCells().get(3).getParagraphs().get(0).setText(String.format("%.2f",data.get(product)*cost));
            index++;
        }

    }

    private static void writeDataToDocument(Document doc, HashMap<Product,Integer> cart) {
        //get the third table
        Table table = doc.getSections().get(0).getTables().get(2);
        //determine if it needs to add rows
        if (cart.size() > 2) {
            //add rows
            addRows(table, cart.size() -2);
        }
        //fill the table cells with value
        fillTableWithData(table, cart);
    }


}