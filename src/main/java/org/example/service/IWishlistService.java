package org.example.service;

import org.example.entity.Wishlist;

import java.util.List;

public interface IWishlistService {
    List<Wishlist> findWishlistByAccountID(int id);
}
