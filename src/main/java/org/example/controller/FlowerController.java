package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.entity.Category;
import org.example.entity.Flower;
import org.example.entity.Purpose;
import org.example.service.ICategoryService;
import org.example.service.IFlowerService;
import org.example.service.IPurposeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

                if (sortProduct.isEmpty()) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No sorted products found");
                }
                return ResponseEntity.ok(sortProduct);
            }
            List<Flower> productList = flowerService.findAllEnable();
            List<Category> categoryList = categoryService.findAllEnable();
            List<Purpose> purposeList = purposeService.findAllEnable();


            Map<String, Object> response = new HashMap<>();
            if (!productList.isEmpty()) response.put("flowers", productList);
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

    @RequestMapping("/sort")
    public ResponseEntity<?> sortListProduct(
            @RequestParam(value = "category", required = false) Integer category,
            @RequestParam(value = "purpose", required = false) Integer purpose
           ) {

        try {
            List<Flower> productList = flowerService.sortFlower(
                    category != null ? category : 0,
                    purpose != null ? purpose : 0
            );

            if (productList.isEmpty()) {
                return ResponseEntity.noContent().build();
            }
            return ResponseEntity.ok(productList);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while sorting the products: " + e.getMessage());
        }
    }
}
