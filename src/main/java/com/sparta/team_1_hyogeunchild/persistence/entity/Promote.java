package com.sparta.team_1_hyogeunchild.persistence.entity;

import com.sparta.team_1_hyogeunchild.presentation.dto.PromoteUserRequestDto;
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
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    public Promote(PromoteUserRequestDto requestDto, String username){
        this.storeName = requestDto.getStoreName();
        this.username = username;
        this.password = requestDto.getPassword();

    }


}