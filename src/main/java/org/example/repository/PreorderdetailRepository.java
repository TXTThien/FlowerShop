package org.example.repository;

import org.example.entity.FlowerSize;
import org.example.entity.Preorder;
import org.example.entity.Preorderdetail;
import org.example.entity.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreorderdetailRepository extends JpaRepository<Preorderdetail, Integer> {
    List<Preorderdetail> findPreorderdetailsByPreorderIDAndStatus(Preorder preorder, Status status);
    @Query("SELECT DISTINCT p.flowerSize FROM Preorderdetail p")
    List<FlowerSize> findDistinctByFlowerSize();
    @Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Preorderdetail p WHERE p.flowerSize = :flowerSize")
    Integer calculateTotalQuantityByFlowerSize(@Param("flowerSize") FlowerSize flowerSize);

}
