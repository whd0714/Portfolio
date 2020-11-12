package com.portfolio.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Shoe {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String brand;

    @Column(nullable = false, unique = true)
    private String modelNo;

    @ManyToMany
    private List<Release> releases = new ArrayList<>();

}
