package com.university.MarketPlace.buyer.repository;

import com.university.MarketPlace.buyer.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CartItemRepository extends JpaRepository<CartItems,Long> {

    @Query("SELECT ci FROM CartItems ci WHERE ci.items.id = ?1 AND ci.cart.id = ?2")
    CartItems findCartItemsByProductIdAndCartID(UUID itemId, Long cartId);

    @Modifying
    @Query("DELETE FROM CartItems ci WHERE ci.items.id = ?1 AND ci.cart.id = ?2")
    void deleteCartItemsByItemIdAndCartId(UUID itemID, Long cartId);
}
