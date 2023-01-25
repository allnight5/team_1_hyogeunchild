package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.MessageResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.SellerProfileResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.SellerResponseDto;
import com.sparta.team_1_hyogeunchild.persistence.entity.Category;
import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.CategoryRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.OrderRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.ProductRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.SellerRepository;
import com.sparta.team_1_hyogeunchild.presentation.dto.CategoryRequestDto;
import com.sparta.team_1_hyogeunchild.presentation.dto.SellerProfileRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

import static com.sparta.team_1_hyogeunchild.enums.MessageEnum.*;

@Service
@RequiredArgsConstructor
public class SellerService {
    private final CategoryRepository categoryRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;
    private final FileService fileService;
    private final OrderRepository orderRepository;
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

    //2. 카테고리 삭제
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

    //3. 판매자 삭제
    @Transactional
    public MessageResponseDto deleteSeller(User user) {
        Seller seller = sellerRepository.findByUsername(user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("그런 사람 없습니다")
        );
        // 쿼리가 긴 이유 -> 조인해서 날라가니까. left outer join 입니다.
        categoryRepository.deleteAllBySellerId(seller.getId());
        productRepository.deleteAllByUsername(seller.getUsername());
        orderRepository.deleteAllByUserId(seller.getId());
        // 위에놈들은 내가 직접 쿼리짜서 하나만 날라가지만
        sellerRepository.delete(seller);
        // 이놈은 JPA 에서 상속 구현할 때의 한계. 자식 객체 조회/삭제등에 쿼리 두번씩 날라감.(큰 단점은 아니라고 하긴 함~)
        return new MessageResponseDto("삭제 완료");
    }
    //4. 판매자 프로필수정
    @Transactional
    public MessageResponseDto changeSellerProfile(SellerProfileRequestDto requestDto, MultipartFile file, Seller seller){
        Seller seller1 = sellerRepository.findByUsername(seller.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("인터넷 문제로 수정이 실패하였습니다.")
        );
        fileService.upload(file, seller1.getUsername());
        seller1.updateSeller(seller1.getUsername(), requestDto.getNickName(),
                requestDto.getIntroduce(), requestDto.getStoreName());
        return new MessageResponseDto("수정 완료");
    }
    //5. 판매자 자기 프로필 조회
    @Transactional
    public SellerProfileResponseDto getSellerProfile(String username){
        Seller seller1 = sellerRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("인터넷 문제로 조회가 실패하였습니다.")
        );
        return SellerProfileResponseDto.from(seller1);
    }
}
