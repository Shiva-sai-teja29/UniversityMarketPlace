package com.university.MarketPlace.buyer.controller;

import com.university.MarketPlace.buyer.dto.CartDTO;
import com.university.MarketPlace.buyer.entity.Cart;
import com.university.MarketPlace.buyer.repository.CartRepository;
import com.university.MarketPlace.buyer.service.BuyerService;
import com.university.MarketPlace.security.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class BuyerController {

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    //    add items to cart
    @PostMapping("/cart/product/{ItemId}/quantity/{quantity}/{email}")
    public ResponseEntity<CartDTO> addProductsToCart(@PathVariable UUID ItemId,
                                                     @PathVariable Integer quantity,
                                                     @PathVariable String email){
        CartDTO cartDTO = buyerService.addItemsToCart(ItemId, quantity,email);
        return new ResponseEntity<CartDTO>(cartDTO, HttpStatus.CREATED);
    }

    //    GEt buyer cart
    @GetMapping("/carts/users/cart/{email}")
    public ResponseEntity<CartDTO> getUserCart(@PathVariable String email){
//        String email = authUtil.loggedInEmail();
        Cart cart = cartRepository.findCartByEmail(email);
        CartDTO cartDTO = buyerService.getUserCart(email, cart.getCartId());
        return new ResponseEntity<>(cartDTO, HttpStatus.FOUND);
    }

    //    update items in cart
    @PutMapping("/carts/items/{itemID}/operation/{operation}/{email}")
    public ResponseEntity<CartDTO> updateCart(@PathVariable UUID itemID,
                                              @PathVariable String operation,
                                              @PathVariable String email){
        CartDTO cartDTO = buyerService.updateTheUserCart(itemID,
                operation.equalsIgnoreCase("delete")? -1 : 1,email);
        return new ResponseEntity<>(cartDTO, HttpStatus.CREATED);
    }

    //    delete items in cart
    @DeleteMapping("/products/{itemID}/cart/{cartId}")
    public ResponseEntity<String> deleteProductCart(@PathVariable UUID itemID,
                                                    @PathVariable Long cartId){
        String status = buyerService.deleteProductFromCart(itemID, cartId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

//    public String loggedInEmail() {
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        User user = userRepository.findByUsername(authentication.getName());
//        if (user == null){
//            throw new RuntimeException("User Not found with username ");
//        } else {
//            return user.getEmail();
//        }
//    }
}


