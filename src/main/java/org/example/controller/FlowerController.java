package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.FlowerDTO;
import org.example.entity.*;
import org.example.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/flower")
@RequiredArgsConstructor
public class FlowerController {
    private final IFlowerService flowerService;
    private final ICategoryService categoryService;
    private final IPurposeService purposeService;
    private final IFlowerSizeService flowerSizeService;
    private final IEventFlowerService eventFlowerService;
    @GetMapping("")
    public ResponseEntity<?> getListProduct(
            @RequestParam(value = "category", required = false) Integer category,
            @RequestParam(value = "purpose", required = false) Integer purpose)
    {
        try {
            if (category != null || purpose != null ) {
                List<Flower> sortProduct = flowerService.sortFlower(
                        category != null ? category : 0,
                        purpose != null ? purpose : 0
                );
                List<FlowerDTO> flowerDTOList = new ArrayList<>();
                for (Flower flower : sortProduct) {
                    FlowerDTO flowerDTO = new FlowerDTO();
                    flowerDTO.setFlowerID(flower.getFlowerID());
                    flowerDTO.setName(flower.getName());
                    flowerDTO.setDescription(flower.getDescription());
                    flowerDTO.setImage(flower.getImage());
                    flowerDTO.setLanguageOfFlowers(flower.getLanguageOfFlowers());
                    flowerDTO.setCategory(flower.getCategory());
                    flowerDTO.setPurpose(flower.getPurpose());
                    FlowerSize minFlowerSize = flowerSizeService.findCheapestPriceByFlowerID(flower.getFlowerID());
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

                    flowerDTOList.add(flowerDTO);

                }
                if (sortProduct.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sorted products found");
                }
                return ResponseEntity.ok(flowerDTOList);
            }
            List<Flower> productList = flowerService.findAllEnable();
            List<Category> categoryList = categoryService.findAllEnable();
            List<Purpose> purposeList = purposeService.findAllEnable();
            List<FlowerDTO> flowerDTOList = new ArrayList<>();

            for (Flower flower : productList) {
                FlowerDTO flowerDTO = new FlowerDTO();
                flowerDTO.setFlowerID(flower.getFlowerID());
                flowerDTO.setName(flower.getName());
                flowerDTO.setDescription(flower.getDescription());
                flowerDTO.setImage(flower.getImage());
                flowerDTO.setLanguageOfFlowers(flower.getLanguageOfFlowers());
                flowerDTO.setCategory(flower.getCategory());
                flowerDTO.setPurpose(flower.getPurpose());
                FlowerSize minFlowerSize = flowerSizeService.findCheapestPriceByFlowerID(flower.getFlowerID());
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

                flowerDTOList.add(flowerDTO);


            }
            Map<String, Object> response = new HashMap<>();
            if (!productList.isEmpty()) response.put("flowers", flowerDTOList);
            if (!categoryList.isEmpty()) response.put("category", categoryList);
            if (!purposeList.isEmpty()) response.put("purpose", purposeList);


            if (!response.isEmpty()) {
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }
}
