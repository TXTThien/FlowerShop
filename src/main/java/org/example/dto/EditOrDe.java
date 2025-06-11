package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EditOrDe {
    private String name;
    private String address;
    private String phoneNumber;
    private String note;
    private LocalDateTime start;
    private String condition;
    private String deliverper;
}
