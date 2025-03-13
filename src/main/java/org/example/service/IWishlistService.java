package org.example.service;

import org.example.entity.Wishlist;

import java.util.List;

public interface IWishlistService {
    List<Wishlist> findWishlistByAccountID(int id);
    Wishlist findByID(int id);
    Wishlist findByFlowerIDAndAccountID(int flowerid, int accountid);

    void harddeleteAccount(Integer id);

    List<Wishlist> findWishlistsByFlowerID(int flowerid);
}
