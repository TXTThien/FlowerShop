package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PreorderList {
    private int id;
    private String flower;
    private String size;
    private int flowersizeid;
    private int number;
}
