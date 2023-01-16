package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.business.dto.OrderResponseDto;
import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import com.sparta.team_1_hyogeunchild.persistence.repository.OrderRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.UserRepository;
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
    @Transactional
    public List<OrderResponseDto> getOrders(String userName) {
        User user = userRepository.findByUsername(userName).orElseThrow(
                () -> new IllegalArgumentException("사용자가 존재하지 않습니다")
        );

        // Controller 에서 인증객체로 들어온 사람 이 여기의 user 입니다.
        // Buyer -> order 날린다(Seller 에게) -> Seller 가 이 Order를 식별할 수 있어야 한다. (나에게 온 것인지)

        //그래서 Order를 Craete 할 때, 원하는 가게의 이름 / 주인장 id 라던지 해서 리퀘스를 날리면 우리가 받아서 체크해야 한다.

        List<Order> orders = orderRepository.findAllByUserName(user.getUsername());

        return orders.stream().map(OrderResponseDto::from).collect(Collectors.toList());
    }

}
