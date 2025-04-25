package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "OrderDeliveryDetail")
@Table(name = "`orderdeliverydetail`", schema = "flowershop")
public class OrderDeliveryDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "orderdeliverydetailid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "orderdeliveryid", nullable = false)
    private OrderDelivery orderDelivery;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flowersizeid", nullable = false)
    private FlowerSize flowerSize;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;
}
