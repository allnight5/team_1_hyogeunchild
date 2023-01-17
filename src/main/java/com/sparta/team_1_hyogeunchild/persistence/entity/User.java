package com.sparta.team_1_hyogeunchild.persistence.entity;

import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import com.sparta.team_1_hyogeunchild.presentation.dto.PromoteRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID")
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;
    @Column
    private String storeName;
    public User(String username, String password, UserRoleEnum role){
        this.username =username;
        this.password = password;
        this.role = role;
    }
    public void promote(String storeName, UserRoleEnum role) {
        this.storeName = storeName;
        this.role = role;
    }
}
