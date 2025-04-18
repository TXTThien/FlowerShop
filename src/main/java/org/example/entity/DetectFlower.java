package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "DetectFlower")
@Table(name = "detectflower", schema = "flowershop")
public class DetectFlower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddetectflower", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "iddect")
    private Detect detect;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idflower")
    private Flower flower;
}
