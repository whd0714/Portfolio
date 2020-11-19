package com.portfolio.domain;

import com.portfolio.controller.member.UserMember;
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
public class Release {

    @Id @GeneratedValue
    private Long id;

    @ManyToMany
    private List<Member> members = new ArrayList<>();

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, unique = true)
    private String modelNo;

    private boolean useImage;

    @Lob @Basic(fetch = FetchType.EAGER)
    private String image;

    @ManyToMany
    private List<Keyword> keywords = new ArrayList<>();

    @ManyToMany
    private List<Store> stores = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Shoe> shoes = new ArrayList<>();

    private LocalDate releaseTime;

    private LocalDate writeTime;

    private boolean open;

    public void addMember(Member member) {
        this.members.add(member);
    }

    public boolean postRelease(){
        return releaseTime.isAfter(LocalDate.now());
    }

    public boolean deadLine(){
        return releaseTime.isBefore(LocalDate.now());
    }

    public boolean todayRelease(){
        return releaseTime.isEqual(LocalDate.now());
    }

    public void openSetting() {
        this.open = true;
        this.writeTime = LocalDate.now();
    }
}
