package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.OrderResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.ProductRequestDto;
import com.sparta.team_1_hyogeunchild.business.dto.ProductResponseDto;
import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.OrderRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.ProductRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    @Transactional
    public List<ProductResponseDto> getProducts(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        List<Product> products = productRepository.findAllByUsersUsername(user.getUsername());

        return products.stream().map(ProductResponseDto::from).collect(Collectors.toList());
    }


    @Transactional
    public String uploadProduct(ProductRequestDto requestDto, String userName) {

        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        Product product = productRepository.save(new Product(requestDto, user));

        return "등록 완료";
    }

    @Transactional
    public ProductResponseDto updateProduct(ProductRequestDto requestDto, String userName){
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        Product product = productRepository.findByUsersId(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("상품이 존재하지 않습니다.")
        );

        product.update(requestDto);

        return ProductResponseDto.from(product);
    }

    @Transactional
    public String deleteProduct(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        Product product = productRepository.findByUsersId(user.getId()).orElseThrow(
                () -> new IllegalArgumentException("상품이 존재하지 않습니다.")
        );
        productRepository.deleteById(product.getId());

        return "삭제완료";
    }
}