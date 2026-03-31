package com.university.MarketPlace.shop.repository;

import com.university.MarketPlace.shop.entity.ShopItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ShopItemsRepository extends JpaRepository<ShopItems, UUID> {

    Optional<ShopItems> findByItemName(String itemName);

    List<ShopItems> findByItemNameLikeIgnoreCaseOrItemDescriptionLikeIgnoreCaseOrCategoryLikeIgnoreCase(String s, String s1, String s2);

    @Query("""
    SELECT i FROM ShopItems i
    WHERE i.shops.shopId = :id
    AND (
        LOWER(i.itemName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(i.itemDescription) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR LOWER(i.category) LIKE LOWER(CONCAT('%', :keyword, '%'))
    )
    """)
    List<ShopItems> searchItems(UUID id, String keyword);
}
