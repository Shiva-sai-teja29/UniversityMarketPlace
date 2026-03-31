package com.university.MarketPlace.shop.service;

import com.university.MarketPlace.shop.entity.ShopItems;
import com.university.MarketPlace.shop.entity.Shops;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShopService {
    List<Shops> getShops();

    Optional<Shops> getShop(UUID id);

    List<ShopItems> getItems(String keyword);

    List<Shops> getShopsBySearch(String keyword);

    List<ShopItems> getItemsByShopBySearch(UUID id, String keyword);
}
