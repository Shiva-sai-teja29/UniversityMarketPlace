package com.university.MarketPlace.seller.dto;

import java.util.UUID;

public class ShopCreationResponse {
    private String shopName;
    private String description;
    private String location;

    public ShopCreationResponse(String shopName, String description, String location) {
        this.shopName = shopName;
        this.description = description;
        this.location = location;
    }

    public ShopCreationResponse() {
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
