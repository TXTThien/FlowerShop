package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "CommentType")
@Table(name = "Commenttype", schema = "flowershop")
public class CommentType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CommenttypeID", nullable = false)
    private Integer commenttypeID;

    @Column(name = "Commenttypename", length = 100, nullable = false)
    private String commenttypename;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

}
