package org.example.service.securityService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.example.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.concurrent.atomic.AtomicReference;
import org.example.repository.*;

@Service
@RequiredArgsConstructor
public class GetIDAccountFromAuthService {
    private final org.example.service.securityService.JwtService jwtService;
    @Autowired
    private AccountRepository accountRepository;

    public int common() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        int idAccount = -1;

        if (auth != null && auth.isAuthenticated() && !(auth instanceof AnonymousAuthenticationToken)) {
            var account = accountRepository.findByUsername(auth.getName());
            if (account.isPresent()) {
                idAccount = account.get().getAccountID();
            }
        }
        if (auth != null) {
            System.out.println("Tình trạng xác thực: " + auth.getName() + ", authenticated: " + auth.isAuthenticated());
        }


        return idAccount;
    }

}