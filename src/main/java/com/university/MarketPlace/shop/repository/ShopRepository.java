package com.university.MarketPlace.shop.repository;

import com.university.MarketPlace.shop.entity.Shops;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShopRepository extends JpaRepository<Shops, UUID> {

    List<Shops> findByShopNameLikeIgnoreCaseOrDescriptionLikeIgnoreCaseOrLocationLikeIgnoreCase(String s, String s1, String s2);

    Optional<Shops> findByShopName(String shopName);
}
