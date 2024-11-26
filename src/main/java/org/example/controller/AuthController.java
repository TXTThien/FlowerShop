package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8000", allowCredentials = "true")
public class AuthController {

    @GetMapping("/login")
    public ResponseEntity<String> loginPage() {
        return ResponseEntity.status(HttpStatus.FOUND)
                .header("Location", "http://localhost:8000/login")
                .build();
    }


}