package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.OrderResponseDto;
import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.OrderRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.ProductRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
import com.sparta.team_1_hyogeunchild.presentation.dto.OrderRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
@RequiredArgsConstructor
@Service
public class OrderService {
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    @Transactional
    public List<OrderResponseDto> getOrders(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        // Controller 에서 인증객체로 들어온 사람 이 여기의 user 입니다.
        // Buyer -> order 날린다(Seller 에게) -> Seller 가 이 Order를 식별할 수 있어야 한다. (나에게 온 것인지)

        //그래서 Order를 Craete 할 때, 원하는 가게의 이름 / 주인장 id 라던지 해서 리퀘스를 날리면 우리가 받아서 체크해야 한다.

        List<Order> orders = orderRepository.findAllByUserUsername(user.getUsername());

        return orders.stream().map(OrderResponseDto::from).collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDto createOrder(OrderRequestDto requestDto, Long productId) {

        //Product List가 있다. 그걸 조회 할 수 있다.(현재) , 유저는 product를 가지고 있지 않다.
        Product product = productRepository.findById(productId).orElseThrow(
                () -> new IllegalArgumentException("상품이 없습니다.")
        );

        // product 에 대해서 주문하면, order를 테이블에 추가해야 한다.
        Order order = Order.builder()
                .totalPrice(product.getPrice()* requestDto.getAmount())
                .product(product)
                .amount(requestDto.getAmount())
                .build();

        orderRepository.save(order);

        return OrderResponseDto.from(order);
    }
}
