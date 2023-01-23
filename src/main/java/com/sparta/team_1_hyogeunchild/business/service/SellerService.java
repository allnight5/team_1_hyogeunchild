package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.MessageResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.SellerProfileResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.SellerResponseDto;
import com.sparta.team_1_hyogeunchild.persistence.entity.Category;
import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.CategoryRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.ProductRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.SellerRepository;
import com.sparta.team_1_hyogeunchild.presentation.dto.CategoryRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.team_1_hyogeunchild.enums.MessageEnum.*;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    //1.카테고리 생성
    @Transactional
    public MessageResponseDto createCategory(CategoryRequestDto requestDto, Seller seller){
        Category category = Category.builder()
                .seller(seller)
                .tag(requestDto.getTag())
                .build();
        categoryRepository.save(category);
        return new MessageResponseDto(CREATE_CATEGORY_SUCCESS.getMessage());
    }

    @Transactional
    public MessageResponseDto deleteCategory(Long id, Seller seller){
        Category category = categoryRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("특정하신 카테고리가 없습니다.")
        );
        if(category.getSeller().getId().equals(seller.getId())) {
            categoryRepository.delete(category);
        } else {
            throw new IllegalArgumentException("자신의 태그만 삭제할 수 있습니다.");
        }
        return new MessageResponseDto(DELETE_CATEGORY_SUCCESS.getMessage());
    }

    @Transactional
    public MessageResponseDto deleteSeller(User user) {
        Seller seller = sellerRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("그런 사람 없습니다")
        );
        categoryRepository.deleteAllBySellerId(seller.getId());
        productRepository.deleteAllByUsername(seller.getUsername());
        sellerRepository.delete(seller);
        return new MessageResponseDto("삭제 완료");
    }
}
