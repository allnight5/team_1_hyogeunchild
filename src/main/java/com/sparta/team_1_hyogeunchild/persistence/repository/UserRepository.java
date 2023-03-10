package com.sparta.team_1_hyogeunchild.persistence.repository;

import com.sparta.team_1_hyogeunchild.enums.UserRoleEnum;
import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import com.sparta.team_1_hyogeunchild.persistence.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
//    Page<User> findByUsername(String username, Pageable pageable);

    Page<User> findByRole(UserRoleEnum role, Pageable pageable);

//    @Query(name = "select u from users u where role like 'sel%'")
//    List<User> findByUsersRole(String role);

    void deleteByUsername(String username);
    // 전체 판매자 목록 조회
}