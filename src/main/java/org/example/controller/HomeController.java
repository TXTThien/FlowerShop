package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.EventDTO;
import org.example.dto.FlowerDTO;
import org.example.dto.ProductDTO;
import org.example.entity.*;
import org.example.entity.enums.Role;
import org.example.entity.enums.Status;
import org.example.repository.CategoryRepository;
import org.example.service.*;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
public class HomeController {
    private final IFlowerService flowerService;
    private final IBannerService bannerService;
    private final INewsService newsService;
    private final IFlowerSizeService flowerSizeService;
    private final GetIDAccountFromAuthService getIDAccountService;
    private final IAccountService accountService;
    private final CategoryRepository categoryRepository;
    private final IEventFlowerService eventFlowerService;
    private final IEventService eventService;
    @RequestMapping("/info")
    public ResponseEntity<?> info(@RequestHeader(value = "Account-ID",required = false) Integer  accountId) {
        if (accountId == null) {
            accountId = -1;
        }
        Account account = accountService.getAccountById(accountId);
        System.out.println("idAccount:" + accountId);
        Map<String, String> response = new HashMap<>();
        if (accountId != -1 && account != null){
            if (account.getRole() == Role.user){
                response.put("redirectUrl", "http://localhost:8000/account");
            }
            else if (account.getRole() == Role.admin){
                response.put("redirectUrl", "http://localhost:8000/dashboard");
            }
            else if (account.getRole() == Role.shipper){
                response.put("redirectUrl", "http://localhost:8000/shipperaccount");
            }
            else if (account.getRole() == Role.staff){
                response.put("redirectUrl", "http://localhost:8000/staffaccount");
            }
        }
        else {
            response.put("redirectUrl", "http://localhost:8000/login");
        }


        return ResponseEntity.ok(response);
    }
    @RequestMapping("/order")
    public ResponseEntity<?> orderdelivery(@RequestHeader(value = "Account-ID",required = false) Integer  accountId) {
        if (accountId == null) {
            accountId = -1;
        }
        Account account = accountService.getAccountById(accountId);
        System.out.println("idAccount:" + accountId);
        Map<String, String> response = new HashMap<>();
        if (accountId != -1 && account != null){
            if (account.getRole() == Role.user){
                response.put("redirectUrl", "http://localhost:8000/orderdelivery");
            }
            else if (account.getRole() == Role.admin){
                response.put("redirectUrl", "http://localhost:8000/dashboard");
            }
            else if (account.getRole() == Role.shipper){
                response.put("redirectUrl", "http://localhost:8000/orderdelivery");
            }
            else if (account.getRole() == Role.staff){
                response.put("redirectUrl", "http://localhost:8000/orderdelivery");
            }
        }
        else {
            response.put("redirectUrl", "http://localhost:8000/login");
        }


        return ResponseEntity.ok(response);
    }
    @RequestMapping("/cart")
    public ResponseEntity<?> cart(@RequestHeader(value = "Account-ID",required = false) Integer  accountId) {
        if (accountId == null) {
            accountId = -1;
        }
        Account account = accountService.getAccountById(accountId);
        Map<String, String> response = new HashMap<>();
        if (accountId != -1 && account != null){
            if (account.getRole() == Role.user){
                response.put("redirectUrl", "http://localhost:8000/prebuy");
            }
            else if (account.getRole() == Role.admin){
                response.put("redirectUrl", "http://localhost:8000/dashboard");
            }
            else if (account.getRole() == Role.shipper){
                response.put("redirectUrl", "http://localhost:8000/shipperaccount");
            }
            else if (account.getRole() == Role.staff){
                response.put("redirectUrl", "http://localhost:8000/staff");
            }
        }
        else {
            response.put("redirectUrl", "http://localhost:8000/login");
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping({"/", "/home","/j4m"})
    public ResponseEntity<String> homePage() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "http://localhost:8000/")
                .build();
    }
    @GetMapping("/homepage")
    public ResponseEntity<?> getBannerData() {
        Map<String, Object> response = new HashMap<>();
        List<Banner> bannerList = bannerService.find4BannerEnable();
        List<News> newsList = newsService.find4NewsEnable();
        List<ProductDTO> productList = flowerService.find10HotestProductEnable();
        List<EventDTO> eventDTOS = new ArrayList<>();
        List<Event> events = eventService.findEventEnable();
        for (ProductDTO productDTO:productList)
        {
            FlowerSize minFlowerSize = flowerSizeService.findCheapestPriceByFlowerID(productDTO.getProductID());
            List<EventFlower> eventFlower = eventFlowerService.findEventFlowersByFlowerID(minFlowerSize.getFlower().getFlowerID());
            BigDecimal minPrice = BigDecimal.ZERO;
            BigDecimal saleOff = BigDecimal.ZERO;
            BigDecimal price = BigDecimal.ZERO;
            if (!eventFlower.isEmpty())
            {
                for (EventFlower eventFlower1 : eventFlower)
                {
                    BigDecimal eventPrice = eventFlower1.getFlowerSize().getPrice().subtract(eventFlower1.getFlowerSize().getPrice().multiply(eventFlower1.getSaleoff().divide(BigDecimal.valueOf(100))));
                    if (minPrice.equals(BigDecimal.ZERO) || minPrice.compareTo(eventPrice) > 0)
                    {
                        minPrice = eventPrice;
                        saleOff = eventFlower1.getSaleoff();
                        price = eventFlower1.getFlowerSize().getPrice();
                    }
                }
            }
            if (!minPrice.equals(BigDecimal.ZERO))
            {
                productDTO.setPriceEvent(minPrice);
                productDTO.setSaleOff(saleOff);
                productDTO.setPrice(price);
            }

        }


        for (Event event : events) {
            EventDTO eventDTO = new EventDTO();
            eventDTO.setEvent(event);
            List<ProductDTO> productDTOS = new ArrayList<>();
            List<EventFlower> eventFlowers = eventFlowerService.findEventFlowerByEventID(event.getId());

            Map<Integer, List<EventFlower>> flowerMap = new HashMap<>();

            // Nhóm EventFlower theo FlowerID
            for (EventFlower eventFlower : eventFlowers) {
                FlowerSize flowerSize = eventFlower.getFlowerSize();
                if (flowerSize == null || flowerSize.getFlower() == null) continue;

                Integer flowerId = flowerSize.getFlower().getFlowerID();
                flowerMap.computeIfAbsent(flowerId, k -> new ArrayList<>()).add(eventFlower);
            }

            // Xử lý từng nhóm FlowerID
            for (Map.Entry<Integer, List<EventFlower>> entry : flowerMap.entrySet()) {
                List<EventFlower> flowerEvents = entry.getValue();
                if (flowerEvents.isEmpty()) continue;

                // Chọn EventFlower có FlowerSize có giá thấp nhất
                EventFlower selectedEventFlower = flowerEvents.stream()
                        .min(Comparator.comparing(ef -> ef.getFlowerSize().getPrice()))
                        .orElse(null);

                FlowerSize selectedFlowerSize = selectedEventFlower.getFlowerSize();
                BigDecimal saleOff = selectedEventFlower.getSaleoff() != null ? selectedEventFlower.getSaleoff() : BigDecimal.ZERO;
                BigDecimal price = selectedFlowerSize.getPrice() != null ? selectedFlowerSize.getPrice() : BigDecimal.ZERO;

                // Tính giá sau khi giảm giá
                BigDecimal priceEvent = price;
                if (saleOff.compareTo(BigDecimal.ZERO) > 0) {
                    priceEvent = price.subtract(price.multiply(saleOff.divide(BigDecimal.valueOf(100))));
                }

                // Lấy số lượng đã bán (chỉ gọi một lần)
                Integer sold = flowerService.HowManyBought(selectedFlowerSize.getFlower().getFlowerID());

                // Tạo ProductDTO
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductID(selectedFlowerSize.getFlower().getFlowerID());
                productDTO.setFlowerSizeID(selectedFlowerSize.getFlowerSizeID());
                productDTO.setAvatar(selectedFlowerSize.getFlower().getImage());
                productDTO.setTitle(selectedFlowerSize.getFlower().getName());
                productDTO.setSold(sold != null ? sold : 0);
                productDTO.setPrice(price);
                productDTO.setPriceEvent(priceEvent);
                productDTO.setSaleOff(saleOff);

                productDTOS.add(productDTO);
            }

            eventDTO.setFlower(productDTOS);
            eventDTOS.add(eventDTO);
        }

        for (int i = 0 ; i<productList.size();i++)
        {
            EventFlower  eventFlower = eventFlowerService.findEventFlowerByFlowerSizeID(productList.get(i).getFlowerSizeID());
            if (eventFlower != null && eventFlower.getSaleoff() != null)
            {
                BigDecimal discountAmount = productList.get(i).getPrice().multiply(eventFlower.getSaleoff().divide(BigDecimal.valueOf(100)));
                productList.get(i).setPriceEvent(productList.get(i).getPrice().subtract(discountAmount));
                productList.get(i).setSaleOff(eventFlower.getSaleoff());
            }
        }
        List<Category> categories = categoryRepository.findAllByStatus(Status.ENABLE);
        if (bannerList != null) {
            response.put("bannerList", bannerList);
        }
        if (newsList != null) {
            response.put("newsList", newsList);
        }
        response.put("productList", productList);
        response.put("eventFlower", eventDTOS);

        if (categories != null) {
            response.put("categories", categories);
        }

        return !response.isEmpty() ?
                ResponseEntity.ok(response) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }
    @RequestMapping(value = "/search",  method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<List<FlowerDTO>> findProductOrBrand(@RequestParam("searchTerm") String search, Model model) {
        String searchTerm = search.trim();
        List<Flower>  searchProduct = flowerService.findByCategory(searchTerm, Status.ENABLE);
        if (searchProduct.isEmpty()) {
            searchProduct = flowerService.findByPurpose(searchTerm, Status.ENABLE);
            if (searchProduct.isEmpty()) {
                searchProduct = flowerService.findByTitle(searchTerm, Status.ENABLE);
            }
        }
        List<FlowerDTO> productBrandDTOs = searchProduct.stream().map(brand -> {
            FlowerDTO flowerDTO = new FlowerDTO();
            flowerDTO.setFlowerID(brand.getFlowerID());
            flowerDTO.setName(brand.getName());
            flowerDTO.setDescription(brand.getDescription());
            flowerDTO.setImage(brand.getImage());
            flowerDTO.setLanguageOfFlowers(brand.getLanguageOfFlowers());
            flowerDTO.setCategory(brand.getCategory());
            flowerDTO.setPurpose(brand.getPurpose());
            FlowerSize minFlowerSize = flowerSizeService.findCheapestPriceByFlowerID(brand.getFlowerID());
            List<EventFlower> eventFlower = eventFlowerService.findEventFlowersByFlowerID(minFlowerSize.getFlower().getFlowerID());
            BigDecimal minPrice = BigDecimal.ZERO;
            BigDecimal saleOff = BigDecimal.ZERO;
            BigDecimal price = BigDecimal.ZERO;
            if (!eventFlower.isEmpty())
            {
                for (EventFlower eventFlower1 : eventFlower)
                {
                    BigDecimal eventPrice = eventFlower1.getFlowerSize().getPrice().subtract(eventFlower1.getFlowerSize().getPrice().multiply(eventFlower1.getSaleoff().divide(BigDecimal.valueOf(100))));
                    if (minPrice.equals(BigDecimal.ZERO) || minPrice.compareTo(eventPrice) > 0)
                    {
                        minPrice = eventPrice;
                        saleOff = eventFlower1.getSaleoff();
                        price = eventFlower1.getFlowerSize().getPrice();
                    }
                }
            }


            if (!minPrice.equals(BigDecimal.ZERO))
            {
                flowerDTO.setPriceEvent(minPrice);
                flowerDTO.setSaleOff(saleOff);
                flowerDTO.setPrice(price);
            }
            else
            {
                flowerDTO.setPrice(minFlowerSize.getPrice());
            }

            return flowerDTO;
        }).toList();
        if (searchProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(productBrandDTOs);
    }



}