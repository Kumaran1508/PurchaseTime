package com.vk.purchasetime.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class InvoicePrimary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long invoiceId;

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(fetch =FetchType.LAZY,mappedBy = "invoicePrimary")
    private List<InvoiceTransaction> invoiceTransactions=new ArrayList<>();

    private Date invoiceDate;
    private double deliveryCharges;
    private String transactionType;

    @OneToMany(mappedBy = "invoicePrimary")
    private List<Transaction> transactions=new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "profileId")
    private Profile profile;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public InvoicePrimary(Date invoiceDate, double deliveryCharges, TransactionType transactionType) {
        this.invoiceDate = invoiceDate;
        this.deliveryCharges = deliveryCharges;
        this.transactionType = transactionType.toString();
    }

    public InvoicePrimary() {
    }

    public List<InvoiceTransaction> getInvoiceTransactions() {
        return invoiceTransactions;
    }

    public void setInvoiceTransactions(List<InvoiceTransaction> invoiceTransactions) {
        this.invoiceTransactions = invoiceTransactions;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public Date getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(Date invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setDeliveryCharges(double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public TransactionType getTransactionType() {
        return TransactionType.valueOf(transactionType);
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType.toString();
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "InvoicePrimary{" +
                "invoiceId=" + invoiceId +
                ", user=" + user +
                ", invoiceDate=" + invoiceDate +
                ", deliveryCharges=" + deliveryCharges +
                ", transactionType='" + transactionType + '\'' +
                ", profile=" + profile +
                '}';
    }
}
