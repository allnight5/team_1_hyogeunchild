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
    private String nickName;
    @Column(nullable = false)
    private String category;
    @Column(nullable = false)
    private String username;
    @ManyToOne
    @JoinColumn(name="Seller_ID", nullable = false)
    private User user;

    public Category(String category,String nickName, User user){
        this.nickName = nickName;
        this.category = category;
        this.username = user.getUsername();
        this.user = user;

    }
}
