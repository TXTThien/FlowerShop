package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostCustom {
    private String name;
    private String phone;
    private String note;
    private String address;
    private String description;
    private String purpose;
    private String sentence;
    private Integer number;
}
