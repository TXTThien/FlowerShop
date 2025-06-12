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
@Entity(name = "CustomDetail")
@Table(name = "customdetail", schema = "flowershop")
public class CustomDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "detailid", nullable = false)
    private Integer detailID;

    @ManyToOne
    @JoinColumn(name = "flowerid")
    private FlowerCustom flower;

    @ManyToOne
    @JoinColumn(name = "otherid")
    private OtherCustom other;

    @Column(name = "number", nullable = false)
    private Integer number;

    @ManyToOne
    @JoinColumn(name = "customid")
    private Customize customize;
}
