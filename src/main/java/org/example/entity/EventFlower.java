package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.type.Decimal;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "EventFlower")
@Table(name = "eventflower", schema = "flowershop")
public class EventFlower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventflowerid", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "eventid")
    private Event event;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flowersizeid")
    private FlowerSize flowerSize;

    @Column(name = "saleoff", nullable = false, precision = 4, scale = 2)
    private BigDecimal saleoff;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    protected Status status;
}
