package com.university.MarketPlace.order;

import com.university.MarketPlace.buyer.entity.Cart;
import com.university.MarketPlace.buyer.entity.CartItems;
import com.university.MarketPlace.buyer.repository.CartRepository;
import com.university.MarketPlace.buyer.service.BuyerService;
import com.university.MarketPlace.order.dto.OrderDTO;
import com.university.MarketPlace.order.dto.OrderItemDTO;
import com.university.MarketPlace.order.entity.Order;
import com.university.MarketPlace.order.entity.OrderItem;
import com.university.MarketPlace.order.entity.Payment;
import com.university.MarketPlace.order.repository.OrderItemRepository;
import com.university.MarketPlace.order.repository.OrderRepository;
import com.university.MarketPlace.order.repository.PaymentRepository;
import com.university.MarketPlace.seller.dto.ItemsDTO;
import com.university.MarketPlace.shop.entity.ShopItems;
import com.university.MarketPlace.shop.repository.ShopItemsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private ShopItemsRepository shopItemsRepository;

    @Autowired
    private BuyerService buyerService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderDTO placeOrder(String email, String paymentMethod, String pgPaymentId, String pgStatus, String pgResponseMessage, String pgName) {
        Cart cart = cartRepository.findCartByEmail(email);
        if (cart == null) throw new RuntimeException("CartEmailId");

        Order order = new Order();
        order.setOrderDate(LocalDate.now());
        order.setOrderStatus("Order Placed");
        order.setEmail(email);
        order.setTotalAmount(cart.getTotalPrice());

        Payment payment = new Payment(paymentMethod, pgPaymentId, pgStatus, pgResponseMessage, pgName);
        payment.setOrder(order);
        payment = paymentRepository.save(payment);
        order.setPayment(payment);

        Order savedOrder = orderRepository.save(order);

        List<CartItems> cartItems = cart.getCartItems();
        if (cartItems == null) throw new RuntimeException("Cart is Empty");

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItems cartItems1 : cartItems) {
            OrderItem orderItem = new OrderItem();
            orderItem.setQuantity(cartItems1.getQuantity());
            orderItem.setOrderProductPrice(cartItems1.getProductPrice());
            orderItem.setProduct(cartItems1.getItems());
            orderItem.setOrder(savedOrder);
            orderItems.add(orderItem);
        }
        orderItems = orderItemRepository.saveAll(orderItems);

        cart.getCartItems().forEach(cartItems1 -> {
            int quantity = cartItems1.getQuantity();
            ShopItems product = cartItems1.getItems();

            product.setQuantity(product.getQuantity() - quantity);

            shopItemsRepository.save(product);

            buyerService.deleteProductFromCart(cartItems1.getItems().getShopItemId(), cart.getCartId());
        });

        OrderDTO orderDTO = modelMapper.map(savedOrder, OrderDTO.class);

        for (OrderItem item : orderItems) {
            OrderItemDTO orderItemDTO = modelMapper.map(item, OrderItemDTO.class);
            ItemsDTO productDTOS = modelMapper.map(item.getProduct(), ItemsDTO.class);
            orderItemDTO.setProductDTO(productDTOS);
            orderDTO.getOrderItems().add(orderItemDTO);
        }
        return orderDTO;
    }
}
