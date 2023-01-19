package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.OrderMessageResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.OrderRequestDto;
import com.sparta.team_1_hyogeunchild.business.dto.OrderResponseDto;
import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.OrderRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.ProductRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
import com.sparta.team_1_hyogeunchild.presentation.dto.OrderAvailableRequestDto;
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

    // 2. buyer 가 seller 에게 order 넣기
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
                .available(requestDto.getAvailable())
                .build();

        orderRepository.save(order);
        return OrderResponseDto.from(order);
    }

    // 3. 고객 요청 처리응답
    @Transactional
    public OrderMessageResponseDto availableOrder(OrderAvailableRequestDto requestDto, String storeName){
//        Order order = orderRepository.findByUserUsernameAndId(username, requestDto.getId());
//        Order order = orderRepository.findById(requestDto.getId()).orElseThrow(
//                ()-> new IllegalArgumentException("주문번호가 존재하지 않습니다.")
//        );
        Order order = orderRepository.findByIdAndStoreName(requestDto.getId(), storeName).orElseThrow(
                ()-> new IllegalArgumentException("주문번호가 존재하지 않습니다.")
        );
        Long available = requestDto.getAvailable();
        order.orderAvailable(available);
        if(available == 1){
            return new OrderMessageResponseDto("정상적으로 주문이 완료되었습니다.");
        }else if(available == 2){
            return new OrderMessageResponseDto("주문을 취소하였습니다.");
        }else{
            return new OrderMessageResponseDto("주문을 확인 할 수 없습니다.");
        }
    }
}