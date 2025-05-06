package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entity.AccountGift;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountGiftDTO {
    private AccountGift accountGift;
    private String disfor;
}
