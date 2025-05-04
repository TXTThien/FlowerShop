package org.example.entity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Discount")
@Table(name = "discount", schema = "flowershop")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DiscountID", nullable = false)
    private Integer discountID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CategoryID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Category categoryID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TypeID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PurposeID")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Purpose purpose;

    @Column (name = "discountcode", nullable = false)
    private String discountcode;

    @Column(name = "Discountpercent", precision = 5, scale = 2)
    private BigDecimal discountPercent;

    @Column(name = "start_date", nullable = false)
    private LocalDateTime startDate;

    @Column(name = "end_date", nullable = false)
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    protected Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "accountid")
    private Account account;


}