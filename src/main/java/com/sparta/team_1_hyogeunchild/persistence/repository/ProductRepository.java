package com.sparta.team_1_hyogeunchild.persistence.repository;

import com.sparta.team_1_hyogeunchild.persistence.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByUsername(String username);
    Optional<Product> findByIdAndUsername(Long id, String username);
    Page<Product> findAll(Pageable pageable);
    Page<Product> findByUsername(Pageable pageable, String username);
    void deleteById(Long id);
    @Modifying(clearAutomatically = true)
    @Query("delete from product p where p.username = :username")
    void deleteAllByUsername(@Param("username") String username);



    // 판매자가 드리겠습니다!! < 이러면, what if 구매자가 구매 능력이 없어보이고, 좀 별로여보여. 매칭 취소하고싶어!!
    // 테이블을 하나 더 만들어서 주문 내용이 이 테이블로 들어가게 하자!
    // 그 후에, 판매자는 이 테이블 안에 쌓여있는 구매자들의 요청들 중에 원하는 것을 골라 승인 해준다. << 요청폼 리스트 아닌가여
    // 그렇게 되면 테이블은 delete 쿼리를 날려서 없애고, 실제 주문이 이뤄지도록 한다.

    // 중간 테이블 엔티티 만들어서 사용해볼까? 라는 얘기 중이었다.

    // -> 자바 ORM JPA 를 쓰는데, ORM 이라는게 자바에 있는 클래스 / SQL 의 테이블
}