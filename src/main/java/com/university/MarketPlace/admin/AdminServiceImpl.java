package com.university.MarketPlace.admin;

import com.university.MarketPlace.security.user.User;
import com.university.MarketPlace.security.user.UserRepository;
import com.university.MarketPlace.shop.entity.ReqShop;
import com.university.MarketPlace.shop.entity.Shops;
import com.university.MarketPlace.shop.repository.ReqShopRepository;
import com.university.MarketPlace.shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReqShopRepository reqShopRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ShopRepository shopRepository;

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not exists"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public String approveShop(ApprovalDTO approvalDTO) {
        ReqShop reqShop = reqShopRepository.findByShopName(approvalDTO.getShopName());

        User user = userRepository.findByUsername(approvalDTO.getShopOwnerName()).orElseThrow(()->new RuntimeException("User mismatch"));

        if (!passwordEncoder.matches(approvalDTO.getOTP(), reqShop.getOtp())) {
            throw new RuntimeException("Invalid token");
        }

        if (reqShop.getRequestExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Token expired");
        }

        Shops shop = new Shops();
        shop.setUser(reqShop.getUser());
//        shop.setShopId(reqShop.getShopId());
        shop.setShopName(reqShop.getShopName());
//        shop.setShopItems(reqShop.getShopItems());
        shop.setLocation(reqShop.getLocation());
        shop.setDescription(reqShop.getDescription());

        user.setShops(shop);

        shopRepository.save(shop);

        reqShopRepository.delete(reqShop);

        return "Approved Successfully";
    }

}
