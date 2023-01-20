package com.sparta.team_1_hyogeunchild.persistence.repository;

import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import com.sparta.team_1_hyogeunchild.persistence.entity.Seller;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    Optional<Seller> findByUsername(String username);
    Optional<Seller> findByStoreName(String storeName);
    Page<Seller> findAll(Pageable pageable);
}
