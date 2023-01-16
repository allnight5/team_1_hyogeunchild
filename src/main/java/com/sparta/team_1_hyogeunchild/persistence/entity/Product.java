package com.sparta.team_1_hyogeunchild.persistence.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Value;

import javax.persistence.*;

@Entity(name="product")
@NoArgsConstructor
@Getter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name ="Product_ID")
    private Long id;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private Long amount;
    // 상품 양(남은 개수)
    @Column(nullable = false)
    private Long price;
    @Column(nullable = false)
    private String storeName;
    
    // Product 엔티티가 필요하다.
    // 상품목록조회는 Product 로 하면 된다.(Pagination 해서)
    // 상품이름, 가격, 갯수

    // Profile 엔티티가 필요하다 < 유저 회원가입시에는 받지 않기로 하자~
    // 조회 때 변경가능

    // 판매자입니까? 구매자입니까? -> 추후에 만들 판매자 승급 로직 < 적용해서 판별. @PreAuthorize(hasRole = "seller")
    // 첫 가입때는 그냥 유저로 하고, 추후에 승급 좋아요~!

    // 구매목록이 요청폼임
    //



    // 도매상과 소매상을 연결시킨다. 라고 봅시다
    // 판매자(도매) -> 구매자(소매) 실제 금액적 거래는 신경쓰지 않아요. 대신 Product는 개당 개수가 들어가야해요.
    // 구매자 가 구매 의사가 있으면 알아 연락해서 사세요.

    // 한사람이 고양이모래를산다 3명 1명이 더산다.

    // 우리가 만드는 시스템은 matching 시스템.
    // 상품을 직접 관리하는 게 아니라, 판매자와 구매자를 연결 시켜주는 역할. (예를 들어 G마켓 관리자페이지? 정도)
    // 처음 가입하면 일반 유저(buyer) 로 와서, 판매자 신청을 할 수 있다.
    // 판매자는 자신의 상품을 등록/수정/삭제 할 수 있다. -> Product 엔티티가 필요하다.
    // 유저 요청폼을 처리한다. <- 무엇을? 상품 구매 요청
    // 펫 용품 팔기로 했다. 유기농 모래 간식 등등 파는 사람들과 매칭 해야한다.
}
