package com.university.MarketPlace.seller.dto;

import com.university.MarketPlace.shop.entity.ShopItems;

import java.util.Set;

public class ShopCreationRequest {
    private String shopName;
    private String description;
    private String location;
    private String requestNote;

    public ShopCreationRequest() {
    }

    public ShopCreationRequest(String shopName, String description, String location, String requestNote) {
        this.shopName = shopName;
        this.description = description;
        this.location = location;
        this.requestNote = requestNote;
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

    public String getRequestNote() {
        return requestNote;
    }

    public void setRequestNote(String requestNote) {
        this.requestNote = requestNote;
    }
}
