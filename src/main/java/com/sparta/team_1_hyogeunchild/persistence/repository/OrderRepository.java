package com.sparta.team_1_hyogeunchild.persistence.repository;

import com.sparta.team_1_hyogeunchild.persistence.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndStoreName(Long id, String storeName);
    Page<Order> findAllByStoreName(String userName, Pageable pageable);
    @Modifying(clearAutomatically = true)
    @Query("delete from orders o where o.user.id = :userId")
    void deleteAllByUserId(@Param("userId") Long userId);
}
