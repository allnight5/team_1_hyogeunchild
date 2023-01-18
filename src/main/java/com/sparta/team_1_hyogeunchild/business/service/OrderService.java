package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.OrderRequestDto;
import com.sparta.team_1_hyogeunchild.business.dto.OrderResponseDto;
import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.OrderRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.ProductRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class OrderService {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
//    @Transactional
//    public List<OrderResponseDto> getOrders(String username) {
//        User user = userRepository.findByUsername(username).orElseThrow(
//                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
//        );
//
//        List<Order> orders = orderRepository.findAllByStoreName(user.getStoreName());
//
//        return orders.stream().map(OrderResponseDto::from).collect(Collectors.toList());
//    }

    // 1. 고객 요청 목록 조회
    @Transactional
    public List<OrderResponseDto> getOrders(int page, User user){
        Pageable pageable = pageableSetting(page);
        Page<Order> orders = orderRepository.findAllByStoreName(user.getStoreName(), pageable);
        return orders.stream().map(OrderResponseDto::new).collect(Collectors.toList());
    }
    public Pageable pageableSetting(int page){
        Sort.Direction direction = Sort.Direction.ASC;

        Sort sort = Sort.by(direction, "id");
        return PageRequest.of(page-1, 5, sort);
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, String username, Long productId) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 없습니다")
        );

        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("찾으시는 상품이 없습니다.")
        );

        Order order = Order.builder()
                .amount(requestDto.getAmount())
                .totalPrice(requestDto.getAmount()*product.getPrice())
                .product(product)
                .user(user)
                .storeName(product.getStoreName())
                .build();

        orderRepository.save(order);
        return OrderResponseDto.from(order);
    }
}