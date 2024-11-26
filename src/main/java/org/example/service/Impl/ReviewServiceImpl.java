package org.example.service.Impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.*;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.FlowerRepository;
import org.example.repository.ReviewRepository;
import org.example.service.IReviewService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements IReviewService {
    private final ReviewRepository reviewRepository;
    private final FlowerRepository flowerRepository;
    private final AccountRepository accountRepository;

    @Override
    public void deleteReview(int id) {
        Review review = reviewRepository.findById(id).orElse(null);
        assert review != null;
        review.setStatus(Status.DISABLE);
        reviewRepository.save(review);
    }

    @Override
    public Review updateReview(int id, Review review) {
        Review existingReview = reviewRepository.findById(id).orElse(null);
        if (existingReview != null) {
            existingReview.setComment(review.getComment());
            existingReview.setRating(review.getRating());
            existingReview.setStatus(review.getStatus());
            existingReview.setDate(review.getDate());
            existingReview.setImage(review.getImage());

            if (review.getFlower() != null && review.getFlower().getFlowerID() != null) {
                Flower product = flowerRepository.findById(review.getFlower().getFlowerID()).orElse(null);
                if (product != null) {
                    existingReview.setFlower(product);
                } else {
                    throw new EntityNotFoundException("Product not found with ID: " + review.getFlower().getFlowerID());
                }
            }

            if (review.getAccountID() != null && review.getAccountID().getAccountID() != null) {
                Account account = accountRepository.findById(review.getAccountID().getAccountID()).orElse(null);
                if (account != null) {
                    existingReview.setAccountID(account);
                } else {
                    throw new EntityNotFoundException("Account not found with ID: " + review.getAccountID().getAccountID());
                }
            }

            return reviewRepository.save(existingReview);
        }
        return null;
    }

    @Override
    public List<Review> findReviewByAccountID(int id, Status status) {
        return reviewRepository.findReviewsByAccountID_AccountIDAndStatus(id,status);
    }

    @Override
    public Review findReviewByAccountIDAndProduct(int id, int product) {
        return reviewRepository.findReviewsByAccountIDAccountIDAndFlowerFlowerIDAndStatusOrderByReviewIDDesc(id, product, Status.ENABLE);
    }

    @Override
    public List<Review> findReviewByProductID(int id) {
        return reviewRepository.findReviewsByFlowerFlowerIDAndStatusOrderByReviewIDDesc(id, Status.ENABLE);
    }

    @Override
    @Transactional
    public void hardDeleteReview(int id) {
        reviewRepository.deleteReviewByReviewID(id);
    }
}