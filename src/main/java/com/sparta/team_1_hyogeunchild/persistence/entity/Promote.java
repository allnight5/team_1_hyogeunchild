package com.sparta.team_1_hyogeunchild.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Promote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Promote_ID")
    private Long id;

    @Column(nullable = false)
    private String storeName;
}