package com.university.MarketPlace.shop.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
public class ShopItems {

    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "item_id")
    private UUID shopItemId;

    private String itemName;
    private String itemDescription;
    private Double price;
    private Integer quantity;
    private String category;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id", nullable = false)  // Foreign Key
    @JsonBackReference
    private Shops shops;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "shop_id", nullable = false)  // Foreign Key
//    @JsonBackReference
//    private ReqShop reqShop;

    public Shops getShops() {
        return shops;
    }

    public void setShops(Shops shops) {
        this.shops = shops;
    }

    public ShopItems() {
    }

    public ShopItems(String itemName, String itemDescription, Double price, Integer quantity, String category) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.price = price;
        this.quantity = quantity;
        this.category=category;
    }

    public UUID getShopItemId() {
        return shopItemId;
    }

    public void setShopItemId(UUID shopItemId) {
        this.shopItemId = shopItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
