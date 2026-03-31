package com.university.MarketPlace.buyer.dto;

import com.university.MarketPlace.seller.dto.ItemsDTO;

import java.util.List;

public class CartDTO {
    private Long cartID;
    private Double totalPrice = 0.0;
    private List<ItemsDTO> itemsDTOS;

    public CartDTO() {
    }

    public CartDTO(Long cartID, Double totalPrice, List<ItemsDTO> itemsDTOS) {
        this.cartID = cartID;
        this.totalPrice = totalPrice;
        this.itemsDTOS = itemsDTOS;
    }

    public Long getCartID() {
        return cartID;
    }

    public void setCartID(Long cartID) {
        this.cartID = cartID;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<ItemsDTO> getItemsDTOS() {
        return itemsDTOS;
    }

    public void setItemsDTOS(List<ItemsDTO> itemsDTOS) {
        this.itemsDTOS = itemsDTOS;
    }
}
