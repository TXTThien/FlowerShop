package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Preorderable;
import org.example.entity.enums.Status;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "FlowerSize")
@Table(name = "flowersize", schema = "flowershop")
public class FlowerSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flower_sizeid", nullable = false)
    private Integer flowerSizeID;

    @ManyToOne
    @JoinColumn(name = "FlowerID", nullable = false)
    private Flower flower;

    @Column(name = "size_name",length = 100, nullable = false)
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

    @Column(name = "Price", nullable = false, precision = 50, scale = 2)
    private BigDecimal price;

    @Column(name = "Cost", nullable = false, precision = 50, scale = 2)
    private BigDecimal cost;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

    @Enumerated(EnumType.STRING)
    @Column(name = "Preorderable", nullable = false)
    protected Preorderable preorderable;

    @JsonIgnore
    @OneToMany(mappedBy = "flowerSize", fetch = FetchType.LAZY)
    private List<Cart> carts;


}
