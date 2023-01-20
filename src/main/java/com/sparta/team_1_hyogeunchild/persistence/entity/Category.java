package com.sparta.team_1_hyogeunchild.persistence.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Category_ID")
    private Long id;
    @Column(nullable = false)
    private String tag;
    @ManyToOne
    @JoinColumn(name="Seller_ID", nullable = false)
    private Seller seller;

    public Category(Seller seller){
        this.seller = seller;
    }
}
