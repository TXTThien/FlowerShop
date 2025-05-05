package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.enums.Status;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RollBarInfoDTOStaff {
    private int days;
    private String name;
    private String color;
    private Status status;
    private List<GiftInfoDTOStaff> giftInfoDTOStaffList;
}
