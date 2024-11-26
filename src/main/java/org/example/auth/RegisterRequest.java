package org.example.auth;


import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.Type;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Size(min = 2, message = "name has at least 2 characters")
    private String name;
    @Size(min = 2, max = 45, message = "username has at least 2 characters")
    private String username;
    @Size(min = 8, message = "password has at least 8 characters")
    private String password;
    @Size(min = 2, message = "email has at least 2 characters")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", message = "Invalid email format")
    private String email;
    @Size(min = 8, message = "phone has at least 8 characters")
    private String phoneNumber;
    @Size(min = 2, message = "address has at least 2 characters")
    private String address;
    private Type type;
    private BigDecimal consume;
    private String avatar;

}