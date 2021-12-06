package com.vk.purchasetime.models;

import javax.persistence.*;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int transactionId;
    private String transactionStatus;
    private double amount;

    @ManyToOne
    @JoinColumn(name = "invoiceId")
    private InvoicePrimary invoicePrimary;


    public Transaction() {
    }

    public Transaction(TransactionStatus transactionStatus, double amount){
        this.transactionStatus = transactionStatus.toString();
        this.amount = amount;
    }



    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public TransactionStatus getTransactionStatus() {
        return TransactionStatus.valueOf(transactionStatus);
    }

    public void setTransactionStatus(TransactionStatus transactionStatus) {
        this.transactionStatus = transactionStatus.toString();
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


    public InvoicePrimary getInvoicePrimary() {
        return invoicePrimary;
    }

    public void setInvoicePrimary(InvoicePrimary invoiceId) {
        this.invoicePrimary = invoiceId;
    }
}
