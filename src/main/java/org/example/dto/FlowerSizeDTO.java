package org.example.dto;

import com.google.type.Decimal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.FlowerSize;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FlowerSizeDTO {
    private int flowerSizeID;
    private String sizeName;
    private String url;
    private BigDecimal price;
}
