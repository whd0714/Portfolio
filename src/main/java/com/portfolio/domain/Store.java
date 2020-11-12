package com.portfolio.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
public class Store {

    @Id @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String storeName;

    @Override
    public String toString(){
        return String.format("%s",storeName);
    }

}
