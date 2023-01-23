package com.sparta.team_1_hyogeunchild.persistence.entity;

import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import com.sparta.team_1_hyogeunchild.presentation.dto.SignUpRequestDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn
@Entity(name = "users")
public class User extends Timestaped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "User_ID")
    private Long id;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column
    private String image;
    @Column(nullable = false)
    private String nickName;
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRoleEnum role;

    public User(String username, String password, UserRoleEnum role, String nickName){
        this.username =username;
        this.password = password;
        this.role = role;
        this.nickName = nickName;
    }
    public void changeProfile(String nickName, String image){
        this.nickName = nickName;
        this.image =image;
    }
}
