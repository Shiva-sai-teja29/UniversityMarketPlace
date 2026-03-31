package com.university.MarketPlace.email;

import com.university.MarketPlace.seller.dto.ShopCreationRequest;
import jakarta.mail.MessagingException;

public interface EmailService {

    boolean sendNewShopRequest(ShopCreationRequest shopCreationRequest, String otp,String ownerName) throws MessagingException;
}
