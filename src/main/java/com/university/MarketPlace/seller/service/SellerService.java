package com.university.MarketPlace.seller.service;

import com.university.MarketPlace.seller.dto.ItemsDTO;
import com.university.MarketPlace.seller.dto.ShopCreationRequest;
import com.university.MarketPlace.seller.dto.ShopCreationResponse;
import com.university.MarketPlace.shop.entity.ShopItems;
import com.university.MarketPlace.shop.entity.Shops;
import com.university.MarketPlace.security.user.User;
import jakarta.mail.MessagingException;

import java.util.UUID;

public interface SellerService {
    ShopCreationResponse createShop(ShopCreationRequest request,String email) throws MessagingException;

    ShopItems addItems(ItemsDTO request, User user);

    ShopItems updateItem(ItemsDTO request, User user);

    Shops deleteShop(UUID id, User user);

    ShopItems deleteItem(UUID id, User user);
}
