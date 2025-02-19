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
@Entity(name = "BlogImage")
@Table(name = "blogimage", schema = "flowershop")
public class BlogImage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "imageblogid", nullable = false)
    private Integer imageblogid;

    @ManyToOne
    @JoinColumn(name = "blogid", nullable = false)
    private Blog blog;

    @Column(name = "imageurl",length = 1000,nullable = false)
    private String image;

    @ManyToOne
    @JoinColumn(name = "commentid", nullable = false)
    private BlogComment blogComment;

}
