package org.example.entity;

import lombok.*;
import jakarta.persistence.*;
import org.example.entity.enums.Status;

import java.io.Serializable;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Banner")
@Table(name = "banner", schema = "flowershop")
public class Banner implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BannerID", nullable = false)
    private Integer bannerID;

    @Column(name = "Image", nullable = false)
    private String image;


    @ManyToOne
    @JoinColumn (name = "link_to_flowerid")
    private Flower flower;

    @ManyToOne
    @JoinColumn (name = "link_to_newsid")
    private News news;

    @ManyToOne
    @JoinColumn (name = "link_to_categoryid")
    private Category category;

    @ManyToOne
    @JoinColumn (name = "link_to_purposeid")
    private Purpose purpose;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;;
}