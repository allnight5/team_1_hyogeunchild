package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.ProductRequestDto;
import com.sparta.team_1_hyogeunchild.business.dto.ProductResponseDto;
import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.ProductRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.SellerRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    @Transactional
    public List<ProductResponseDto> getProducts(String userName) {
        List<Product> products = productRepository.findAllByUsername(userName);

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            ProductResponseDto productResponseDto = new ProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

    // 1. 내 상품 페이징
    @Transactional
    public List<ProductResponseDto> getAllProducts(int pageChoice, String username){
       Page<Product> products = productRepository.findByUsername(pageableProductsSetting(pageChoice), username);
       return products.stream().map(ProductResponseDto::new).collect(Collectors.toList());
    }

    private Pageable pageableProductsSetting(int pageChoice) {
        Sort.Direction direction = Sort.Direction.DESC;
        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(pageChoice - 1, 10, sort);
    }

    @Transactional
    public String uploadProduct(ProductRequestDto requestDto, String userName) {

        Seller seller = sellerRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다.")
        );

        Product product = Product.builder()
                .productName(requestDto.getProductName())
                .storeName(seller.getStoreName())
                .price(requestDto.getPrice())
                .amount(requestDto.getAmount())
                .username(seller.getUsername())
                .build();

        productRepository.save(product);

        return "등록 완료";
    }

    @Transactional
    public ProductResponseDto updateProduct(ProductRequestDto requestDto, String userName, Long id) {
        Seller seller = sellerRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        Product product = productRepository.findByIdAndUsername(id, seller.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("상품이 존재하지 않습니다.")
        );

        product.update(requestDto);

        return new ProductResponseDto(product);
    }

    @Transactional
    public String deleteProduct(Long id, String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        Product product = productRepository.findByIdAndUsername(id, user.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("상품이 존재하지 않습니다.")
        );
        productRepository.deleteById(product.getId());

        return "삭제완료";
    }


}