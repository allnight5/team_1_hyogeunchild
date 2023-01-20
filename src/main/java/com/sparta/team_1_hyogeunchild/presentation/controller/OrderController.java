package com.sparta.team_1_hyogeunchild.presentation.controller;

import com.sparta.team_1_hyogeunchild.business.dto.MessageResponseDto;
import com.sparta.team_1_hyogeunchild.business.dto.OrderRequestDto;
import com.sparta.team_1_hyogeunchild.business.dto.OrderResponseDto;
import com.sparta.team_1_hyogeunchild.business.service.OrderService;
import com.sparta.team_1_hyogeunchild.presentation.dto.OrderAvailableRequestDto;
import com.sparta.team_1_hyogeunchild.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    @GetMapping
    public List<OrderResponseDto> getOrders(@PageableDefault Pageable pageable, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return orderService.getOrders(pageable.getPageNumber(), userDetails.getUser());
    }

    @PostMapping("/{productId}")
    @PreAuthorize("hasRole('BUYER')")
    public OrderResponseDto createOrder(@RequestBody OrderRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long productId){
        return orderService.createOrder(requestDto, userDetails.getUsername(), productId);
    }

    @PutMapping("/available")
    @PreAuthorize("hasRole('SELLER')")
    public MessageResponseDto availableOrder(@RequestBody OrderAvailableRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails){
        return orderService.availableOrder(requestDto, userDetails.getUser().getUsername());
    }

}