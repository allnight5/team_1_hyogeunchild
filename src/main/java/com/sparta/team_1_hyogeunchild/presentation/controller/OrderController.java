package com.sparta.team_1_hyogeunchild.presentation.controller;

import com.sparta.team_1_hyogeunchild.business.dto.OrderResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.UserResponseDto;
import com.sparta.team_1_hyogeunchild.business.service.OrderService;
import com.sparta.team_1_hyogeunchild.presentation.dto.OrderRequestDto;
import com.sparta.team_1_hyogeunchild.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderResponseDto> getOrders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.getOrders(userDetails.getUsername());
    }

    @PostMapping
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto, @PathVariable Long productId) {
        return orderService.createOrder(requestDto, productId);
    }
}
