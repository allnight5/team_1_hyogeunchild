package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Promote;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PromoteUserResponseDto {
    private String username;
    private String password;
    private String storeName;

    public PromoteUserResponseDto(Promote promote){
        this.username = promote.getUsername();
        this.password = promote.getPassword();
        this.storeName = promote.getStoreName();
    }

}
