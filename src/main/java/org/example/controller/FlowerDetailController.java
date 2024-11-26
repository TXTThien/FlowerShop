package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.FlowerDTO;
import org.example.dto.PrebuyDTO;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.CartRepository;
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

@RestController
@RequestMapping("")
@RequiredArgsConstructor
public class FlowerDetailController {
    private final IFlowerService flowerService;
    private final IReviewService reviewService;
    private final IFlowerSizeService flowerSizeService;
    private final IFlowerImageService flowerImageService;

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
        List<FlowerSize> productSizesList = flowerSizeService.findProductSizeByProductID(id);
        List<Flower> productBrand = flowerService.findFlowersWithPurpose(product.getPurpose().getPurposeID());
        List<Flower> productSimilar = flowerService.findFlowersSimilar(product.getCategory().getCategoryID());
        int howManyBought = flowerService.HowManyBought(id);
        Map<String, Object> response = new HashMap<>();

        if (product != null && product.getStatus() == Status.ENABLE) {
            response.put("product", flowerDTO);
            response.put("reviews", reviewList);
            response.put("productSizes", productSizesList);
            response.put("imageList", imageList);
            response.put("productBrand", productBrand);
            response.put("productSimilar", productSimilar);
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
        FlowerSize productSize =  flowerSizeService.findProductSizeByID(prebuyDTO.getProductSizeID());

        try {
            Cart cart = new Cart();
            cart.setFlowerSize(productSize);
            cart.setQuantity(prebuyDTO.getNumber());
            cart.setAccountID(account);
            cart.setStatus(Status.ENABLE);

            Cart createCart = cartService.createCart(cart);
            return ResponseEntity.status(HttpStatus.CREATED).body(createCart);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the cart.");
        }
    }

}