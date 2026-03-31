package com.university.MarketPlace.shop.repository;

import com.university.MarketPlace.shop.entity.ReqShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReqShopRepository extends JpaRepository<ReqShop, UUID> {

    ReqShop findByShopName(String shopName);
}
