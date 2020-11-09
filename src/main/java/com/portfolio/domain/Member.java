package com.portfolio.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter @Setter
@Builder @EqualsAndHashCode(of = "id")
@AllArgsConstructor @NoArgsConstructor
public class Member {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String memberId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String passwordConfirm;

    @Column(nullable = false)
    private String email;

    private int shoeSize;

    private String emailToken;

    private LocalDateTime joinAt;

    private boolean verifyEmail;

    private boolean WebRelease;

    private boolean EmailRelease;

    private boolean WebLocale;

    private boolean EmailLocale;

    public void generateToken() {
        this.emailToken = UUID.randomUUID().toString();
    }
}
