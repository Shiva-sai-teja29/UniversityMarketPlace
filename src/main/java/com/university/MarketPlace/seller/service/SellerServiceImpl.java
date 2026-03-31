package com.university.MarketPlace.seller.service;

import com.university.MarketPlace.email.EmailService;
import com.university.MarketPlace.seller.dto.ItemsDTO;
import com.university.MarketPlace.seller.dto.ShopCreationRequest;
import com.university.MarketPlace.seller.dto.ShopCreationResponse;
import com.university.MarketPlace.shop.entity.ReqShop;
import com.university.MarketPlace.shop.entity.ShopItems;
import com.university.MarketPlace.shop.entity.Shops;
import com.university.MarketPlace.shop.repository.ReqShopRepository;
import com.university.MarketPlace.shop.repository.ShopItemsRepository;
import com.university.MarketPlace.shop.repository.ShopRepository;
import com.university.MarketPlace.security.user.User;
import com.university.MarketPlace.security.user.UserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class SellerServiceImpl implements SellerService{

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ShopItemsRepository shopItemsRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReqShopRepository reqShopRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public ShopCreationResponse createShop(ShopCreationRequest request,String email) throws MessagingException {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("No user found"));
        shopRepository.findByShopName(request.getShopName())
                .ifPresent(shop -> {
                    throw new RuntimeException("Shop already exists");
                });

        SecureRandom random = new SecureRandom();
        String otp = String.valueOf(10000000 + random.nextLong(90000000));

        ReqShop reqShop = new ReqShop();
        reqShop.setShopName(request.getShopName());
        reqShop.setDescription(request.getDescription());
        reqShop.setLocation(request.getLocation());
        reqShop.setOtp(passwordEncoder.encode(otp));
        reqShop.setRequestExpiry(LocalDateTime.now().plusDays(2));
        reqShop.setUser(user);
        user.setReqShop(reqShop);

        boolean sent = emailService.sendNewShopRequest(request, otp,user.getUsername());
        ReqShop saved = new ReqShop();
        if (sent){
            try {
                saved = reqShopRepository.save(reqShop);
            }catch (Exception e){
                throw new RuntimeException("Mail sent to Admins for confirmation of shop set up ");
            }
        }
        return mapToResponse(saved);
    }

    @Override
    public ShopItems addItems(ItemsDTO request, User user) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Shops shops = shopRepository.findByShopName(request.getShopName())
                .orElseThrow(()->new RuntimeException("No Shop with that name"));

        if (!shops.getUser().equals(user)){
            throw new RuntimeException("The Shop is not associate with you");
        }

        shopItemsRepository.findByItemName(request.getItemName())
                .ifPresent(item -> {
                    throw new RuntimeException("Item already exists");
                });
        ShopItems shopItems = new ShopItems();
        shopItems.setItemName(request.getItemName());
        shopItems.setItemDescription(request.getItemDescription());
        shopItems.setCategory(request.getCategory());
        shopItems.setPrice(request.getPrice());
        shopItems.setQuantity(request.getQuantity());
        shopItems.setShops(shops);

        List<ShopItems> shopItems1 = shops.getShopItems();
        shopItems1.add(shopItems);
        shops.setShopItems(shopItems1);

        return shopItemsRepository.save(shopItems);

    }

    @Override
    public ShopItems updateItem(ItemsDTO request, User user) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null");
        }

        Shops shops = shopRepository.findByShopName(request.getShopName())
                .orElseThrow(()->new RuntimeException("No Shop with that name"));

        if (!shops.getUser().equals(user)){
            throw new RuntimeException("The Shop is not associate with you");
        }

        ShopItems existingShopItem = shopItemsRepository.findByItemName(request.getItemName())
                .orElseThrow(()->new RuntimeException("No Item with that name"));

//        ShopItems shopItems = new ShopItems();
        existingShopItem.setItemName(request.getItemName());
        existingShopItem.setItemDescription(request.getItemDescription());
        existingShopItem.setCategory(request.getCategory());
        existingShopItem.setPrice(request.getPrice());
        existingShopItem.setQuantity(existingShopItem.getQuantity()+request.getQuantity());
        existingShopItem.setShops(shops);

        List<ShopItems> shopItems1 = shops.getShopItems();
        shopItems1.add(existingShopItem);
        shops.setShopItems(shopItems1);

        return shopItemsRepository.save(existingShopItem);
    }

    @Override
    @Transactional
    public Shops deleteShop(UUID id, User us) {
        Shops shops = shopRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shop not found with id: " + id));
        User user = shops.getUser();

        if (!shops.getUser().equals(us)){
            throw new RuntimeException("The Shop is not associate with you");
        }
        if (user != null) {
            user.setShops(null);   // ✅ break relationship
        }
        shops.setUser(null);

        shopRepository.delete(shops);
        System.out.println("Deleted");
        return shops;
    }

    @Override
    public ShopItems deleteItem(UUID id, User user) {
        ShopItems shopsItems = shopItemsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));
        Shops shops = shopsItems.getShops();
        if (!shops.getUser().equals(user)){
            throw new RuntimeException("The Shop is not associate with you");
        }
        shopItemsRepository.delete(shopsItems);
        return shopsItems;
    }

    private ShopCreationResponse mapToResponse(ReqShop shop) {
        ShopCreationResponse res = new ShopCreationResponse();
        res.setShopName(shop.getShopName());
        res.setLocation(shop.getLocation());
        res.setDescription(shop.getDescription());
        return res;
    }

}
