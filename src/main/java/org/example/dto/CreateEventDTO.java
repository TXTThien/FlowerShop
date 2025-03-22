package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.enums.Status;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventDTO {
    private String eventName;
    private String description;
    private String color;
    private LocalDateTime start;
    private LocalDateTime end;
    private List<EventFlowerDTO> eventFlowerDTOS;
}
