package org.example.auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.entity.enums.Role;
import org.example.entity.enums.Status;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestForAdmin {
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
    @NotNull(message = "Status must not be null")
    private Status status;
    @NotNull(message = "Role must not be null")
    private Role role;
}