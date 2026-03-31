package com.university.MarketPlace.shop.service;

import com.university.MarketPlace.shop.entity.ShopItems;
import com.university.MarketPlace.shop.entity.Shops;
import com.university.MarketPlace.shop.repository.ShopItemsRepository;
import com.university.MarketPlace.shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService{

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopItemsRepository shopItemsRepository;

    @Override
    public List<Shops> getShops() {

        return shopRepository.findAll();
    }

    @Override
    public Optional<Shops> getShop(UUID id) {
        return shopRepository.findById(id);
    }

    @Override
    public List<ShopItems> getItems(String keyword) {
        List<ShopItems> items = shopItemsRepository.findByItemNameLikeIgnoreCaseOrItemDescriptionLikeIgnoreCaseOrCategoryLikeIgnoreCase(
                "%" + keyword + "%","%"+keyword+"%","%"+keyword+"%");
        if (items.isEmpty()) throw new RuntimeException("Product not exists");
        return items;
    }

    @Override
    public List<Shops> getShopsBySearch(String keyword) {
        List<Shops> shops = shopRepository.findByShopNameLikeIgnoreCaseOrDescriptionLikeIgnoreCaseOrLocationLikeIgnoreCase("%"+keyword+"%","%"+keyword+"%","%"+keyword+"%");
        if (shops.isEmpty()) throw new RuntimeException("Shops not exists");
        return shops;
    }

    @Override
    public List<ShopItems> getItemsByShopBySearch(UUID id, String keyword) {
        List<ShopItems> items = shopItemsRepository.searchItems(id,keyword);
        if (items.isEmpty()) throw new RuntimeException("Product not exists");
        return items;
    }
}
