package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Promote;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class AdminPromoteShowResponseDto {
    private final Long id;
    private final String username;
    private final String storeName;
    public AdminPromoteShowResponseDto(Promote promote){
        this.id = promote.getId();
        this.username = promote.getUsername();
        this.storeName = promote.getStoreName();
    }
}
