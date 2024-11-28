package org.example.controller.User;

import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.entity.Review;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.repository.ReviewRepository;
import org.example.service.IReviewService;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/review")
@RequiredArgsConstructor
public class UserReviewController {
    private final IReviewService reviewService;
    private final AccountRepository accountRepository;
    private final ReviewRepository reviewRepository;

    private final GetIDAccountFromAuthService getIDAccountService;

    @PostMapping("")
    public ResponseEntity<Review> makeReview(@RequestBody Review review) {
        int idAccount = getIDAccountService.common();
        Account account = accountRepository.findAccountByAccountID(idAccount);
        int idProduct = review.getFlower().getFlowerID();
        review.setStatus(Status.ENABLE);
        review.setAccountID(account);
        Review existingReview = reviewService.findReviewByAccountIDAndProduct(idAccount, idProduct);


        if (review.getDate() == null)
        {
            review.setDate(LocalDateTime.now());
        }
        if (existingReview == null) {
            Review newReview = reviewRepository.save(review);
            return ResponseEntity.status(HttpStatus.CREATED).body(newReview);
        }
        else  {
            existingReview.setRating(review.getRating());
            if (review.getImage() != null)
            {
                existingReview.setImage(review.getImage());
            }
            existingReview.setComment(review.getComment());
            existingReview.setDate(LocalDateTime.now());
            Review updatedReview = reviewRepository.save(existingReview);
            return ResponseEntity.ok(updatedReview);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteReview(@PathVariable("id") int id) {
        Review review = reviewRepository.findById(id).orElse(null);
        if (review != null) {
            reviewService.hardDeleteReview(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}