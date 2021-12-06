package com.vk.purchasetime.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class HubId implements Serializable {
    @Column(name = "hubPincode")
    private int hubPincode;

    @Column(name="productId")
    private int productId;

    public HubId(int hubPincode, int productId) {
        this.hubPincode = hubPincode;
        this.productId = productId;
    }

    public HubId() {
    }

    public int getHubPincode() {
        return hubPincode;
    }

    public void setHubPincode(int hubPincode) {
        this.hubPincode = hubPincode;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }
}
