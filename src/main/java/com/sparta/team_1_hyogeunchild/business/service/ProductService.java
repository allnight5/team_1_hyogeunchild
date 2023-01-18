package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.ProductRequestDto;
import com.sparta.team_1_hyogeunchild.business.dto.ProductResponseDto;
import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.ProductRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();

        for (Product product : products) {
            ProductResponseDto productResponseDto = new ProductResponseDto(product);
            productResponseDtoList.add(productResponseDto);
        }

        return productResponseDtoList;
    }

//    @Transactional
//    public List<ProductResponseDto> getAllProducts(){
//        List<User> users = userRepository.findAllByRole(UserRoleEnum.SELLER);
//
//        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
//
//
//
//        for (User user : users) {
//            List<Product> productList = productRepository.findAllByUsersUsername(user.getUsername());
//            for (Product product : productList) {
//                ProductResponseDto responseDto = new ProductResponseDto(product, user);
//                productResponseDtoList.add(responseDto);
//            }
//        }
//        return productResponseDtoList;
//    }
//    // 확인해보자.

    @Transactional
    public List<ProductResponseDto> getAllProducts(){
        List<User> users = userRepository.findAllByRole(UserRoleEnum.SELLER);

        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        List<Product> productList = productRepository.findAll();
        for (Product product : productList) {
            ProductResponseDto productResponseDto = new ProductResponseDto(product);
        }

        return productResponseDtoList;
    }
    // 확인해보자.

    @Transactional
    public String uploadProduct(ProductRequestDto requestDto, String userName) {

        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        Product product = productRepository.save(new Product(requestDto, user));

        return "등록 완료";
    }

    @Transactional
    public ProductResponseDto updateProduct(ProductRequestDto requestDto, String userName, Long id){
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        Product product = productRepository.findByIdAndUsersId(id, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("상품이 존재하지 않습니다.")
        );

        product.update(requestDto, user);

        return new ProductResponseDto(product);
    }

    @Transactional
    public String deleteProduct(Long id, String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        Product product = productRepository.findByIdAndUsersId(id, user.getId()).orElseThrow(
                () -> new IllegalArgumentException("상품이 존재하지 않습니다.")
        );
        productRepository.deleteById(product.getId());

        return "삭제완료";
    }


}