package com.university.MarketPlace.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.university.MarketPlace.security.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Entity
public class Shops {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID shopId;
    private String shopName;
    private String description;
    private String location;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shops", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<ShopItems> shopItems;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference
    private User user;

    public Shops() {
    }

    public Shops(UUID shopId, String shopName, String description, String location, List<ShopItems> shopItems, User user) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.description = description;
        this.location = location;
        this.shopItems = shopItems;
        this.user = user;
    }

    public Shops(String shopName, String description, String location, List<ShopItems> shopItems, User user) {
        this.shopName = shopName;
        this.description = description;
        this.location = location;
        this.shopItems = shopItems;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UUID getShopId() {
        return shopId;
    }

    public void setShopId(UUID shopId) {
        this.shopId = shopId;
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

    public List<ShopItems> getShopItems() {
        return shopItems;
    }

    public void setShopItems(List<ShopItems> shopItems) {
        this.shopItems = shopItems;
    }
}
