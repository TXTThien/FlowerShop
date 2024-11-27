package org.example.repository;

import org.example.entity.FlowerSize;
import org.example.entity.enums.Status;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface FlowerSizeRepository  extends JpaRepository<FlowerSize, Integer> {
    List<FlowerSize> findFlowerSizesByFlowerFlowerIDAndStatus(int id, Status status);
    FlowerSize findFlowerSizeByFlowerSizeIDAndStatus(int id, Status status);
    @Transactional
    @Modifying
    @Query("UPDATE FlowerSize ps SET ps.stock = ps.stock- :number WHERE ps.flowerSizeID = :pz")
    void UpdateStock(@Param("pz") int pz, @Param("number") int number);

    @Query("SELECT fs FROM FlowerSize fs " +
            "WHERE fs.flower.flowerID = :flowerID " +
            "ORDER BY fs.price ASC")
    List<FlowerSize> findLowestPriceByFlowerID(@Param("flowerID") Integer flowerID, Pageable pageable);

    FlowerSize findFlowerSizeByFlowerFlowerIDAndSizeNameAndStatus(int id, String size, Status status);

}
