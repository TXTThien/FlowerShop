package org.example.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePassword {
    private String newpass;
    private String curpass;
}