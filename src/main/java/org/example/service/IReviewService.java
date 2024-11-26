package org.example.service;

import org.example.entity.Review;
import org.example.entity.enums.Status;

import java.util.List;

public interface IReviewService {
    void deleteReview(int id);

    Review updateReview(int id, Review review);
    List<Review> findReviewByAccountID(int id, Status status);

    Review findReviewByAccountIDAndProduct (int id, int product);

    List<Review> findReviewByProductID(int id);

    void hardDeleteReview(int id);
}
