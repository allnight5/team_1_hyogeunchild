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
    private String comment;

    public Promote(PromoteUserRequestDto requestDto, User user){
        this.storeName = requestDto.getStoreName();
        this.user = user;
        this.password = requestDto.getPassword();

    }


}