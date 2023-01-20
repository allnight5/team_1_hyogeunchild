package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.SellerProfileResponseDto;
import com.sparta.team_1_hyogeunchild.persistence.entity.Category;
import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.CategoryRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import static com.sparta.team_1_hyogeunchild.enums.MessageEnum.UPDATE_CATEGORY_SUCCESS;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;
    //1.카테고리 생성
    @Transactional
    public String updateCategory(List<String> categorys, User user){
        List<Category> categoryList = new ArrayList<>();
        for(String category : categorys){
            Category updateCategory= new Category(category, user.getNickName(), user);
            categoryList.add(updateCategory);
        }
        categoryRepository.saveAll(categoryList);
        //성공했다는 메시지 발송
        return UPDATE_CATEGORY_SUCCESS.getMessage();
        //카테고리 유저 닉네임기준으로 조회할때 seller면 seller.getNickName()변경 필요
    }
    //2.판매자 프로필 조회
    @Transactional
    public SellerProfileResponseDto getProfileSeller(User user, Long sellerId){
        Seller seller = sellerRepository.findByIdAndUsername(sellerId, user.getUsername()).orElseThrow(
                ()-> new IllegalArgumentException("올바른 유저 번호를 입력 해주세요.")
        );

        //localhost:8080/seller/profile
        List<Category> categoryList = categoryRepository.findAllByNickName(user.getNickName());
        return new SellerProfileResponseDto(
                seller.getNickName(),
                seller.getStoreName(),
                seller.getImage(),
                seller.getIntroduce(),
                categoryList
        );
    }
}
