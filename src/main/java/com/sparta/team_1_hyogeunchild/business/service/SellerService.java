package com.sparta.team_1_hyogeunchild.business.service;

import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import com.sparta.team_1_hyogeunchild.persistence.repository.OrderRepository;
import com.sparta.team_1_hyogeunchild.persistence.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public List<Product> getProducts(Long userId) {
        return productRepository.findAllByUserId(userId);
        // userId랑 연관관계가 없어서 가져올 수가 없다.
    }

    public List<Order> getOrders(Long userId){
        return orderRepository.findAllByUserId(userId);
    }
}
