package org.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.example.entity.enums.Role;
import org.example.entity.Type;
import org.example.entity.enums.Status;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"orders", "hibernateLazyInitializer", "handler"})
@Entity(name = "Account")
@Table(name = "account", schema = "flowershop")
public class Account implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AccountID", nullable = false)
    private Integer accountID;

    @Column(name = "Username", length = 50, nullable = false)
    private String username;

    @Column(name = "Password", nullable = false)
    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name="Role", nullable = false)
    protected Role role;

    @Column(name = "Phonenumber", length = 15, nullable = false)
    private String phoneNumber;

    @Column(name = "Address", length = 500, nullable = false)
    private String address;

    @Column(name = "Fullname", length = 100, nullable = false)
    private String name;

    @Column(name = "Email", length = 100, nullable = false)
    private String email;

    @Column(name = "Consume", precision = 10, scale = 2)
    private BigDecimal consume;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Type")
    private Type type;

    @Column(name = "Avatar", length = 1000, nullable = false)
    private String avatar;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", nullable = false)
    protected Status status;

    @Column(name = "otp", length = 6)
    private String otp;

    @Column(name = "otp_expiry")
    private LocalDateTime otpExpiry;

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return this.status == Status.ENABLE;
    }

    @OneToMany(mappedBy = "accountID")
    @ToString.Exclude
    private Set<Order> orders = new LinkedHashSet<>();

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }
}