package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "FlowerSize")
@Table(name = "flowersize", schema = "flowershop")
public class FlowerSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flower_sizeid", nullable = false)
    private Integer flowerSizeID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FlowerID", nullable = false)
    private Flower flower;

    @Column(name = "size_name",length = 10, nullable = false)
    private String sizeName;

    @Column(name = "Length", nullable = false)
    private float length;

    @Column(name = "High", nullable = false)
    private float high;

    @Column(name = "Width", nullable = false)
    private float width;

    @Column(name = "Weight", nullable = false)
    private float weight;

    @Column(name = "Stock", nullable = false)
    private Integer stock;

    @Column(name = "Price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "Cost", nullable = false, precision = 10, scale = 2)
    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;
}
