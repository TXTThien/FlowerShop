package org.example.repository;

import org.example.entity.BlogComment;
import org.example.entity.BlogFlower;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogFlowerRepository extends JpaRepository<BlogFlower, Integer> {
}
