package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.controller.User.UserPrebuyController;
import org.example.dto.FlowerDTO;
import org.example.dto.FlowerSizeEvent;
import org.example.dto.PrebuyDTO;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.entity.enums.Type;
import org.example.repository.CartRepository;
import org.example.repository.PreOrderRepository;
import org.example.repository.WishlistRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
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
    private final PreOrderRepository preOrderRepository;
    private final UserPrebuyController userPrebuyController;
    private final ICartService cartService;
    private final IAccountService accountService;

    private final GetIDAccountFromAuthService getIDAccountService;
    private final IEventFlowerService eventFlowerService;
    @GetMapping("/detail/{id}")
    public ResponseEntity<?> ProductDetail(@PathVariable("id") int id){
        Flower product = flowerService.findFlowerByIdEnable(id);
        FlowerDTO flowerDTO = new FlowerDTO();
        flowerDTO.setFlowerID(product.getFlowerID());
        flowerDTO.setName(product.getName());
        flowerDTO.setDescription(product.getDescription());
        flowerDTO.setImage(product.getImage());
        flowerDTO.setLanguageOfFlowers(product.getLanguageOfFlowers());
        flowerDTO.setCategory(product.getCategory());
        flowerDTO.setPurpose(product.getPurpose());
        FlowerSize minFlowerSize = flowerSizeService.findCheapestPriceByFlowerID(product.getFlowerID());
        EventFlower eventFlower = eventFlowerService.findEventFlowerByFlowerSizeID(minFlowerSize.getFlowerSizeID());
        if (eventFlower != null && eventFlower.getSaleoff()!=null)
        {
            BigDecimal discountAmount = minFlowerSize.getPrice().multiply(eventFlower.getSaleoff().divide(BigDecimal.valueOf(100)));
            flowerDTO.setPriceEvent(minFlowerSize.getPrice().subtract(discountAmount));
            flowerDTO.setSaleOff(eventFlower.getSaleoff());
        }
        flowerDTO.setPrice(minFlowerSize.getPrice());

        int idAccount = getIDAccountService.common();
        List<Review> reviewList = reviewService.findReviewByProductID (id);
        List<FlowerImages> imageList = flowerImageService.findImagesByProductID(id);
        List<FlowerSize> flowerSizesList = flowerSizeService.findFlowerSizeByProductID(id);
        List<FlowerSizeEvent> flowerSizeEvents = new ArrayList<>();

        for (int i = 0; i < flowerSizesList.size(); i++) {
            FlowerSizeEvent flowerSizeEvent = new FlowerSizeEvent();
            flowerSizeEvent.setFlower(flowerSizesList.get(i).getFlower());
            flowerSizeEvent.setFlowerSizeID(flowerSizesList.get(i).getFlowerSizeID());
            flowerSizeEvent.setSizeName(flowerSizesList.get(i).getSizeName());
            flowerSizeEvent.setHigh(flowerSizesList.get(i).getHigh());
            flowerSizeEvent.setLength(flowerSizesList.get(i).getLength());
            flowerSizeEvent.setWeight(flowerSizesList.get(i).getWeight());
            flowerSizeEvent.setWidth(flowerSizesList.get(i).getWidth());
            flowerSizeEvent.setStock(flowerSizesList.get(i).getStock());
            flowerSizeEvent.setPreorderable(flowerSizesList.get(i).getPreorderable());
            flowerSizeEvent.setStatus(flowerSizesList.get(i).getStatus());
            flowerSizeEvent.setPrice(flowerSizesList.get(i).getPrice());
            EventFlower flowerSizeEventPrice = eventFlowerService.findEventFlowerByFlowerSizeID(flowerSizesList.get(i).getFlowerSizeID());
            if (flowerSizeEventPrice != null && flowerSizeEventPrice.getSaleoff()!=null)
            {
                BigDecimal discountAmount = flowerSizesList.get(i).getPrice().multiply(flowerSizeEventPrice.getSaleoff().divide(BigDecimal.valueOf(100)));
                flowerSizeEvent.setPriceEvent(flowerSizesList.get(i).getPrice().subtract(discountAmount));
                flowerSizeEvent.setSaleOff(flowerSizeEventPrice.getSaleoff());
            }
            flowerSizeEvents.add(flowerSizeEvent);
        }

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
            EventFlower productBrandDTOsEventFlower = eventFlowerService.findEventFlowerByFlowerSizeID(size.getFlowerSizeID());
            if (productBrandDTOsEventFlower != null && productBrandDTOsEventFlower.getSaleoff()!=null)
            {
                BigDecimal discountAmount = size.getPrice().multiply(productBrandDTOsEventFlower.getSaleoff().divide(BigDecimal.valueOf(100)));
                dto.setPriceEvent(size.getPrice().subtract(discountAmount));
                dto.setSaleOff(productBrandDTOsEventFlower.getSaleoff());
            }
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
            EventFlower productBrandDTOsEventFlower = eventFlowerService.findEventFlowerByFlowerSizeID(size.getFlowerSizeID());
            if (productBrandDTOsEventFlower != null && productBrandDTOsEventFlower.getSaleoff()!=null)
            {
                BigDecimal discountAmount = size.getPrice().multiply(productBrandDTOsEventFlower.getSaleoff().divide(BigDecimal.valueOf(100)));
                dto.setPriceEvent(size.getPrice().subtract(discountAmount));
                dto.setSaleOff(productBrandDTOsEventFlower.getSaleoff());
            }
            dto.setPrice(size.getPrice());
            return dto;
        }).toList();

        int howManyBought = flowerService.HowManyBought(id);
        Map<String, Object> response = new HashMap<>();
        if (idAccount > 0) {
            Wishlist wishlist = wishlistRepository.findWishlistByFlowerFlowerIDAndAccountIDAccountIDAndStatus(id, idAccount, Status.ENABLE);
            if (wishlist != null) {
                response.put("wishlist", wishlist);
            }
        }

        if (product.getStatus() == Status.ENABLE) {
            response.put("product", flowerDTO);
            response.put("reviews", reviewList);
            response.put("productSizes", flowerSizeEvents);
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
            cart.setType(Type.Order);
            Cart createCart = cartService.createCart(cart);
            userPrebuyController.notifyCartUpdate(idAccount, userPrebuyController.cartCount(idAccount));
            return ResponseEntity.status(HttpStatus.CREATED).body(createCart);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the cart.");
        }
    }
    @PostMapping("/addToWishlist")
    public ResponseEntity<?> addToWishlist(@RequestBody Flower flowerID) {
        int idAccount = getIDAccountService.common();
        Account account = accountService.getAccountById(idAccount);

        if (account == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account not found.");
        }

        try {
            Wishlist existingWishlist = wishlistRepository.findWishlistByFlowerFlowerIDAndAccountIDAccountIDAndStatus(
                    flowerID.getFlowerID(), idAccount, Status.ENABLE);

            if (existingWishlist == null) {
                Wishlist wishlist = new Wishlist();
                wishlist.setFlower(flowerID);
                wishlist.setAccountID(account);
                wishlist.setStatus(Status.ENABLE);
                wishlistRepository.save(wishlist);

                return ResponseEntity.status(HttpStatus.CREATED).body(wishlist);
            } else {
                wishlistRepository.deleteById(existingWishlist.getWishListID());
                return ResponseEntity.status(HttpStatus.CONFLICT).body("This flower has been removed from your wishlist.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while managing the wishlist: " + e.getMessage());
        }
    }
    @PostMapping("/addPreorder")
    public  ResponseEntity<?> Preorder (@RequestBody PrebuyDTO prebuyDTO){
        int idAccount = getIDAccountService.common();
        Account account = accountService.getAccountById(idAccount);
        FlowerSize FlowerSize =  flowerSizeService.findFlowerSizeByID(prebuyDTO.getProductSizeID());

        try {
            Cart cart = new Cart();
            cart.setFlowerSize(FlowerSize);
            cart.setQuantity(prebuyDTO.getNumber());
            cart.setAccountID(account);
            cart.setStatus(Status.ENABLE);
            cart.setType(Type.Order);
            cart.setType(Type.Preorder);
            Cart createCart = cartService.createCart(cart);
            userPrebuyController.notifyCartUpdate(idAccount, userPrebuyController.cartCount(idAccount));
            return ResponseEntity.status(HttpStatus.CREATED).body(createCart);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the cart.");
        }
    }

}