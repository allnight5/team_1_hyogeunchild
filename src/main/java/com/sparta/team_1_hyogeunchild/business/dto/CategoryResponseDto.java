package com.sparta.team_1_hyogeunchild.business.dto;

import com.sparta.team_1_hyogeunchild.persistence.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CategoryResponseDto {
    private String tag;

    private CategoryResponseDto(Category category) {
        this.tag = category.getTag();
    }
    public static CategoryResponseDto from(Category category){
        return new CategoryResponseDto(category);
    }
}
