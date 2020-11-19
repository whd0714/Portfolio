package com.portfolio.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
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
    private String email;

    @ManyToMany
    private List<Keyword> keywords = new ArrayList<>();

    @ManyToMany
    private List<Comment> comments = new ArrayList<>();

    private int shoeSize;

    private String emailToken;

    private LocalDateTime joinAt;

    private boolean verifyEmail;

    private boolean webRelease;

    private boolean emailRelease;

    private boolean webLocale;

    private boolean emailLocale;

    public void generateToken() {
        this.emailToken = UUID.randomUUID().toString();
    }

    public boolean checkedToken(String token){
        return this.emailToken.equals(token);
    }

    public void successCheckedEmailSettings() {
        this.verifyEmail = true;
        this.joinAt = LocalDateTime.now();
    }
}
