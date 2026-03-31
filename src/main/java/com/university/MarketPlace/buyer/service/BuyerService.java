package com.university.MarketPlace.buyer.service;

import com.university.MarketPlace.buyer.dto.CartDTO;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

public interface BuyerService {
    CartDTO addItemsToCart(UUID itemId, Integer quantity,String email);

    CartDTO getUserCart(String email, Long cartId);

    CartDTO updateTheUserCart(UUID itemID, Integer quantity,String email);

    String deleteProductFromCart(UUID itemID, Long cartId);
}
