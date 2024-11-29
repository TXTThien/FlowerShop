package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.FlowerDTO;
import org.example.dto.PrebuyDTO;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.CartRepository;
import org.example.repository.WishlistRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class FlowerDetailController {
    private final IFlowerService flowerService;
    private final IReviewService reviewService;
    private final IFlowerSizeService flowerSizeService;
    private final IFlowerImageService flowerImageService;
    private final WishlistRepository wishlistRepository;

    private final ICartService cartService;
    private final IAccountService accountService;

    private final GetIDAccountFromAuthService getIDAccountService;

    @GetMapping("/detail/{id}")
    public ResponseEntity<?> ProductDetail(@PathVariable("id") int id){
        Flower product = flowerService.getProductById(id);
        FlowerDTO flowerDTO = new FlowerDTO();
        flowerDTO.setFlowerID(product.getFlowerID());
        flowerDTO.setName(product.getName());
        flowerDTO.setDescription(product.getDescription());
        flowerDTO.setImage(product.getImage());
        flowerDTO.setLanguageOfFlowers(product.getLanguageOfFlowers());
        flowerDTO.setCategory(product.getCategory());
        flowerDTO.setPurpose(product.getPurpose());
        FlowerSize minFlowerSize = flowerSizeService.findCheapestPriceByFlowerID(product.getFlowerID());
        flowerDTO.setPrice(minFlowerSize.getPrice());

        List<Review> reviewList = reviewService.findReviewByProductID (id);
        List<FlowerImages> imageList = flowerImageService.findImagesByProductID(id);
        List<FlowerSize> FlowerSizesList = flowerSizeService.findFlowerSizeByProductID(id);
        List<Flower> productBrand = flowerService.findFlowersWithPurpose(product.getPurpose().getPurposeID());
        List<FlowerDTO> productBrandDTOs = productBrand.stream().map(brand -> {
            FlowerDTO dto = new FlowerDTO();
            dto.setFlowerID(brand.getFlowerID());
            dto.setName(brand.getName());
            dto.setDescription(brand.getDescription());
            dto.setImage(brand.getImage());
            dto.setLanguageOfFlowers(brand.getLanguageOfFlowers());
            dto.setCategory(brand.getCategory());
            dto.setPurpose(brand.getPurpose());
            FlowerSize size = flowerSizeService.findCheapestPriceByFlowerID(brand.getFlowerID());
            dto.setPrice(size.getPrice());
            return dto;
        }).toList();

        List<Flower> productSimilar = flowerService.findFlowersSimilar(product.getCategory().getCategoryID());
        List<FlowerDTO> productSimilarDTOs = productSimilar.stream().map(similar -> {
            FlowerDTO dto = new FlowerDTO();
            dto.setFlowerID(similar.getFlowerID());
            dto.setName(similar.getName());
            dto.setDescription(similar.getDescription());
            dto.setImage(similar.getImage());
            dto.setLanguageOfFlowers(similar.getLanguageOfFlowers());
            dto.setCategory(similar.getCategory());
            dto.setPurpose(similar.getPurpose());
            FlowerSize size = flowerSizeService.findCheapestPriceByFlowerID(similar.getFlowerID());
            dto.setPrice(size.getPrice());
            return dto;
        }).toList();

        int howManyBought = flowerService.HowManyBought(id);
        Map<String, Object> response = new HashMap<>();

        if (product != null && product.getStatus() == Status.ENABLE) {
            response.put("product", flowerDTO);
            response.put("reviews", reviewList);
            response.put("productSizes", FlowerSizesList);
            response.put("imageList", imageList);
            response.put("productBrand", productBrandDTOs);
            response.put("productSimilar", productSimilarDTOs);
            response.put("howManyBought", howManyBought);

            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Product not available or disabled.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
    @PostMapping("/addToPrebuy")
    public  ResponseEntity<?> AddToCart (@RequestBody PrebuyDTO prebuyDTO){
        int idAccount = getIDAccountService.common();
        Account account = accountService.getAccountById(idAccount);
        FlowerSize FlowerSize =  flowerSizeService.findFlowerSizeByID(prebuyDTO.getProductSizeID());

        try {
            Cart cart = new Cart();
            cart.setFlowerSize(FlowerSize);
            cart.setQuantity(prebuyDTO.getNumber());
            cart.setAccountID(account);
            cart.setStatus(Status.ENABLE);

            Cart createCart = cartService.createCart(cart);
            return ResponseEntity.status(HttpStatus.CREATED).body(createCart);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the cart.");
        }
    }
    @PostMapping("/addToWishlist")
    public ResponseEntity<?> AddToWishlist(@RequestBody Flower flowerID) {
        int idAccount = getIDAccountService.common();
        Account account = accountService.getAccountById(idAccount);
        try {
            Wishlist findWishlist = wishlistRepository.findWishlistByFlowerFlowerIDAndAccountIDAccountIDAndStatus(flowerID.getFlowerID(), idAccount, Status.ENABLE);
            if (findWishlist == null) {
                Wishlist wishlist = new Wishlist();
                wishlist.setFlower(flowerID);
                wishlist.setAccountID(account);
                wishlist.setStatus(Status.ENABLE);
                wishlistRepository.save(wishlist);
                return ResponseEntity.status(HttpStatus.CREATED).body(wishlist);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This flower is already in your wishlist.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the flower to the wishlist: " + e.getMessage());
        }
    }

}