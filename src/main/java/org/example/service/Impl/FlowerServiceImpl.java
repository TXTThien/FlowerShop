package org.example.service.Impl;

import lombok.RequiredArgsConstructor;
import org.example.repository.CategoryRepository;
import org.example.repository.PurposeRepository;
import org.example.service.IFlowerService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import org.example.dto.ProductDTO;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.FlowerRepository;
import org.example.service.IFlowerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FlowerServiceImpl implements IFlowerService {
    private final FlowerRepository flowerRepository;
    private final PurposeRepository purposeRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Flower> findAll() {
        return flowerRepository.findAll();
    }

    @Override
    public List<Flower> findAllEnable() {
        return flowerRepository.findFlowersByStatusOrderByFlowerIDDesc(Status.ENABLE);
    }

    @Override
    public Flower updateProduct(Integer id, Flower newProduct) {
        Flower product = flowerRepository.findById(id).orElse(null);
        if (product != null) {
            product.setImage(newProduct.getImage());
            product.setName(newProduct.getName());
            product.setDescription(newProduct.getDescription());
            product.setStatus(newProduct.getStatus());
            product.setLanguageOfFlowers(newProduct.getLanguageOfFlowers());
            if (newProduct.getPurpose() != null && newProduct.getPurpose().getPurposeID() != null) {
                Purpose purpose = purposeRepository.findById(newProduct.getPurpose().getPurposeID()).orElse(null);
                product.setPurpose(purpose);
            }
            if (newProduct.getCategory() != null && newProduct.getCategory().getCategoryID() != null) {
                Category category = categoryRepository.findById(newProduct.getCategory().getCategoryID()).orElse(null);
                product.setCategory(category);
            }

            return flowerRepository.save(product);
        }
        return null;
    }

    @Override
    public void deleteProduct(Integer id) {
        Flower product = flowerRepository.findById(id).orElse(null);
        if (product != null) {
            product.setStatus(Status.DISABLE);
            flowerRepository.save(product);
        }
    }

    @Override
    public Flower getProductById(Integer id) {
        return flowerRepository.findById(id).orElse(null);
    }


    @Override
    public List<Flower> findByCategory(String category, Status status) {
        return flowerRepository.findFlowersByCategoryCategoryNameAndStatusOrderByFlowerIDDesc(category, status);
    }



    @Override

    public List<Flower> findByTitle(String title, Status status) {
        return flowerRepository.findFlowersByNameContainingAndStatusOrderByFlowerIDDesc(title, status);
    }

    @Override
    public List<ProductDTO> find10HotestProductEnable() {
        Pageable topTen = PageRequest.of(0, 10);
        System.out.println("Top10Bug");
        List<ProductDTO> top10BestSellingProducts = flowerRepository.findTop10BestSellingProducts(topTen);
        return top10BestSellingProducts;
    }

    @Override
    public List<Flower> findFlowersWithPurpose(Integer purposeid) {
        Pageable topTen = PageRequest.of(0, 8);
        return flowerRepository.findFlowersByPurposePurposeIDAndStatusOrderByFlowerIDDesc(purposeid,topTen,Status.ENABLE);
    }

    @Override
    public List<Flower> findFlowersSimilar(Integer categoryid) {
        Pageable topTen = PageRequest.of(0, 20);
        return flowerRepository.findFlowersByCategoryCategoryIDAndStatusOrderByFlowerIDDesc(categoryid,topTen,Status.ENABLE);
    }

    @Override
    public Integer HowManyBought(int id) {
        System.out.println("HowManyBoughtBug");
        return flowerRepository.HowManyBought(id);
    }

    @Override
    public List<Flower> findByPurpose(String purpose, Status status) {
        return flowerRepository.findFlowersByPurposePurposeNameAndStatusOrderByFlowerIDDesc(purpose,status);
    }
}
