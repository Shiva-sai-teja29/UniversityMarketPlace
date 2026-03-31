package com.university.MarketPlace.order;

import com.university.MarketPlace.order.dto.OrderDTO;
import org.springframework.transaction.annotation.Transactional;

public interface OrderService {

    @Transactional
    public OrderDTO placeOrder(String email, String paymentMethod, String pgPaymentId, String pgStatus, String pgResponseMessage, String pgName);
}
