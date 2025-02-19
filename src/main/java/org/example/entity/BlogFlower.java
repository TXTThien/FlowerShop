package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity(name = "BlogFlower")
@Table(name = "blogflower", schema = "flowershop")
public class BlogFlower {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "flowerblogid", nullable = false)
    private Integer flowerblogid;

    @ManyToOne
    @JoinColumn(name = "flowerid", nullable = false)
    private Flower flower;

    @ManyToOne
    @JoinColumn(name = "blogid", nullable = false)
    private Blog blog;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    protected Status status;
}
