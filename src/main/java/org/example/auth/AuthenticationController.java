package org.example.auth;


import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.controller.User.UserPrebuyController;
import org.example.entity.Account;
import org.example.entity.Type;
import org.example.entity.enums.Role;
import org.example.entity.enums.Status;
import org.example.repository.AccountRepository;
import org.example.service.Impl.AccountServiceImpl;
import org.example.service.Impl.EmailServiceImpl;
import org.example.service.securityService.GetIDAccountFromAuthService;
import org.example.service.securityService.JwtService;
import org.example.service.token.TokenRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.Collections;
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
    private final TokenRepository tokenRepository;
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;
    private final UserPrebuyController userPrebuyController;
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
        userPrebuyController.notifyCartUpdate(-1,userPrebuyController.cartCount(-1));
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
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }
    @PostMapping("/viagoogle")
    public ResponseEntity<AuthenticationResponse> verifyGoogleToken(@RequestBody GoogleTokenRequest googleTokenRequest) {
        Optional<Account> account = accountService.findAccountByUsername(googleTokenRequest.getName()+" "+googleTokenRequest.getEmail());
        System.out.println("Thông tin đã tới: " );
        Type type = new Type();
        type.setTypeID(1);
        if (account.isEmpty()) {
            Account viaAccount = Account.builder()
                    .username(googleTokenRequest.getName()+" "+googleTokenRequest.getEmail())
                    .email(googleTokenRequest.getEmail())
                    .name(googleTokenRequest.getName())
                    .consume(BigDecimal.ZERO) // Giá trị mặc định
                    .type(type)
                    .avatar(googleTokenRequest.getPicture())
                    .role(Role.user)
                    .status(Status.ENABLE)
                    .build();
            accountService.save(viaAccount);
        }
        return ResponseEntity.ok(auth(googleTokenRequest.getName()+" "+googleTokenRequest.getEmail()));
    }

    public AuthenticationResponse auth (String username) {
        System.out.println("Here");

        var user = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        System.out.println("Tài khoản: " + user.getUsername() + ", ID: " + user.getAccountID());

        Integer idAccount = user.getAccountID();
        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        authenticationService.revokeAllUserTokens(user);
        authenticationService.saveUserToken(user, jwtToken);
        AuthenticationResponse authResponse = AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .idAccount(idAccount)
                .build();

        System.out.println("AuthenticationResponse:");
        System.out.println("ID Account: " + authResponse.getIdAccount());
        System.out.println("Access Token: " + authResponse.getAccessToken());
        System.out.println("Refresh Token: " + authResponse.getRefreshToken());

        return authResponse;
    }

}
