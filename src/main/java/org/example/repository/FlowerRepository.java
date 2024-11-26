package org.example.repository;

import org.springframework.stereotype.Repository;
import org.example.dto.ProductDTO;
import org.example.entity.Flower;
import org.example.entity.enums.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FlowerRepository extends JpaRepository<Flower, Integer>{
    List<Flower> findFlowersByStatusOrderByFlowerIDDesc(Status status);

    List<Flower> findFlowersByNameContainingAndStatusOrderByFlowerIDDesc(String title , Status status);
    List<Flower> findFlowersByPurposePurposeIDAndStatusOrderByFlowerIDDesc(int id,Pageable pageable,Status status);

    List<Flower>findFlowersByCategoryCategoryIDAndStatusOrderByFlowerIDDesc(int id, Pageable pageable, Status status);

    List<Flower> findFlowersByCategoryCategoryNameAndStatusOrderByFlowerIDDesc(String Category , Status status);
    List<Flower> findFlowersByPurposePurposeNameAndStatusOrderByFlowerIDDesc(String purpose , Status status);

    List<Flower>findFlowersByCategoryCategoryIDAndPurposePurposeIDAndStatusOrderByFlowerIDDesc(int category, int purpose, Status status);

    @Query("SELECT new org.example.dto.ProductDTO(f.flowerID, f.image, f.name, SUM(bi.quantity), MIN(fs.price)) " +
            "FROM Flower f " +
            "JOIN FlowerSize fs ON fs.flower = f " +
            "JOIN OrderDetail bi ON bi.flowerSize.flowerSizeID = fs.flowerSizeID " +
            "WHERE bi.status = 'ENABLE' " +
            "GROUP BY f.flowerID " +
            "ORDER BY SUM(bi.quantity) DESC")
    List<ProductDTO> findTop10BestSellingProducts(Pageable pageable);


    @Query("SELECT COALESCE(SUM(bi.quantity), 0) " +
            "FROM Flower p " +
            "JOIN FlowerSize ps ON ps.flower = p " +
            "JOIN OrderDetail bi ON bi.flowerSize = ps " +
            "WHERE bi.status = 'ENABLE' AND p.flowerID = :id")
    int HowManyBought(@Param("id") int id);


}
