package com.vk.purchasetime.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class InvoiceTransactionId implements Serializable {

    @Column(name="invoiceId")
    private long invoiceId;
    @Column(name="productId")
    private int productId;

    public InvoiceTransactionId() {
    }

    public InvoiceTransactionId(long invoiceId, int productId) {
        this.invoiceId = invoiceId;
        this.productId = productId;
    }

    public long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
