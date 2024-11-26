package org.example.repository;

import org.example.entity.Account;
import org.example.entity.Review;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {

    List<Review> findReviewsByAccountID_AccountIDAndStatus(int id, Status status);

    Review findReviewsByAccountIDAccountIDAndFlowerFlowerIDAndStatusOrderByReviewIDDesc(int id, int flower, Status status);

    List<Review> findReviewsByFlowerFlowerIDAndStatusOrderByReviewIDDesc(int id, Status status);

    void deleteReviewByReviewID(int id);

}