package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Condition;
import org.example.entity.enums.CustomCondition;
import org.example.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "Customize")
@Table(name = "customize", schema = "flowershop")
public class Customize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customid", nullable = false)
    private Integer customID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountid",nullable = false)
    private Account accountID;

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    protected Status status;

    @Column(name = "total", precision = 50, scale = 2)
    private BigDecimal totalAmount;

    @Column(name = "address", nullable = false)
    private String deliveryAddress;

    @Column(name = "phone",length = 15, nullable = false)
    private String phoneNumber;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "note")
    private String note;

    @Column(name = "image",length = 1000)
    private String image;

    @Column(name = "description",nullable = false)
    private String description;

    @Column(name = "sentence")
    private String sentence;

    @Column(name = "purpose",nullable = false)
    private String purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "`condition`", nullable = false)
    protected CustomCondition condition;

    @Column(name = "number", nullable = false)
    private Integer number;
}
