package com.sparta.team_1_hyogeunchild.persistence.entity;

import lombok.AccessLevel;
import lombok.Builder;
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
    private String tag; // ex) 고양이 / 고양이모래 등등 ~
    @ManyToOne
    @JoinColumn(name="Seller_ID", nullable = false)
    private Seller seller;
    @Builder
    public Category(Seller seller, String tag){
        this.tag = tag;
        this.seller = seller;
    }
}
