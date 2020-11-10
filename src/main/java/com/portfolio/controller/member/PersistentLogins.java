package com.portfolio.controller.member;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "persistent_logins")
public class PersistentLogins {

    @Id @GeneratedValue
    @Column(nullable = false, length = 64)
    private String series;

    @Column(nullable = false, length = 64)
    private String username;

    @Column(nullable = false, length = 64)
    private String token;

    @Column(name = "last_used", nullable = false)
    private LocalDateTime lastUsed;


}
