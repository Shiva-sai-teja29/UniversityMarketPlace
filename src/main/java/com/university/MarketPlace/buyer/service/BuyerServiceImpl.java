package com.university.MarketPlace.buyer.service;

import com.university.MarketPlace.buyer.dto.CartDTO;
import com.university.MarketPlace.buyer.entity.Cart;
import com.university.MarketPlace.buyer.entity.CartItems;
import com.university.MarketPlace.buyer.repository.CartItemRepository;
import com.university.MarketPlace.buyer.repository.CartRepository;
import com.university.MarketPlace.seller.dto.ItemsDTO;
import com.university.MarketPlace.shop.entity.ShopItems;
import com.university.MarketPlace.shop.repository.ShopItemsRepository;
import com.university.MarketPlace.security.user.User;
import com.university.MarketPlace.security.user.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
public class BuyerServiceImpl implements BuyerService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ShopItemsRepository shopItemsRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public CartDTO addItemsToCart(UUID itemId, Integer quantity,String email) {
        Cart cart = createCart(email);

        ShopItems shopItems = shopItemsRepository.findById(itemId)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        CartItems cartItems = cartItemRepository.findCartItemsByProductIdAndCartID(itemId, cart.getCartId());

        if (cartItems != null) throw new RuntimeException("Product already exists in cart");

        if (shopItems.getQuantity() == 0) throw new RuntimeException("Item is not available");

        if (shopItems.getQuantity()<quantity) throw new RuntimeException("Please order the product less than or equal quantity.");

        CartItems newCartItem = new CartItems();
        newCartItem.setCart(cart);
        newCartItem.setItems(shopItems);
        newCartItem.setQuantity(quantity);
        newCartItem.setProductPrice(shopItems.getPrice());
        cartItemRepository.save(newCartItem);

        shopItems.setQuantity(shopItems.getQuantity());

        cart.setTotalPrice(cart.getTotalPrice() + (shopItems.getPrice() * quantity));
        cartRepository.save(cart);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItems> cartItems1 = cart.getCartItems();

        Stream<ItemsDTO> itemsStream = cartItems1.stream().map(items ->{
            ItemsDTO map = modelMapper.map(items.getItems(), ItemsDTO.class);
            map.setQuantity(items.getQuantity());
            return map;
        });
        //cartDTO.setProducts(productStream.collect(Collectors.toList()));
        cartDTO.setItemsDTOS(itemsStream.toList());

        return cartDTO;
    }

    @Override
    public CartDTO getUserCart(String email, Long cartId) {
        Cart cart = cartRepository.findCartByEmailAndCartId(email, cartId);
        if (cart == null) throw new RuntimeException("Cart Doesn't exists");
        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);
        cart.getCartItems().forEach(c->c.getItems().setQuantity(c.getQuantity()));
        List<ItemsDTO> products = cart.getCartItems().stream()
                .map(p->modelMapper.map(p.getItems(), ItemsDTO.class)).toList();
        cartDTO.setItemsDTOS(products);
        return cartDTO;
    }

    @Override
    @Transactional
    public CartDTO updateTheUserCart(UUID itemID, Integer quantity,String email) {
//        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        Cart userCart = cartRepository.findCartByEmail(email);
        Long id = userCart.getCartId();
        Cart cart = cartRepository.findById(id).orElseThrow(()-> new RuntimeException("Cart Doesn't exists"));

        ShopItems shopItems = shopItemsRepository.findById(itemID)
                .orElseThrow(() -> new RuntimeException("Product--productID"));

        if (shopItems.getQuantity() == 0) throw new RuntimeException(shopItems.getItemName()+" is not available");

        if (shopItems.getQuantity()<quantity) throw new RuntimeException("Please order the less than or equal quantity.");

        CartItems cartItems = cartItemRepository.findCartItemsByProductIdAndCartID(itemID, cart.getCartId());

        if (cartItems == null) throw new RuntimeException("Product not exists in cart");

        int newQuantity = cartItems.getQuantity() + quantity;

        if (newQuantity < 0) throw new RuntimeException("The resulting quantity cannot be negative");

        if (newQuantity == 0){
            deleteProductFromCart(itemID,id);
        } else {
            cartItems.setQuantity(cartItems.getQuantity() + quantity);
            cartItems.setProductPrice(cartItems.getProductPrice());
            cart.setTotalPrice(cart.getTotalPrice() + (cartItems.getProductPrice()*quantity));
            cartRepository.save(cart);
        }
        CartItems cartItems1 = cartItemRepository.save(cartItems);
        if (cartItems1.getQuantity() == 0) cartItemRepository.deleteById(cartItems1.getCartItemId());

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<CartItems> cartItems2 = cart.getCartItems();

        Stream<ItemsDTO> productStream = cartItems2.stream().map(items ->{
            ItemsDTO map = modelMapper.map(items.getItems(), ItemsDTO.class);
            map.setQuantity(items.getQuantity());
            return map;
        });
        //cartDTO.setProducts(productStream.collect(Collectors.toList()));
        cartDTO.setItemsDTOS(productStream.toList());

        return cartDTO;
    }

    @Override
    @Transactional
    public String deleteProductFromCart(UUID itemID, Long cartId) {
        Cart cart = cartRepository.findById(cartId).orElseThrow(()-> new RuntimeException("Cart Doesn't exists"));

        CartItems cartItems = cartItemRepository.findCartItemsByProductIdAndCartID(itemID, cart.getCartId());
        if (cartItems == null) throw new RuntimeException("Product not exists in cart");

        cart.setTotalPrice(cart.getTotalPrice() - (cartItems.getProductPrice() * cartItems.getQuantity()));
        cartItemRepository.deleteCartItemsByItemIdAndCartId(itemID, cartId);
        return "Product " + cartItems.getItems().getItemName()+" removed from cart!";
    }

    private Cart createCart(String email) {
//        Cart userCart = cartRepository.findCartByEmail(authUtil.loggedInEmail());
        Cart userCart = cartRepository.findCartByEmail(email);
        User user = userRepository.findByEmail(email).orElseThrow(()->new RuntimeException("No user found"));
        if (userCart !=null){
            return userCart;
        }
        Cart cart = new Cart();
//        cart.setUser(authUtil.loggedInUser());
        cart.setUser(user);
        cart.setTotalPrice(0.00);
        return cartRepository.save(cart);
    }
}
