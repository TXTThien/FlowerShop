package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;
import org.example.entity.enums.TypeGift;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Gift")
@Table(name = "gift", schema = "flowershop")
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "giftid", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "discountpercent", length = 5, scale = 2)
    private BigDecimal discountpercent;

    @Enumerated(EnumType.STRING)
    @Column(name = "typegift",nullable = false)
    protected TypeGift typeGift;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    protected Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "flowersizeid")
    private FlowerSize flowersizeid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "rollbarid")
    private RollBar rollbarid;

    @Column(name = "percent", length = 4, scale = 2)
    private BigDecimal percent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "CategoryID")
    private Category categoryID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "TypeID")
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "PurposeID")
    private Purpose purpose;

    @Column(name= "timeend")
    private LocalDateTime timeEnd;
}
