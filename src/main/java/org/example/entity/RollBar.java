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
@Entity(name = "RollBar")
@Table(name = "rollbar", schema = "flowershop")
public class RollBar {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rollbarid", nullable = false)
    private Integer id;

    @Column(name = "days", nullable = false)
    private Integer days;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "color")
    private String color;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    protected Status status;
}
