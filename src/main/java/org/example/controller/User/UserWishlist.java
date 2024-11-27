package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.dto.FlowerDTO;
import org.example.entity.*;
import org.example.repository.AccountRepository;
import org.example.service.IAccountService;
import org.example.service.IFlowerService;
import org.example.service.IFlowerSizeService;
import org.example.service.IWishlistService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class UserWishlist {
    private final IWishlistService wishlistService;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final IAccountService accountService;
    private final IFlowerSizeService flowerSizeService;
    private final AccountRepository accountRepository;

    @GetMapping("")
    public ResponseEntity<?> getWishlist() {
        int idAccount = getIDAccountFromAuthService.common();
        List<Wishlist> wishlists = wishlistService.findWishlistByAccountID(idAccount);
        List<FlowerDTO> productSimilarDTOs = wishlists.stream().map(similar -> {
            FlowerDTO dto = new FlowerDTO();
            dto.setFlowerID(similar.getFlower().getFlowerID());
            dto.setName(similar.getFlower().getName());
            dto.setDescription(similar.getFlower().getDescription());
            dto.setImage(similar.getFlower().getImage());
            dto.setLanguageOfFlowers(similar.getFlower().getLanguageOfFlowers());
            dto.setCategory(similar.getFlower().getCategory());
            dto.setPurpose(similar.getFlower().getPurpose());
            FlowerSize size = flowerSizeService.findCheapestPriceByFlowerID(similar.getFlower().getFlowerID());
            dto.setPrice(size.getPrice());
            return dto;
        }).toList();

        Map<String, Object> response = new HashMap<>();
        response.put("wishlists", wishlists);
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWishlist(@PathVariable Integer id) {
        Wishlist wishlist = wishlistService.findByID(id);
        if (wishlist == null) {
            return ResponseEntity.notFound().build();
        }
        wishlistService.harddeleteAccount(id);
        return ResponseEntity.noContent().build();
    }
}
