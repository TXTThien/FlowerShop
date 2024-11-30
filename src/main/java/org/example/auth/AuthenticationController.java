package org.example.auth;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.example.repository.AccountRepository;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.Impl.EmailServiceImpl;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final GetIDAccountFromAuthService getIDAccountFromAuthService;
    private final AccountServiceImpl accountService;
    private final AccountRepository accountRepository;
    private final EmailServiceImpl emailService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request, BindingResult result) {
        Map<String, Object> log = new HashMap<>();
        log.put("type", "success");
        log.put("message", "success");
        Map<String, Object> errors = new HashMap<>();


        // Username already exists
        var existingUserName = accountRepository.findByUsername(request.getUsername());
        if (existingUserName.isPresent()) {
            result.rejectValue("username", "error.username", "There is already a user with this username");
        }

        // Validation errors
        if (result.hasErrors()) {
            log.put("type", "error");
            log.put("message", "Validation failed");
            for (Object object : result.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    errors.put(fieldError.getField(), fieldError.getDefaultMessage());
                }
            }
            log.put("errors", errors);
            return ResponseEntity.badRequest().body(log);
        }

        // Register the user
        AuthenticationResponse authResponse = service.register(request);
        return ResponseEntity.ok(authResponse);
    }



    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @GetMapping("/account")
        public ResponseEntity<?> getAccountInfo(@RequestParam Integer accountID) {
        Map<String, Object> response = new HashMap<>();
        Account account = accountService.getAccountById(accountID);

        if (account != null) {
            response.put("id", account.getAccountID());
            response.put("name", account.getName());
            response.put("password", account.getPassword());
            response.put("address", account.getAddress());
            response.put("phonenumber", account.getPhoneNumber());
            response.put("email", account.getEmail());
            response.put("role", account.getRole());
            response.put("consume", account.getConsume());
            response.put("avatar", account.getAvatar());
            response.put("typeName", account.getType().getTypeName());


            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account not found");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Đã đăng xuất thành công");
    }
    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String username = forgotPasswordRequest.getUsername();
        Optional<Account> userOpt = accountService.findAccountByUsername(username);

        if (userOpt.isPresent()) {
            Account user = userOpt.get();
            String email = accountRepository.findEmailByUsername(username);

            if (email != null && !email.isEmpty()) {
                String otp = emailService.generateRandomCode(); // Tạo mã OTP

                // Lưu mã OTP và thời gian hết hạn
                user.setOtp(otp);
                user.setOtpExpiry(LocalDateTime.now().plusMinutes(10)); // OTP hết hạn sau 10 phút
                accountService.save(user);

                // Gửi email chứa mã OTP
                String subject = "[FlowerShop] Quên mật khẩu";
                String text = "OTP của bạn là: " + otp + "\n" +
                        "Mã sẽ hết hạn sau 10 phút.";
                emailService.sendSimpleMessage(email, subject, text);

                return ResponseEntity.ok("OTP đã được gửi tới email của bạn");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Tài khoản này chưa có email liên kết");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tài khoản chưa được đăng ký");
        }
    }
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody VerifyOtpRequest verifyOtpRequest) {

        String username = verifyOtpRequest.getUsername();
        String otp = verifyOtpRequest.getOtp();
        String newPassword = verifyOtpRequest.getNewPassword();

        Optional<Account> userOpt = accountService.findAccountByUsername(username);
        if (userOpt.isPresent()) {
            Account user = userOpt.get();

            if (user.getOtp().equals(otp) && user.getOtpExpiry().isAfter(LocalDateTime.now())) {
                String encodedPassword = passwordEncoder.encode(newPassword);
                user.setPassword(encodedPassword);

                user.setOtp(null);
                user.setOtpExpiry(null);

                accountService.save(user);

                return ResponseEntity.ok("Mật khẩu đã được đặt lại thành công.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body("Mã OTP không hợp lệ hoặc đã hết hạn.");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Tài khoản chưa được đăng ký");
        }
    }

    @PostMapping("/refresh-token")
    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        service.refreshToken(request, response);
    }


}