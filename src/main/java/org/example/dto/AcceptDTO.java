package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AcceptDTO {
    private String imageurl;
    private BigDecimal total;
    private List<CustomizeDetailDTO> customizeDetailDTOList;
}
