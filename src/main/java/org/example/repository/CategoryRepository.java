package org.example.repository;

import org.example.entity.Category;
import org.example.entity.Purpose;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    List<Category> findAllByStatus(Status status);
    Category findCategoryByCategoryID(int id);
}
