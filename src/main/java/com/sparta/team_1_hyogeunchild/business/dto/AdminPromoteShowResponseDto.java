package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Promote;
import lombok.Getter;

@Getter
public class AdminPromoteShowResponseDto {
    private final Long id;
    private final String username;
    private final String storeName;
    public AdminPromoteShowResponseDto(Promote promote){
        this.id = promote.getId();
        this.username = promote.getUser().getUsername();
        this.storeName = promote.getStoreName();
    }
}
