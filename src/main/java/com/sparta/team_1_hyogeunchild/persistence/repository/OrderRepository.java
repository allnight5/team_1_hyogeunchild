package com.sparta.team_1_hyogeunchild.persistence.repository;

import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByUserUsername(String userName);
//    List<Order> findAllByStoreName(String storeName);

    Optional<Order> findByIdAndStoreName(Long id, String storeName);
    Order findByUserUsernameAndId(String username, Long id);
    Page<Order> findAllByStoreName(String storeName, Pageable pageable);
}
