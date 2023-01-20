package com.sparta.team_1_hyogeunchild.persistence.repository;

import com.sparta.team_1_hyogeunchild.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    List<Category> findByNickName(String nickName);

    List<Category> findAllByNickName(String nickName);

    Optional<Category> findByIdAndSellerUsername(Long id, String sellerName);
}
