package com.university.MarketPlace.order.dto;

import com.university.MarketPlace.seller.dto.ItemsDTO;

public class OrderItemDTO {
    private Long orderItemId;
    private ItemsDTO productDTO;
    private Integer quantity;
    private double discount;
    private double orderProductPrice;

    public OrderItemDTO() {
    }

    public OrderItemDTO(Long orderItemId, ItemsDTO productDTO, Integer quantity, double discount, double orderProductPrice) {
        this.orderItemId = orderItemId;
        this.productDTO = productDTO;
        this.quantity = quantity;
        this.discount = discount;
        this.orderProductPrice = orderProductPrice;
    }

    public Long getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(Long orderItemId) {
        this.orderItemId = orderItemId;
    }

    public ItemsDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ItemsDTO productDTO) {
        this.productDTO = productDTO;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getOrderProductPrice() {
        return orderProductPrice;
    }

    public void setOrderProductPrice(double orderProductPrice) {
        this.orderProductPrice = orderProductPrice;
    }
}
