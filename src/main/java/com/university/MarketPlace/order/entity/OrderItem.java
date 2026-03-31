package com.university.MarketPlace.order.entity;

import com.university.MarketPlace.shop.entity.ShopItems;
import jakarta.persistence.*;

@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long orderItemId;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ShopItems product;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer quantity;
    private double orderProductPrice;

    public OrderItem() {
    }

    public OrderItem(Long orderItemId, ShopItems product, Order order, Integer quantity, double orderProductPrice) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.order = order;
        this.quantity = quantity;
        this.orderProductPrice = orderProductPrice;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ShopItems getProduct() {
        return product;
    }

    public void setProduct(ShopItems product) {
        this.product = product;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getOrderProductPrice() {
        return orderProductPrice;
    }

    public void setOrderProductPrice(double orderProductPrice) {
        this.orderProductPrice = orderProductPrice;
    }
}