package com.vk.purchasetime.models;

import javax.persistence.*;
import java.util.List;

@Entity
public class InvoiceTransaction {

    @EmbeddedId
    private InvoiceTransactionId invoiceTransactionId;

    private double quantity;
    private double cost;
    private double discount;

    @ManyToOne
    @JoinColumn(name = "invoiceId",referencedColumnName = "invoiceId",updatable = false,insertable = false)
    private InvoicePrimary invoicePrimary;

    public InvoiceTransaction() {
    }

    public InvoiceTransaction(InvoiceTransactionId invoiceTransactionId, double quantity, double cost, double discount) {
        this.invoiceTransactionId = invoiceTransactionId;
        this.quantity = quantity;
        this.cost = cost;
        this.discount = discount;
    }

    public InvoicePrimary getInvoicePrimary() {
        return invoicePrimary;
    }

    public void setInvoicePrimary(InvoicePrimary invoicePrimary) {
        this.invoicePrimary = invoicePrimary;
    }

    public InvoiceTransactionId getInvoiceTransactionId() {
        return invoiceTransactionId;
    }

    public void setInvoiceTransactionId(InvoiceTransactionId invoiceTransactionId) {
        this.invoiceTransactionId = invoiceTransactionId;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }
}
