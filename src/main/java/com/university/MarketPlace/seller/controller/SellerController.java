package com.university.MarketPlace.seller.controller;

import com.university.MarketPlace.seller.dto.ItemsDTO;
import com.university.MarketPlace.seller.dto.ShopCreationRequest;
import com.university.MarketPlace.seller.dto.ShopCreationResponse;
import com.university.MarketPlace.seller.service.SellerService;
import com.university.MarketPlace.shop.entity.ShopItems;
import com.university.MarketPlace.shop.entity.Shops;
import com.university.MarketPlace.security.user.User;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/seller")
public class SellerController {

    @Autowired
    private SellerService service;

    //    create shop and request for approval
    @PostMapping("/createShop")
    public ResponseEntity<ShopCreationResponse> createShop(@RequestBody ShopCreationRequest request) throws MessagingException {
        User user = extractUser();
        ShopCreationResponse response = service.createShop(request, user.getEmail());
        return ResponseEntity.ok(response);
    }

    //  add items to shop
    @PostMapping("/addItem")
    public ResponseEntity<ShopItems> addItems(@RequestBody ItemsDTO request){
        User user = extractUser();
        ShopItems response = service.addItems(request,user);
        return ResponseEntity.ok(response);
    }

    //  update item to shop
    @PutMapping("/updateItem")
    public ResponseEntity<ShopItems> updateItem(@RequestBody ItemsDTO request){
        User user = extractUser();
        ShopItems response = service.updateItem(request,user);
        return ResponseEntity.ok(response);
    }

    //    Delete shop
    @DeleteMapping("/shop/{id}")
    public ResponseEntity<Shops> deleteShop(@PathVariable UUID id){
        User user = extractUser();
        Shops shops = service.deleteShop(id,user);
        return ResponseEntity.ok(shops);
    }

    //    Delete Item
    @DeleteMapping("/shop/item/{id}")
    public ResponseEntity<ShopItems> deleteItem(@PathVariable UUID id){
        User user = extractUser();
        ShopItems items = service.deleteItem(id,user);
        return ResponseEntity.ok(items);
    }

    public User extractUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assert auth != null;
        return (User) auth.getPrincipal();
    }
}
