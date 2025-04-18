package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Detect")
@Table(name = "detect", schema = "flowershop")
public class Detect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "iddetect", nullable = false)
    private Integer id;

    @Column(name = "flowerdetect",nullable = false)
    private String flowerdetect;

    @Column(name = "vietnamname")
    private String vietnamname;

    @Column(name = "imageurl",length = 10000)
    private String imageurl;

    @Column(name = "origin")
    private String origin;

    @Column(name = "timebloom")
    private String timebloom;

    @Column(name = "characteristic",length = 500)
    private String characteristic;

    @Column(name = "flowerlanguage",length = 500)
    private String flowerlanguage;

    @Column(name = "bonus",length = 500)
    private String bonus;

    @Column(name = "uses",length = 500)
    private String uses;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;;
}
