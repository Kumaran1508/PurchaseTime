package com.vk.purchasetime.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Hub {
    @EmbeddedId
    private HubId hubId;

    private int quantity;

    @ManyToOne
    @JoinColumn(name = "productId",referencedColumnName = "productId",updatable = false,insertable = false)
    private Product product;


    public Hub() {
    }

    public Hub(HubId hubId, int quantity) {
        this.hubId = hubId;
        this.quantity = quantity;
    }

    public HubId getHubId() {
        return hubId;
    }

    public void setHubId(HubId hubId) {
        this.hubId = hubId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
