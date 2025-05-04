package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RollBarInfoDTO {
    private int id;
    private int days;
    private String name;
    private String color;
    private List<GiftInfoDTO> giftInfoDTOList;
}
