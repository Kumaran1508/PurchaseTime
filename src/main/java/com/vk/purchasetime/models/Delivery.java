package com.vk.purchasetime.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Delivery {

    @Id
    private int invoiceId;
    private DeliveryStatus deliveryStatus;
    private Date expectedDeliveryDate;

    public Delivery() {
    }

    public Delivery(int invoiceId, DeliveryStatus deliveryStatus, Date expectedDeliveryDate) {
        this.invoiceId = invoiceId;
        this.deliveryStatus = deliveryStatus;
        this.expectedDeliveryDate = expectedDeliveryDate;
    }

    public int getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(int invoiceId) {
        this.invoiceId = invoiceId;
    }

    public DeliveryStatus getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public Date getExpectedDeliveryDate() {
        return expectedDeliveryDate;
    }

    public void setExpectedDeliveryDate(Date expectedDeliveryDate) {
        this.expectedDeliveryDate = expectedDeliveryDate;
    }
}
