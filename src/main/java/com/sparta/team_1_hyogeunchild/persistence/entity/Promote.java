package com.sparta.team_1_hyogeunchild.persistence.entity;

import com.sparta.team_1_hyogeunchild.presentation.dto.PromoteUserRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Promote extends Timestaped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Promote_ID")
    private Long id;

    @Column(nullable = false)
    private String storeName;
    @Column(nullable = false)
    private String password;
    @ManyToOne
    private User user;
    private String newName;
    private String introduce;
    @Column
    private Integer isPromoted;
    private String nickName;

    // 바이어가 이미 주문한 오더는, 바이어로써 확인 가능하고
    // 셀러 승급하면 다른 아이디임. 다른 유저임 < 한 유저가 아이디 두개 가지는거임.
    public Promote(PromoteUserRequestDto requestDto, User user){
        this.storeName = requestDto.getStoreName();
        this.user = user;
        this.password = requestDto.getPassword();
        this.introduce = requestDto.getIntroduce();
        this.newName = requestDto.getNewName();
        this.nickName = requestDto.getNickName();
    }

    public void isPromoted(Integer isPromoted) {
        this.isPromoted = isPromoted;
    }
}