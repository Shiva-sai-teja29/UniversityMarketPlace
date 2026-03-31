package com.university.MarketPlace.buyer.entity;

import com.university.MarketPlace.shop.entity.ShopItems;
import jakarta.persistence.*;

@Entity
public class CartItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartItemId;

    @ManyToOne
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "item_id")
    private ShopItems items;

    private Integer quantity;
    private Double productPrice;

    public CartItems() {
    }

    public CartItems(Long cartItemId, Cart cart, ShopItems items, Integer quantity, Double productPrice) {
        this.cartItemId = cartItemId;
        this.cart = cart;
        this.items = items;
        this.quantity = quantity;
        this.productPrice = productPrice;
    }

    public Double getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Double productPrice) {
        this.productPrice = productPrice;
    }

    public Long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(Long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public ShopItems getItems() {
        return items;
    }

    public void setItems(ShopItems items) {
        this.items = items;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
