package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties("id")
@Entity(name = "Preorderdetail")
@Table(name = "preorderdetail", schema = "flowershop")
public class Preorderdetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PreorderdetailID", nullable = false)
    private Integer preorderdetailid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PreorderID", nullable = false)
    private Preorder preorderID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flower_sizeid", nullable = false)
    private FlowerSize flowerSize;

    @Column(name = "Quantity", nullable = false)
    private Integer quantity;

    @Column(name = "Paid", precision = 50, scale = 2)
    private BigDecimal paid;

    @Column(name = "Price", nullable = false, precision = 50, scale = 2)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;
}


