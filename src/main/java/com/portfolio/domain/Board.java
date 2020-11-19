package com.portfolio.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    @Lob
    private String description;

    private LocalDate atWriting;

    @ManyToMany
    private List<Member> members = new ArrayList<>();

    @ManyToMany
    private List<Comment> comments = new ArrayList<>();

    public void settingTime() {
        this.atWriting = LocalDate.now();
    }

    public void addMember(Member member){
        members.add(member);
    }
}
