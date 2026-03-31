package com.university.MarketPlace.order;

import com.university.MarketPlace.order.dto.OrderDTO;
import com.university.MarketPlace.order.dto.OrderRequestDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/buyer")
public class OrderController {

    @Autowired
    private OrderService orderService;

    //    Order the items in cart
    @PostMapping("/orders/payment/{paymentMethod}/{email}")
    public ResponseEntity<OrderDTO> placeOrder(@PathVariable String paymentMethod,
                                               @RequestBody OrderRequestDTO orderRequestDTO,
                                               @PathVariable String email){
//        String email = authUtil.loggedInEmail();

        OrderDTO orderDTO = orderService.placeOrder(
                email,
                paymentMethod,
                orderRequestDTO.getPgPaymentId(),
                orderRequestDTO.getPgStatus(),
                orderRequestDTO.getPgResponseMessage(),
                orderRequestDTO.getPgName()
        );
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }
}
