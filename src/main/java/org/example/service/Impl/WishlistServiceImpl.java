package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.entity.Wishlist;
import org.example.entity.enums.Status;
import org.example.repository.WishlistRepository;
import org.example.service.IWishlistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements IWishlistService {
    private final WishlistRepository wishlistRepository;
    @Override
    public List<Wishlist> findWishlistByAccountID(int id) {
        return WishlistRepository.findWishlistsByAccountID_AccountIDAndStatus(id, Status.ENABLE);
    }
}
