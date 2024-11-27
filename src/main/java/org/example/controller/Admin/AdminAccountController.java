package org.example.controller.Admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.auth.RegisterRequestForAdmin;
import org.example.entity.Account;

import org.example.repository.AccountRepository;
import org.example.service.Impl.AccountServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/admin/account")
@RequiredArgsConstructor
public class AdminAccountController {
    private final AccountRepository accountRepository;
    private final AccountServiceImpl accountService;
    private final PasswordEncoder passwordEncoder;


    @GetMapping("")
    public ResponseEntity<?> getListAccount(){
        List<Account> accounts = accountRepository.findAll();
        if (!accounts.isEmpty()) {
            Map<String, Object> response = new HashMap<>();
            response.put("accounts", accounts);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No products found");
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createAccount(@Valid @RequestBody RegisterRequestForAdmin registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }
        if (accountRepository.findAccountByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Username " + registerRequest.getUsername() + " already exists.");
        }

        Account account = Account.builder()
                .name(registerRequest.getName())
                .username(registerRequest.getUsername())
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .phoneNumber(registerRequest.getPhoneNumber())
                .address(registerRequest.getAddress())
                .status(registerRequest.getStatus())
                .role(registerRequest.getRole())
                .build();
        try {
            Account newAccount = accountRepository.save(account);
            return ResponseEntity.status(HttpStatus.CREATED).body(newAccount);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error occurred while creating account.");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAccount(@PathVariable("id") int id, @Valid @RequestBody RegisterRequestForAdmin registerRequest, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = bindingResult.getFieldErrors()
                    .stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
        }

        Account updatedAccount = accountService.updateAccount(id, registerRequest);

        if (updatedAccount == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        return ResponseEntity.ok(updatedAccount);
    }

    @DeleteMapping("/softdelete/{id}")
    public ResponseEntity<?> softdeleteAccount(@PathVariable("id") int id) {
        Account existingAccount = accountRepository.findById(id).orElse(null);
        if (existingAccount != null) {
            accountService.deleteAccount(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/harddelete/{id}")
    public ResponseEntity<?> harddeleteAccount(@PathVariable("id") int id) {
        Account existingAccount = accountRepository.findById(id).orElse(null);

        if (existingAccount != null) {
            accountService.harddeleteAccount(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}