package com.university.MarketPlace.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.university.MarketPlace.security.user.User;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ReqShop {
    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID shopId;
    private String shopName;
    private String description;
    private String location;
    private String otp;
    private LocalDateTime requestExpiry;
//
//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "shops", cascade = CascadeType.ALL, orphanRemoval = true)
//    @JsonManagedReference
//    private List<ShopItems> shopItems;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    @JsonBackReference
    private User user;

    public ReqShop() {
    }

    public ReqShop(UUID shopId, String shopName, String description, String location, String otp, LocalDateTime requestExpiry, User user) {
        this.shopId = shopId;
        this.shopName = shopName;
        this.description = description;
        this.location = location;
        this.otp = otp;
        this.requestExpiry = requestExpiry;
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

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getRequestExpiry() {
        return requestExpiry;
    }

    public void setRequestExpiry(LocalDateTime requestExpiry) {
        this.requestExpiry = requestExpiry;
    }
}
