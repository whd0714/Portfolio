package com.portfolio.domain;

import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Comment {

    @Id @GeneratedValue
    private Long id;

    @Column(length = 200, nullable = false)
    private String reply;

    private LocalDate time;

}
