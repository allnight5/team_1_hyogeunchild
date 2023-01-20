package com.sparta.team_1_hyogeunchild.persistence.repository;

import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndStoreName(Long id, String storeName);
    Page<Order> findAllByStoreName(String userName, Pageable pageable);
}
