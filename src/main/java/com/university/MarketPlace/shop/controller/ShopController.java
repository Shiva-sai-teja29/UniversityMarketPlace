package com.university.MarketPlace.shop.controller;

import com.university.MarketPlace.shop.entity.ShopItems;
import com.university.MarketPlace.shop.entity.Shops;
import com.university.MarketPlace.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/buyer")
public class ShopController {

    @Autowired
    private ShopService shopService;

    //    View available shops
    @GetMapping("/shops")
    public ResponseEntity<List<Shops>> availableShops(){
        List<Shops> shops = shopService.getShops();
        return ResponseEntity.ok(shops);
    }

    // View available items by particular shop
    @GetMapping("/shop/{id}")
    public ResponseEntity<Optional<Shops>> availableShop(@PathVariable UUID id){
        Optional<Shops> shop = shopService.getShop(id);
        return ResponseEntity.ok(shop);
    }

    // Search items for all shops
    @GetMapping("/items/{keyword}")
    public ResponseEntity<List<ShopItems>> searchItems(@PathVariable String keyword){
        List<ShopItems> items = shopService.getItems(keyword);
        return ResponseEntity.ok(items);
    }

    // Search items from particular shop
    @GetMapping("/shops/{id}/search/{keyword}")
    public ResponseEntity<List<ShopItems>> searchItemsInAShop(@PathVariable UUID id,@PathVariable String keyword){
        List<ShopItems> shopItems = shopService.getItemsByShopBySearch(id,keyword);
        return ResponseEntity.ok(shopItems);
    }

    // Search shop with shop name
    @GetMapping("/shops/{keyword}")
    public ResponseEntity<List<Shops>> searchShops(@PathVariable String keyword){
        List<Shops> shops = shopService.getShopsBySearch(keyword);
        return ResponseEntity.ok(shops);
    }
}
