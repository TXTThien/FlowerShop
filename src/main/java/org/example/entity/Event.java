package org.example.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.entity.enums.Status;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "Event")
@Table(name = "event", schema = "flowershop")
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventid", nullable = false)
    private Integer id;

    @Column(name="eventname", nullable = false)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Column(name = "color")
    private String color;

    @Column(name = "start", nullable = false)
    private LocalDateTime start;

    @Column(name = "end", nullable = false)
    private LocalDateTime end;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status")
    protected Status status;

    @Column(name = "is_manual", nullable = false)
    private boolean is_manual;
}
