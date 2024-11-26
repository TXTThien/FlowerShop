package org.example.controller;

import lombok.RequiredArgsConstructor;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
public class HomeController {
    private final IFlowerService flowerService;
    private final IBannerService bannerService;
    private final INewsService newsService;
    private final GetIDAccountFromAuthService getIDAccountService;
    private final IAccountService accountService;
    private final CategoryRepository categoryRepository;
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
                response.put("redirectUrl", "http://localhost:8000/shipper");
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
                response.put("redirectUrl", "http://localhost:8000/shipper");
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
        List<Category> categories = categoryRepository.findAllByStatus(Status.ENABLE);
        if (bannerList != null) {
            response.put("bannerList", bannerList);
        }
        if (newsList != null) {
            response.put("newsList", newsList);
        }
        if (productList != null) {
            response.put("productList", productList);
        }
        if (categories != null) {
            response.put("categories", categories);
        }

        return !response.isEmpty() ?
                ResponseEntity.ok(response) :
                ResponseEntity.status(HttpStatus.NOT_FOUND).body("Not found");
    }
    @RequestMapping(value = "/search",  method = {RequestMethod.GET, RequestMethod.POST})
    public ResponseEntity<List<Flower>> findProductOrBrand(@RequestParam("searchTerm") String search, Model model) {
        String searchTerm = search.trim();
        List<Flower>  searchProduct = flowerService.findByCategory(searchTerm, Status.ENABLE);
            if (searchProduct.isEmpty()) {
                searchProduct = flowerService.findByPurpose(searchTerm, Status.ENABLE);
                if (searchProduct.isEmpty()) {
                    searchProduct = flowerService.findByTitle(searchTerm, Status.ENABLE);
                }
            }
        if (searchProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return ResponseEntity.ok(searchProduct);
    }



}